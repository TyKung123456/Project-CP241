public class Income extends Money{
    private double incomeTotal;

    public Income() {
        super("", 0);
    }

    public void recordIncome(double amount) {
        incomeTotal += amount;
    }

    public void deductIncome(double expense) {
        incomeTotal -= expense;
    }

    public void setIncomeTotal(double incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public double getIncomeTotal() {
        return incomeTotal;
    }
}
