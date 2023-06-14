package enumeration.calculationFormula.homeFormula;

public enum HomeCompensation {

	MoreThan450M(450_000_000, 500_000_000, "4.5억원 이상 5억원 이하"),
	MoreThan400M(400_000_000, MoreThan450M.getMinAmount()-1, "4억원 이상"),
	MoreThan350M(350_000_000, MoreThan400M.getMinAmount()-1, "3.5억원 이상"),
	MoreThan300M(300_000_000, MoreThan350M.getMinAmount()-1, "3억원 이상"),
	MoreThan250M(250_000_000, MoreThan300M.getMinAmount()-1, "2.5억원 이상"),
	MoreThan200M(200_000_000, MoreThan250M.getMinAmount()-1, "2억원 이상"),
	MoreThan150M(150_000_000, MoreThan200M.getMinAmount()-1, "1.5억원 이상"),
	MoreThan100M(100_000_000, MoreThan150M.getMinAmount()-1, "1억원 이상"),
	MoreThan50M(50_000_000, MoreThan100M.getMinAmount()-1, "5천만원 이상"),
	MoreThan10M(10_000_000, MoreThan50M.getMinAmount()-1, "천만원 이상");

	private final int minAmount;
	private final int maxAmount;
	private final String name;
	
	HomeCompensation(int minAmount, int maxAmount, String name){
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
}
