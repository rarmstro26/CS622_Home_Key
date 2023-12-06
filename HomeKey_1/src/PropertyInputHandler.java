import java.util.Scanner;

public class PropertyInputHandler<T extends Property> {
    private static Scanner scanner = new Scanner(System.in);
    private final String propertyType;
    private final boolean testMode;

    public PropertyInputHandler(String propertyType, boolean testMode) {
        this.propertyType = propertyType;
        this.testMode = testMode;
    }

    public T inputPropertyInformation() throws PropNotFoundException {
        /**
         * Preconditions: The propertyType parameter must be a valid property type (e.g., "Home", "Condo", "Multi-unit").
         * The testMode parameter must be a boolean value indicating whether the class is operating in test mode or not.
         * Postconditions: The method prompts the user for input and returns a property object of type based on the user-provided information.
         * Postcondition2: If testMode is true, the method returns a property object of type T with hardcoded test values corresponding to the specified propertyType.
         * Postcondition3: If the specified propertyType is not valid, the method throws a PropNotFoundException.
         */

        T property;

        if (testMode) {
            // Use hardcoded test values
            if (propertyType.equalsIgnoreCase("Home")) {
                property = (T) new House("123 Test St", 500000, 5000);
            } else if (propertyType.equalsIgnoreCase("Condo")) {
                property = (T) new Condo("456 Test Ave", 350000, 3000, 150);
            } else if (propertyType.equalsIgnoreCase("Multi-unit")) {
                property = (T) new MultiUnit("789 Test Blvd", 800000, 8000, 2);
            } else {
                throw new PropNotFoundException(propertyType);
            }
        } else {
            // Prompt user for input
            if (propertyType.equalsIgnoreCase("Home")) {
                System.out.print("Enter the information for a Home:\nAddress: ");
                String address = scanner.nextLine();
                System.out.print("Price: $");
                int price = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter estimated (yearly) property tax: $");
                int propertyTax = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println();

                property = (T) new House(address, price, propertyTax);

            } else if (propertyType.equalsIgnoreCase("Condo")) {
                System.out.print("Enter the information for a Condo:\nAddress: ");
                String address = scanner.nextLine();
                System.out.print("Price: $");
                int price = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter estimated (yearly) property tax: $");
                int propertyTax = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("HOA Fee: $");
                int hoaFee = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println();

                property = (T) new Condo(address, price, propertyTax, hoaFee);

            } else if (propertyType.equalsIgnoreCase("Multi-unit")) {
                System.out.print("Enter the information for a Multi-unit property:\nAddress: ");
                String address = scanner.nextLine();
                System.out.print("Price: $");
                int price = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter estimated (yearly) property tax: $");
                int propertyTax = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter number of units for property: ");
                int numUnits = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                System.out.println();

                property = (T) new MultiUnit(address, price, propertyTax, numUnits);

            } else {
                throw new PropNotFoundException(propertyType);
            }
        }

        return property;
    }
}
