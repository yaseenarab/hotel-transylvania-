package Hotel.Room;

import Hotel.Enums.BedType;
import Hotel.Enums.RoomStatus;




/**
 * Abstract base class for different types of rooms within the hotel.
 * It provides common attributes and operations for all room variants.
 */
public abstract class Room {
    protected RoomStatus roomStatus;
    protected BedType bedType;
    protected Integer numBeds;
    protected Boolean smokingStatus;
    protected Integer roomNumber;


    /**
     * Constructs a Room with default settings.
     */
    Room() {}


    /**
     * Sets the status of the room.
     *
     * @param roomStatus The new status of the room.
     * @throws Exception If the provided roomStatus is null.
     */
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

    /**
     * Sets the type of bed in the room.
     *
     * @param bedType The new bed type of the room.
     * @throws Exception If the provided bedType is null.
     */
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

    /**
     * Sets the number of beds in the room.
     *
     * @param numBeds The new number of beds in the room.
     * @throws Exception If the provided numBeds is null.
     */
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


    /**
     * Sets the smoking status of the room.
     *
     * @param smokingStatus The new smoking status of the room.
     * @throws Exception If the provided smokingStatus is null.
     */
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


    /**
     * Sets the room number.
     *
     * @param roomNumber The new room number.
     * @throws Exception If the provided roomNumber is null.
     */
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

    /**
     * Gets the bed type of the room.
     * @return The bed type of the room.
     */
    public BedType getBedType() {
        return this.bedType;
    }

    /**
     * Gets the current status of the room.
     * @return The current room status.
     */
    public RoomStatus getRoomStatus() {
        return this.roomStatus;
    }

    /**
     * Gets the smoking status of the room.
     * @return The smoking status.
     */
    public Boolean getSmokingStatus() {
        return this.smokingStatus;
    }

    /**
     * Gets the room number.
     * @return The room number.
     */
    public Integer getRoomNumber() {
        return this.roomNumber;
    }


    /**
     * Provides a string representation of the room's attributes.
     * @return A string detailing the room number, status, bed type, and smoking status.
     */
    public String toString() {
        return this.roomNumber + ", " + this.roomStatus + ", " + this.bedType + ", " + this.smokingStatus;
    }

}
