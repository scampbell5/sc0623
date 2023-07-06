package models;

import lombok.Data;
import models.constants.ToolBrand;
import models.constants.ToolType;

@Data
public class Tool {

    private final String code;
    private final ToolType type;
    private final ToolBrand brand;
}
