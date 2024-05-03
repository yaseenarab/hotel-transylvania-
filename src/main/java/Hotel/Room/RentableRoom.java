package Hotel.Room;

import Hotel.Enums.*;

import Hotel.LoggerPackage.MyLogger;

import java.util.logging.Level;

public class RentableRoom extends Room implements RoomData {

    private Integer roomID;
    private RoomTheme roomTheme;
    private RoomSize roomSize;
    private QualityLevel qualityLevel;
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
    private void setRoomTheme(RoomTheme roomTheme) throws Exception {
        if(roomTheme == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setRoomTheme: roomTheme is null");
            throw new Exception();
        }
        this.roomTheme = roomTheme;
    }
    private void setRoomSize(RoomSize roomSize) throws Exception {
        if(roomSize == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setRoomSize: roomSize is null");
            throw new Exception();
        }
        try {
            if(roomSize.equals(RoomSize.Single) || roomSize.equals(RoomSize.Standard)) {
                this.setNumBeds(1);
            }
            else if (roomSize.equals(RoomSize.Double) || roomSize.equals(RoomSize.Suite) ||
                    roomSize.equals(RoomSize.Deluxe)) {
                this.setNumBeds(2);
            }
            else if (roomSize.equals(RoomSize.Family)) {
                this.setNumBeds(3);
            }
            this.roomSize = roomSize;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RentableRoom.setRoomSize: Passed value was " + roomSize);
            throw new Exception();
        }
    }
    private void setQualityLevel(QualityLevel qualityLevel) throws Exception {
        if(qualityLevel == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RentableRoom.setQualityLevel: qualityLevel is null");
            throw new Exception();
        }
        try {
            if(qualityLevel.equals(QualityLevel.EcL)) {
                this.setBedType(BedType.TW);
            }
            else if(qualityLevel.equals(QualityLevel.CoL)) {
                this.setBedType(BedType.FL);
            }
            else if(qualityLevel.equals(QualityLevel.BuL)) {
                this.setBedType(BedType.QN);
            }
            else if(qualityLevel.equals(QualityLevel.ExL)) {
                this.setBedType(BedType.KG);
            }
            this.qualityLevel = qualityLevel;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RentableRoom.setQualityLevel: Passed value was " + qualityLevel);
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
