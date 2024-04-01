package ReservationSystem.RoomEnums;

// Executive Level, Business Level, Comfort Level, Economy Level
public enum QualityLevel {
    ExL, BuL, CoL, EcL;
    private static final QualityLevel[] types = values();
    public QualityLevel next() {
        return types[(this.ordinal() + 1) % types.length];
    }
    public static QualityLevel getEnum(String str) {
        if (str.toLowerCase().contains("ex")) {
            return QualityLevel.ExL;
        } else if (str.toLowerCase().contains("bu")) {
            return QualityLevel.BuL;
        } else if (str.toLowerCase().contains("co")) {
            return QualityLevel.CoL;
        } else if (str.contains("eco")){
            return QualityLevel.EcL;
        } else {
            throw new IllegalArgumentException("Invalid QualityLevel");
        }
    }
}
