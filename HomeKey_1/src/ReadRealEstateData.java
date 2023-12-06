import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReadRealEstateData {

    private static final String INSERT_QUERY = "INSERT INTO RealEstateData (region, city, type, year, date, medianSalePrice, " +
            "medianSalePriceYoY, homesSold, newListings, newListingsYoY, inventory, daysOnMarket, avgSaleToList, " +
            "avgSaleToListYoY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static void insertRealEstateData(String filePath, Connection connection) {
        /**
         * Preconditions: filePath contains the path to a valid format CSV file specified as specified in KeyMain.
         * Postconditions: The data from the CSV file is inserted into the RealEstateData table in the SQLite database.
         * Any exceptions that occur during the file reading or data insertion process are handled.
         */

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {

            // Skip the header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Parse the values from the CSV line
                String region = values[0].trim();
                String city = values[1].trim();
                String type = values[2].trim();
                LocalDate date = LocalDate.parse(values[3].trim(), DateTimeFormatter.ofPattern("M/d/yyyy"));
                int year = date.getYear();
                int medianSalePrice = Integer.parseInt(values[4].trim());
                double medianSalePriceYoY = Double.parseDouble(values[5].trim());
                int homesSold = Integer.parseInt(values[6].trim());
                int newListings = Integer.parseInt(values[7].trim());
                double newListingsYoY = Double.parseDouble(values[8].trim());
                int inventory = Integer.parseInt(values[9].trim());
                int daysOnMarket = Integer.parseInt(values[10].trim());
                double avgSaleToList = Double.parseDouble(values[11].trim());
                double avgSaleToListYoY = Double.parseDouble(values[12].trim());

                // Set the parameter values for the prepared statement
                statement.setString(1, region);
                statement.setString(2, city);
                statement.setString(3, type);
                statement.setInt(4, year);
                statement.setString(5, date.toString());
                statement.setInt(6, medianSalePrice);
                statement.setDouble(7, medianSalePriceYoY);
                statement.setInt(8, homesSold);
                statement.setInt(9, newListings);
                statement.setDouble(10, newListingsYoY);
                statement.setInt(11, inventory);
                statement.setInt(12, daysOnMarket);
                statement.setDouble(13, avgSaleToList);
                statement.setDouble(14, avgSaleToListYoY);

                // Execute the prepared statement to insert the data
                statement.executeUpdate();
            }
            // Success message to console
            System.out.println("\nSuccessfully inserted real estate data to database.\n");

        } catch (IOException e) {
            System.err.println("The system encountered an error when attempting to read from CSV file: " + filePath);
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("The system encountered an error when connecting to the SQLite database or executing the SQL statement.");
            e.printStackTrace();
        }
    }
}
