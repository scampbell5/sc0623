package models.constants;

public enum ToolBrand {

    STIHL("Stihl"),
    Werner("Werner"),
    DE_WALT("DeWalt"),
    RIDGID("Ridgid"),


    ;


    private final String toolBrand;

    ToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public String getToolBrand() {
        return toolBrand;
    }
}
