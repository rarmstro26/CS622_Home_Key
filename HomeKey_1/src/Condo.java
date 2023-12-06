public class Condo extends Property {

    private int hoa_fee;

    public Condo(String address, int price, int propertyTax, int hoa_fee) {
        super(address, price, propertyTax, "Condo");
        this.hoa_fee = hoa_fee;
    }
    public int getHoa_fee() {
        return hoa_fee;
    }
    public void setHoa_fee(int hoa_fee) {
        this.hoa_fee = hoa_fee;
    }

    @Override
    public String getPropertyType() {
        return "Condo";
    }

    @Override
    public void displayInfo() {
        System.out.println("Property Type: " + getPropertyType());
        System.out.println("Address: " + getAddress());
        System.out.println("Price: $" + getPrice());
        System.out.println("HOA Fee: $" + getHoa_fee());
        System.out.println("------------------------");
    }
}
