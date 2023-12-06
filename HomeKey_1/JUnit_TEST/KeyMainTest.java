import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class KeyMainTest {

    @Test
    public void testReadRealEstateData() {
        try (Connection connection = SQLiteManager.getConnection()) {
            ReadRealEstateData.insertRealEstateData("JUnit_TEST/Test_RE_Data.csv", connection);

            // Add your assertions to validate the data as needed
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputHouseInformation() {
        Property property = inputHouseInformation();

        // Add your assertions to validate the property object as needed
        assertEquals("123 Main St", property.getAddress());
        assertEquals(200000, property.getPrice());
        // Add more assertions as necessary
    }

    @Test
    public void testInputCondoInformation() {
        Property property = inputCondoInformation();

        // Add your assertions to validate the property object as needed
        assertEquals("456 Elm St", property.getAddress());
        assertEquals(300000, property.getPrice());
        // Add more assertions as necessary
    }

    @Test
    public void testInputMultiUnitInformation() {
        Property property = inputMultiUnitInformation();

        // Add your assertions to validate the property object as needed
        assertEquals("789 Oak St", property.getAddress());
        assertEquals(400000, property.getPrice());
        // Add more assertions as necessary
    }

    private Property inputHouseInformation() {
        String houseAddress = "123 Main St";
        int housePrice = 200000;
        int housePropertyTax = 5000;

        return new House(houseAddress, housePrice, housePropertyTax);
    }

    private Property inputCondoInformation() {
        String condoAddress = "456 Elm St";
        int condoPrice = 300000;
        int condoPropertyTax = 6000;
        int hoaFee = 200;

        return new Condo(condoAddress, condoPrice, condoPropertyTax, hoaFee);
    }

    private Property inputMultiUnitInformation() {
        String multiUnitAddress = "789 Oak St";
        int multiUnitPrice = 400000;
        int multiPropertyTax = 7000;
        int numUnits = 3;

        return new MultiUnit(multiUnitAddress, multiUnitPrice, multiPropertyTax, numUnits);
    }
    public void testMain() throws ExecutionException, InterruptedException {
        // Set up the input data
        String csvFilePath = "2020_RE_Data.csv";
        String propertyTypeInput = "Home\n";
        String houseAddressInput = "123 Main St\n";
        String housePriceInput = "200000\n";
        String housePropertyTaxInput = "5000\n";
        String annualSalaryInput = "60000\n";
        String mortgageInterestRateInput = "3.5\n";
        String monthlyInsuranceCostInput = "200\n";

        // Create the concatenated input string
        String userInput = propertyTypeInput + houseAddressInput + housePriceInput + housePropertyTaxInput
                + annualSalaryInput + mortgageInterestRateInput + monthlyInsuranceCostInput;

        // Convert the input string to an InputStream
        InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        // Invoke the main method
        KeyMain.main(null);

        // Add your assertions here to validate the output as needed
        // For example, you can capture the console output and assert on it
        // using System.out.println(...)
    }

//    @Test
//    public void testMainInvalidPropertyType() {
//        // Configure the necessary inputs for the test
//        String csvFilePath = "2020_RE_Data.csv"; // Replace with the actual file path
//        String invalidPropertyType = "InvalidType";
//
//        // Use a lambda expression to capture the exception thrown by the main method
//        PropNotFoundException exception = assertThrows(PropNotFoundException.class, () -> {
//            // Call the main method with invalid property type
//            KeyMain.main(new String[]{csvFilePath, invalidPropertyType});
//        });
//
//        // Perform assertions to validate the exception
//        String expectedErrorMessage = "Property type '" + invalidPropertyType + "' not found. Please try again and enter 'Home', 'Condo', or 'Multi-unit'";
//        String actualErrorMessage = exception.getMessage();
//        assertEquals(expectedErrorMessage, actualErrorMessage, "Expected error message does not match actual error message");
//    }
}


