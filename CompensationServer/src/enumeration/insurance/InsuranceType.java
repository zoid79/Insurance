package enumeration.insurance;

public enum InsuranceType {

	HomeFire("주택화재"),
	WorkplaceFire("사업장화재");

	private final String name;

	InsuranceType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
