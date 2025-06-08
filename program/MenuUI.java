package program;

public class MenuUI {
    public void displayMainMenu() {
        System.out.println("\nRecipe Management System");
        System.out.println("1. Add Recipe");
        System.out.println("2. View Recipe");
        System.out.println("3. Update Recipe");
        System.out.println("4. Delete Recipe");
        System.out.println("5. Search by Ingredient");
        System.out.println("6. Generate Shopping List");
        System.out.println("7. View Shopping List");
        System.out.println("8. Search by Dietary Preference");
        System.out.println("9. Search by Category");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }
    
    public void displayUpdateMenu() {
        System.out.println("\nUpdate options:");
        System.out.println("A) Name");
        System.out.println("B) Description");
        System.out.println("C) Ingredients");
        System.out.println("D) Steps");
        System.out.println("E) Category");
        System.out.println("F) Dietary");
        System.out.println("G) Cancel");
        System.out.print("Choose action: ");
    }
    
    public void displayShoppingMenu() {
    	System.out.println("\nShopping List Menu:");
        System.out.println("A) Add recipe ingredients");
        System.out.println("B) Add manual ingredient");
        System.out.println("C) Modify ingredients");
        System.out.println("D) Delete ingredients");
        System.out.println("E) Clear list");
        System.out.println("F) Done");
        System.out.print("Enter your choice: ");
    }

    public void displayListMenu(String itemType) {
        System.out.println("\n" + itemType + " options: [A]dd, [D]elete, [M]odify, [C]lear, [F]inish");
        System.out.print("Choose action (A/D/M/C/F): ");
    }
} 