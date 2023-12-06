public class House extends Property {
    public House(String address, int price, int propertyTax) {
        super(address, price, propertyTax, "Home");
    }

    @Override
    public String getPropertyType() {
        return "Home";
    }

    @Override
    public void displayInfo() {
        System.out.println("Property Type: " + getPropertyType());
        System.out.println("Address: " + getAddress());
        System.out.println("Price: $" + getPrice());
        System.out.println("Property tax: $" + getPropertyTax());
        System.out.println("------------------------");
    }
}

