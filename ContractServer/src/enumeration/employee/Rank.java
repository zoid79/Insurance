package enumeration.employee;

public enum Rank {
	
	Staff("사원"), 
	SeniorStaff("주임"),
	AssisantManager("대리"),
	Manager("과장"),
	DeputyGeneralManager("차장"),
	GeneralManager("부장");
	
	private final String name;
	
	Rank(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
