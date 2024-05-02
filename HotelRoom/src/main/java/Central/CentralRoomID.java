package Central;

import Enums.QualityLevel;
import Enums.RoomSize;
import Enums.RoomTheme;
import LoggerPackage.MyLogger;
import Room.RoomID;

import java.util.logging.Level;

public class CentralRoomID {
    private final static int ROOM_ID_LENGTH = 7;
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

    public static int extractRoomNumber(String roomID) {
        return Integer.parseInt(roomID.substring(ROOM_ID_LENGTH-3));
    }
}
