package enumeration.contract;

public enum PaymentCycle {
    AMonth(1),
    ThreeMonth(3),
    SixMonth(6);
    private final int month;
    PaymentCycle(int month) {
        this.month = month;
    }

    public static int getCycle(PaymentCycle cycleName) {
        return cycleName.month;
    }
    public int getMonth() {
        return month;
    }
}
