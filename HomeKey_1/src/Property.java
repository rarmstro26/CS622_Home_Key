public abstract class Property {
    private String address;
    private int price;
    private int propertyTax;
    private String propertyType;


    public Property(String address, int price, int propertyTax, String propertyType) {
        this.address = address;
        this.price = price;
        this.propertyTax = propertyTax;
        this.propertyType = propertyType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPropertyTax() {
        return propertyTax;
    }

    public void setPropertyTax(int propertyTax) {
        this.propertyTax = propertyTax;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    public abstract void displayInfo();
}

