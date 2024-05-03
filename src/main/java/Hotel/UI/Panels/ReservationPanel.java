package Hotel.UI.Panels;

import Hotel.Central.CentralDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationPanel extends JPanel {
    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel model;
    private JTable table;
    private JLabel noRooms;
    private JScrollPane scrollPane;
    public ReservationPanel(String username, String password) {
        super();
        ResultSet myReservations = CentralDatabase.getReservations();

        this.setLayout(new BorderLayout());
        model = new DefaultTableModel();


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));


        noRooms = new JLabel("<html>No Rooms Available</html>");
        noRooms.setFont(new Font("Serif", Font.ITALIC, 55));
        noRooms.setPreferredSize(new Dimension( 500,100));

        model.addColumn("Guest");
        model.addColumn("Room Num");
        model.addColumn("Start Date");
        model.addColumn("End Date");
        model.addColumn("Checked in");
        table = new JTable(model);
        table.setRowHeight(15);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(65);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(30);

        table.setAutoCreateRowSorter(true);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this::valueChanged);

        scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);

        add(scrollPane,BorderLayout.CENTER);
        add(textPanel,BorderLayout.SOUTH);
        textPanel.add(noRooms);

        updateTable(myReservations);
    }
    public void valueChanged(ListSelectionEvent event) {
        int viewRow = table.getSelectedRow();

        if(viewRow >= 0){
            int modelRow = table.convertRowIndexToModel(viewRow);
            String idStr = (String) model.getValueAt(modelRow, 0);
        }
    }
    public void updateTable(ResultSet res){
        scrollPane.setVisible(true);

        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
        try {
            putValues(res, model);

            if(model.getRowCount() == 0){
                throw new SQLException();
            }

            noRooms.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void putValues(ResultSet res, DefaultTableModel model) throws SQLException {
        if(res == null){
            throw new SQLException();
        }

        while (res.next()) {
            Object[] obj = new Object[5];
            try {
                String username = res.getString("USERNAME");
                obj[0] =username;
                obj[1] = res.getString("ROOMNUMBER");
                obj[2] = res.getString("STARTDATE");
                obj[3] = res.getString("ENDDATE");
                obj[4] = res.getBoolean("CHECKEDIN");
                model.insertRow(0, obj);
            } catch (Exception e) {}
        }
    }
    public boolean roomIsSelected() {

        return table.getSelectedRow() > -1;
    }
    public int getRoomNumTable() {
        int viewRow = table.getSelectedRow();
        int modelRow = table.convertRowIndexToModel(viewRow);
        String numStr = (String)model.getValueAt(modelRow, 1);
        int num = Integer.parseInt(numStr);
        return num;
    }
    public String getUsernameTable() {
        int viewRow = table.getSelectedRow();
        int modelRow = table.convertRowIndexToModel(viewRow);
        String user = (String)model.getValueAt(modelRow, 0);
        return user;
    }

}
