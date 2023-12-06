import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PropertyInputHandlerTest {

    @Test
    public void testInputPropertyInformationWithHardcodedValues() {
        // Arrange
        String propertyType = "Home";
        boolean testMode = true;

        // Create the expected property object
        House expectedProperty = new House("123 Test St", 500000, 5000);

        // Create an instance of the PropertyInputHandler
        PropertyInputHandler<House> inputHandler = new PropertyInputHandler<>(propertyType, testMode);

        // Act
        House actualProperty = null;
        try {
            actualProperty = inputHandler.inputPropertyInformation();
        } catch (PropNotFoundException e) {
            // Handle exception if needed
        }

        // Assert
        //assertEquals(expectedProperty.getAddress(), actualProperty.getAddress());
        assertEquals(expectedProperty.getPrice(), actualProperty.getPrice());
        assertEquals(expectedProperty.getPropertyTax(), actualProperty.getPropertyTax());
    }


    @Test
    public void testInputPropertyInformationWithInvalidPropertyType() {
        // Arrange
        String propertyType = "InvalidType";
        boolean testMode = true;

        // Create an instance of the PropertyInputHandler
        PropertyInputHandler<House> inputHandler = new PropertyInputHandler<>(propertyType, testMode);

        // Act and Assert
        assertThrows(PropNotFoundException.class, inputHandler::inputPropertyInformation);
    }
}
