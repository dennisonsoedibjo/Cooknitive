package enums;

public enum UnitMeasure {
	G("g", "weight"),
	KG("kg", "weight"),
	ML("ml", "volume"),
	L("l", "volume"),
	UNIT("unit", "count");

	private final String symbol;
	private final String category;

	UnitMeasure(String symbol, String category) {
		this.symbol = symbol;
		this.category = category;
	}

	public String getSymbol() { return symbol; }
	public String getCategory() { return category; }

	public boolean isInSameCategory(UnitMeasure other) {
		return this.category.equals(other.category);
	}

	// ml, g, and unit are base units
	public double convertToBase(double quantity) {
		return switch (this) {
			case KG -> quantity * 1000; 
			case L -> quantity * 1000; 
			default -> quantity;        
		};
	}

	public double convertFromBase(double baseQuantity) {
		return switch (this) {
			case KG -> baseQuantity / 1000;
			case L -> baseQuantity / 1000; 
			default -> baseQuantity;     
		};
	}
}