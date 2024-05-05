package org.example;

import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;



public class Room implements IRoom {
    // Private Variables
    protected RoomStatus roomStatus;
    protected BedType bedType;
    protected Boolean smokingStatus;
    protected Integer roomNumber;

    // Constructors
    public Room(RoomStatus roomStatus, BedType bedType, Boolean smokingStatus, Integer roomNumber) throws Exception {
        try {
            this.setRoomStatus(roomStatus);
            this.setBedType(bedType);
            this.setSmokingStatus(smokingStatus);
            this.setRoomNumber(roomNumber);
            System.out.println("Successfully initialized Room");
        }
        catch(Exception e) {
            throw new Exception("Exception caught in Room constructor.", e);
        }
    }

    // Set Functions
    public void setBedType(BedType bedType) throws Exception {
        if(bedType == null) {
            throw new Exception("Fatal error thrown in setBedType, value is NULL");
        }
        try {
            this.bedType = bedType;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setBedType", e);
        }
    }
    public void setRoomStatus(RoomStatus roomStatus) throws Exception {
        if(roomStatus == null) {
            throw new Exception("Fatal error thrown in setRoomStatus, value is NULL");
        }
        try {
            this.roomStatus = roomStatus;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setRoomStatus", e);
        }
    }
    public void setSmokingStatus(Boolean smokingStatus) throws Exception {
        if(smokingStatus == null) {
            throw new Exception("Fatal error thrown in setSmokingStatus, value is NULL");
        }
        try {
            this.smokingStatus = smokingStatus;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setSmokingStatus", e);
        }
    }
    public void setRoomNumber(Integer roomNumber) throws Exception {
        if(roomNumber == null) {
            throw new Exception("Fatal error thrown in setRoomNumber, value is NULL");
        }
        try {
            this.roomNumber = roomNumber;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setRoomNumber", e);
        }
    }

    // Get Functions
    public BedType getBedType() {
        return this.bedType;
    }
    public RoomStatus getRoomStatus() {
        return this.roomStatus;
    }
    public Boolean getSmokingStatus() {
        return this.smokingStatus;
    }
    public Integer getRoomNumber() {
        return this.roomNumber;
    }
    public String toString() {
        return this.roomNumber + ", " + this.roomStatus + ", " + this.bedType + ", " + this.smokingStatus;
    }
}

