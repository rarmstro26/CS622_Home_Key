import java.text.NumberFormat;

public class AffordCalculator<T extends Property, U extends UserProfile> {

    private T property;
    private U userProfile;

    public AffordCalculator(T property, U userProfile) {
        this.property = property;
        this.userProfile = userProfile;
    }

    public double calculateMonthlyMortgage() {
        /**
         * Preconditions: None.
         * Postconditions: Returns the calculated monthly mortgage amount based on the property price,
         * down payment, mortgage interest rate, and loan term.
         */
        double loanAmount = property.getPrice() - userProfile.getDownPayment();
        double monthlyInterestRate = userProfile.getMortgageInterestRate() / 100.0 / 12.0;
        int loanTermInMonths = 30 * 12; // Assuming 30-year mortgage
        double monthlyMortgage = (loanAmount * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));
        return monthlyMortgage;
    }

    public double calculateMonthlyTotalCost() {
        /**
         * Preconditions: None.
         * Postconditions: Returns the calculated monthly total cost, which includes the monthly mortgage,
         * property tax, insurance cost, mortgage insurance cost, and HOA fee (if applicable).
         */
        double monthlyMortgage = calculateMonthlyMortgage();
        double monthlyPropertyTax = property.getPropertyTax() / 12.0;
        double monthlyInsuranceCost = userProfile.getMonthlyInsuranceCost();
        double monthlyMortgageInsurance = userProfile.getMortgageInsurance();
        double monthlyHOAFee = 0;

        // Handle Condo specific attribute
        if (property instanceof Condo) {
            monthlyHOAFee = ((Condo) property).getHoa_fee();
        }

        return monthlyMortgage + monthlyPropertyTax + monthlyInsuranceCost + monthlyMortgageInsurance + monthlyHOAFee;
    }

    public double calculatePercentOfIncome() {
        /**
         * Preconditions: None.
         * Postconditions: Returns the calculated percentage of monthly income spent on housing costs.
         */
        double monthlyTotalCost = calculateMonthlyTotalCost();
        double monthlyGrossIncome = userProfile.calculateMonthlyGrossIncome();
        double percentOfIncome = (monthlyTotalCost / monthlyGrossIncome) * 100;
        return percentOfIncome;
    }

    // Additional currency formatting helper methods not needed at this time
//    public String getFormattedMonthlyMortgage() {
//        double monthlyMortgage = calculateMonthlyMortgage();
//        return formatCurrency(monthlyMortgage);
//    }
//
//    public String getFormattedMonthlyTotalCost() {
//        double monthlyTotalCost = calculateMonthlyTotalCost();
//        return formatCurrency(monthlyTotalCost);
//    }
//
//    private String formatCurrency(double amount) {
//        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
//        return currencyFormatter.format(amount);
//    }

    // Add calc for monthly gross income after housing costs
    public double calculateMonthlyIncomeAfterCost() {
        /**
         * Preconditions: None.
         * Postconditions: Returns the calculated monthly income after subtracting the monthly total cost from
         * monthly gross income.
         */
        double monthlyGrossIncome = userProfile.calculateMonthlyGrossIncome();
        double monthlyTotalCost = calculateMonthlyTotalCost();
        return monthlyGrossIncome - monthlyTotalCost;
    }
}
