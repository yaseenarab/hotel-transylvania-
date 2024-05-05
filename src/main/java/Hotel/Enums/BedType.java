package Hotel.Enums;

// Twin, Full, Queen, King

/**
 * Enumeration for bed types available in the hotel.
 * Each constant represents a different type of bed, such as Twin, Full, Queen, and King.
 */
public enum BedType {
    TW, FL,
    QN, KG;
    private static final BedType[] types = values();

    /**
     * Provides the next bed type in a cyclic manner.
     * When the end of the enum list is reached, it wraps around to the first element.
     *
     * @return BedType The next bed type in the sequence.
     */
    public BedType next() {
        return types[(this.ordinal() + 1) % types.length];
    }

    /**
     * Converts a string representation of a bed type into the corresponding BedType enum.
     * The string should include abbreviations such as "tw" for Twin, "fu" for Full, etc.
     *
     * @param str A string containing the abbreviation of the bed type.
     * @return BedType The corresponding BedType enum.
     * @throws IllegalArgumentException If the string does not map to a valid BedType.
     */
    public static BedType getEnum(String str) {
        str = str.toLowerCase();
        if (str.toLowerCase().contains("tw")) {
            return BedType.TW;
        } else if (str.toLowerCase().contains("fu")) {
            return BedType.FL;
        } else if (str.toLowerCase().contains("qu")) {
            return BedType.QN;
        } else if (str.toLowerCase().contains("kg")) {
            return BedType.KG;
        } else {
            System.out.println("Errer b");
            throw new IllegalArgumentException("Invalid BedType");
        }
    }

    /**
     * Converts a string abbreviation of a bed type into a standard formatted string for database storage.
     * The method recognizes abbreviations like "tw", "fl", "qn", and "kg", converting them to "Twin", "Full", "Queen", and "King".
     *
     * @param str A string abbreviation of the bed type.
     * @return String The formatted name of the bed type suitable for database entries.
     * @throws IllegalArgumentException If the input string is not a valid abbreviation for any bed type.
     */
    public static String databaseFormat(String str) {
        str = str.toLowerCase();
        if (str.contains("tw")) {
            return "Twin";
        } else if (str.contains("fl")) {
            return "Full";
        } else if (str.contains("qn")) {
            return "Queen";
        } else if (str.contains("kg")) {
            return "King";
        } else {
            System.out.println(str);

            throw new IllegalArgumentException("Invalid BedType");
        }
    }
}