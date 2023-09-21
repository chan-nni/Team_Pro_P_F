package spring.Pro_P_F.domain;

public enum EmployType {
    Employee("정직원"),
    Part_time("아르바이트"),
    Intern("인턴");

    private final String displayName;

    EmployType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
