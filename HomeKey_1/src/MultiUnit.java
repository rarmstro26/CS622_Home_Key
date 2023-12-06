public class MultiUnit extends Property {
    private int numUnits;
    public MultiUnit(String address, int price, int propertyTax, int numUnits) {
        super(address, price, propertyTax, "Multi-unit");
        this.numUnits = numUnits;
    }
    public int getNumUnits(){
        return numUnits;
    }
    public void setNumUnits(int numUnits){
        this.numUnits = numUnits;
    }

    @Override
    public String getPropertyType() {
        return "Multi-Unit";
    }

    @Override
    public void displayInfo() {
        System.out.println("Property Type: " + getPropertyType());
        System.out.println("Address: " + getAddress());
        System.out.println("Price: $" + getPrice());
        System.out.println("Property tax: $" + getPropertyTax());
        System.out.println("Number of Units: " + numUnits);
        System.out.println("------------------------");
    }
}

