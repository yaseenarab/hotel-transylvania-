package org.example;


import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;



public class Main {
    public static void main(String[] args) throws Exception {



        GuestProfile guest = new GuestProfile("Bob", "Wilson", "Bwill123@gmail.com", "8329474839");

        guest.MakeOnlineAccount("bwill123", "Password");


        Room room = new Room(RoomStatus.VaCl, BedType.FL, RoomType.NR, QualityLevel.CoL, false, 104);

        Reservations r = new Reservations();
        Integer []roomNumbers = new Integer[1];
        roomNumbers[0] = room.getRoomNumber();

        //UI STUFF
        JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setSize(400,400);

        JButton login = login();

        JTextField username = addJTextFeild(142,170, 100 ,30);


        JTextField password = addJTextFeild(142,215, 100 ,30);


        JLabel enterUsername = addLabel("Username:", 75,123,100,125);

        JLabel enterPassword = addLabel("Password:", 75,180,100,100);

        JLabel invalidPassword = new JLabel("");
        invalidPassword.setBounds(100,100, 200,100);


        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(username.getText().equals( guest.getUsername().toString())
                    && password.getText().equals(guest.getPassword().toString())){

                    System.out.println("Correct!");
                }
                else{;
                    System.out.println("Invalid");
                    invalidPassword.setText("Invalid Username or Password");
                }
            }
        } );

        frame.add(username);
        frame.add(password);
        frame.add(login);
        frame.add(enterPassword);
        frame.add(enterUsername);
        frame.add(invalidPassword);
        //frame.add(panel);

        frame.setLayout(null);
        frame.setVisible(true);

        guest.reserveRoom(roomNumbers, new Date(2024, 4, 1), new Date(2024,4,8));

    }
    public static JTextField addJTextFeild(int x, int y, int width, int height){
        JTextField textFeild = new JTextField();
        textFeild.setBounds(x,y, width ,height);


        return textFeild;

    }

    public static JLabel addLabel(String label, int x, int y, int width, int height){
        JLabel jLabel = new JLabel(label);
        jLabel.setBounds(x,y,width,height);


        return jLabel;
    }

    public static JButton login(){
        JButton login = new JButton("login");
        login.setBounds(142,250, 100 ,30);





        return login;
    }
}