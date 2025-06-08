package program;

import java.util.*;

import enums.*;
import manager.RecipeManager;
import model.Ingredient;
import model.Recipe;
import model.RecipeStep;

public class Program {
	
	private static void initializeSampleData(RecipeManager manager) {
        // Create sample ingredients
        Ingredient pancakeFlour = new Ingredient("Flour", 0.2, UnitMeasure.KG);
        Ingredient pancakeSugar = new Ingredient("Sugar", 0.1, UnitMeasure.KG);
        Ingredient cakeFlour = new Ingredient("Flour", 200, UnitMeasure.G);
        Ingredient cakeSugar = new Ingredient("Sugar", 100, UnitMeasure.G);
        Ingredient eggs = new Ingredient("Eggs", 2, UnitMeasure.UNIT);
        Ingredient milk = new Ingredient("Milk", 300, UnitMeasure.ML);
        Ingredient chocolate = new Ingredient("Chocolate", 150, UnitMeasure.G);
        Ingredient butter = new Ingredient("Butter", 50, UnitMeasure.G);
        
        // Create sample steps
        List<RecipeStep> pancakeSteps = new ArrayList<>();
        pancakeSteps.add(new RecipeStep("Mix dry ingredients", 5));
        pancakeSteps.add(new RecipeStep("Add wet ingredients and mix", 5));
        pancakeSteps.add(new RecipeStep("Cook on medium heat", 10));
        
        List<RecipeStep> cakeSteps = new ArrayList<>();
        cakeSteps.add(new RecipeStep("Preheat oven to 180C", 10));
        cakeSteps.add(new RecipeStep("Mix all ingredients", 15));
        cakeSteps.add(new RecipeStep("Bake for 30 minutes", 30));
        
        // Create sample recipes
        Recipe pancake = new Recipe("Pancakes", "Classic breakfast pancakes", 
                Arrays.asList(pancakeFlour, pancakeSugar, eggs, milk), pancakeSteps, 
                RecipeCategory.BREAKFAST, DietaryPreference.VEGETARIAN);
        
        Recipe chocolateCake = new Recipe("Chocolate Cake", "Delicious chocolate cake", 
                Arrays.asList(cakeFlour, cakeSugar, eggs, chocolate, butter), cakeSteps, 
                RecipeCategory.DESSERT, DietaryPreference.VEGETARIAN);
        
        // Add to manager
        manager.add(pancake);
        manager.add(chocolateCake);
    }
	
    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();
        
        // Initialize sample data
        initializeSampleData(manager);
        
        // Create and start the UI
        ConsoleUI ui = new ConsoleUI(manager);
        ui.start();
    }
}