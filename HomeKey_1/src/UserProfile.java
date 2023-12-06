import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserProfile {
    private int annualSalary;
    private double mortgageInterestRate;
    private int monthlyInsuranceCost;
    private int downPayment;
    private int mortgageInsurance;

    // Constructor
    public UserProfile(int annualSalary, double mortgageInterestRate, int monthlyInsuranceCost, int downPayment, int mortgageInsurance) {
        this.annualSalary = annualSalary;
        this.mortgageInterestRate = mortgageInterestRate;
        this.monthlyInsuranceCost = monthlyInsuranceCost;
        this.downPayment = downPayment;
        this.mortgageInsurance = mortgageInsurance;
    }

    // Getters and setters for the user-specific variables

    public int getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(int annualSalary) {
        this.annualSalary = annualSalary;
    }

    public double getMortgageInterestRate() {
        return mortgageInterestRate;
    }

    public void setMortgageInterestRate(double mortgageInterestRate) {
        this.mortgageInterestRate = mortgageInterestRate;
    }

    public int getMonthlyInsuranceCost() {
        return monthlyInsuranceCost;
    }
    public int getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }

    public void setMonthlyInsuranceCost(int monthlyInsuranceCost) {
        this.monthlyInsuranceCost = monthlyInsuranceCost;
    }

    public int getMortgageInsurance() {
        return mortgageInsurance;
    }

    public void setMortgageInsurance(int mortgageInsurance) {
        this.mortgageInsurance = mortgageInsurance;
    }
    /**
     * Preconditions: annualSalary should be greater than zero
     * Postconditions: will calculate gross monthly income based on user's annualSalary
     * Outcome: will calculate the gross monthly income based on the annual salary provided
     */
    public double calculateMonthlyGrossIncome() {
        double monthlyGrossIncome = annualSalary / 12.0;
        return monthlyGrossIncome;
    }

    // --------------------- SAVE/LOAD DEFAULT USER PROFILE METHODS-------------------------//
/*    *//**
     * Saves the user profile to a new CSV file.
     *
     * @throws IOException If an I/O error occurs.
     *//*
    // Create new CSV to save default user profile data
    String filePath = "savedUser.csv";

    public void saveProfile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(annualSalary));
            writer.newLine();
            writer.write(String.valueOf(mortgageInterestRate));
            writer.newLine();
            writer.write(String.valueOf(monthlyInsuranceCost));
            writer.newLine();
            writer.write(String.valueOf(downPayment));
            writer.newLine();
            writer.write(String.valueOf(mortgageInsurance));
        }
    }

    *//**
     * Loads the user profile from the CSV file.
     *
     * @throws IOException If an I/O error occurs.
     *//*
    public void loadProfile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            annualSalary = Integer.parseInt(reader.readLine());
            mortgageInterestRate = Double.parseDouble(reader.readLine());
            monthlyInsuranceCost = Integer.parseInt(reader.readLine());
            downPayment = Integer.parseInt(reader.readLine());
            mortgageInsurance = Integer.parseInt(reader.readLine());
        }
    }*/
}
