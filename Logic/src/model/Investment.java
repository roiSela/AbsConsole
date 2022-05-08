package model;

import java.io.Serializable;

public class Investment implements Serializable {

    private String nameOfCustomer;
    private double sizeOfInvestment;

    public  Investment(String nameOfCustomer, double sizeOfInvestment) {
        this.nameOfCustomer = nameOfCustomer;
        this.sizeOfInvestment = sizeOfInvestment;
    }
    public void addInvestment(double addToSizeOfInvestment) {
        this.sizeOfInvestment += addToSizeOfInvestment;
    }
    public double getSizeOfInvestment() {
        return sizeOfInvestment;
    }
    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

}
