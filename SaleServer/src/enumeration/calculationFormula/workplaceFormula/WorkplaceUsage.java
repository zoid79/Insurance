package enumeration.calculationFormula.workplaceFormula;

public enum WorkplaceUsage {
	
	Restaurant("음식점"),
	Store("판매점"),
	LivingFacility("생활시설"),
	Office("사무실"),
	Officetels("오피스텔"),
	SportsFacility("스포츠시설"),
	Hospital("병원"),
	Accommodation("숙박시설"),
	Academy("학원"),
	Church("교회");
	
	private final String name;
	
	WorkplaceUsage(String name){
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
