import java.time.LocalDate;
import java.io.Serializable;


public class RealEstateData implements Serializable {
    private String region;
    private String city;
    private String type;
    private int year;
    private LocalDate date;
    private int medianSalePrice;
    private double medianSalePriceYoY;
    private int homesSold;
    private int newListings;
    private double newListingsYoY;
    private int inventory;
    private int daysOnMarket;
    private double avgSaleToList;
    private double avgSaleToListYoY;


    public RealEstateData(String region, String city, String type, int year, LocalDate date, int medianSalePrice, double medianSalePriceYoY, int homesSold, int newListings, double newListingsYoY, int inventory, int daysOnMarket, double avgSaleToList, double avgSaleToListYoY) {
        this.region = region;
        this.city = city;
        this.type = type;
        this.year = year;
        this.date = date;
        this.medianSalePrice = medianSalePrice;
        this.medianSalePriceYoY = medianSalePriceYoY;
        this.homesSold = homesSold;
        this.newListings = newListings;
        this.newListingsYoY = newListingsYoY;
        this.inventory = inventory;
        this.daysOnMarket = daysOnMarket;
        this.avgSaleToList = avgSaleToList;
        this.avgSaleToListYoY = avgSaleToListYoY;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMedianSalePrice() {
        return medianSalePrice;
    }

    public double getMedianSalePriceYoY() {
        return medianSalePriceYoY;
    }

    public int getHomesSold() {
        return homesSold;
    }

    public int getNewListings() {
        return newListings;
    }

    public double getNewListingsYoY() {
        return newListingsYoY;
    }

    public int getInventory() {
        return inventory;
    }

    public int getDaysOnMarket() {
        return daysOnMarket;
    }

    public double getAvgSaleToList() {
        return avgSaleToList;
    }

    public double getAvgSaleToListYoY() {
        return avgSaleToListYoY;
    }

    @Override
    public String toString() {
        return "Real Estate Data Entry:\n" +
                //"Region: " + region + "\n" +
                "City: " + city + "\n" +
                "Type: " + type + "\n" +
                //"Year: " + year + "\n" +
                "Date: " + date + "\n" +
                "Median Sale Price: " + medianSalePrice + "\n" +
//                "Median Sale Price YoY: " + medianSalePriceYoY + "\n" +
//                "Homes Sold: " + homesSold + "\n" +
//                "New Listings: " + newListings + "\n" +
//                "New Listings YoY: " + newListingsYoY + "\n" +
                "Inventory: " + inventory + "\n" +
                "Days on Market: " + daysOnMarket + "\n";
//                "Average Sale to List: " + avgSaleToList + "\n" +
//                "Average Sale to List YoY: " + avgSaleToListYoY + "\n";
    }
}
