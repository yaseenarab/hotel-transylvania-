package Enums;

//Vacant Clean, Vacant Dirty, Occupied Clean, Occupied Dirty, Out Of Order
public enum RoomStatus {
    VaCl, VaDi,
    OcCl, OcDi,
    OOO;
    private static final RoomStatus[] types = values();
    public RoomStatus next() {
        return types[(this.ordinal() + 1) % types.length];
    }
    public static RoomStatus getEnum(String str) {
        str = str.toLowerCase();
        if (str.contains("vac")) {
            return RoomStatus.VaCl;
        } else if (str.contains("vad")) {
            return RoomStatus.VaDi;
        } else if (str.contains("occ")) {
            return RoomStatus.OcCl;
        } else if (str.contains("ocd")) {
            return RoomStatus.OcDi;
        } else if (str.contains("ooo")) {
            return RoomStatus.OOO;
        } else {
            throw new IllegalArgumentException("Invalid RoomStatus");
        }
    }
}
