public class Investment {

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
