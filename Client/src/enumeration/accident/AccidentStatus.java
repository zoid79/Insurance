package enumeration.accident;

public enum AccidentStatus {
    ReportAccident("사고접수"),
    RefuseCompensate("보상거절"),
    Compensate("보상완료");

    private final String name;

    AccidentStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
