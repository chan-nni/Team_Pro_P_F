package spring.Pro_P_F.domain;

public enum WorkType {
    Frontend("프론트엔드"),
    Backend("백엔드"),
    Design("디자인");

    private final String displayName;

    WorkType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
