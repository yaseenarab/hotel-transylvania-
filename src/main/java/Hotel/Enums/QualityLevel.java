package Hotel.Enums;

// Executive Level, Business Level, Comfort Level, Economy Level

/**
 * Enumeration for different quality levels of hotel rooms.
 * This enum defines categories such as Executive Level, Business Level, Comfort Level, and Economy Level.
 */
public enum QualityLevel {
    ExL, BuL, CoL, EcL;
    private static final QualityLevel[] types = values();

    /**
     * Returns the next quality level in a cyclic manner.
     * When the end of the enum list is reached, it wraps around to the first element.
     *
     * @return QualityLevel The next quality level in the sequence.
     */
    public QualityLevel next() {
        return types[(this.ordinal() + 1) % types.length];
    }
    /**
     * Converts a string representation of a quality level into the corresponding QualityLevel enum.
     * The string should include words like "executive", "business", "comfort", or "economy".
     *
     * @param str A string containing the name or part of the name of the quality level.
     * @return QualityLevel The corresponding QualityLevel enum.
     * @throws IllegalArgumentException If the string does not map to a valid QualityLevel.
     */
    public static QualityLevel getEnum(String str) {
        str = str.toLowerCase();
        if (str.toLowerCase().contains("executive")) {
            return QualityLevel.ExL;
        } else if (str.toLowerCase().contains("business")) {
            return QualityLevel.BuL;
        } else if (str.toLowerCase().contains("comfort")) {
            return QualityLevel.CoL;
        } else if (str.toLowerCase().contains("economy")){
            return QualityLevel.EcL;
        } else {
            throw new IllegalArgumentException("Invalid QualityLevel");
        }
    }

    /**
     * Converts a string abbreviation of a quality level into a standard formatted string for database storage.
     * The method recognizes abbreviations like "ex", "bu", "co", and "ec", converting them to
     * "Executive Level", "Business Level", "Comfort Level", and "Economy Level" respectively.
     *
     * @param str A string abbreviation of the quality level.
     * @return String The formatted name of the quality level suitable for database entries.
     * @throws IllegalArgumentException If the input string is not a valid abbreviation for any quality level.
     */
    public static String databaseFormat (String str) {
        str = str.toLowerCase();
        if (str.toLowerCase().contains("ex")) {
            return "Executive Level";
        } else if (str.toLowerCase().contains("bu")) {
            return "Business Level";
        } else if (str.toLowerCase().contains("co")) {
            return "Comfort Level";
        } else if (str.toLowerCase().contains("ec")){
            return "Economy Level";
        } else {
            throw new IllegalArgumentException("Invalid QualityLevel");
        }
    }
}
