import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ExpensesTracker extends JFrame implements ActionListener {

    private final Map<String, Double> expenses;
    private double totalExpenses;

    private final JTextField amountField;
    private final JTextField causeField;
    private final JTextArea outputArea;

    private String expenseToEdit; // Track the expense being edited

    public ExpensesTracker() {
        super("Expense Tracker");
        expenses = new HashMap<>();
        totalExpenses = 0;

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JLabel amountLabel = new JLabel("Amount (in rupees):");
        amountField = new JTextField();
        JLabel causeLabel = new JLabel("Cause of Expense:");
        causeField = new JTextField();
        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(this);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(causeLabel);
        inputPanel.add(causeField);
        inputPanel.add(addExpenseButton);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Expenses Summary"));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton viewExpensesButton = new JButton("View Total Expenses");
        viewExpensesButton.addActionListener(this);
        JButton deleteExpenseButton = new JButton("Delete Expense");
        deleteExpenseButton.addActionListener(this);
        JButton editExpenseButton = new JButton("Edit Expense");
        editExpenseButton.addActionListener(this);
        buttonPanel.add(viewExpensesButton);
        buttonPanel.add(deleteExpenseButton);
        buttonPanel.add(editExpenseButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(outputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Expense")) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String cause = causeField.getText().trim();
                if (!cause.isEmpty()) {
                    if (expenses.containsKey(cause)) {
                        expenses.put(cause, expenses.get(cause) + amount);
                    } else {
                        expenses.put(cause, amount);
                    }
                    totalExpenses += amount;
                    outputArea.setText("Expense added successfully.");
                } else {
                    outputArea.setText("Please enter a cause for the expense.");
                }
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid amount. Please enter a valid number.");
            }
        } else if (e.getActionCommand().equals("View Total Expenses")) {
            StringBuilder output = new StringBuilder("Total expenses: " + totalExpenses + " rupees\n");
            output.append("Breakdown by cause:\n");
            for (Map.Entry<String, Double> entry : expenses.entrySet()) {
                output.append(entry.getKey()).append(": ").append(entry.getValue()).append(" rupees\n");
            }
            outputArea.setText(output.toString());
        } else if (e.getActionCommand().equals("Delete Expense")) {
            String cause = causeField.getText().trim();
            if (!cause.isEmpty() && expenses.containsKey(cause)) {
                double amountToRemove = expenses.get(cause);
                expenses.remove(cause);
                totalExpenses -= amountToRemove;
                outputArea.setText("Expense deleted successfully.");
            } else {
                outputArea.setText("Expense not found.");
            }
        } else if (e.getActionCommand().equals("Edit Expense")) {
            String cause = causeField.getText().trim();
            if (!cause.isEmpty() && expenses.containsKey(cause)) {
                expenseToEdit = cause;
                amountField.setText(String.valueOf(expenses.get(cause))); // Populate amount for editing
                outputArea.setText("Editing expense for: " + cause);
            } else {
                outputArea.setText("Expense not found.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpensesTracker::new);
    }
}
