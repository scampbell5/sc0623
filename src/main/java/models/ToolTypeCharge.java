package models;

import lombok.Data;
import models.constants.ToolType;

import java.math.BigDecimal;

@Data
public class ToolTypeCharge {

    private final ToolType toolType;
    private final BigDecimal dailyRentalCharge;
    private final Boolean weekdayCharge;
    private final Boolean weekendCharge;
    private final Boolean holidayCharge;
}
