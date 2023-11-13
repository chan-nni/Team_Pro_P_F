package spring.Pro_P_F.domain;

public enum JobStatus {
    OPEN("모집중"),
    CLOSE("모집 완료"),
    BEFORE("모집 전");

    private final String displayName;

    JobStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
