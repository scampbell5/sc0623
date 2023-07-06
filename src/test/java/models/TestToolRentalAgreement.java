package models;

import models.constants.ToolBrand;
import models.constants.ToolType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class TestToolRentalAgreement {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testPrint() {
        ToolRentalAgreement toolRentalAgreement = ToolRentalAgreement
                .builder()
                .toolCode("JAKR")
                .toolType(ToolType.JACKHAMMER)
                .toolBrand(ToolBrand.RIDGID)
                .rentalDays(9)
                .checkoutDate(LocalDate.of(2015, 7, 2))
                .dueDate(LocalDate.of(2015, 7, 11))
                .dailyRentalCharge(new BigDecimal("2.99"))
                .chargeDays(5)
                .preDiscountCharge(new BigDecimal("14.95"))
                .discountPercent(15)
                .discountAmount(new BigDecimal("3.92"))
                .finalCharge(new BigDecimal("14.95"))
                .build();

        toolRentalAgreement.print();

        String expected = """
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 9
                Checkout date: 07/02/15
                Due date: 07/11/15
                Daily rental charge: $2.99
                Charge days: 5
                Pre-discount charge: $14.95
                Discount percent: 15%
                Discount amount: $3.92
                Final charge: $14.95
                """;

        assertThat(outContent.toString())
                .isEqualTo(expected);
    }
}
