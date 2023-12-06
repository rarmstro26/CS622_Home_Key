import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLiteManagerTest {

    @Test
    public void testInsertProperty() {
        // Hardcode the database URL
        String databaseURL = "jdbc:sqlite:/Users/rarmstro/Documents/CS622_Advanced_Prog_Techniques/MOD_6/realestatedata.db";

        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            // Create a test table
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS TestProperty (id INTEGER PRIMARY KEY AUTOINCREMENT, address TEXT, price INTEGER)");
            }

            // Create some test properties
            ArrayList<Property> properties = new ArrayList<>();
            properties.add(new TestProperty("123 Main St", 200000, 5000, "Home"));
            properties.add(new TestProperty("456 Elm St", 300000, 6000, "Condo"));
            properties.add(new TestProperty("789 Oak St", 400000, 7000, "MultiUnit"));

            // Insert the properties
            SQLiteManager.insertProperty(properties);

            // Verify the data has been inserted correctly
            try (Statement selectStatement = connection.createStatement()) {
                ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM TestProperty");

                // Verify the number of inserted rows
                int rowCount = 0;
                while (resultSet.next()) {
                    rowCount++;
                }
                assertEquals(properties.size(), rowCount, "Number of inserted rows does not match");

                // Verify the inserted data
                resultSet = selectStatement.executeQuery("SELECT * FROM TestProperty");
                int index = 0;
                while (resultSet.next()) {
                    String address = resultSet.getString("address");
                    int price = resultSet.getInt("price");

                    // Verify the address and price of each property
                    assertEquals(properties.get(index).getAddress(), address, "Address does not match");
                    assertEquals(properties.get(index).getPrice(), price, "Price does not match");

                    index++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    // Concrete subclass of Property for testing
    private static class TestProperty extends Property {
        public TestProperty(String address, int price, int propertyTax, String propertyType) {
            super(address, price, propertyTax, propertyType);
        }

        @Override
        public void displayInfo() {

        }
    }
}
