package org.example;

import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class main {
    public static void main(String[] args) throws Exception {
        Person.Arlow.init();
        try{
            Scanner s = new Scanner(new File("Person_Profiles.csv"));
            while(s.hasNextLine()){
                String line = s.nextLine();
                String []split = line.split(",");
                Guest  guest = new Guest(split[0],split[1],split[2],split[3],split[4],split[5],split[6]);
                Person.Arlow.addGuest(guest);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        JFrame login = new LoginFrame();

        login.setVisible(true);

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