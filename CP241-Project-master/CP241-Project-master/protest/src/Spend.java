public class Spend extends Money{
    private String product;
    private double expense;
    private static double totalExpense = 0;

    public Spend(String prod, double ex) {
        super(prod, ex);
//        setSpend(prod,ex);
    }

    public Spend() {
        super("", 0);
    }


    public void setSpend(String prod, double ex){
        product = prod;
        expense = ex;
        totalExpense += ex;
    }

    public static void clearTotalExpense() {
        totalExpense = 0;
    }

    public static double getTotalExpense() {
        return totalExpense;
    }

    public String getProduct(){
        return product;
    }
    public double getExpense(){
        return expense;
    }



}
