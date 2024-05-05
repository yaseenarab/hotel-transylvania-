package Hotel.Enums;

// Nature Retreat, Urban Elegance, Vintage Classic


/**
 * Enum representing different themes of rooms in a hotel.
 * Each theme corresponds to a specific style or decor of a hotel room.
 */
public enum RoomTheme {
    NatureRetreat, UrbanElegance,VintageCharm;

    private static final RoomTheme[] types = values();

    /**
     * Returns the next theme in the enum sequence. Wraps around to the first if it reaches the end.
     *
     * @return The next RoomTheme in the sequence.
     */
    public RoomTheme next() {
        return types[(this.ordinal() + 1) % types.length];
    }

    /**
     * Converts a string representation of a room theme to its corresponding RoomTheme enum.
     * The string comparison is case-insensitive and checks for substring matches with the theme names.
     *
     * @param str The string representation of the RoomTheme.
     * @return The corresponding RoomTheme enum.
     * @throws IllegalArgumentException if the input string does not match any RoomTheme.
     */
    public static RoomTheme getEnum(String str) {
        str = str.toLowerCase();
        if (str.contains("na")) {
            return RoomTheme.NatureRetreat;
        } else if (str.contains("ur")) {
            return RoomTheme.UrbanElegance;
        } else if (str.contains("vi")) {
            return RoomTheme.VintageCharm;
        } else {
            throw new IllegalArgumentException("Invalid RoomType");
        }
    }
}
