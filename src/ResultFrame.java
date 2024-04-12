import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultFrame extends JFrame {
    private JTable table;

    public ResultFrame(Object[][] data) {
        setTitle("Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        

        if (data == null || data.length == 0) {
            JOptionPane.showMessageDialog(this, "No submissions found", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[] columnNames = {"Quiz ID", "Quiz Name", "Score"};
            DefaultTableModel model = new DefaultTableModel(data, columnNames);

            table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            JScrollPane scrollPane = new JScrollPane(table);
            getContentPane().add(scrollPane, BorderLayout.CENTER);
        }
    }
}