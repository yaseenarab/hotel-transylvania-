package Hotel.Enums;


/**
 * Enum representing different room sizes available in a hotel.
 * Each enum constant represents a specific category of hotel room size.
 */
public enum RoomSize {
    Single, Standard, Double, Family, Suite, Deluxe;
    private static final RoomSize[] types = values();

    /**
     * Returns the next room size in the list, wrapping around to the first room size if at the end.
     * This method is useful for iterating over room sizes cyclically.
     *
     * @return RoomSize The next room size in the enumeration.
     */
    public RoomSize next() {
        return types[(this.ordinal() + 1) % types.length];
    }

    /**
     * Converts a string identifier into the corresponding RoomSize enum constant.
     * This method simplifies parsing text input into specific room size enums.
     *
     * @param str The string identifier for a room size.
     * @return RoomSize The corresponding RoomSize enum constant.
     * @throws IllegalArgumentException If the string does not correspond to any known room size.
     */
    public static RoomSize getEnum(String str) {
        str = str.toLowerCase();
        if (str.contains("si")) {
            return RoomSize.Single;
        } else if (str.contains("st")) {
            return RoomSize.Standard;
        } else if (str.contains("do")) {
            return RoomSize.Double;
        } else if (str.contains("fa")) {
            return RoomSize.Family;
        } else if (str.contains("su")) {
            return RoomSize.Suite;
        } else if (str.contains("de")) {
            return RoomSize.Deluxe;
        } else {
            throw new IllegalArgumentException("Invalid RoomType");
        }
    }
}
