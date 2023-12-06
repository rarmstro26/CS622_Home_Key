import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLiteManager {
    private static final String DATABASE_URL = "jdbc:sqlite:/Users/rarmstro/Documents/CS622_Advanced_Prog_Techniques/MOD_6/realestatedata.db";

    public static Connection getConnection() throws SQLException {
        /**
         * Preconditions: None
         *
         * Postconditions:
         * - Returns a valid connection to the SQLite database.
         * - Throws an SQLException if a connection cannot be established.
         */
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void initializeDatabase() {
        /**
         * Preconditions: None.
         *
         * Postconditions:
         * - Initializes the SQLite database by creating the RealEstateData and Property tables if they do not exist.
         * - The RealEstateData table has the following columns: id, region, city, type, year, date, medianSalePrice,
         *   medianSalePriceYoY, homesSold, newListings, newListingsYoY, inventory, daysOnMarket, avgSaleToList,
         *   avgSaleToListYoY.
         * - The Property table has the following columns: id, address, price, propertyTax, propertyType.
         * - The propertyType column in the Property table references the type column in the RealEstateData table.
         */
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS RealEstateData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "region TEXT, " +
                    "city TEXT, " +
                    "type TEXT, " +
                    "year INTEGER, " +
                    "date TEXT, " +
                    "medianSalePrice INTEGER, " +
                    "medianSalePriceYoY REAL, " +
                    "homesSold INTEGER, " +
                    "newListings INTEGER, " +
                    "newListingsYoY REAL, " +
                    "inventory INTEGER, " +
                    "daysOnMarket INTEGER, " +
                    "avgSaleToList REAL, " +
                    "avgSaleToListYoY REAL" +
                    ")");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Property (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "address TEXT, " +
                    "price INTEGER, " +
                    "propertyTax INTEGER, " +
                    "propertyType TEXT, " +
                    "FOREIGN KEY (propertyType) REFERENCES RealEstateData (type)" +
                    ")");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void insertProperty(ArrayList<Property> properties) {
        /**
         * Preconditions:
         * - The `properties` parameter is a valid ArrayList of Property objects.
         *
         * Postconditions:
         * - Inserts the property data from the `properties` list into the Property table in the database.
         * - Uses a PreparedStatement for batch insert to improve performance.
         * - Each Property object is inserted with its address, price, propertyTax, and propertyType values.
         * - Prints a success message to the console if the insert operation is successful.
         * - Throws an SQLException and prints an error message if the insert operation fails.
         */

        try (Connection connection = getConnection()) {
            // Create a PreparedStatement for batch insert
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Property (address, price, propertyTax, propertyType) VALUES (?, ?, ?, ?)");

            // Iterate over the properties list
            for (Property property : properties) {
                // Set the values for the PreparedStatement
                statement.setString(1, property.getAddress());
                statement.setInt(2, property.getPrice());
                statement.setInt(3, property.getPropertyTax());
                statement.setString(4, property.getPropertyType());
                statement.addBatch(); // Add the statement to the batch
            }

            // Execute the batch insert
            statement.executeBatch();

            // Confirm successful insert to console
            System.out.println("Successfully inserted property data to the database.\n");

        } catch (SQLException e) {
            System.err.println("Error inserting properties into the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
