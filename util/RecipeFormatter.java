package util;

import model.Ingredient;
import model.Recipe;
import model.RecipeStep;

public class RecipeFormatter{
    public String format(Recipe recipe) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(recipe.getName()).append(" ===\n");
        sb.append(recipe.getDescription()).append("\n\n");
        sb.append("Category: ").append(recipe.getCategory()).append("\n");
        sb.append("Dietary: ").append(recipe.getDietaryPreference()).append("\n\n");
        
        sb.append("Ingredients:\n");
        for (Ingredient ingredient : recipe.getIngredients()) {
            sb.append("- ").append(ingredient).append("\n");
        }
        
        sb.append("\nSteps:\n");
        int stepNum = 1;
        for (RecipeStep step : recipe.getSteps()) {
            sb.append(stepNum++).append(". ").append(step.getDescription())
              .append(" (").append(step.getDurationInMinutes()).append(" mins)\n");
        }
        
        sb.append("\nTotal Time: ").append(recipe.calculateTotalTime()).append(" minutes\n");
        return sb.toString();
    }
}