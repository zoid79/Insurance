package enumeration.employee;

public enum Department {
	
	InsuranceDeveloper("상품개발자"), 
	InsuranceManager("상품관리자"),
	UW("언더라이터"),
	Salesperson("영업사원"),
	CompensationManager("보상담당자");


	
	private final String name;
	
	Department(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
