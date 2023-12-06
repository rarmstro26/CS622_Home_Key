public class PropNotFoundException extends Exception {
    private String propertyName;

    public PropNotFoundException(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String getMessage() {
        return "Property type '" + propertyName + "' not found. Please try again and enter " +
                "'Home', 'Condo', or 'Multi-unit'";
    }
}
