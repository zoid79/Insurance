package enumeration.calculationFormula.workplaceFormula;

public enum Floor {
	
	MoreThan55(55, 60, "55층 이상 60층 이하"),
	MoreThan50(50, MoreThan55.getMinFloor()-1, "50층 이상"),
	MoreThan45(45, MoreThan50.getMinFloor()-1, "45층 이상"),
	MoreThan40(40, MoreThan45.getMinFloor()-1, "40층 이상"),
	MoreThan35(35, MoreThan40.getMinFloor()-1, "35층 이상"),
	MoreThan30(30, MoreThan35.getMinFloor()-1, "30층 이상"),
	MoreThan25(25, MoreThan30.getMinFloor()-1, "25층 이상"),
	MoreThan20(20, MoreThan25.getMinFloor()-1, "20층 이상"),
	MoreThan15(15, MoreThan20.getMinFloor()-1, "15층 이상"),
	MoreThan10(10, MoreThan15.getMinFloor()-1, "10층 이상"),
	MoreThan5(5, MoreThan10.getMinFloor()-1, "5층 이상"),
	MoreThan1(1, MoreThan5.getMinFloor()-1, "1층 이상");


	private final int minFloor;
	private final int maxFloor;
	private final String name;
	
	private Floor(int minFloor, int maxFloor, String name) {
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
		this.name = name;
	}

	public int getMinFloor() {
		return minFloor;
	}

	public int getMaxFloor() {
		return maxFloor;
	}

	public String getName() {
		return name;
	}
	
	
}
