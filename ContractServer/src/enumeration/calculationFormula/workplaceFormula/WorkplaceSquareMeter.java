package enumeration.calculationFormula.workplaceFormula;

public enum WorkplaceSquareMeter {
	
	MoreThan1488(1488, 1653, "450평(1488㎡) 이상 500평(1653㎡) 이하"),
	MoreThan1322(1322, MoreThan1488.getMinSquareFeet()-1, "400평(1322㎡) 이상"),
	MoreThan1157(1157, MoreThan1322.getMinSquareFeet()-1, "350평(1157㎡) 이상"),
	MoreThan992(992, MoreThan1157.getMinSquareFeet()-1, "300평(992㎡) 이상"),
	MoreThan826(826, MoreThan992.getMinSquareFeet()-1, "250평(826㎡) 이상"),
	MoreThan661(661, MoreThan826.getMinSquareFeet()-1, "200평(661㎡) 이상"),
	MoreThan496(496, MoreThan661.getMinSquareFeet()-1, "150평(496㎡) 이상"),
	MoreThan331(331, MoreThan496.getMinSquareFeet()-1, "100평(331㎡) 이상"),
	MoreThan165(165, MoreThan331.getMinSquareFeet()-1, "50평(165㎡) 이상"),
	MoreThan17(17, MoreThan165.getMinSquareFeet()-1, "5평(17㎡) 이상");

	private final int minSquareMeter;
	private final int maxSquareMeter;
	private final String name;
	
	WorkplaceSquareMeter(int minSquareMeter, int maxSquareMeter, String name) {
		this.minSquareMeter = minSquareMeter;
		this.maxSquareMeter = maxSquareMeter;
		this.name = name;
	}

	public int getMinSquareFeet() {
		return minSquareMeter;
	}

	public int getMaxSquareFeet() {
		return maxSquareMeter;
	}

	public String getName() {
		return name;
	}
	
}
