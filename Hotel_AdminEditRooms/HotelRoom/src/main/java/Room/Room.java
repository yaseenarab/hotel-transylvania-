package Room;

import Enums.BedType;
import Enums.RoomStatus;
import Utilities.MyLogger;

import java.util.logging.Level;

public abstract class Room implements RoomData {
    protected RoomStatus roomStatus;
    protected BedType bedType;
    protected Integer numBeds;
    protected Boolean smokingStatus;
    protected Integer roomNumber;
    Room() {}
    protected void setRoomStatus(RoomStatus roomStatus) throws Exception {
        if(roomStatus == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setRoomStatus: roomStatus is null");
            throw new Exception();
        }

        this.roomStatus = roomStatus;
    }
    protected void setBedType(BedType bedType) throws Exception {
        if(bedType == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setBedType: bedType is null");
            throw new Exception();
        }

        this.bedType = bedType;
    }
    protected void setNumBeds(Integer numBeds) throws Exception {
        if(numBeds == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setNumBeds: numBeds is null");
            throw new Exception();
        }
        else if (numBeds <= 0) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setNumBeds: numBeds must be > 0, received " +
                    numBeds);
            throw new Exception();
        }

        this.numBeds = numBeds;
    }
    protected void setSmokingStatus(Boolean smokingStatus) throws Exception {
        if(smokingStatus == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setSmokingStatus: smokingStatus is null");
            throw new Exception();
        }

        this.smokingStatus = smokingStatus;
    }
    protected void setRoomNumber(Integer roomNumber) throws Exception {
        if(roomNumber == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setRoomNumber: roomNumber is null");
            throw new Exception();
        }
        else if (roomNumber < ROOM_NUMBER_FORMAT) {
            MyLogger.logger.log(Level.SEVERE, "Error in Room.setNumBeds: numBeds must be > " +
                    ROOM_NUMBER_FORMAT + ", received " + roomNumber);
            throw new Exception();
        }

        this.roomNumber = roomNumber;
    }

    // Get Functions
    protected BedType getBedType() {
        return this.bedType;
    }
    protected RoomStatus getRoomStatus() {
        return this.roomStatus;
    }
    protected Boolean getSmokingStatus() {
        return this.smokingStatus;
    }
    protected Integer getRoomNumber() {
        return this.roomNumber;
    }
    public String toString() {
        return this.roomNumber + ", " + this.roomStatus + ", " + this.bedType + ", " + this.smokingStatus;
    }

}
