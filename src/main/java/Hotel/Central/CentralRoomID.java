package Hotel.Central;

import Hotel.Enums.QualityLevel;
import Hotel.Enums.RoomSize;
import Hotel.Enums.RoomTheme;
import Hotel.LoggerPackage.MyLogger;
import Hotel.Room.RoomID;

import java.util.logging.Level;

public class CentralRoomID {

    public static String createRoomID(String theme, String size, String quality, boolean smoking) {

        try {
            return RoomID.buildRoomID(RoomTheme.getEnum(theme), RoomSize.getEnum(size), QualityLevel.getEnum(quality), smoking, 0);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralRoomID.createRoomID: " +
                    "Values passed were " + theme + "," + size + "," + quality);
            return null;
        }


    }
    public static String createRoomID(RoomTheme theme, RoomSize size, QualityLevel quality, Boolean smoking) {
        try {
            return RoomID.buildRoomID(theme, size, quality, smoking, 0);
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in CentralRoomID.createRoomID: " +
                    "Values passed were " + theme + "," + size + "," + quality);
            return null;
        }
    }

}
