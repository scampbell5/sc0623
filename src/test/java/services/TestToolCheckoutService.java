package services;

import models.ToolRentalAgreement;
import models.constants.ToolBrand;
import models.constants.ToolType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TestToolCheckoutService {

    private final ToolCheckoutService toolCheckoutService = new ToolCheckoutService(new ToolService(), new ToolTypeChargeService(), new RentalAmountService(), new RentalDateService());


    // Exception Test Cases

    // Test Case 1 verifies that ToolCheckoutService::checkoutTool throws an
    // exception when discount percent is greater than 100
    @Test
    public void test1() {

        String toolCode = "JAKR";
        Integer rentalDays = 5;
        Integer discountPercent = 101;
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        assertThatThrownBy(() -> toolCheckoutService.checkoutTool(toolCode, rentalDays, discountPercent, checkoutDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount percent must be between 0 and 100 when checking out a tool.");
    }

    // Happy Path Tests

    @Test
    public void test2() {

        String toolCode = "LADW";
        Integer rentalDays = 3;
        Integer discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        ToolRentalAgreement expected = ToolRentalAgreement
                .builder()
                .toolCode(toolCode)
                .toolType(ToolType.LADDER)
                .toolBrand(ToolBrand.Werner)
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(LocalDate.of(2020, 7, 5))
                .dailyRentalCharge(new BigDecimal("1.99"))
                .chargeDays(2)
                .preDiscountCharge(new BigDecimal("3.98"))
                .discountPercent(10)
                .discountAmount(new BigDecimal("0.40"))
                .finalCharge(new BigDecimal("3.58"))
                .build();

        assertThat(toolCheckoutService.checkoutTool(toolCode, rentalDays, discountPercent, checkoutDate))
                .isEqualTo(expected);
    }

    @Test
    public void test3() {

        String toolCode = "CHNS";
        Integer rentalDays = 5;
        Integer discountPercent = 25;
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);

        ToolRentalAgreement expected = ToolRentalAgreement
                .builder()
                .toolCode(toolCode)
                .toolType(ToolType.CHAINSAW)
                .toolBrand(ToolBrand.STIHL)
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(LocalDate.of(2015, 7, 7))
                .dailyRentalCharge(new BigDecimal("1.49"))
                .chargeDays(3)
                .preDiscountCharge(new BigDecimal("4.47"))
                .discountPercent(25)
                .discountAmount(new BigDecimal("1.12"))
                .finalCharge(new BigDecimal("3.35"))
                .build();

        assertThat(toolCheckoutService.checkoutTool(toolCode, rentalDays, discountPercent, checkoutDate))
                .isEqualTo(expected);
    }

    @Test
    public void test4() {

        String toolCode = "JAKD";
        Integer rentalDays = 6;
        Integer discountPercent = 0;
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        ToolRentalAgreement expected = ToolRentalAgreement
                .builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .toolBrand(ToolBrand.DE_WALT)
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(LocalDate.of(2015, 9, 9))
                .dailyRentalCharge(new BigDecimal("2.99"))
                .chargeDays(3)
                .preDiscountCharge(new BigDecimal("8.97"))
                .discountPercent(0)
                .discountAmount(new BigDecimal("0.00"))
                .finalCharge(new BigDecimal("8.97"))
                .build();

        assertThat(toolCheckoutService.checkoutTool(toolCode, rentalDays, discountPercent, checkoutDate))
                .isEqualTo(expected);
    }

    @Test
    public void test5() {

        String toolCode = "JAKR";
        Integer rentalDays = 9;
        Integer discountPercent = 0;
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);

        ToolRentalAgreement expected = ToolRentalAgreement
                .builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .toolBrand(ToolBrand.RIDGID)
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(LocalDate.of(2015, 7, 11))
                .dailyRentalCharge(new BigDecimal("2.99"))
                .chargeDays(5)
                .preDiscountCharge(new BigDecimal("14.95"))
                .discountPercent(0)
                .discountAmount(new BigDecimal("0.00"))
                .finalCharge(new BigDecimal("14.95"))
                .build();

        assertThat(toolCheckoutService.checkoutTool(toolCode, rentalDays, discountPercent, checkoutDate))
                .isEqualTo(expected);
    }

    @Test
    public void test6() {

        String toolCode = "JAKR";
        Integer rentalDays = 4;
        Integer discountPercent = 50;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        ToolRentalAgreement expected = ToolRentalAgreement
                .builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .toolBrand(ToolBrand.RIDGID)
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(LocalDate.of(2020, 7, 6))
                .dailyRentalCharge(new BigDecimal("2.99"))
                .chargeDays(1)
                .preDiscountCharge(new BigDecimal("2.99"))
                .discountPercent(50)
                .discountAmount(new BigDecimal("1.50"))
                .finalCharge(new BigDecimal("1.49"))
                .build();

        assertThat(toolCheckoutService.checkoutTool(toolCode, rentalDays, discountPercent, checkoutDate))
                .isEqualTo(expected);
    }

}
