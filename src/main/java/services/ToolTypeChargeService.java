package services;

import models.ToolTypeCharge;
import models.constants.ToolType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ToolTypeChargeService {

    private final Map<ToolType, ToolTypeCharge> toolTypeChargeMap;

    public ToolTypeChargeService() {
        this.toolTypeChargeMap = new HashMap<>();
        toolTypeChargeMap.put(ToolType.LADDER, new ToolTypeCharge(ToolType.LADDER, new BigDecimal("1.99"), Boolean.TRUE, Boolean.TRUE, Boolean.FALSE));
        toolTypeChargeMap.put(ToolType.CHAINSAW, new ToolTypeCharge(ToolType.CHAINSAW, new BigDecimal("1.49"), Boolean.TRUE, Boolean.FALSE, Boolean.TRUE));
        toolTypeChargeMap.put(ToolType.JACKHAMMER, new ToolTypeCharge(ToolType.JACKHAMMER, new BigDecimal("2.99"), Boolean.TRUE, Boolean.FALSE, Boolean.FALSE));
    }

    /**
     * Get an optional that contains a tool type charge by the type of tool.
     *
     * @param toolType - type of tool
     * @return - an optional that contains a tool type charge if found for the type of tool.
     */
    public Optional<ToolTypeCharge> getToolTypeCharge(ToolType toolType) {

        if (toolType == null) {
            throw new IllegalArgumentException("Tool type cannot be null when retrieving tool type charge`.");
        }

        return Optional.ofNullable(toolTypeChargeMap.get(toolType));
    }
}
