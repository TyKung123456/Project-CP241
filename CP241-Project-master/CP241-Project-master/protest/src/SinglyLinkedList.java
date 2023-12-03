public class SinglyLinkedList implements ListADT{

    private MyNode incomeFirst, incomeCurrent;
    private MyNode expenseFirst, expenseCurrent;

    public SinglyLinkedList() {
        List();

    }

    @Override
    public void List() {
        incomeFirst = incomeCurrent = expenseFirst = expenseCurrent = null;
    }

    @Override
    public void insert(Object e) throws Exception {
        MyNode newNode = new MyNode(e, null);
        if (e instanceof Income) {
            if (incomeFirst == null) {
                incomeFirst = incomeCurrent = newNode;
            } else {
                incomeCurrent.setNextNode(newNode);
                incomeCurrent = newNode;
            }
        } else if (e instanceof Spend) {
            if (expenseFirst == null) {
                expenseFirst = expenseCurrent = newNode;
            } else {
                expenseCurrent.setNextNode(newNode);
                expenseCurrent = newNode;
            }
        } else {
            throw new IllegalArgumentException("Unsupported type: " + e.getClass().getName());
        }
    }

    @Override
    public Object retrieve() throws Exception {
        if (isEmpty()) {
            throw new Exception("List is empty");
        } else {
            return expenseCurrent.getData();
        }
    }

    @Override
    public MyNode findFirstExpenses() throws Exception {
        if (expenseFirst == null) {
            throw new Exception("Expense list is empty");
        } else {
            expenseCurrent = expenseFirst;
        }
        return expenseCurrent;
    }

    @Override
    public MyNode findNextExpenses() throws Exception {
        if (expenseCurrent == null || expenseCurrent.getNextNode() == null) { // expenseCurrent = last
            throw new Exception("No next expense data");
        } else {
            expenseCurrent = expenseCurrent.getNextNode();
        }
        return expenseCurrent;
    }

//    public String getAllExpenses() {
//        StringBuilder result = new StringBuilder();
//        MyNode current = expenseFirst;
//
//        while (current != null) {
//            if (current.getData() instanceof Spend) {
//                Spend spend = (Spend) current.getData();
//                result.append(String.format("%-33s%-5s฿\n", spend.getProduct(), spend.getExpense()));
//            }
//            current = current.getNextNode();
//        }
//
//        return result.toString();
//    }



    @Override
    public boolean isEmpty() {
        return expenseFirst == null;
    }

    @Override
    public boolean isFull() { //ไม่ต้องตรวจ
        return false;
    }

}
