package manager;

import java.util.*;
import model.Ingredient;
import util.InputValidator;
import enums.UnitMeasure;

public class IngredientManager extends RecipeComponentManager<Ingredient> {
    
    public IngredientManager(List<Ingredient> items, InputValidator validator, Scanner scanner) {
        super(items, validator, scanner, "Ingredient");
    }

    @Override
    protected void displayCurrentItems() {
        System.out.println("\nCurrent Ingredients:");
        if (items.isEmpty()) {
            System.out.println("No ingredients added yet.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, formatItem(items.get(i)));
        }
    }

    @Override
    protected Ingredient createItem(String prompt) {
        System.out.println(prompt);
        String name = validator.getStringInput(scanner, "Enter ingredient name: ");
        
        if (name.isEmpty()) {
            return null;
        }

        double quantity = validator.getDoubleInput(scanner, "Enter quantity: ");
        UnitMeasure unit = validator.enumSelect(scanner, UnitMeasure.class, "Select unit of measure (number): ");
        
        return new Ingredient(name, quantity, unit);
    }

    @Override
    protected Ingredient modifyItem(String prompt, Ingredient existingItem) {
        System.out.println(prompt);
        System.out.print("Enter new name (or press Enter to keep '" + existingItem.getName() + "'): ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter new quantity (or -1 to keep " + existingItem.getQuantity() + "): ");
        double quantity = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        System.out.print("Change unit measure? (y/n): ");
        UnitMeasure unit = existingItem.getUnitMeasure();
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            unit = validator.enumSelect(scanner, UnitMeasure.class, "Select unit of measure (number): ");
        }
        
        return new Ingredient(
            name.isEmpty() ? existingItem.getName() : name,
            quantity < 0 ? existingItem.getQuantity() : quantity,
            unit
        );
    }
    
    public static List<Ingredient> getIngredientsFromUser(InputValidator validator, Scanner scanner) {
        List<Ingredient> ingredients = new ArrayList<>();
        System.out.println("\nEnter ingredients (leave ingredient name empty when done):");
        
        while (true) {
            String name = validator.getStringInput(scanner, "\nIngredient name: ");
            if (name.isEmpty()) {
                break;
            }
            
            double quantity = validator.getDoubleInput(scanner, "Quantity: ");
            UnitMeasure unit = validator.enumSelect(scanner, UnitMeasure.class, "Select unit of measure (number): ");
            ingredients.add(new Ingredient(name, quantity, unit));
        }
        
        return ingredients;
    }

    @Override
    protected String formatItem(Ingredient item) {
        return String.format("%s (%.1f %s)", 
            item.getName(), 
            item.getQuantity(), 
            item.getUnitMeasure().getSymbol());
    }
} 