package Hotel.Enums;

//Vacant Clean, Vacant Dirty, Occupied Clean, Occupied Dirty, Out Of Order
/**
 * Enum representing various statuses that a hotel room can have.
 * Each status provides information about the room's occupancy and cleanliness.
 */
public enum RoomStatus {
    VaCl, VaDi,
    OcCl, OcDi,
    OOO;
    private static final RoomStatus[] types = values();

    /**
     * Returns the next room status in the sequence, wrapping around to the first status.
     * This method can be used to cycle through statuses, for example, in user interfaces or simulations.
     *
     * @return RoomStatus The next room status in the enumeration.
     */
    public RoomStatus next() {
        return types[(this.ordinal() + 1) % types.length];
    }

}
