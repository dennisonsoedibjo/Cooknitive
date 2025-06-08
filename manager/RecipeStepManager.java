package manager;

import java.util.*;
import model.RecipeStep;
import util.InputValidator;

public class RecipeStepManager extends RecipeComponentManager<RecipeStep> {
    
    public RecipeStepManager(List<RecipeStep> items, InputValidator validator, Scanner scanner) {
        super(items, validator, scanner, "Step");
    }

    @Override
    protected void displayCurrentItems() {
        System.out.println("\nCurrent Steps:");
        if (items.isEmpty()) {
            System.out.println("No steps added yet.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, formatItem(items.get(i)));
        }
    }

    @Override
    protected RecipeStep createItem(String prompt) {
        System.out.println(prompt);
        System.out.print("Enter step description: ");
        String description = scanner.nextLine().trim();
        
        if (description.isEmpty()) {
            return null;
        }

        int duration = validator.getIntInput(scanner, "Enter duration (in minutes): ");
        return new RecipeStep(description, duration);
    }

    @Override
    protected RecipeStep modifyItem(String prompt, RecipeStep existingStep) {
        System.out.println(prompt);
        System.out.print("Enter new description (or press Enter to keep current): ");
        String description = scanner.nextLine().trim();
        
        System.out.print("Enter new duration in minutes (or -1 to keep " + existingStep.getDurationInMinutes() + "): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        return new RecipeStep(
            description.isEmpty() ? existingStep.getDescription() : description,
            duration < 0 ? existingStep.getDurationInMinutes() : duration
        );
    }

    @Override
    protected String formatItem(RecipeStep step) {
        return String.format("%s (%d minutes)", 
            step.getDescription(), 
            step.getDurationInMinutes());
    }
    
    public static List<RecipeStep> getStepsFromUser(InputValidator validator, Scanner scanner) {
        List<RecipeStep> steps = new ArrayList<>();
        System.out.println("\nEnter steps (leave step description empty when done):");
        int stepNumber = 1;
        
        while (true) {
            String description = validator.getStringInput(scanner, "\nStep " + stepNumber + " description: ");
            if (description.isEmpty()) {
                break;
            }
            
            int time = validator.getIntInput(scanner, "Time required (minutes): ");
            steps.add(new RecipeStep(description, time));
            stepNumber++;
        }
        
        return steps;
    }
} 