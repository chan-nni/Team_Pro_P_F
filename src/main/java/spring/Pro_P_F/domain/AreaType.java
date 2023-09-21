package spring.Pro_P_F.domain;

public enum AreaType {
    seoul("서울"),
    busan("부산"),
    daegu("대구"),
    gyeonggi("경기도");

    private final String displayName;

    AreaType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
