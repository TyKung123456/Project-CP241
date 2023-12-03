import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Main {
    static String exList = "";
    static SinglyLinkedList data = new SinglyLinkedList();
    static Income income = new Income();
    static Spend spend = new Spend();
    static JLabel titleLabel,MoneyLabel;


    public static void main(String[] args) throws Exception {
        loadDataFromFile();
        SwingUtilities.invokeLater(Main::createMainPage);
    }

    private static void createMainPage() {
        JFrame frame = new JFrame("Cashable - Main Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        ImagePanel panel = new ImagePanel("CP241-Project-master/protest/res/BackGround.png");
        frame.getContentPane().add(panel);
        placeComponents(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static class ImagePanel extends JPanel {
        private ImageIcon backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(ImageIO.read(new File(imagePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the background image
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        titleLabel = new JLabel("Cashable");
        titleLabel.setBounds(153, 35, 200, 20);
        Font titleFont = titleLabel.getFont();
        titleLabel.setFont(new Font("Trebuchet MS", Font.BOLD , 20));
        MoneyLabel = new JLabel(String.valueOf(income.getIncomeTotal()));
        Font MoneyFont = MoneyLabel.getFont();
        MoneyLabel.setFont(new Font("Trebuchet MS", Font.BOLD , 18));
        MoneyLabel.setBounds(165, 68, 200, 20);

        panel.add(titleLabel);
        panel.add(MoneyLabel);

        JButton recordIncomeButton = new JButton("Record Income");
        recordIncomeButton.setBounds(50, 120, 150, 25);
        recordIncomeButton.addActionListener(e -> {
            try {
                createRecordIncomePage();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "! Wrong input !", "Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(recordIncomeButton);

        JButton recordExpenseButton = new JButton("Record Expense");
        recordExpenseButton.setBounds(50, 155, 150, 25);
        recordExpenseButton.addActionListener(e -> {
            try {
                createRecordExpensePage();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "! Wrong input !", "Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(recordExpenseButton);

        JButton showTransactionsButton = new JButton("Show Expenses List");
        showTransactionsButton.setBounds(50, 190, 150, 25);
        showTransactionsButton.addActionListener(e -> showAllExpenses());
        panel.add(showTransactionsButton);

        JButton clearDataButton = new JButton("Clear");
        clearDataButton.setBounds(240, 120, 100, 25);
        clearDataButton.addActionListener(e -> clearData());
        panel.add(clearDataButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(240, 155, 100, 25);
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);
    }

    private static void createRecordIncomePage() {
        JFrame incomeFrame = new JFrame("Record Income");
        incomeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        incomeFrame.setSize(300, 200);

        JPanel panel = new JPanel();
        incomeFrame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("Enter income:");
        label.setBounds(20, 20, 100, 20);
        panel.add(label);

        JTextField textField = new JTextField();
        textField.setBounds(120, 20, 150, 20);
        panel.add(textField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 80, 80, 25);
        submitButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(textField.getText());
                income.recordIncome(amount);
                data.insert(income);
                updateIncomeLabel();
                saveDataToFile();
                incomeFrame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "! Wrong input !", "Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(submitButton);

        incomeFrame.setLocationRelativeTo(null);
        incomeFrame.setVisible(true);
    }

    private static void createRecordExpensePage() {
        JFrame expenseFrame = new JFrame("Record Expense");
        expenseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        expenseFrame.setSize(300, 200);

        JPanel panel = new JPanel();
        expenseFrame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel prodLabel = new JLabel("What did you buy:");
        prodLabel.setBounds(20, 20, 150, 20);
        panel.add(prodLabel);

        JTextField prodTextField = new JTextField();
        prodTextField.setBounds(180, 20, 100, 20);
        panel.add(prodTextField);

        JLabel exLabel = new JLabel("Enter expense:");
        exLabel.setBounds(20, 50, 150, 20);
        panel.add(exLabel);

        JTextField exTextField = new JTextField();
        exTextField.setBounds(180, 50, 100, 20);
        panel.add(exTextField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 100, 80, 25);
        submitButton.addActionListener(e -> {
            try {
                String prod = prodTextField.getText();
                double ex = Double.parseDouble(exTextField.getText());

                income.deductIncome(ex);

                exList += String.format("%-33s%-5s฿\n", prod, ex);

                spend.setSpend(prod, ex);
                data.insert(new Spend(prod, ex));
                updateIncomeLabel();
                saveDataToFile();
                expenseFrame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "! Wrong input !", "Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(submitButton);

        expenseFrame.setLocationRelativeTo(null);
        expenseFrame.setVisible(true);
    }

    private static void updateIncomeLabel() {
        SwingUtilities.invokeLater(() -> {
            MoneyLabel.setText(String.valueOf(income.getIncomeTotal()));
        });
    }

    private static void showAllExpenses() {
        JOptionPane.showMessageDialog(null, "            Expenses List \n\nProduct                      Price" +
                "\n-------------------------------------\n" + exList + "\n------------------------------------- " +
                "\nTotal                            " + Spend.getTotalExpense() + "฿");
    }


    private static void clearData() {
        income = new Income();
        updateIncomeLabel();

        exList = "";
        Spend.clearTotalExpense();

        data = new SinglyLinkedList();
        saveDataToFile();
    }

    private static void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data.txt"))) {
            income.setIncomeTotal(Double.parseDouble(reader.readLine()));

            StringBuilder exListBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                exListBuilder.append(line).append("\n");
            }
            exList = exListBuilder.toString();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            writer.write(String.valueOf(income.getIncomeTotal()));
            writer.newLine();

            writer.write(exList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public String getAllExpenses() throws Exception {
//        StringBuilder result = new StringBuilder();
//        MyNode current = data.findFirstExpenses();
//
//        while (current != null) {
//            if (current.getData() instanceof Spend) {
//                Spend spend = (Spend) current.getData();
//                result.append(String.format("%-33s%-5s฿\n", spend.getProduct(), spend.getExpense()));
//            }
//            current = data.findNextExpenses();
//        }
//
//        return result.toString();
//    }

}
