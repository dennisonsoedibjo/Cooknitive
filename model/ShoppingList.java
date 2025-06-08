package model;

import java.time.LocalDate;
import java.util.*;
import enums.UnitMeasure;

public class ShoppingList {
	// Attributes
    private final LocalDate date;
    private final String name;
    private final Map<String, Ingredient> items;

    // Constructor Method
    public ShoppingList(LocalDate date, String name) {
        this.date = date;
        this.name = name;
        this.items = new HashMap<>();
    }

    // Getters
    public LocalDate getDate() { return date; }
    public String getName() { return name; }
    public Map<String, Ingredient> getItems() { return new HashMap<>(items); }

    public void addItem(String name, double quantity, UnitMeasure unit) {
        String key = name.toLowerCase();
        Ingredient existingItem = items.get(key);
        
        if (existingItem != null) {
            if (existingItem.getUnitMeasure() == unit || existingItem.getUnitMeasure().isInSameCategory(unit)) {
                // Convert both quantities to base unit and add
                double existingBaseQuantity = existingItem.getUnitMeasure().convertToBase(existingItem.getQuantity());
                double newBaseQuantity = unit.convertToBase(quantity);
                double totalBaseQuantity = existingBaseQuantity + newBaseQuantity;
                
                // Convert back to original unit
                double totalQuantity = existingItem.getUnitMeasure().convertFromBase(totalBaseQuantity);
                existingItem.setQuantity(totalQuantity);
            } else {
                // If units are incompatible, add as new item with a modified name
                String newName = name + " (" + unit.getSymbol() + ")";
                items.put(newName.toLowerCase(), new Ingredient(newName, quantity, unit));
            }
        } else {
            items.put(key, new Ingredient(name, quantity, unit));
        }
    }

    public void updateItem(String name, double quantity, UnitMeasure unit) {
        items.put(name.toLowerCase(), new Ingredient(name, quantity, unit));
    }

    public void removeItem(String name) {
        items.remove(name.toLowerCase());
    }

    public Ingredient getItem(String name) {
        return items.get(name.toLowerCase());
    }

    public void clearItems() {
        items.clear();
        System.out.println("Shopping list cleared!");
    }

    public List<Ingredient> getItemsSorted() {
        List<Ingredient> sortedItems = new ArrayList<>(items.values());
        Collections.sort(sortedItems, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return sortedItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Shopping List: ").append(name).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Items:\n");
        
        for (Ingredient item : getItemsSorted()) {
            sb.append("- ").append(item).append("\n");
        }
        
        return sb.toString();
    }
}