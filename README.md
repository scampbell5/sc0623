# SC0723
[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://scampbell5.github.io/sc0723/javadoc/)

Programming demo implemented in Java.

## Building Project

Building the project will also run the JUnit test suite.

### Mac
```
./gradlew clean build
```

### Windows
```
./gradlew.bat clean build
```

## Running the Application
Running the application will print the results for Test Scenarios 2 - 6 by using the `ToolRentalAgreement::print` method.

<details>

<summary>Application Output</summary>

```
Test Scenario 2

Tool code: LADW
Tool type: Ladder
Tool brand: Werner
Rental days: 3
Checkout date: 07/02/2015
Due date: 07/05/2015
Daily rental charge: $1.99
Charge days: 2
Pre-discount charge: $3.98
Discount percent: 10%
Discount amount: $0.40
Final charge: $3.58

Test Scenario 3

Tool code: CHNS
Tool type: Chainsaw
Tool brand: Stihl
Rental days: 5
Checkout date: 07/02/2020
Due date: 07/07/2020
Daily rental charge: $1.49
Charge days: 3
Pre-discount charge: $4.47
Discount percent: 25%
Discount amount: $1.12
Final charge: $3.35

Test Scenario 4

Tool code: JAKD
Tool type: Jackhammer
Tool brand: DeWalt
Rental days: 6
Checkout date: 09/03/2015
Due date: 09/09/2015
Daily rental charge: $2.99
Charge days: 3
Pre-discount charge: $8.97
Discount percent: 0%
Discount amount: $0.00
Final charge: $8.97

Test Scenario 5

Tool code: JAKR
Tool type: Jackhammer
Tool brand: Ridgid
Rental days: 9
Checkout date: 07/02/2015
Due date: 07/11/2015
Daily rental charge: $2.99
Charge days: 5
Pre-discount charge: $14.95
Discount percent: 0%
Discount amount: $0.00
Final charge: $14.95

Test Scenario 6

Tool code: JAKR
Tool type: Jackhammer
Tool brand: Ridgid
Rental days: 4
Checkout date: 07/02/2020
Due date: 07/06/2020
Daily rental charge: $2.99
Charge days: 1
Pre-discount charge: $2.99
Discount percent: 50%
Discount amount: $1.50
Final charge: $1.49
```
</details>

### Mac
```
./gradlew run
```

### Windows
```
./gradlew.bat run
```
