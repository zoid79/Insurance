package enumeration.calculationFormula;

public enum OutwallType {
	
	Concrete("콘크리트"),
	Brick("벽돌"),
	Block("블록"),
	Glass("유리");
	
	private final String name;
	
	OutwallType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

