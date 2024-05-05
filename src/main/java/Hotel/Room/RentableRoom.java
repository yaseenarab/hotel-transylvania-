package Hotel.Room;

import Hotel.Enums.*;

import Hotel.Utilities.MyLogger;

import java.util.logging.Level;


/**
 * Represents a rentable room in the hotel, extending the base Room class with additional
 * properties and behaviors specific to rooms that can be rented.
 */
public class RentableRoom extends Room implements RoomData {

    private Integer roomID;
    private RoomTheme roomTheme;
    private RoomSize roomSize;
    private QualityLevel qualityLevel;


    /**
     * Constructs a RentableRoom with specified identifiers and status.
     * Validates the room types before setting the room number.
     *
     * @param roomID The unique identifier for the room.
     * @param roomStatus The initial status of the room (e.g., vacant, occupied).
     * @param roomNumber The room number.
     * @throws Exception if there is an error in setting initial values.
     */
    public RentableRoom(Integer roomID, RoomStatus roomStatus, Integer roomNumber) throws Exception {
        try {
            this.setRoomID(roomID);
            this.setRoomStatus(roomStatus);
            this.validTypes(roomTheme, roomSize);
            this.setRoomNumber(roomNumber);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RentableRoom constructor: Passed values were " +
                    roomID + "," + roomStatus + "," + roomNumber);
            throw new Exception();
        }
    }

    // Set Functions

    /**
     * Sets the room ID after validating it.
     * Processes the ID to extract theme, size, quality level, and smoking status.
     *
     * @param roomID The room ID to set.
     * @throws Exception If the room ID is null or invalid, or if there is an error in processing the ID.
     */
    private void setRoomID(Integer roomID) throws Exception {
        if(roomID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setRoomID: roomID is null");
            throw new Exception();
        }
        else if(!RoomID.checkValidRoomID(roomID)) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setRoomID: roomID is invalid, received " + roomID);
            throw new Exception();
        }

        try {
            //RoomID.processRoomThemeID(Character.getNumericValue(roomID.charAt(ROOM_THEME)));
            //RoomID.processRoomSizeID(Character.getNumericValue(roomID.charAt(ROOM_SIZE)));
            //RoomID.processQualityLevelID(Character.getNumericValue(roomID.charAt(QUALITY_LEVEL)));
            //RoomID.processSmokingStatusID(Character.getNumericValue(roomID.charAt(SMOKING_STATUS)));
            this.roomID = roomID;

            this.setRoomFromID(this.roomID);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RentableRoom.setRoomID: Passed value was " + roomID);
            throw new Exception();
        }
    }

    /**
     * Sets attributes from the parsed room ID.
     *
     * @param roomID The integer room ID from which attributes are set.
     * @throws Exception If the room ID is null, invalid, or there is an error in setting attributes.
     */
    private void setRoomFromID(Integer roomID) throws Exception {
        if(roomID == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setRoomFromID: roomID is null");
            throw new Exception();
        }
        else if(!RoomID.checkValidRoomID(roomID)) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setRoomFromID: roomID is invalid, received " + roomID);
            throw new Exception();
        }

        try {
            //this.setRoomTheme(RoomID.processRoomThemeID(Character.getNumericValue(roomID.charAt(ROOM_THEME))));
            //this.setRoomSize(RoomID.processRoomSizeID(Character.getNumericValue(roomID.charAt(ROOM_SIZE))));
            //this.setQualityLevel(RoomID.processQualityLevelID(Character.getNumericValue(roomID.charAt(QUALITY_LEVEL))));
            //this.setSmokingStatus(RoomID.processSmokingStatusID(Character.getNumericValue(roomID.charAt(SMOKING_STATUS))));
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RentableRoom.setRoomFromID: Passed value was " + roomID);
            throw new Exception();
        }
    }



    // Get Functions
    public Integer getRoomID() { return this.roomID; }
    public RoomTheme getRoomTheme() {
        return this.roomTheme;
    }
    public RoomSize getRoomSize() { return this.roomSize; }
    public QualityLevel getQualityLevel() {
        return this.qualityLevel;
    }

    /**
     * Validates compatibility of room theme and size.
     * Throws an exception if the combination is invalid.
     *
     * @param roomTheme The theme of the room to validate.
     * @param roomSize The size of the room to validate.
     * @throws Exception If the room theme and size combination is invalid.
     */
    private void validTypes(RoomTheme roomTheme, RoomSize roomSize) throws Exception {
        boolean valid = false;
        if (roomTheme.equals(RoomTheme.NatureRetreat)) {
            if(roomSize.equals(RoomSize.Single) || roomSize.equals(RoomSize.Double) ||
                    roomSize.equals(RoomSize.Family)) {
                valid = true;
            }
        }
        else if (roomTheme.equals(RoomTheme.VintageCharm)) {
            if(roomSize.equals(RoomSize.Standard) || roomSize.equals(RoomSize.Deluxe)) {
                valid = true;
            }
        }
        else if (roomTheme.equals(RoomTheme.UrbanElegance)) {
            if(roomSize.equals(RoomSize.Suite) || roomSize.equals(RoomSize.Deluxe)) {
                valid = true;
            }
        }

        if(!valid) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.validTypes: roomTheme and " +
                    "roomSize are incompatible. Passed values were " + roomTheme + "," + roomSize);
            throw new Exception();
        }
    }
}
