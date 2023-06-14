package enumeration.calculationFormula;

public enum RoofType {

	ConcreteSlab("콘크리트 슬라브"),
	Panel("판넬");
	
	private final String name;
	
	RoofType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
