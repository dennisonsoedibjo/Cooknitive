package manager;

import java.time.LocalDate;
import java.util.*;
import model.ShoppingList;
import model.Ingredient;

public class ShoppingListManager extends EntityManager<ShoppingList> {
    
    public ShoppingListManager() {
        super();
    }

    @Override
    protected String getItemName(ShoppingList list) {
        return list.getName();
    }

    // Date-specific operations
    public List<ShoppingList> getListsByDate(LocalDate date) {
        List<ShoppingList> result = new ArrayList<>();
        for (ShoppingList list : items) {
            if (list.getDate().equals(date)) {
                result.add(list);
            }
        }
        return result;
    }

    public boolean hasListsForDate(LocalDate date) {
        for (ShoppingList list : items) {
            if (list.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public void displayListsForDate(LocalDate date) {
        List<ShoppingList> lists = getListsByDate(date);
        if (lists.isEmpty()) {
            System.out.println("No shopping lists found for " + date);
            return;
        }

        System.out.println("\nShopping lists for " + date + ":");
        for (ShoppingList list : lists) {
            System.out.println("\n" + list.getName() + ":");
            for (Ingredient item : list.getItemsSorted()) {
                System.out.println(" - " + item);
            }
        }
    }
} 