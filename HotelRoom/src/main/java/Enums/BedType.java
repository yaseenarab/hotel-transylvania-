package Enums;

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
        if (str.contains("tw")) {
            return BedType.TW;
        } else if (str.contains("fu")) {
            return BedType.FL;
        } else if (str.contains("qu")) {
            return BedType.QN;
        } else if (str.contains("ki")) {
            return BedType.KG;
        } else {
            throw new IllegalArgumentException("Invalid BedType");
        }
    }
}