package model;

public class RecipeStep {
	// Attributes
    private String description;
    private int durationInMinutes;
    
	// Constructor Method
    public RecipeStep(String description, int durationInMinutes) {
        this.description = description;
        this.durationInMinutes = durationInMinutes;
    }
    
    // Getters
    public String getDescription() { return description; }
    public int getDurationInMinutes() { return durationInMinutes; }
    
    @Override
    public String toString() {
        return description + " (" + durationInMinutes + " minutes)";
    }

}