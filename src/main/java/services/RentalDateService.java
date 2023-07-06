package services;

import lombok.NoArgsConstructor;
import models.ToolTypeCharge;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class RentalDateService {

    /**
     * Determine the number of charge days, based on the tool type charge, checkout date and number of rental days.
     *
     * @param toolTypeCharge - rental agreement for the specific tool type
     * @param checkoutDate   - date the tool is checked out
     * @param rentalDays     - number of days for which the customer wants to rent the tool. (e.g. 4 days)
     * @return - number of days to charge for the tool rental
     */
    public Integer determineNumberOfChargeDays(ToolTypeCharge toolTypeCharge, LocalDate checkoutDate, Integer rentalDays) {

        if (toolTypeCharge == null || checkoutDate == null || rentalDays == null) {
            throw new IllegalArgumentException("Tool type charge, checkout date and rental days cannot be null when determining number of charge days.");
        }

        List<LocalDate> rentalDates = generateRentalDates(checkoutDate, rentalDays);

        return rentalDates
                .stream()
                // If it's a chargeable day, then we should add 1, otherwise add 0 (not a chargeable day)
                .mapToInt(rentalDate -> isChargeableDay(toolTypeCharge, rentalDate) ? 1 : 0)
                .sum();
    }

    /**
     * Private helper method to generate a list of rental dates based on the checkout date
     * and the number of rentals days.
     *
     * @param checkoutDate - date the tool is checked out
     * @param rentalDays   - number of days for which the customer wants to rent the tool. (e.g. 4 days)
     * @return - list of dates generated from the checkout date and rental days
     */
    private List<LocalDate> generateRentalDates(LocalDate checkoutDate, Integer rentalDays) {
        List<LocalDate> rentalDates = new ArrayList<>();

        if (checkoutDate == null
                || rentalDays == null) {
            throw new IllegalArgumentException("Checkout date or rental days cannot be null when generating the list of rental dates");
        }

        for (int i = 1; i <= rentalDays; i++) {
            rentalDates.add(checkoutDate.plusDays(i));
        }

        return rentalDates;
    }

    /**
     * Calculate the daily charge based on the tool type charge and what the date of rental is.
     *
     * @param toolTypeCharge - rental agreement for the specific tool type
     * @param dateOfRental   - date of the rental
     * @return - returns true if it's a chargeable day, otherwise false.
     */
    private boolean isChargeableDay(ToolTypeCharge toolTypeCharge, LocalDate dateOfRental) {

        if (toolTypeCharge == null || dateOfRental == null) {
            throw new IllegalArgumentException("Tool type charge or date of rental cannot be null when determining if date of rental is a chargeable day.");
        }

        if (isObservedAsHoliday(dateOfRental)) {
            return toolTypeCharge.getHolidayCharge();
        }

        if (isWeekend(dateOfRental)) {
            return toolTypeCharge.getWeekendCharge();
        }

        if (isWeekday(dateOfRental)) {
            return toolTypeCharge.getWeekdayCharge();
        }

        return Boolean.FALSE;
    }

    /**
     * Private helper method to determine if the date of rental is a weekday or not.
     *
     * @param dateOfRental - date of tool rental
     * @return - true if the date of rental is a weekday, false otherwise
     */
    private boolean isWeekday(LocalDate dateOfRental) {
        if (dateOfRental == null) {
            throw new IllegalArgumentException("Date of rental cannot be null when determining if date is a weekday.");
        }

        return !isWeekend(dateOfRental);
    }

    /**
     * Private helper method to determine if the date of rental is a weekend day or not.
     *
     * @param dateOfRental - date of tool rental
     * @return - true if the date of rental is a weekend day, false otherwise.
     */
    private boolean isWeekend(LocalDate dateOfRental) {

        if (dateOfRental == null) {
            throw new IllegalArgumentException("Date of rental cannot be null when determining if date is a weekend day.");
        }

        return dateOfRental.getDayOfWeek() == DayOfWeek.SATURDAY
                || dateOfRental.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    /**
     * Private helper method to determine if the date of rental is observed as a holiday or not.
     *
     * @param dateOfRental - date of rental
     * @return - true if date of rental is an observable holiday, false otherwise.
     */
    private boolean isObservedAsHoliday(LocalDate dateOfRental) {
        if (dateOfRental == null) {
            throw new IllegalArgumentException("Date of rental cannot be null when determining if date is a holiday.");
        }

        return isMemorialDay(dateOfRental) || isObservedAsIndependenceDay(dateOfRental);
    }

    /**
     * If the 4th of July falls on the weekend, the holiday rental day will be observed on the closest weekday.
     * Friday if 4th of July falls on Saturday
     * Monday if 4th of July falls on Sunday
     *
     * @param dateOfRental - date of rental
     * @return - true if the date should be observed as independence day, false otherwise
     */
    private boolean isObservedAsIndependenceDay(LocalDate dateOfRental) {
        if (dateOfRental == null) {
            throw new IllegalArgumentException("Date of rental cannot be null when determining if date is in observation of Independence Day.");
        }

        // If month isn't July then we don't need to perform any additional logic to
        // determine if the date of rental is observed as Independence Day
        if (dateOfRental.getMonth() != Month.JULY) {
            return false;
        }

        LocalDate fourthOfJuly = LocalDate.of(dateOfRental.getYear(), Month.JULY, 4);

        if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return dateOfRental.equals(fourthOfJuly.minusDays(1));
        }

        if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return dateOfRental.equals(fourthOfJuly.plusDays(1));
        }

        return dateOfRental.equals(fourthOfJuly);
    }

    /**
     * Private helper method to determine if the date of rental is Memorial Day or not.
     * Memorial Day is defined as the first Monday in September.
     *
     * @param dateOfRental - date of rental
     * @return - true if the date of rental is Memorial Day, false otherwise.
     */
    private boolean isMemorialDay(LocalDate dateOfRental) {
        if (dateOfRental == null) {
            throw new IllegalArgumentException("Date of rental cannot be null when determining if date is Memorial Day.");
        }

        return dateOfRental.getMonth() == Month.SEPTEMBER
                && dateOfRental.getDayOfWeek() == DayOfWeek.MONDAY
                && dateOfRental.getDayOfMonth() <= 7;
    }
}
