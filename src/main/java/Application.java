import models.ToolRentalAgreement;
import services.*;

import java.time.LocalDate;

public class Application {

    /**
     * Simple application to print out the Tool Rental Agreements that are created for Test Scenarios 2 - 6
     *
     * @param args - any application arguments
     */
    public static void main(String[] args) {

        ToolCheckoutService toolCheckoutService = new ToolCheckoutService(new ToolService(), new ToolTypeChargeService(), new RentalAmountService(), new RentalDateService());

        ToolRentalAgreement testScenario2 = toolCheckoutService.checkoutTool("LADW", 3, 10, LocalDate.of(2015, 7, 2));
        ToolRentalAgreement testScenario3 = toolCheckoutService.checkoutTool("CHNS", 5, 25, LocalDate.of(2020, 7, 2));
        ToolRentalAgreement testScenario4 = toolCheckoutService.checkoutTool("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        ToolRentalAgreement testScenario5 = toolCheckoutService.checkoutTool("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        ToolRentalAgreement testScenario6 = toolCheckoutService.checkoutTool("JAKR", 4, 50, LocalDate.of(2020, 7, 2));

        System.out.println("Test Scenario 2\n");
        testScenario2.print();
        System.out.println();

        System.out.println("Test Scenario 3\n");
        testScenario3.print();
        System.out.println();

        System.out.println("Test Scenario 4\n");
        testScenario4.print();
        System.out.println();

        System.out.println("Test Scenario 5\n");
        testScenario5.print();
        System.out.println();

        System.out.println("Test Scenario 6\n");
        testScenario6.print();
    }
}
