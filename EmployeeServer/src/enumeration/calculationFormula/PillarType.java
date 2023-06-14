package enumeration.calculationFormula;

public enum PillarType {
	
	Concrete("콘크리트"),
	Brick("벽돌");
	
	private final String name;
	
	PillarType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	
}
