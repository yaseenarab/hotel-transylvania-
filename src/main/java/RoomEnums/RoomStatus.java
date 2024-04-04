package RoomEnums;

//Vacant Clean, Vacant Dirty, Occupied Clean, Occupied Dirty, Out Of Order
public enum RoomStatus {
    VaCl, VaDi,
    OcCl, OcDi,
    OOO;
    private static final RoomStatus[] types = values();
    public RoomStatus next() {
        return types[(this.ordinal() + 1) % types.length];
    }
}
