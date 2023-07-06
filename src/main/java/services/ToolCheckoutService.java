package services;

import exceptions.NotFoundException;
import exceptions.SystemErrorException;
import models.Tool;
import models.ToolRentalAgreement;
import models.ToolTypeCharge;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ToolCheckoutService {

    private final ToolService toolService;
    private final ToolTypeChargeService toolTypeChargeService;
    private final RentalAmountService rentalAmountService;
    private final RentalDateService rentalDateService;

    public ToolCheckoutService(ToolService toolService, ToolTypeChargeService toolTypeChargeService, RentalAmountService rentalAmountService, RentalDateService rentalDateService) {
        this.toolService = toolService;
        this.toolTypeChargeService = toolTypeChargeService;
        this.rentalAmountService = rentalAmountService;
        this.rentalDateService = rentalDateService;
    }

    /**
     * Checking out a tool creates a new rental agreement based on the passed in parameters.
     *
     * @param toolCode        - code of the tool to checkout
     * @param rentalDays      - number of days for which the customer wants to rent the tool. (e.g. 4 days)
     * @param discountPercent - discount percent, must be between 0 and 100
     * @param checkoutDate    - date the tool is checked out
     * @return - tool rental agreement created based on the input parameters
     */
    public ToolRentalAgreement checkoutTool(String toolCode, Integer rentalDays,
                                            Integer discountPercent, LocalDate checkoutDate) {

        validate(toolCode, rentalDays, discountPercent, checkoutDate);

        Tool toolToRent = toolService.getToolByToolCode(toolCode)
                .orElseThrow(() -> new NotFoundException("Tool code entered must be a valid tool code, could not find matching tool."));

        ToolTypeCharge toolTypeCharge = toolTypeChargeService.getToolTypeCharge(toolToRent.getType())
                .orElseThrow(() -> new SystemErrorException("Could not find tool type charge for the specific tool. A tool type charge may not have been setup for tool type: " + toolToRent.getType()));

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        Integer numberOfChargeDays = rentalDateService.determineNumberOfChargeDays(toolTypeCharge, checkoutDate, rentalDays);
        BigDecimal preDiscountCharge = rentalAmountService.calculatePreDiscountCharge(numberOfChargeDays, toolTypeCharge.getDailyRentalCharge());
        BigDecimal discountAmount = rentalAmountService.calculateDiscountAmount(discountPercent, preDiscountCharge);
        BigDecimal finalCharge = rentalAmountService.calculateFinalCharge(preDiscountCharge, discountAmount);

        return ToolRentalAgreement
                .builder()
                .toolCode(toolCode)
                .toolType(toolToRent.getType())
                .toolBrand(toolToRent.getBrand())
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(dueDate)
                .dailyRentalCharge(toolTypeCharge.getDailyRentalCharge())
                .chargeDays(numberOfChargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(discountPercent)
                .discountAmount(discountAmount)
                .finalCharge(finalCharge)
                .build();
    }

    /**
     * Private helper method to validate method parameters
     *
     * @param toolCode        - code of the tool to validate
     * @param rentalDays      - number of rental days to validate
     * @param discountPercent - discount percent to validate
     * @param checkoutDate    - checkout date to validate
     */
    private void validate(String toolCode, Integer rentalDays, Integer discountPercent, LocalDate checkoutDate) {
        validateToolCode(toolCode);
        validateRentalDays(rentalDays);
        validateDiscountPercent(discountPercent);
        validateCheckoutDate(checkoutDate);
    }

    /**
     * Validate the tool code is not blank.
     *
     * @param toolCode - code of the tool to validate
     */
    private void validateToolCode(String toolCode) {
        if (StringUtils.isBlank(toolCode)) {
            throw new IllegalArgumentException("Tool code cannot be blank when checking out a tool.");
        }
    }

    /**
     * Validate the rental days, must be 1 or greater
     *
     * @param rentalDays - rental days to validate
     */
    private void validateRentalDays(Integer rentalDays) {
        if (rentalDays == null || rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be 1 or greater when checking out a tool.");
        }
    }

    /**
     * Validate the discount percent, cannot be null and must be between 0 - 100
     *
     * @param discountPercent - discount percent to validate
     */
    private void validateDiscountPercent(Integer discountPercent) {
        if (discountPercent == null || discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100 when checking out a tool.");
        }
    }

    /**
     * Validate the checkout date is not null
     *
     * @param checkoutDate - checkout date to validate
     */
    private void validateCheckoutDate(LocalDate checkoutDate) {
        if (checkoutDate == null) {
            throw new IllegalArgumentException("Checkout date cannot be null when when checking out a tool.");
        }
    }
}
