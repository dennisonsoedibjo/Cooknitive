package util;

import java.time.LocalDate;
import java.util.*;

import enums.UnitMeasure;
import manager.RecipeManager;
import model.Ingredient;
import model.Recipe;
import model.ShoppingList;
import program.MenuUI;
import manager.ShoppingListManager;

public class ShoppingListGenerator {
    private final ShoppingListManager manager;
    private final InputValidator validator;

    public ShoppingListGenerator(ShoppingListManager manager, InputValidator validator) {
        this.manager = manager;
        this.validator = validator;
    }

    public void manageShoppingList(Scanner scanner, RecipeManager recipeManager) {
        LocalDate date = validator.getDateFromUser(scanner, "\nEnter date for shopping list (YYYY-MM-DD) or leave empty to cancel: ");
        if (date == null) { return; }

        if (manager.hasListsForDate(date)) {
            System.out.println("\nWarning: Shopping lists already exist for this date.");
            if (!validator.confirmAction(scanner, "Would you like to continue and create another list?")) {
                return;
            }
        }

        String listName = validator.getStringInput(scanner, "Enter name for shopping list or leave empty to cancel: ");
        if (listName == null) { return; }
        
        for(ShoppingList list : manager.getListsByDate(date)) {
        	if(list.getName().equalsIgnoreCase(listName)) {
        		System.out.println("List with that name already exists for " + date + "! Starting over...");
        		return;
        	}
        }

        ShoppingList shoppingList = new ShoppingList(date, listName);
        generateList(scanner, recipeManager, shoppingList);

        if (!shoppingList.getItems().isEmpty() && 
            validator.confirmAction(scanner, "\nWould you like to save this shopping list?")) {
            if (!manager.add(shoppingList)) {
                System.out.println("Error: A shopping list with name '" + listName + 
                    "' already exists for date " + date);
            } else {
                System.out.println("Shopping list saved successfully!");
            }
        }
    }
    
    private void generateList(Scanner scanner, RecipeManager recipeManager, ShoppingList shoppingList) {
    	MenuUI menu = new MenuUI();
    	while (true) {
    		displayCurrentList(shoppingList);
            menu.displayShoppingMenu();
            String choice = scanner.nextLine().trim().toUpperCase();
            
            switch (choice) {
                case "A" -> addRecipesToList(scanner, recipeManager, shoppingList);
                case "B" -> addManualIngredient(scanner, shoppingList);
                case "C" -> {
                	String name = validator.getStringInput(scanner, "\nEnter ingredient name to modify or leave empty to cancel: ");
                    modifyIngredient(scanner, shoppingList, name);
                }
                case "D" -> deleteIngredient(scanner, shoppingList);
                case "E" -> shoppingList.clearItems();
                case "F" -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addRecipesToList(Scanner scanner, RecipeManager recipeManager, ShoppingList shoppingList) {
        recipeManager.displayAsTable(recipeManager.getAllRecipes());
        
        String recipeName = validator.getStringInput(scanner, "\nEnter recipe name or leave empty to cancel: ");
        
        if (!recipeName.isEmpty()) {
            Recipe recipe = recipeManager.getByName(recipeName);
            if (recipe == null) {
                System.out.println("Recipe not found.");
                return;
            }

            double servings = validator.getDoubleInput(scanner, "Enter number of servings: ");
            for (Ingredient ingredient : recipe.getIngredients()) {
                double quantity = ingredient.getQuantity() * servings;
                shoppingList.addItem(ingredient.getName(), quantity, ingredient.getUnitMeasure());
            }
            System.out.println("Recipe ingredients added to shopping list!");
        }
    }

    private void addManualIngredient(Scanner scanner, ShoppingList shoppingList) {
        String name = validator.getStringInput(scanner, "\nEnter ingredient name or leave empty to cancel: ");
        if (name.isEmpty()) { return; }

        Ingredient existingItem = shoppingList.getItem(name);
        if (existingItem != null) {
            System.out.printf("Ingredient already exists in the list with %.2f %s%n", 
                existingItem.getQuantity(), existingItem.getUnitMeasure());
            
            if (!validator.confirmAction(scanner, "Would you like to update this ingredient?")) {
                return;
            }
            
            modifyIngredient(scanner, shoppingList, name);
            return;
        }

        double quantity = validator.getDoubleInput(scanner, "Enter quantity: ");
        UnitMeasure unit = validator.enumSelect(scanner, UnitMeasure.class, "Select unit of measure (number): ");
        shoppingList.addItem(name, quantity, unit);
        System.out.println("Ingredient added successfully!");
    }

    private void modifyIngredient(Scanner scanner, ShoppingList shoppingList, String name) {
    	if (shoppingList.getItems().isEmpty() || name.isEmpty()) { return; }

    	Ingredient item = shoppingList.getItem(name);
        if (item == null) {
            System.out.println("Ingredient not found in the list.");
            return;
        }

        double quantity = validator.getDoubleInput(scanner, "Enter new quantity: ");
        UnitMeasure unit = validator.enumSelect(scanner, UnitMeasure.class, "Select unit of measure (number): ");
        shoppingList.updateItem(name, quantity, unit);
        System.out.println("Ingredient updated successfully!");
    }

    private void deleteIngredient(Scanner scanner, ShoppingList shoppingList) {
    	if (shoppingList.getItems().isEmpty()) { return; }

        String name = validator.getStringInput(scanner, "\nEnter ingredient name to delete or leave empty to cancel: ");
        if (!name.isEmpty()) {
            if (shoppingList.getItem(name) != null) {
                shoppingList.removeItem(name);
                System.out.println("Ingredient deleted successfully!");
            } else {
                System.out.println("Ingredient not found in the list.");
            }
        }
    }

    private void displayCurrentList(ShoppingList shoppingList) {
        List<Ingredient> items = shoppingList.getItemsSorted();
        if (items.isEmpty()) {
            System.out.println("\nShopping list is empty.");
            return;
        }

        System.out.println("\nCurrent Shopping List:");
        for (Ingredient item : items) {
            System.out.println("- " + item);
        }
    }
}