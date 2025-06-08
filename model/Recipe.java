package model;

import java.util.*;

import enums.DietaryPreference;
import enums.RecipeCategory;

public class Recipe {
	// Attributes
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private List<RecipeStep> steps;
    private RecipeCategory category;
    private DietaryPreference dietaryPreference;
    
    // Constructor Method
    public Recipe(String name, String description, List<Ingredient> ingredients, 
    		List<RecipeStep> steps, RecipeCategory category, DietaryPreference dietaryPreference) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.category = category;
	    this.dietaryPreference = dietaryPreference;
    }
	    
	// Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Ingredient> getIngredients() { return ingredients;}
    public List<RecipeStep> getSteps() { return steps; }
    public RecipeCategory getCategory() { return category; }
    public DietaryPreference getDietaryPreference() { return dietaryPreference; }
	
    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }
    public void setSteps(List<RecipeStep> steps) { this.steps = steps; }
    public void setCategory(RecipeCategory category) { this.category = category; }
    public void setDietaryPref(DietaryPreference dietaryPreference) { this.dietaryPreference = dietaryPreference; }
	    
    public int calculateTotalTime() {
        int totalTime = 0;
        for (RecipeStep step : steps) {
            totalTime += step.getDurationInMinutes();
        }
        return totalTime;
    }
    
    @Override
    public String toString() {
        return "Recipe: " + name + "\n" +
               "Description: " + description + "\n" +
               "Category: " + category + "\n" +
               "Dietary: " + dietaryPreference + "\n" +
               "Ingredients: " + ingredients + "\n" +
               "Steps: " + steps + "\n" +
               "Total Time: " + calculateTotalTime() + " minutes";
    }
}