package Hotel.Enums;

// Nature Retreat, Urban Elegance, Vintage Classic
public enum RoomTheme {
    NatureRetreat, UrbanElegance,VintageCharm;

    private static final RoomTheme[] types = values();
    public RoomTheme next() {
        return types[(this.ordinal() + 1) % types.length];
    }
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
