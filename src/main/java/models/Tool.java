package models;

import lombok.Data;
import models.constants.ToolBrand;
import models.constants.ToolType;

@Data
public class Tool {

    private final String toolCode;
    private final ToolType toolType;
    private final ToolBrand brand;
}
