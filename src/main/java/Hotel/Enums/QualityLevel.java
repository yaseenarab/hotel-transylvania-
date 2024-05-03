package Hotel.Enums;

// Executive Level, Business Level, Comfort Level, Economy Level
public enum QualityLevel {
    ExL, BuL, CoL, EcL;
    private static final QualityLevel[] types = values();
    public QualityLevel next() {
        return types[(this.ordinal() + 1) % types.length];
    }
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
