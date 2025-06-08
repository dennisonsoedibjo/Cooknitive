package manager;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityManager<T> {
    protected List<T> items;
    
    public EntityManager() {
        this.items = new ArrayList<>();
    }
    
    // Abstract operation that all entity managers should implement    
    protected abstract String getItemName(T item);
    
    // Common implementations that can be used by all entity managers
    public List<T> getAllItems() {
        return new ArrayList<>(items);
    }

    public T getByName(String name) {
        for (T item : items) {
            if (getItemName(item).equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
    
    public boolean add(T item) {
        if (getByName(getItemName(item)) != null) {
            return false;
        }
        items.add(item);
        return true;
    }

    public boolean delete(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (getItemName(items.get(i)).equalsIgnoreCase(name)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }
}