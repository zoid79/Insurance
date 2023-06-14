package enumeration.calculationFormula.homeFormula;

public enum HouseType {
	
	Multigenerational("다세대"),
	multifamily("다가구");
	
	private final String name;
	
	HouseType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
