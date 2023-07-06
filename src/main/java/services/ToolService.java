package services;

import models.Tool;
import models.constants.ToolBrand;
import models.constants.ToolType;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ToolService {

    private final Map<String, Tool> toolMap;

    public ToolService() {
        this.toolMap = new HashMap<>();

        toolMap.put("CHNS", new Tool("CHNS", ToolType.CHAINSAW, ToolBrand.STIHL));
        toolMap.put("LADW", new Tool("LADW", ToolType.LADDER, ToolBrand.Werner));
        toolMap.put("JAKD", new Tool("JAKD", ToolType.JACKHAMMER, ToolBrand.DE_WALT));
        toolMap.put("JAKR", new Tool("JAKR", ToolType.JACKHAMMER, ToolBrand.RIDGID));
    }

    /**
     * Get an optional that contains a tool if it exists for the provided tool code.
     *
     * @param toolCode - code of the tool
     * @return - an optional that contains a tool if found for the provided tool code.
     */
    public Optional<Tool> getToolByToolCode(String toolCode) {

        if (StringUtils.isBlank(toolCode)) {
            throw new IllegalArgumentException("Tool code cannot be blank when retrieving tool by tool code.");
        }

        return Optional.ofNullable(toolMap.get(toolCode));
    }

}
