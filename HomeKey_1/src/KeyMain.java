//import javafx.application.Application;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * KeyMain is the driver file for the 'HomeKey' program.
 */

public class KeyMain {

    // Setup scanner object for console input/output methods
    private static Scanner scanner = new Scanner(System.in);

    static int inputNumberProperties() {
        /**
         * Preconditions: None.
         * Postconditions: An integer representing the user's desired number of properties is returned.
         */
        System.out.print("Enter number of properties you would like to compare (up to 3):");
        return scanner.nextInt();
    }

    static String inputPropertyType() {
        /**
         * Preconditions: None.
         * Postconditions: A string representing the selected property type is returned.
         */
        System.out.print("Select a property type: Home, Condo, or Multi-unit: ");
        return scanner.nextLine();
    }

    static UserProfile inputUserProfile() {
        /**
         * Preconditions: None.
         * Postconditions: An object representing the user's information with associated information is returned.
         */
        System.out.println("Enter the following income information:");
        System.out.print("Enter your annual salary: $");
        int annualSalary = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter your mortgage interest rate (%): ");
        double mortgageInterestRate = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter estimated monthly homeowners insurance cost: $");
        int monthlyInsuranceCost = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter down payment amount: $");
        int downPayment = scanner.nextInt();
        scanner.nextLine(); // consume newline
        // Collect mortgage insurance if downpayment < 20% of property price
        System.out.print("Enter estimated monthly mortgage insurance (if <20% down): $");
        int mortgageInsurance = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.println(); // formatting

        return new UserProfile(annualSalary, mortgageInterestRate, monthlyInsuranceCost, downPayment, mortgageInsurance);
    }

    // Input methods for filtering comparison real estate data
    static int inputFilterYear() {
        /**
         * Preconditions: None.
         * Postconditions: An integer representing the user's desired filter year is returned.
         */
        System.out.print("Enter a year between 2018-2023 to see filtered real estate data to compare: ");
        return scanner.nextInt();
    }

    static String inputFilterCity() {
        /**
         * Preconditions: None.
         * Postconditions: A string representing the user's desired filter city is returned.
         */
        System.out.print("SEARCH FILTER 1: \n-\tEnter the City you would like to see comparison data for (Dover, Hampton, Manchester, Portsmouth, Salem): ");
        return scanner.nextLine();
    }

    static String inputFilterType() {
        /**
         * Preconditions: None.
         * Postconditions: A string representing the user's desired filter type is returned.
         */
        System.out.print("SEARCH FILTER 2: \n-\tEnter the type of property (Home, Condo, Multi-unit) to see comparison data for: ");
        return scanner.nextLine();
    }

    static int inputFilterMaxResults(){
        /**
         * Preconditions: None.
         * Postconditions: An integer representing the user's desired number limit of filter results is returned.
         */
        System.out.print("How many search results would you like to see? (5, 10, 15.. or ALL): ");
        String userInput = scanner.next();
        // Handle showing ALL results
        if (userInput.equalsIgnoreCase("all")) {
            return Integer.MAX_VALUE;
        } else {
            return Integer.parseInt(userInput);
        }
    }

    public static void main(String[] args)  {

        // ------------------------------ USER INPUT--------------------------------------------//
        // Create new ArrayList of Property objects
        ArrayList<Property> properties = new ArrayList<>();

        // Prompt user for number of properties to compare
        int numProperties = inputNumberProperties();
        scanner.nextLine(); // Consume newline

        // Loop through to collect user input based on number of properties to compare
        for (int i = 0; i < numProperties; i++){

            // Get user input for PROPERTY TYPE - trim for leading/trailing spaces
            String propertyType = inputPropertyType().trim();

            // PROD: propertyType. Arguments for TEST: Home, 'true' for testMode)
            //PropertyInputHandler<Property> propertyInputHandler = new PropertyInputHandler<>("Home", true);
            PropertyInputHandler<Property> propertyInputHandler = new PropertyInputHandler<>(propertyType, false);

            Property property = null;

            // Returns the applicable property type object from InputHandler
            try {
                property = propertyInputHandler.inputPropertyInformation();
            } catch (PropNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Add property to the properties list:
            properties.add(property);
        }

        // PROD - Create userProfile object
        UserProfile uProfile = inputUserProfile();

        // TEST - hardcode annual salary, mortgage int rate, mo insurance cost, down payment
        //UserProfile uProfile = new UserProfile(150000, 5.5, 100, 80000, 120);

        // Prompt to collect filter year
        int filterYear = inputFilterYear();
        scanner.nextLine(); // Consume newline

        // Prompt to collect filter city
        String filterCity = inputFilterCity();

        // Prompt to collect filter type
        String filterType = inputFilterType().trim();

        // Prompt to collect search results limit
        int filterMaxResults = inputFilterMaxResults();
        scanner.nextLine(); // Consume newline

        //------------------------------------ FORMATTED OUTPUT -----------------------------------//

        // Formatted Table 1 - PROPERTY & LOAN INFORMATION
        System.out.println("\nProperty & Loan Information:\n");
        System.out.printf("%-18s %-18s %-20s %-15s %-18s %-15s %-15s %-18s\n",
                "Property Type", "Home Price", "Address", "Property Tax", "HOA Fee", "Num Units", "Interest Rate", "Insurance Cost");

        // Iterate over the properties list
        for (Property prop : properties) {
            int hoaFee = 0;
            int numUnits = 0;

            // Downcasting to condo/multiunit objects in order to access specific subclass methods
            if (prop instanceof Condo) {
                hoaFee = ((Condo) prop).getHoa_fee();
            }
            if (prop instanceof MultiUnit) {
                numUnits = ((MultiUnit) prop).getNumUnits();
            }

            // Formatted print statement for property and loan information
            System.out.printf("%-18s %-18d %-20s %-15d %-18d %-15d %-15.1f %-18d\n",
                    prop.getPropertyType(), prop.getPrice(),prop.getAddress(), prop.getPropertyTax(), hoaFee, numUnits,
                    uProfile.getMortgageInterestRate(), uProfile.getMonthlyInsuranceCost());
        }

        // Formatted Table 2 - AFFORDABILITY CALCULATIONS
        System.out.println("\nAffordability Calculations:\n");
        System.out.printf("%-18s %-20s %-18s %-18s %-18s %-18s\n",
                "Annual Salary", "Monthly Gross Income", "Monthly Mortgage",
                "Monthly Total Cost", "Percent of Income", "Remaining Income");

        // Iterate over the properties list
        for (Property prop : properties) {

            // Instantiate the affordCalculator object passing property type + user profile
            AffordCalculator<Property, UserProfile> affordCalculator = new AffordCalculator<>(prop, uProfile);

            // Calculate monthly mortgage
            double monthlyMortgage = affordCalculator.calculateMonthlyMortgage();

            // Calculate monthly total cost
            double monthlyTotalCost = affordCalculator.calculateMonthlyTotalCost();

            // Calculate percent of income
            double percentOfIncome = affordCalculator.calculatePercentOfIncome();

            // Calculate monthly income after housing costs
            double monthlyIncomeAfterCost = affordCalculator.calculateMonthlyIncomeAfterCost();

            // Formatted print statement for affordability calculations
            System.out.printf("%-18d %-20.1f %-18.2f %-18.2f %-18.1f %-18.1f",
                    uProfile.getAnnualSalary(), uProfile.calculateMonthlyGrossIncome(),
                    monthlyMortgage, monthlyTotalCost, percentOfIncome, monthlyIncomeAfterCost);
            System.out.println(); // formatting
        }

        // ----------------------- FILE I/O - PARSE CSV FILE + CREATE DB TABLES + INSERT TO DB --------------------------------------//

        try (Connection connection = SQLiteManager.getConnection()) {
            // Input CSV file path
            String csvFilePath = "2018_RE_Data.csv";

            // File IO concurrency section print out
            //System.out.println("\nLoading Real Estate Data From File - Parallel Processing:\n");

            // Read input CSV file and save realestatedata objects to data list
            //List<RealEstateData> data = ReadRealEstateData.readCSV(csvFilePath);

            // Initialize the SQLite database and create tables
            SQLiteManager.initializeDatabase();

            // Insert parsed CSV RealEstateData data to RealEstateData database table
            ReadRealEstateData.insertRealEstateData(csvFilePath, connection);

            // Insert all Property objects from user into RealEstateData 'Property' database table
            SQLiteManager.insertProperty(properties);

        } catch (SQLException e) {
            System.err.println("The system encountered an error when connecting to the SQLite database: " + e.getMessage());
            e.printStackTrace();
        }

        // ------------------ (RETIRED FOR REL_6) WRITE + READ OBJECT TO BINARY -----------------------------//

        // Setup new binary output filepath
        //String saveFilePath = "realestateobj_data.bin";

        // Write real estate data to binary
        //RealEstateDataIO.saveRealEstateData(saveFilePath, data);

        // Read real estate data back in from binary
        //List<RealEstateData> loadedData = RealEstateDataIO.loadRealEstateData(saveFilePath);

        // ---------------- (RETIRED FOR REL_6) REAL ESTATE COMPARISON DATA - FILTERING + CONCURRENCY -----------//

        // Create new dataFilter object to pass along filter attributes provided by user
        //RealEstateDataFilter dataFilter = new RealEstateDataFilter(loadedData, filterYear, filterCity, filterType, filterMaxResults);

        // Call filterData to filter data and display results to the console
        //dataFilter.filterData();

        // ---------------------- DATABASE SELECTION, ORDERING, AGGREGATION OF DATA -----------------------------//

        try (Connection connection = SQLiteManager.getConnection()) {

            // Create a RealEstateDataFilter object to filter results
            RealEstateDataFilter dataFilter = new RealEstateDataFilter(connection, filterYear, filterCity, filterType, filterMaxResults);

            // Call filterData, pass DB connection, to filter data and display results for Filter 1-3 to the console
            dataFilter.filterData(connection);

            // Call summaryData, pass DB connection, to calculate and display realestatedata summary info
            dataFilter.summaryData(connection);

        } catch (SQLException e) {
            System.err.println("The system encountered an error when connecting to the SQLite database: " + e.getMessage());
            e.printStackTrace();
        }

        // ------------------------------------ (TO BE BUILT) JAVA FX OUTPUT ---------------------------------------//

/*        // Format the table for JavaFX display
        StringBuilder formattedTableBuilder = new StringBuilder();
        formattedTableBuilder.append("Property & Loan Information:\n");
        formattedTableBuilder.append(String.format("%-18s %-20s %-15s %-18s %-15s %-15s %-18s\n",
                "Property Type", "Address", "Property Tax", "HOA Fee", "Num Units", "Interest Rate", "Insurance Cost"));
        for (Property prop : properties) {
            // Append property and loan information to the formatted table
            formattedTableBuilder.append(String.format("%-18s %-20s %-15d %-18d %-15d %-15.1f %-18d\n",
                    prop.getPropertyType(), prop.getAddress(), prop.getPropertyTax(), prop.getPrice(), prop.getPrice(),
                    uProfile.getMortgageInterestRate(), uProfile.getMonthlyInsuranceCost()));
        }

        // Create the JavaFXDisplay instance with the formatted table
        String formattedTable = formattedTableBuilder.toString();
        JavaFXDisplay display = new JavaFXDisplay(formattedTable);

        // Launch the JavaFX application
        Application.launch(JavaFXDisplay.class, args);*/

    }
}

