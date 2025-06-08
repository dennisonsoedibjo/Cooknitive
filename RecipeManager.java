package manager;

import java.util.*;

import enums.DietaryPreference;
import enums.RecipeCategory;
import model.Ingredient;
import model.Recipe;

public class RecipeManager extends EntityManager<Recipe> {
    
    public RecipeManager() {
        super();
    }
    
    @Override
    protected String getItemName(Recipe recipe) {
        return recipe.getName();
    }
        
    public boolean updateRecipeName(Recipe recipe, String newName) {
        if (newName.isEmpty()) {
            return true;
        }
        if (getByName(newName) != null) {
            return false;
        }
        recipe.setName(newName);
        return true;
    }
    
    // Keeping the old method for backward compatibility
    public List<Recipe> getAllRecipes() {
        return getAllItems();
    }
    
    public void displayAsTable(List<Recipe> recipesToDisplay) {
        if (recipesToDisplay.isEmpty()) {
            System.out.println("No recipes to display.");
            return;
        }
        
        System.out.println("+------------------+--------------------------------+-----------+---------------+");
        System.out.println("| Name             | Description                    | Category  | Dietary Pref. |");
        System.out.println("+------------------+--------------------------------+-----------+---------------+");
        
        for (Recipe recipe : recipesToDisplay) {
            String name = truncate(recipe.getName(), 16);
            String desc = truncate(recipe.getDescription(), 30);
            String category = truncate(recipe.getCategory().toString(), 9);
            String dietary = truncate(recipe.getDietaryPreference().toString(), 13);
            
            System.out.printf("| %-16s | %-30s | %-9s | %-13s |\n", 
                name, desc, category, dietary);
        }
        
        System.out.println("+------------------+--------------------------------+-----------+---------------+");
    }
    
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length-3) + "..." : str;
    }
    
    // Search operations
    public List<Recipe> searchByIngredients(List<Ingredient> ingredients) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : items) {
            if (containsAllIngredientsWithMaxQuantities(recipe, ingredients)) {
                result.add(recipe);
            }
        }
        return result;
    }

    private boolean containsAllIngredientsWithMaxQuantities(Recipe recipe, List<Ingredient> ingredients) {
        for (Ingredient searchIng : ingredients) {
            String searchName = searchIng.getName();
            double maxQty = searchIng.getQuantity();
            
            boolean found = false;
            for (Ingredient ing : recipe.getIngredients()) {
                if (ing.getName().equalsIgnoreCase(searchName) && 
                    (maxQty == 0 || ing.getQuantity() <= maxQty)) {
                    found = true;
                    break;
                }
            }
            
            if (!found) return false;
        }
        return true;
    }
    
    public List<Recipe> searchByDietaryPreference(DietaryPreference preference) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : items) {
            if (recipe.getDietaryPreference() == preference) {
                result.add(recipe);
            }
        }
        return result;
    }
    
    public List<Recipe> getRecipesByCategory(RecipeCategory category) {
        List<Recipe> result = new ArrayList<>();
        for (Recipe recipe : items) {
            if (recipe.getCategory() == category) {
                result.add(recipe);
            }
        }
        return result;
    }
}
