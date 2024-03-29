package edu.baylor.hoteltransylvania;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) throws Exception {
        GuestProfile guest = new GuestProfile("Bob", "Wilson", "Bwill123@gmail.com", "8329474839");
        guest.MakeOnlineAccount("bwill123", "Password");
        Reservations r = new Reservations();

        //UI STUFF
        JFrame frame = new JFrame("Hotel Transylvania");
        //frame.setSize(400,400);
        frame.setSize(new Dimension(720 , 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setSize(new Dimension(720, 480));
        //mainPanel.setSize(400,400);

        JPanel guestLogin = new JPanel();
        guestLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
        //guestLogin.setLayout(new BorderLayout(0, 0));
        guestLogin.setLayout(new GridLayout(2, 1));

        JButton login = login();
        JTextField username = addJTextField(142,170, 100 ,30);
        JTextField password = addJTextField(142,215, 100 ,30);
        JLabel enterUsername = addLabel("Username:", 75,123,100,125);
        JLabel enterPassword = addLabel("Password:", 75,180,100,100);
        JLabel invalidPassword = new JLabel("");
        invalidPassword.setBounds(100,100, 200,100);

        JPanel loginFieldPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;

        loginFieldPanel.add(enterUsername, gbc);
        ++gbc.gridx;
        loginFieldPanel.add(username, gbc);

        gbc.gridx = 0;
        ++gbc.gridy;
        loginFieldPanel.add(enterPassword, gbc);
        ++gbc.gridx;
        loginFieldPanel.add(password, gbc);

        JPanel loginButtonAndFeedbackPanel = new JPanel(new GridBagLayout());
        gbc.gridx = gbc.gridy = 0;

        loginButtonAndFeedbackPanel.add(invalidPassword, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loginButtonAndFeedbackPanel.add(login, gbc);

        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 1;
        guestLogin.add(loginFieldPanel);
        guestLogin.add(loginButtonAndFeedbackPanel);
        mainPanel.add(guestLogin);
        //mainPanel.add(loginFieldPanel);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(username.getText().equals(guest.getUsername())
                    && password.getText().equals(guest.getPassword())){

                    System.out.println("Correct!");
                    
                    //frame.setEnabled(false);
                    //frame.setVisible(false);
                    //GuestReservationPanel reservationSystem = new GuestReservationPanel();
                    //frame.remove(mainPanel);
                    //frame.add((new GuestHomePanel(username.toString())).getPanel());
                    //frame.revalidate();
                    //frame.repaint();
                    mainPanel.remove(guestLogin);
                    //mainPanel.add((new GuestHomePanel(username.getText())).getPanel());
                    mainPanel.add((new GuestHomePanel(mainPanel, guest.getFirstName())).getPanel());
                    frame.revalidate();
                    frame.repaint();
                }
                else{;
                    System.out.println("Invalid");
                    invalidPassword.setText("Invalid Username or Password");
                    //frame.repaint();
                }
            }
        } );

        /*
        frame.add(username);
        frame.add(password);
        frame.add(login);
        frame.add(enterPassword);
        frame.add(enterUsername);
        frame.add(invalidPassword);
         */

        /*
        JPanel guestLoginPane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        guestLoginPane.add(enterUsername, gbc);
        ++gbc.gridx;
        guestLoginPane.add(username, gbc);
        gbc.gridx = 0;
        ++gbc.gridy;
        guestLoginPane.add(enterPassword, gbc);
        ++gbc.gridx;
        guestLoginPane.add(password, gbc);
        ++gbc.gridy;
        guestLoginPane.add(invalidPassword, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        ++gbc.gridy;
        guestLoginPane.add(login);
         */

        frame.add(mainPanel);

        frame.setLayout(null);
        frame.setVisible(true);
    }
    public static JTextField addJTextField(int x, int y, int width, int height){
        JTextField jtxt = new JTextField();
        jtxt.setBounds(x,y, width ,height);
        jtxt.setPreferredSize(new Dimension(width, height));
        jtxt.setMinimumSize(new Dimension(width, height));


        return jtxt;

    }

    public static JLabel addLabel(String label, int x, int y, int width, int height){
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(x,y,width,height);
        jLabel.setMinimumSize(new Dimension(width, height));

        return jLabel;
    }

    public static JButton login(){
        JButton login = new JButton("login");
        login.setBounds(142,250, 100 ,30);

        return login;
    }

    /*
    public static void reservationWindow() {
    	ReserveRoomPanel reservationPanel = new ReserveRoomPanel();
    	JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(reservationPanel);
        System.out.println("Here");
    }
     */
}
