import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class RealEstateDataFilter {
    private int filterYear;
    private String filterCity;
    private String filterType;
    private int filterMaxResults;

    public RealEstateDataFilter(Connection connection, int filterYear, String filterCity, String filterType, int filterMaxResults) {
        this.filterYear = filterYear;
        this.filterCity = filterCity;
        this.filterType = filterType;
        this.filterMaxResults = filterMaxResults;
    }

    public void filterData(Connection connection) {
        /**
         * Preconditions:
         * - The `filterYear` variable must be set to a valid year.
         * - The `filterCity` variable must be set to a valid city name.
         * - The `filterType` variable must be set to a valid type.
         * - The `filterMaxResults` variable must be set to a non-negative integer.
         *
         * Postconditions:
         - Three filters are applied to the `RealEstateData` table based on the given conditions:
         *     - Filter 1: RealEstateData objects with a matching year and city are selected and limited to `filterMaxResults`.
         *     - Filter 2: RealEstateData objects with a matching year and type are selected and limited to `filterMaxResults`.
         *     - Filter 3: RealEstateData objects with the closest median sale price to the property price and same type.
         * - The filtered results are stored in separate lists (`filter1Results`, `filter2Results`, and `filter3Results`).
         * - The results of Filter 1 are printed to the console, including the filter conditions.
         * - The results of Filter 2 are printed to the console, including the filter conditions.
         * - The results of Filter 3 are printed to the console, including the filter conditions.
         */

        try (Statement statement = connection.createStatement()) {

            // Filter 1: filterYear + filterCity
            String filter1Query = "SELECT * FROM RealEstateData " +
                    "WHERE year = " + filterYear + " AND city = '" + filterCity + "' " +
                    "ORDER BY medianSalePrice DESC " +
                    "LIMIT " + filterMaxResults;

            ResultSet filter1Result = statement.executeQuery(filter1Query);
            List<RealEstateData> filter1Results = extractRealEstateData(filter1Result);

            // Print Filter 1 results
            System.out.println("SEARCH FILTER 1 RESULTS (" + filterYear + " + " + filterCity + "):");
            System.out.println("---------------------------------------------------");
            for (RealEstateData entry : filter1Results) {
                System.out.println(entry.toString());
            }

            // Filter 2: filterYear + filterType
            String filter2Query = "SELECT * FROM RealEstateData " +
                    "WHERE year = " + filterYear + " AND type = '" + filterType + "' " +
                    "ORDER BY medianSalePrice DESC " +
                    "LIMIT " + filterMaxResults;

            ResultSet filter2Result = statement.executeQuery(filter2Query);
            List<RealEstateData> filter2Results = extractRealEstateData(filter2Result);

            // Print Filter 2 results
            System.out.println("SEARCH FILTER 2 RESULTS (" + filterYear + " + " + filterType + "):");
            System.out.println("---------------------------------------------------");
            for (RealEstateData entry : filter2Results) {
                System.out.println(entry.toString());
            }

            // Filter 3: Query entry with the closest median sale price to property price for same type/year/city
            String filter3Query = "SELECT r.* FROM RealEstateData r " +
                    "JOIN Property p ON r.type = p.propertyType " +
                    "WHERE r.type = '" + filterType + "' " +
                    "AND r.year = " + filterYear + " " +
                    "AND r.city = '" + filterCity + "' " +
                    "ORDER BY ABS(r.medianSalePrice - p.price) " +
                    "LIMIT 1";

            ResultSet filter3Result = statement.executeQuery(filter3Query);
            List<RealEstateData> filter3Results = extractRealEstateData(filter3Result);

            // Print Filter 3 results
            System.out.println("SEARCH FILTER 3 RESULTS (Closest Match):");
            System.out.println("---------------------------------------------------");
            for (RealEstateData entry : filter3Results) {
                System.out.println(entry.toString());
            }

        } catch (SQLException e) {
            System.err.println("The system encountered an error when connecting to the SQLite database or executing the SQL statement.");
            e.printStackTrace();
        }
    }

    public void summaryData(Connection connection) {
        /**
         * Preconditions:
         * The `filterYear` variable must be set to a valid year.
         *
         * Postconditions:
         * Calculates and prints the following summary statistics for the specified year:
         *     - Total number of homes sold for `filterYear` for each type.
         *     - Average median sale price for `filterYear` for each type.
         *     - Total inventory for `filterYear` for each type.
         */

        try (Statement statement = connection.createStatement()) {
            // Calculate total number of homes sold for filterYear for each type
            String totalHomesSoldQuery = "SELECT type, SUM(homesSold) AS totalHomesSold " +
                    "FROM RealEstateData " +
                    "WHERE year = " + filterYear + " " +
                    "GROUP BY type";
            ResultSet totalHomesSoldResult = statement.executeQuery(totalHomesSoldQuery);

            System.out.println("SUMMARY REAL ESTATE DATA STATS:");
            System.out.println("---------------------------------------------------");
            System.out.println("(1) Total Sold for " + filterYear + ":");
            while (totalHomesSoldResult.next()) {
                String type = totalHomesSoldResult.getString("type");
                int totalHomesSold = totalHomesSoldResult.getInt("totalHomesSold");

                // Format totalHomesSold
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                String formattedTotalHomesSold = numberFormat.format(totalHomesSold);

                System.out.println("Total " + type + "s" + " Sold: " + formattedTotalHomesSold);
            }

            // Calculate average median sale price for filterYear for each type
            String averageMedianSalePriceQuery = "SELECT type, AVG(medianSalePrice) AS averageMedianSalePrice " +
                    "FROM RealEstateData " +
                    "WHERE year = " + filterYear + " " +
                    "GROUP BY type";
            ResultSet averageMedianSalePriceResult = statement.executeQuery(averageMedianSalePriceQuery);

            System.out.println("\n(2) Average Median Sale Price for " + filterYear + ":");
            while (averageMedianSalePriceResult.next()) {
                String type = averageMedianSalePriceResult.getString("type");
                double averageMedianSalePrice = averageMedianSalePriceResult.getDouble("averageMedianSalePrice");

                // Format averageMedianSalePrice
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                currencyFormat.setCurrency(Currency.getInstance("USD"));
                currencyFormat.setMaximumFractionDigits(0); // Set the maximum fraction digits to 0

                String formattedAverageMedianSalePrice = currencyFormat.format(averageMedianSalePrice);

                System.out.println("Average Median Sale Price for " + type + "s: " + formattedAverageMedianSalePrice);
            }

            // Calculate total inventory for filterYear
            String totalInventoryQuery = "SELECT type, SUM(inventory) AS totalInventory " +
                    "FROM RealEstateData " +
                    "WHERE year = " + filterYear + " " +
                    "GROUP BY type";
            ResultSet totalInventoryResult = statement.executeQuery(totalInventoryQuery);

            System.out.println("\n(3) Total Inventory for " + filterYear + ":");
            while (totalInventoryResult.next()) {
                String type = totalInventoryResult.getString("type");
                int totalInventory = totalInventoryResult.getInt("totalInventory");

                // Format totalInventory
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                String formattedTotalInventory = numberFormat.format(totalInventory);

                System.out.println("Total " + type + "s Inventory: " + formattedTotalInventory);
            }

        } catch (SQLException e) {
            System.err.println("The system encountered an error when connecting to the SQLite database or executing the SQL statement.");
            e.printStackTrace();
        }
    }

    private List<RealEstateData> extractRealEstateData(ResultSet resultSet) throws SQLException {
        /**
         * Preconditions:
         * - The `resultSet` parameter must contain valid data from the RealEstateData table.
         *
         * Postconditions:
         * - The method extracts RealEstateData objects from the `resultSet` and stores them in a list.
         * - The extracted RealEstateData objects are returned as a list.
         */

        List<RealEstateData> data = new ArrayList<>();

        while (resultSet.next()) {
            String region = resultSet.getString("region");
            String city = resultSet.getString("city");
            String type = resultSet.getString("type");
            int year = resultSet.getInt("year");
            String dateString = resultSet.getString("date");
            LocalDate date = LocalDate.parse(dateString); // Parse the date string into LocalDate
            int medianSalePrice = resultSet.getInt("medianSalePrice");
            double medianSalePriceYoY = resultSet.getDouble("medianSalePriceYoY");
            int homesSold = resultSet.getInt("homesSold");
            int newListings = resultSet.getInt("newListings");
            double newListingsYoY = resultSet.getDouble("newListingsYoY");
            int inventory = resultSet.getInt("inventory");
            int daysOnMarket = resultSet.getInt("daysOnMarket");
            double avgSaleToList = resultSet.getDouble("avgSaleToList");
            double avgSaleToListYoY = resultSet.getDouble("avgSaleToListYoY");

            RealEstateData realEstateData = new RealEstateData(region, city, type, year, date, medianSalePrice, medianSalePriceYoY,
                    homesSold, newListings, newListingsYoY, inventory, daysOnMarket, avgSaleToList, avgSaleToListYoY);

            data.add(realEstateData);
        }

        return data;
    }
}
