package models.constants;

public enum ToolType {

    CHAINSAW("Chainsaw"),
    LADDER("Ladder"),
    JACKHAMMER("Jackhammer"),

    ;


    private final String toolType;

    ToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolType() {
        return toolType;
    }
}
