import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadRealEstateDataTest {

    @Test
    public void testInsertRealEstateData() {
        String filePath = "JUnit_TEST/Test_RE_Data.csv";

        try (Connection connection = SQLiteManager.getConnection()) {
            // Call the method under test with the connection
            ReadRealEstateData.insertRealEstateData(filePath, connection);

            // Retrieve the inserted real estate data from the database
            List<RealEstateData> realEstateDataList = retrieveRealEstateData(connection);

            // Perform assertions to validate the results
            assertEquals(1, realEstateDataList.size());

            RealEstateData data = realEstateDataList.get(0);
            assertEquals("NH", data.getRegion());
            assertEquals("Dover", data.getCity());
            assertEquals("Home", data.getType());
            assertEquals(2022, data.getYear());
            assertEquals(LocalDate.parse("2/1/2020", DateTimeFormatter.ofPattern("M/d/yyyy")), data.getDate());
            assertEquals(290000, data.getMedianSalePrice());
            assertEquals(-0.11, data.getMedianSalePriceYoY());
            assertEquals(27, data.getHomesSold());
            assertEquals(32, data.getNewListings());
            assertEquals(-11.0, data.getNewListingsYoY());
            assertEquals(82, data.getInventory());
            assertEquals(52, data.getDaysOnMarket());
            assertEquals(0.99, data.getAvgSaleToList());
            assertEquals(1.0, data.getAvgSaleToListYoY());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<RealEstateData> retrieveRealEstateData(Connection connection) throws SQLException {
        List<RealEstateData> realEstateDataList = new ArrayList<>();

        String query = "SELECT * FROM RealEstateData";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                // Retrieve the data from the result set
                String region = resultSet.getString("region");
                String city = resultSet.getString("city");
                String type = resultSet.getString("type");
                int year = resultSet.getInt("year");
                LocalDate date = LocalDate.parse(resultSet.getString("date"), DateTimeFormatter.ofPattern("M/d/yyyy"));
                int medianSalePrice = resultSet.getInt("medianSalePrice");
                double medianSalePriceYoY = resultSet.getDouble("medianSalePriceYoY");
                int homesSold = resultSet.getInt("homesSold");
                int newListings = resultSet.getInt("newListings");
                double newListingsYoY = resultSet.getDouble("newListingsYoY");
                int inventory = resultSet.getInt("inventory");
                int daysOnMarket = resultSet.getInt("daysOnMarket");
                double avgSaleToList = resultSet.getDouble("avgSaleToList");
                double avgSaleToListYoY = resultSet.getDouble("avgSaleToListYoY");

                // Create a RealEstateData object and add it to the list
                RealEstateData data = new RealEstateData(region, city, type, year, date, medianSalePrice, medianSalePriceYoY,
                        homesSold, newListings, newListingsYoY, inventory, daysOnMarket, avgSaleToList, avgSaleToListYoY);
                realEstateDataList.add(data);
            }
        }

        return realEstateDataList;
    }
}
