package Hotel.Enums;

// Twin, Full, Queen, King
public enum BedType {
    TW, FL,
    QN, KG;
    private static final BedType[] types = values();
    public BedType next() {
        return types[(this.ordinal() + 1) % types.length];
    }
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
    public static String databaseFormat(String str) {
        str = str.toLowerCase();
        if (str.contains("tw")) {
            return "Twin";
        } else if (str.toLowerCase().contains("fl")) {
            return "Full";
        } else if (str.toLowerCase().contains("qn")) {
            return "Queen";
        } else if (str.toLowerCase().contains("kg")) {
            return "King";
        } else {
            System.out.println(str);
            throw new IllegalArgumentException("Invalid BedType");
        }
    }
}