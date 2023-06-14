package enumeration.contract;

public enum ContractStatus {
    Apply("신청"),
    RefuseUnderwrite("인수거절"),
    Underwrite("인수완료"),
    Conclude("체결완료"),
    Terminate("중지"),
    Expire("만료");

    private final String name;

    ContractStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
