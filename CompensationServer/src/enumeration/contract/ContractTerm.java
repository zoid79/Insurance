package enumeration.contract;

public enum ContractTerm {
    OneYear(1),
    TwoYear(2),
    ThreeYear(3),
    FiveYear(5),
    TenYear(10);

    private final int year;
    ContractTerm(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}
