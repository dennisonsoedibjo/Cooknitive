package program;

import java.time.LocalDate;
import java.util.*;

import enums.*;
import manager.RecipeManager;
import manager.ShoppingListManager;
import manager.IngredientManager;
import manager.RecipeStepManager;
import model.Ingredient;
import model.Recipe;
import model.RecipeStep;
import util.InputValidator;
import util.ShoppingListGenerator;
import util.RecipeFormatter;

public class ConsoleUI {
    private final Scanner scanner;
    private final RecipeManager recipeManager;
    private final InputValidator validator;
    private final MenuUI menu;
    private final ShoppingListManager shoppingListManager;

    public ConsoleUI(RecipeManager recipeManager) {
        this.scanner = new Scanner(System.in);
        this.recipeManager = recipeManager;
        this.shoppingListManager = new ShoppingListManager();
        this.validator = new InputValidator();
        this.menu = new MenuUI();
    }

    public void start() {
    	while (true) {
    		menu.displayMainMenu();
            int choice = validator.getIntInput(scanner, "");
            switch (choice) {
                case 1 -> addRecipe();
                case 2 -> viewRecipe();
                case 3 -> {
                    String recipeName = validator.getStringInput(scanner, "\nEnter recipe name to update: ");
                    updateRecipe(recipeName);
                }
                case 4 -> deleteRecipe();
                case 5 -> searchByIngredient();
                case 6 -> generateShoppingList();
                case 7 -> viewShoppingList();
                case 8 -> searchByDietaryPreference();
                case 9 -> searchByCategory();
                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
    	}
    }

    private void addRecipe() {
        String name = validator.getStringInput(scanner, "\nEnter recipe name: ");
        
        Recipe existing = recipeManager.getByName(name);
        if (existing != null) {
            if (validator.confirmAction(scanner, "Recipe already exists. Would you like to update it?")) {
                updateRecipe(name);
            } else {
                System.out.println("Recipe creation cancelled.");
            }
            return;
        }

        String description = validator.getStringInput(scanner, "Enter description: ");
        List<Ingredient> ingredients = IngredientManager.getIngredientsFromUser(validator, scanner);
        List<RecipeStep> steps = RecipeStepManager.getStepsFromUser(validator, scanner);
        RecipeCategory category = validator.enumSelect(scanner, RecipeCategory.class, "Select recipe category (number): ");
        DietaryPreference preference = validator.enumSelect(scanner, DietaryPreference.class, "Select dietary preference (number): ");

        Recipe recipe = new Recipe(name, description, ingredients, steps, category, preference);
        if (recipeManager.add(recipe)) {
            System.out.println("Recipe added successfully!");
        } else {
            System.out.println("Error: A recipe with this name already exists.");
        }
    }
    
    private void viewRecipe() {
    	System.out.println("\nRecipes Preview:");
        recipeManager.displayAsTable(recipeManager.getAllRecipes());
        String name = validator.getStringInput(scanner, "\nEnter recipe name to view: ");
        
        Recipe recipe = recipeManager.getByName(name);
        if (recipe == null) {
            System.out.println("Recipe not found.");
            return;
        }
        
        RecipeFormatter formatter = new RecipeFormatter();
        String formatted = formatter.format(recipe);
        System.out.print("\n");
        System.out.println(formatted);
    }

    private void updateRecipe(String name) {
        Recipe existing = recipeManager.getByName(name);
        if (existing == null) {
            System.out.println("Recipe not found.");
            return;
        }

        menu.displayUpdateMenu();
        String action = scanner.nextLine().trim().toUpperCase();
        
        switch (action) {
            case "A":
            	String newName = validator.getStringInput(scanner, "Enter new name (leave blank to keep current): ");
            	
            	Recipe duplicate = recipeManager.getByName(newName);
                if (duplicate != null) {
                    System.out.println("Recipe with that name already exists.");
                    return;
                }
                
                if (recipeManager.updateRecipeName(existing, newName)) {
                    System.out.println("Recipe name updated successfully!");
                } else {
                    System.out.println("Error: A recipe with this name already exists.");
                }
                break;
            case "B":
                System.out.println("\nCurrent description: " + existing.getDescription());
                String newDesc = validator.getStringInput(scanner, "Enter new description (leave blank to keep current): ");
                if (!newDesc.isEmpty()) { existing.setDescription(newDesc); }
                System.out.println("Recipe description updated successfully!");
                break;
            case "C":
            	IngredientManager ingredientManager = new IngredientManager(new ArrayList<>(existing.getIngredients()), validator, scanner);
                ingredientManager.manage(menu);
                if (validator.confirmAction(scanner, "Would you like to save changes?")) { existing.setIngredients(ingredientManager.getItems()); }
                break;
            case "D":
            	RecipeStepManager stepManager = new RecipeStepManager(new ArrayList<>(existing.getSteps()), validator, scanner);
                stepManager.manage(menu);
                if (validator.confirmAction(scanner, "Would you like to save changes?")) { existing.setSteps(stepManager.getItems()); }
                break;
            case "E":
                RecipeCategory newCategory = validator.enumSelect(scanner, RecipeCategory.class, "Select new category: ");
                if (newCategory != null) { existing.setCategory(newCategory); }
                break;
            case "F":
                DietaryPreference newPreference = validator.enumSelect(scanner, DietaryPreference.class, "Select new dietary preference: ");
                if (newPreference != null) { existing.setDietaryPref(newPreference); }
                break;
            case "G":
                System.out.println("Update cancelled.");
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private void deleteRecipe() {
    	System.out.println("\nRecipes Preview:");
        recipeManager.displayAsTable(recipeManager.getAllRecipes());
        String name = validator.getStringInput(scanner, "\nEnter recipe name to delete (leave blank to cancel): ");
        
        if (name.isEmpty()) {
            System.out.println("Recipe deletion was cancelled.");
            return;
        }
        
        if (recipeManager.delete(name)) {
            System.out.println("Recipe deleted successfully!");
        } else {
            System.out.println("Recipe not found.");
        }
    }

    private void searchByIngredient() {
        List<Ingredient> ingredientsSearchList = new ArrayList<>();
        IngredientManager ingredientManager = new IngredientManager(ingredientsSearchList, validator, scanner);
        ingredientManager.manage(menu);
        
        if (validator.confirmAction(scanner, "Would you like to search for recipes based on this list?")) {
            if (!ingredientsSearchList.isEmpty()) {
                List<Recipe> results = recipeManager.searchByIngredients(ingredientsSearchList);
                if (results.isEmpty()) {
                    System.out.println("No recipes found with those ingredients.");
                } else {
                    System.out.println("\nFound recipes:");
                    recipeManager.displayAsTable(results);
                }
            }
        }
    }

    private void generateShoppingList() {
        ShoppingListGenerator generator = new ShoppingListGenerator(shoppingListManager, validator);
        generator.manageShoppingList(scanner, recipeManager);
    }

    private void viewShoppingList() {
        LocalDate date = validator.getDateFromUser(scanner, "\nEnter date for shopping list (YYYY-MM-DD) or leave empty to cancel: ");
        if (date == null) {
            return;
        }
        if(shoppingListManager.getListsByDate(date).isEmpty()) {
            System.out.println("No shopping lists found for that date.");
            return;
        }
        shoppingListManager.displayListsForDate(date);
    }
    
    private void searchByDietaryPreference() {
        DietaryPreference preference = validator.enumSelect(scanner, DietaryPreference.class, "Select dietary preference to view (number): ");
        
        if (preference != null) {
            List<Recipe> results = recipeManager.searchByDietaryPreference(preference);
            if (results.isEmpty()) {
                System.out.println("No recipes found for that dietary preference.");
            } else {
                System.out.println("\nRecipes in " + preference + " dietary preference:");
                recipeManager.displayAsTable(results);
            }
        }
    }

    private void searchByCategory() {
        RecipeCategory category = validator.enumSelect(scanner, RecipeCategory.class, "Select category to view (number): ");
        
        if (category != null) {
            List<Recipe> results = recipeManager.getRecipesByCategory(category);
            if (results.isEmpty()) {
                System.out.println("No recipes found in this category.");
            } else {
                System.out.println("\nRecipes in " + category + " category:");
                recipeManager.displayAsTable(results);
            }
        }
    }
} 