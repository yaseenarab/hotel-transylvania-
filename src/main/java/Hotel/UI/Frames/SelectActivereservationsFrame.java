package UI.Frames;

import Central.CentralDatabase;
import Central.CentralReservations;
import UI.Panels.ShoppingMainPanel;
import UI.ShoppingCartPanelHandler;
import Utilities.DateProcessor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class SelectActivereservationsFrame extends JFrame {

    private JTable availRooms;

    private double addedCost;

    private ShoppingMainPanel shoppingPanel;
    public SelectActivereservationsFrame(ShoppingMainPanel shoppingPanel, double addedCost){

        JPanel tablePanel= new JPanel();

        this.addedCost = addedCost;

        String[] columnNames = {"Reservation Id", "Room #", "Start Date", "End Date", "Cost"};
        DefaultTableModel reservationModel = new DefaultTableModel();
        for(String name : columnNames) {
            reservationModel.addColumn(name);
        }
        JLabel label = new JLabel("Please select the reservation you want to bill this to");
        //tablePanel.add(label,BorderLayout.NORTH);


        try {

            Connection con = CentralDatabase.getConReservationDatabase();
            Statement stmt = con.createStatement();

            ResultSet res = stmt.executeQuery("Select * from reservations where Username = '" + shoppingPanel.getGuest().getUsername() +"'");


            while(res.next()) {

                String[] split =  new String[5];

                Date start = DateProcessor.stringToDate(res.getString("startdate"));
                Date end = DateProcessor.stringToDate(res.getString("enddate"));

                if(DateProcessor.inBetweenToday(start,end)){
                    split[0]= res.getString("ReservationId") +"";
                    split[1]= res.getInt("roomnumber") +"";
                    split[2]= res.getString("startdate") +"";
                    split[3]= res.getString("enddate") +"";
                    split[4]= res.getDouble("Cost")+"";

                    reservationModel.addRow(split);
                }
            }

            if(reservationModel.getRowCount() == 0){
                JOptionPane.showMessageDialog(this, "You Have no Active Reservations. Please Make a Reservation" );
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        availRooms = new JTable(reservationModel);

        getContentPane().add(label,BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(availRooms),BorderLayout.CENTER);
        availRooms.getSelectionModel().addListSelectionListener(this::updateCost);


        availRooms.setFillsViewportHeight(true);

        tablePanel.setLayout(new BorderLayout());
        setSize(500,500);
        setVisible(true);

        add(tablePanel);

    }

    private void updateCost(ListSelectionEvent listSelectionEvent) {

        int viewRow = availRooms.getSelectedRow();

        if(viewRow >= 0){

            String resID = (String) availRooms.getValueAt(viewRow, 0);
            CentralReservations.updateCost(resID,addedCost );

        }

        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));


    }

    public JTable getAvailRooms() {
        return availRooms;
    }

}
