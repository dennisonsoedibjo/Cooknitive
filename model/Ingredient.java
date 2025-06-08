package model;

import enums.UnitMeasure;

public class Ingredient{
	// Attributes
	private String name;
	private double quantity;
	private UnitMeasure unitMeasure;

	// Constructor Method
	public Ingredient(String name, double quantity, UnitMeasure unitMeasure) {
        this.name = name;
        this.quantity = quantity;
        this.unitMeasure = unitMeasure;
    }
	
	// Getters
    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public UnitMeasure getUnitMeasure() { return unitMeasure; }
    
    // Setters
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setUnit(UnitMeasure unit) { this.unitMeasure = unit; }
    public void setName (String name) { this.name = name; }
    
    @Override
    public String toString() {
        return String.format("%.2f %s of %s", quantity, unitMeasure.toString().toLowerCase(), name);
    }
}