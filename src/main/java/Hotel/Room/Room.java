package Room;

import Enums.BedType;
import Enums.RoomStatus;

public abstract class Room {
    protected RoomStatus roomStatus;
    protected BedType bedType;
    protected Integer numBeds;
    protected Boolean smokingStatus;
    protected Integer roomNumber;
    Room() {}
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
    public void setNumBeds(Integer numBeds) throws Exception {
        if(numBeds == null) {
            throw new Exception("Fatal error thrown in setNumBeds, value is NULL");
        }
        try {
            this.numBeds = numBeds;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setNumBeds", e);
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
