public class Money {
    private String product;
    private double expense;

    public Money(String prod, double ex) {
        product = prod;
        expense = ex;
    }

    public void setMoney(String prod, double ex) {
        product = prod;
        expense = ex;
    }

    public Money getMoney() {
        return this;
    }

    public String getProduct() {
        return product;
    }

    public double getExpense() {
        return expense;
    }
}
