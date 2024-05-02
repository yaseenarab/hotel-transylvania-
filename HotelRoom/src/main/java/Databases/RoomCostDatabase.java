package Databases;

import Enums.QualityLevel;
import Enums.RoomSize;
import Utilities.MyLogger;
import Room.RoomData;
import Room.RoomID;

import java.math.BigDecimal;
import java.util.logging.Level;

public class RoomCostDatabase implements RoomData {
    private static int DEFAULT_RATE = 100, SMOKING_DAILY_RATE = 10, NO_SMOKING_DAILY_RATE = 0,
            ECONOMY_DAILY_RATE = 0, COMFORT_DAILY_RATE = 5, BUSINESS_DAILY_RATE = 10,
            EXECUTIVE_DAILY_RATE = 15, SINGLE_DAILY_RATE = 0, STANDARD_DAILY_RATE = 5,
            DOUBLE_DAILY_RATE = 10, FAMILY_DAILY_RATE = 15, SUITE_DAILY_RATE = 20,
            DELUXE_DAILY_RATE = 25;
    private static BigDecimal calculateDailyRate(String roomID) {
        try {
            int cost = DEFAULT_RATE;

            cost += calculateRoomSizeRate(roomID);
            cost += calculateRoomQualityRate(roomID);
            cost += calculateSmokingRate(roomID);

            return new BigDecimal(cost);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RoomCostDatabase.calculateDailyRate: Passed values were " + roomID);
            return null;
        }
    }
    private static int calculateRoomSizeRate(String roomID) throws Exception {
        try {
            RoomSize roomSize = RoomID.processRoomSizeID(
                    Character.getNumericValue(roomID.charAt(ROOM_SIZE)));
            if(roomSize.equals(RoomSize.Single)) {
                return SINGLE_DAILY_RATE;
            }
            else if(roomSize.equals(RoomSize.Standard)) {
                return STANDARD_DAILY_RATE;
            }
            else if(roomSize.equals(RoomSize.Double)) {
                return DOUBLE_DAILY_RATE;
            }
            else if(roomSize.equals(RoomSize.Family)) {
                return FAMILY_DAILY_RATE;
            }
            else if(roomSize.equals(RoomSize.Suite)) {
                return SUITE_DAILY_RATE;
            }
            else {
                return DELUXE_DAILY_RATE;
            }
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RoomCostDatabase.calculateRoomSizeRate: Passed value was " + roomID);
            throw new Exception();
        }
    }
    private static int calculateRoomQualityRate(String roomID) throws Exception {
        try {
            QualityLevel qualityLevel = RoomID.processQualityLevelID(
                    Character.getNumericValue(roomID.charAt(QUALITY_LEVEL)));
            if(qualityLevel.equals(QualityLevel.EcL)) {
                return ECONOMY_DAILY_RATE;
            }
            else if(qualityLevel.equals(QualityLevel.CoL)) {
                return COMFORT_DAILY_RATE;
            }
            else if(qualityLevel.equals(QualityLevel.BuL)) {
                return BUSINESS_DAILY_RATE;
            }
            else {
                return EXECUTIVE_DAILY_RATE;
            }
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RoomCostDatabase.calculateRoomQualityRate: Passed value was " + roomID);
            throw new Exception();
        }
    }
    private static int calculateSmokingRate(String roomID) throws Exception {
        try {
            Boolean smokingStatus = RoomID.processSmokingStatusID(
                    Character.getNumericValue(roomID.charAt(SMOKING_STATUS)));
            if(smokingStatus) {
                return SMOKING_DAILY_RATE;
            }
            else {
                return NO_SMOKING_DAILY_RATE;
            }
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in RoomCostDatabase.calculateSmokingRate: Passed value was " + roomID);
            throw new Exception();
        }
    }
}
