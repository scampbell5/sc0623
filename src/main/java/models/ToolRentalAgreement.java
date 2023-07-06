package models;

import lombok.Builder;
import lombok.Data;
import models.constants.ToolBrand;
import models.constants.ToolType;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Builder
public class ToolRentalAgreement {

    private final String toolCode;
    private final ToolType toolType;
    private final ToolBrand toolBrand;
    private final Integer rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final BigDecimal dailyRentalCharge;
    private final Integer chargeDays;
    private final BigDecimal preDiscountCharge;
    private final Integer discountPercent;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;

    /**
     * Private helper method for formatting a LocalDate to a String in the MM/dd/yyyy format.
     * <p>
     * ex. 07/15/2023
     *
     * @param date - date to return a formatted string for
     * @return - string for the date, formatted as MM/dd/yyyy, null if date is null.
     */
    private String getFormattedDate(LocalDate date) {
        if (date != null) {
            return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        return null;
    }

    /**
     * Private helper method for formatting an Integer into a percent string
     * <p>
     * ex. 20%
     *
     * @param percent - percent to return a formatted string for
     * @return - string for the percent, null if percent is null
     */
    private String getFormattedPercent(Integer percent) {
        if (percent != null) {
            return percent + "%";
        }

        return null;
    }

    /**
     * Private helper method for returning a formatted string for amount
     * <p>
     * ex. $1.49
     *
     * @param amount - amount to return a formatted string for
     * @return formatted string for the amount, null if amount is null
     */
    private String getFormattedAmount(BigDecimal amount) {
        if (amount != null) {
            Locale locale = new Locale("en", "US");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            return currencyFormatter.format(amount);
        }

        return null;
    }

    /**
     * Print the rental agreement to console.
     * <p>
     * <br>Tool code: LADW
     * <br>Tool type: Ladder
     * <br>...
     * <br>Final charge: $9.99
     * <p>
     * <br>Following types are formatted as such:
     * <br>Date - mm/dd/yy
     * <br>Currency - $9,999.99
     * <br>Percent - 99%
     */
    public void print() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getStringToAdd("Tool code: ", toolCode, stringBuilder));
        stringBuilder.append(getStringToAdd("Tool type: ", toolType == null ? null : toolType.getToolType(), stringBuilder));
        stringBuilder.append(getStringToAdd("Tool brand: ", toolBrand == null ? null : toolBrand.getToolBrand(), stringBuilder));
        stringBuilder.append(getStringToAdd("Rental days: ", rentalDays == null ? null : rentalDays.toString(), stringBuilder));
        stringBuilder.append(getStringToAdd("Checkout date: ", getFormattedDate(checkoutDate), stringBuilder));
        stringBuilder.append(getStringToAdd("Due date: ", getFormattedDate(dueDate), stringBuilder));
        stringBuilder.append(getStringToAdd("Daily rental charge: ", getFormattedAmount(dailyRentalCharge), stringBuilder));
        stringBuilder.append(getStringToAdd("Charge days: ", chargeDays == null ? null : chargeDays.toString(), stringBuilder));
        stringBuilder.append(getStringToAdd("Pre-discount charge: ", getFormattedAmount(preDiscountCharge), stringBuilder));
        stringBuilder.append(getStringToAdd("Discount percent: ", getFormattedPercent(discountPercent), stringBuilder));
        stringBuilder.append(getStringToAdd("Discount amount: ", getFormattedAmount(discountAmount), stringBuilder));
        stringBuilder.append(getStringToAdd("Final charge: ", getFormattedAmount(finalCharge), stringBuilder));

        System.out.println(stringBuilder.toString());
    }

    /**
     * Private helper method to create a StringBuilder with the label tex, value and an optional new line if existing
     * string builder has any existing characters
     * <p>
     * <br>ex (Empty String Builder).       Tool code: LADW
     * <br>ex (Non-empty String Builder).   \nTool code: LADW
     *
     * @param labelText           - text of label
     * @param value               - value of the field
     * @param existingStringBuild - existing string build
     * @return - string builder with label text, value and an optional new line in
     */
    private StringBuilder getStringToAdd(String labelText, String value, StringBuilder existingStringBuild) {
        StringBuilder stringBuilder = new StringBuilder();

        if (value != null && labelText != null) {

            // If the existing string builder length is greater than 0, we need to append a new line prior to
            // appending the label text and value
            if (existingStringBuild != null && existingStringBuild.length() > 0) {
                stringBuilder.append("\n");
            }


            stringBuilder.append(labelText);
            stringBuilder.append(value);
        }

        return stringBuilder;
    }
}
