package enumeration.calculationFormula.workplaceFormula;

public enum WorkplaceCompensation {
	
	MoreThan1_5B(1_500_000_000, 2_000_000_000, "15억원 이상 20억원 이하"),
	MoreThan1B(1_000_000_000, MoreThan1_5B.getMinAmount()-1, "10억원 이상"),
	MoreThan500M(500_000_000, MoreThan1B.getMinAmount()-1, "5억원 이상"),
	MoreThan100M(100_000_000, MoreThan500M.getMinAmount()-1, "1억원 이상"),
	MoreThan50M(50_000_000, MoreThan100M.getMinAmount()-1, "5천만원 이상"),
	MoreThan10M(10_000_000, MoreThan50M.getMinAmount()-1, "천만원 이상");

	private final int minAmount;
	private final int maxAmount;
	private final String name;

	WorkplaceCompensation(int minAmount, int maxAmount, String name){
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.name = name;	
	}

	public int getMinAmount() {
		return minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public String getName() {
		return name;
	}
	
	//고객이 보험 신청 시 사용
//	public static WorkplaceCompensation getWorkplaceCompensation(long amount) {
//		if(amount > WorkplaceCompensation.values()[WorkplaceCompensation.values().length-1].getMaxAmount() 
//				|| amount < WorkplaceCompensation.values()[0].getMinAmount()) return null;
//		for(int i = 0; i < WorkplaceCompensation.values().length; i++) {
//			if(amount <= WorkplaceCompensation.values()[i].getMaxAmount()) return WorkplaceCompensation.values()[i];
//		}
//		return null;
//	}
}
