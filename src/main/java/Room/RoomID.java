package Room;

import Enums.QualityLevel;
import Enums.RoomSize;
import Enums.RoomTheme;
import Utilities.MyLogger;

import java.util.logging.Level;

public class RoomID implements RoomData {
    public static String buildRoomID(RoomTheme theme, RoomSize size, QualityLevel quality, Boolean smoking, Integer roomNumber) throws Exception {
        if(theme == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.buildRoomID: theme is null");
            throw new Exception();
        }
        else if(size == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.buildRoomID: size is null");
            throw new Exception();
        }
        else if(quality == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.buildRoomID: quality is null");
            throw new Exception();
        }
        else if(smoking == null) {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.buildRoomID: smoking is null");
            throw new Exception();
        }

        String roomID = "";
        try {
            roomID += (char)(RoomID.processRoomTheme(theme) + '0');
            roomID += (char)(RoomID.processRoomSize(size) + '0');
            roomID += (char)(RoomID.processQualityLevel(quality) + '0');
            roomID += (char)(RoomID.processSmokingStatus(smoking) + '0');
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.buildRoomID: Passed values were " +
                    theme + "," + size + "," + quality + "," + smoking + "," + roomNumber);
            throw new Exception();
        }

        if(roomNumber == 0) {
            roomID += "000";
        }
        else if(roomNumber.toString().length() == 3) {
            roomID += roomNumber;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.buildRoomID: Room number not " +
                    "properly formatted, received " + roomNumber);
            throw new Exception();
        }
        return roomID;
    }
    public static Boolean checkValidRoomID(String roomID) {
        try {
            processRoomThemeID(Character.getNumericValue(roomID.charAt(RoomData.ROOM_THEME)));
            processRoomSizeID(Character.getNumericValue(roomID.charAt(RoomData.ROOM_SIZE)));
            processQualityLevelID(Character.getNumericValue(roomID.charAt(RoomData.QUALITY_LEVEL)));
            processSmokingStatusID(Character.getNumericValue(roomID.charAt(RoomData.SMOKING_STATUS)));
            if (RoomInitializer.isValidRoomNumber(
                    Integer.parseInt(roomID.substring(SMOKING_STATUS + 1)))) {
                return true;
            }
        } catch (Exception e) {}

        return false;
    }

    public static RoomTheme processRoomThemeID(Integer themeID) throws Exception {
        if(themeID.equals(RoomData.NATURE_RETREAT)) {
            return RoomTheme.NatureRetreat;
        }
        else if (themeID.equals(RoomData.URBAN_ELEGANCE)) {
            return RoomTheme.UrbanElegance;
        }
        else if (themeID.equals(RoomData.VINTAGE_CHARM)) {
            return RoomTheme.VintageCharm;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processRoomThemeID: " +
                    "Passed value was " + themeID);
            throw new Exception();
        }
    }
    public static RoomSize processRoomSizeID(Integer sizeID) throws Exception {
        if(sizeID.equals(RoomData.SINGLE)) {
            return RoomSize.Single;
        }
        else if(sizeID.equals(RoomData.STANDARD)) {
            return RoomSize.Standard;
        }
        else if(sizeID.equals(RoomData.DOUBLE)) {
            return RoomSize.Double;
        }
        else if(sizeID.equals(RoomData.FAMILY)) {
            return RoomSize.Family;
        }
        else if(sizeID.equals(RoomData.SUITE)) {
            return RoomSize.Suite;
        }
        else if(sizeID.equals(RoomData.DELUXE)) {
            return RoomSize.Deluxe;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processRoomSizeID: " +
                    "Passed value was " + sizeID);
            throw new Exception();
        }
    }
    public static QualityLevel processQualityLevelID(Integer qualityID) throws Exception {
        if(qualityID.equals(RoomData.ECONOMY)) {
            return QualityLevel.EcL;
        }
        else if(qualityID.equals(RoomData.COMFORT)) {
            return QualityLevel.CoL;
        }
        else if(qualityID.equals(RoomData.BUSINESS)) {
            return QualityLevel.BuL;
        }
        else if(qualityID.equals(RoomData.EXECUTIVE)) {
            return QualityLevel.ExL;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processQualityLevelID: " +
                    "Passed value was " + qualityID);
            throw new Exception();
        }
    }
    public static Boolean processSmokingStatusID(Integer smokingID) throws Exception {
        if(smokingID.equals(RoomData.TRUE)) {
            return true;
        }
        else if (smokingID.equals(RoomData.FALSE)) {
            return false;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processSmokingStatusID: " +
                    "Passed value was " + smokingID);
            throw new Exception();
        }
    }

    public static Integer processRoomTheme(RoomTheme theme) throws Exception {
        if(theme.equals(RoomTheme.NatureRetreat)) {
            return RoomData.NATURE_RETREAT;
        }
        else if (theme.equals(RoomTheme.UrbanElegance)) {
            return RoomData.URBAN_ELEGANCE;
        }
        else if (theme.equals(RoomTheme.VintageCharm)) {
            return RoomData.VINTAGE_CHARM;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processRoomTheme: " +
                    "Passed value was " + theme);
            throw new Exception();
        }
    }
    public static Integer processRoomSize(RoomSize size) throws Exception {
        if(size.equals(RoomSize.Single)) {
            return RoomData.SINGLE;
        }
        else if(size.equals(RoomSize.Standard)) {
            return RoomData.STANDARD;
        }
        else if(size.equals(RoomSize.Double)) {
            return RoomData.DOUBLE;
        }
        else if(size.equals(RoomSize.Family)) {
            return RoomData.FAMILY;
        }
        else if(size.equals(RoomSize.Suite)) {
            return RoomData.SUITE;
        }
        else if(size.equals(RoomSize.Deluxe)) {
            return RoomData.DELUXE;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processRoomSize " +
                    "Passed value was " + size);
            throw new Exception();
        }
    }
    public static Integer processQualityLevel(QualityLevel quality) throws Exception {
        //TRUE = 1, FALSE = 2;
        if(quality.equals(QualityLevel.EcL)) {
            return RoomData.ECONOMY;
        }
        else if(quality.equals(QualityLevel.CoL)) {
            return RoomData.COMFORT;
        }
        else if(quality.equals(QualityLevel.BuL)) {
            return RoomData.BUSINESS;
        }
        else if(quality.equals(QualityLevel.ExL)) {
            return RoomData.EXECUTIVE;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processQualityLevel: " +
                    "Passed value was " + quality);
            throw new Exception();
        }
    }
    public static Integer processSmokingStatus(Boolean smoking) throws Exception {
        if(smoking.equals(true)) {
            return RoomData.TRUE;
        }
        else if (smoking.equals(false)) {
            return RoomData.FALSE;
        }
        else {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomID.RoomIDEval.processSmokingStatus: " +
                    "Passed value was " + smoking);
            throw new Exception();
        }
    }
}
