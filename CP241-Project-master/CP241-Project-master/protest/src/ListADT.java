public interface ListADT {
    public void List();

    public void insert(Object e) throws Exception;

    public Object retrieve() throws Exception;

    public MyNode findFirstExpenses() throws Exception;

    public MyNode findNextExpenses() throws Exception;

    public boolean isEmpty();

    public boolean isFull();

}
