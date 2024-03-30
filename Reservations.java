package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;


public class Reservations {
    private List<Room>
            firstFloorRooms = new ArrayList<Room>(),
            secondFloorRooms = new ArrayList<Room>(),
            thirdFloorRooms = new ArrayList<Room>();
    String fileName = "./src/main/resources/roomTest.csv";
    
    Reservations() {
        RoomStatus roomStatus;
        BedType bedType;
        RoomType roomType;
        QualityLevel qualityLevel;
        Boolean smokingAllowed;
        Integer roomNumber;
        
        BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader(new File(fileName)));
        	
        	String line = null;
        	while ((line = reader.readLine()) != null) {
        		String[] split = line.split(",");
        		try {
        			roomNumber = Integer.valueOf(split[0]);
        			roomStatus = RoomStatus.valueOf(split[1]);
        			roomType = RoomType.getEnum(split[2]);
        			bedType = BedType.getEnum(split[3]);
        			qualityLevel = QualityLevel.getEnum(split[4]);
        			if (split[5].toLowerCase().contains("tr")) {
        				smokingAllowed = true;
        			} else if (split[5].toLowerCase().contains("fa")) {
        				smokingAllowed = false;
        			} else {
        				throw new IllegalArgumentException("Invalid smokingAllowed");
        			}
 
        			if (roomNumber < 200) {
        				firstFloorRooms.add(new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, roomNumber));
        			} else if (roomNumber < 300) {
        				secondFloorRooms.add(new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, roomNumber));
        			} else if (roomNumber < 400){
        				thirdFloorRooms.add(new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, roomNumber));
        			} else {
        				throw new IllegalArgumentException("Invalid room number");        			}
        		} catch (IllegalArgumentException e) {
        			//System.out.println(e.getMessage());
        		} catch (ArrayIndexOutOfBoundsException e) {
        			//System.out.println(e.getMessage());
        		} catch (Exception e) {
        			//System.out.println(e.getStackTrace());
        		}
        	}
        } catch (FileNotFoundException e) {
        	//System.out.println(e.getStackTrace());
        } catch (IOException e) {
        	//System.out.println(e.getStackTrace());
        }

    }

    public Room getRoom(Integer roomNumber) throws Exception{
        Room room = new Room();
        if(roomNumber >= 100 && roomNumber <= 135){
            return firstFloorRooms.get(roomNumber-100);
        }
        else if(roomNumber >= 200 && roomNumber <= 235){
            return secondFloorRooms.get(roomNumber-100);
        }
        else if(roomNumber >= 300 && roomNumber <= 335){
            return thirdFloorRooms.get(roomNumber-100);
        }
        else{
            throw new NullPointerException();
        }


    }
    
    public List<Room> getFirstFloorRooms() {
    	return firstFloorRooms;
    }
    
    public List<Room> getSecondFloorRooms() {
    	return secondFloorRooms;
    }
    
    public List<Room> getThirdFloorRooms() {
    	return thirdFloorRooms;
    }
    
    protected void setVCRooms(Room[] rooms) {

    }
}
