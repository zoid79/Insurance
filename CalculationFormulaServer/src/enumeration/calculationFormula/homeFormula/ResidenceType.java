package enumeration.calculationFormula.homeFormula;

public enum ResidenceType {
	
	SelfOwned("자가소유"),
	Rental("임차");
	
	private final String name;
	
	ResidenceType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
