package Hotel.UI.Frames;

import Hotel.AccountService.Card;
import Hotel.Central.CentralDatabase;
import Hotel.Enums.RoomStatus;
import Hotel.UI.Panels.ReservationPanel;
import Hotel.Utilities.DateProcessor;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CheckInFrame extends JFrame {
    private static ReservationPanel resPanel;
    private JButton checkInBtn, checkOutBtn;


    public CheckInFrame(String username, String password) {
        setLayout(new BorderLayout());
        setSize(new Dimension(900, 500));

        resPanel = new ReservationPanel(username, password);
        add(resPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

        checkInBtn = new JButton("Check in");
        buttonPanel.add(checkInBtn);
        checkInBtn.addActionListener(e -> performCheckIn());

        checkOutBtn = new JButton("Check out");
        buttonPanel.add(checkOutBtn);
        checkOutBtn.addActionListener(e -> performCheckOut());

        add(buttonPanel, BorderLayout.SOUTH);

    }

    public static void performCheckIn() {
        if (!resPanel.roomIsSelected()) {
            JOptionPane.showMessageDialog(resPanel, "Please select a room");
        } else {
            CentralDatabase.updateRoom(resPanel.getRoomNumTable(), RoomStatus.OcDi);
            CentralDatabase.checkIn(resPanel.getRoomNumTable());
            resPanel.updateTable(CentralDatabase.getReservations());
        }
    }

    public static void performCheckOut() {
        if (!resPanel.roomIsSelected()) {
            JOptionPane.showMessageDialog(resPanel, "Please select a room");
        } else {
            int roomNum = resPanel.getRoomNumTable();
            ResultSet res = CentralDatabase.getReservation(roomNum);
            try {
                if (res.next()) {
                    double cost = res.getDouble("COST");
                    int choice = JOptionPane.showOptionDialog(resPanel, "Total cost: " + cost + " Charge card on reservation?", "Checkout", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                    if (choice == JOptionPane.YES_OPTION) {
                        CentralDatabase.updateCardBalance(CentralDatabase.getUsernameFromRoom(roomNum), cost);
                        JOptionPane.showMessageDialog(resPanel,"Card on file has been charged. Have a nice day!");
                    } else if (choice == JOptionPane.NO_OPTION) {
                        boolean valid = false;
                        while (!valid) {
                            String cardNumber = JOptionPane.showInputDialog("Enter the card number: ");
                            String cardExp = JOptionPane.showInputDialog("Enter the expiration as mm/dd/yyyy: ");

                            try {
                                Card c = new Card(cardNumber, DateProcessor.stringToDate(cardExp));

                                // No need to add card, this is just one off
                                //CentralCashiering.saveCard(res.getString("USERNAME"),res.getString("PASSWORD"),cardNumber, DateProcessor.stringToDate(cardExp));
                                valid = true;
                            } catch (Exception e) {}
                            if(!valid) {
                                JOptionPane.showMessageDialog(resPanel,"Invalid card. Please try again");
                            }
                        }
                        JOptionPane.showMessageDialog(resPanel,"Provided card has been charged. Have a nice day!");
                    }
                    CentralDatabase.removeReservation(roomNum);
                    CentralDatabase.updateRoom(roomNum, RoomStatus.VaCl);
                    resPanel.updateTable(CentralDatabase.getReservations());
                }
            } catch (Exception ept) {}
        }
    }
}
