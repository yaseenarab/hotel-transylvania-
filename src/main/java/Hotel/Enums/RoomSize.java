package Hotel.Enums;

public enum RoomSize {
    Single, Standard, Double, Family, Suite, Deluxe;
    private static final RoomSize[] types = values();
    public RoomSize next() {
        return types[(this.ordinal() + 1) % types.length];
    }
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
