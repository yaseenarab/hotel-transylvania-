package Hotel.UI.Panels;

import Hotel.AccountService.Guest;
import Hotel.Central.CentralReservations;
import Hotel.Central.CentralRoom;
import Hotel.Utilities.DateProcessor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

public class AvailableRoomsPanel extends JPanel {

    private TableRowSorter<DefaultTableModel> sorter;
    private DefaultTableModel model;
    private Guest guest;
    private JTable table;
    private JLabel noRooms;
    private JLabel validDate;
    private Integer id;
    private JScrollPane scrollPane;
    private Date start,end;
    AvailableRoomsPanel(Guest guest ) {

        super();
        this.guest = guest;
        this.end = end;
        this.setLayout(new BorderLayout());
        //this.setLayout(null);
        model = new DefaultTableModel();
        //reservationSummary = new JLabel();


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));


        noRooms = new JLabel("<html>No Rooms Available</html>");
        noRooms.setFont(new Font("Serif", Font.ITALIC, 55));
        noRooms.setPreferredSize(new Dimension( 500,100));

        validDate = new JLabel("<html>Please Enter Valid Date</html>");
        validDate.setFont(new Font("Serif", Font.ITALIC, 12));
        validDate.setPreferredSize(new Dimension( 500,100));

        model.addColumn("Room ID");
        model.addColumn("Room Status");
        model.addColumn("Room Type");
        model.addColumn("Bed Type");
        model.addColumn("Quality Level");
        model.addColumn("Smoking Allowed");
        table = new JTable(model);
        //table.setPreferredSize(new Dimension(300,300));
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
        textPanel.add(validDate);




    }

    public boolean roomIsSelected() {

        return table.getSelectedRow() > -1;
    }

    public void updateTable(String SQL, Date start, Date end){
        scrollPane.setVisible(true);

        id = null;

        this.start = start;
        this.end = end;

        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }

        try {
            CentralReservations.putValues(SQL, model);

            if(model.getRowCount() == 0){
                throw new SQLException();
            }

            noRooms.setText("");
            validDate.setText("");

        }catch (SQLException e) {
            //e.printStackTrace();

            noRooms.setFont(new Font("Serif", Font.ITALIC, 55));
            noRooms.setText("<html>No Rooms Available</html>");
            if(start == null || end == null){
                validDate.setText("<html>Please Enter Valid Date</html>");
            }
            //scrollPane.setVisible(false);
        }

    }


    public void valueChanged(ListSelectionEvent event) {
        int viewRow = table.getSelectedRow();

        if(viewRow >= 0){
            int modelRow = table.convertRowIndexToModel(viewRow);
            String idStr = (String) model.getValueAt(modelRow, 0);
            id = Integer.parseInt(idStr);
            displayReservationSummary();
        }
    }

    public void displayReservationSummary(){


        String startStr = DateProcessor.dateToString(start);
        String endStr = DateProcessor.dateToString(end);
        noRooms.setFont(new Font("a", Font.BOLD,12));
        noRooms.setLayout(null);

        BigDecimal days =new BigDecimal( DateProcessor.differenceinDays(start,end));
        BigDecimal totalCost = CentralRoom.calculatorCost(id);

        BigDecimal oneNightMultiplier = new BigDecimal("1.00");
        if(CentralReservations.getNightsStayed() == 0 &&  DateProcessor.differenceinDays(start,end) ==1){
            oneNightMultiplier = new BigDecimal("1.25");
        }


        totalCost = totalCost.multiply(days.multiply(oneNightMultiplier));

        noRooms.setText("<html>Reservation Summary: <br/> Name: "+ guest.getFirstName() + " <br/>Last Name: " + guest.getLastName() +
                "<br/>Username: " + guest.getUsername()+ " <br/>Room ID: " + id +"  <br/> Start Date:  " + startStr + "<br/>End Date: " + endStr+
                "<br/>Days Staying:" +days + " <br/>Cost Per Night: " + CentralRoom.calculatorCost(id)+ " <br/> Total Cost: " + totalCost +" </html>");


    }

    public Integer getRoomID() {
        return id;
    }
}
