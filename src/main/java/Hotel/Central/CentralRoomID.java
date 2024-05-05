package Hotel.Central;

import Hotel.Enums.QualityLevel;
import Hotel.Enums.RoomSize;
import Hotel.Enums.RoomTheme;
import Hotel.Utilities.MyLogger;
import Hotel.Room.RoomID;

import java.util.logging.Level;


/**
 * Provides methods for creating and extracting room IDs.
 */
public class CentralRoomID {
    private final static int ROOM_ID_LENGTH = 7;

    /**
     * Creates a room ID based on the specified theme, size, quality, and smoking status.
     *
     * @param theme    The theme of the room.
     * @param size     The size of the room.
     * @param quality  The quality level of the room.
     * @param smoking  The smoking status of the room.
     * @return A room ID as a String.
     *         Returns null if an error occurs during ID creation.
     */
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

    /**
     * Creates a room ID based on the specified theme, size, quality, and smoking status.
     *
     * @param theme    The theme of the room.
     * @param size     The size of the room.
     * @param quality  The quality level of the room.
     * @param smoking  The smoking status of the room.
     * @return A room ID as a String.
     *         Returns null if an error occurs during ID creation.
     */
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

    /**
     * Extracts the room number from a room ID.
     *
     * @param roomID The room ID from which to extract the room number.
     * @return The extracted room number as an integer.
     */
    public static int extractRoomNumber(String roomID) {
        return Integer.parseInt(roomID.substring(ROOM_ID_LENGTH-3));
    }
}
