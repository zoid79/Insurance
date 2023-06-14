package enumeration.calculationFormula.homeFormula;

public enum HomeSquareMeter {
	
	MoreThan496(496, 661, "150평(496㎡) 이상 200평(661㎡) 이하"),
	MoreThan331(331, MoreThan496.getMinSquareFeet()-1, "100평(331㎡) 이상"),
	MoreThan165(165, MoreThan331.getMinSquareFeet()-1, "50평(165㎡) 이상"),
	MoreThan17(17, MoreThan165.getMinSquareFeet()-1, "5평(17㎡) 이상");

	private final int minSquareMeter;
	private final int maxSquareMeter;
	private final String name;
	
	HomeSquareMeter(int minSquareMeter, int maxSquareMeter, String name) {
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
