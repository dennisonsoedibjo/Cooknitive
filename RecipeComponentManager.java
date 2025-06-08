package manager;

import java.util.*;
import program.MenuUI;
import util.InputValidator;

public abstract class RecipeComponentManager<T> {
    protected final List<T> items;
    protected final InputValidator validator;
    protected final Scanner scanner;
    protected final String componentName;

    public RecipeComponentManager(List<T> items, InputValidator validator, Scanner scanner, String componentName) {
        this.items = items;
        this.validator = validator;
        this.scanner = scanner;
        this.componentName = componentName;
    }

    public List<T> getItems() {
        return new ArrayList<>(items);
    }

    protected abstract void displayCurrentItems();
    protected abstract T createItem(String prompt);
    protected abstract T modifyItem(String prompt, T existingItem);
    protected abstract String formatItem(T item);

    public void manage(MenuUI menuUI) {
        boolean editing = true;
        while (editing) {
            displayCurrentItems();
            menuUI.displayListMenu(componentName);
            String action = scanner.nextLine().toUpperCase();
            
            switch (action) {
                case "A" -> addItem();
                case "D" -> deleteItem();
                case "M" -> modifyItem();
                case "C" -> items.clear();
                case "F" -> editing = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }
    
    private void addItem() {
        String prompt = componentName + " details: ";
        T newItem = createItem(prompt);
        if (newItem != null) {
            items.add(newItem);
            System.out.println(componentName + " added.");
        }
    }

    private void deleteItem() {
        int index = validator.getIntInput(scanner, componentName + " number to delete: ") - 1;
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            System.out.println(componentName + " removed.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void modifyItem() {
        int index = validator.getIntInput(scanner, componentName + " number to modify: ") - 1;
        if (index >= 0 && index < items.size()) {
            String prompt = "Updated " + componentName + " details: ";
            T updatedItem = modifyItem(prompt, items.get(index));
            if (updatedItem != null) {
                items.set(index, updatedItem);
                System.out.println(componentName + " modified.");
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }
} 