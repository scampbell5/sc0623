package services;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor
public class RentalAmountService {

    /**
     * Calculate the pre-discount charge for the tool rental based on the number of chargeable days and daily rental charge.
     *
     * @param numberOfChargeableDays - number of chargeable days for the tool rental
     * @param dailyRentalCharge      - daily rental charge
     * @return - pre-discount charge, calculated from the number of chargeable days multiplied by the daily rental charge.
     */
    public BigDecimal calculatePreDiscountCharge(Integer numberOfChargeableDays, BigDecimal dailyRentalCharge) {
        return dailyRentalCharge.multiply(new BigDecimal(numberOfChargeableDays));
    }


    /**
     * Calculate the discount amount from the discount percent and pre-discount charge. Calculated value is rounded
     * half-up to cents
     *
     * @param discountPercent   - discount percent
     * @param preDiscountCharge - pre-discount charge
     * @return - discount amount calculated by multiplying the discount percent by the pre-discount charge, dividing by 100
     * and rounding half-up to cents.
     */
    public BigDecimal calculateDiscountAmount(Integer discountPercent, BigDecimal preDiscountCharge) {
        if (discountPercent == null || preDiscountCharge == null) {
            throw new IllegalArgumentException("Discount percent or pre-discount charge cannot be null when calculating the discount amount.");
        }

        // Calculate the discount amount by taking the pre-discount charge, minus the discount percent
        return preDiscountCharge.multiply(new BigDecimal(discountPercent)).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate the final charge from the pre-discount charge and discount amount.
     *
     * @param preDiscountCharge - pre-discount charge
     * @param discountAmount    - discount amount
     * @return - final charge calculated by taking the pre-discount charge and subtracting the discount amount.
     */
    public BigDecimal calculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
        if (preDiscountCharge == null || discountAmount == null) {
            throw new IllegalArgumentException("Pre-discount charge or discount amount cannot be null when calculating the final charge.");
        }

        return preDiscountCharge.subtract(discountAmount);
    }
}
