import java.util.ArrayList;

public class Floor {
    private ArrayList<Room> Rooms;

    public ArrayList<Room> findRooms(Bed b) {
        ArrayList<Room> results = new ArrayList<Room>();
        for(Room r : this.Rooms) {
            if(Room.NOT_FOUND < r.hasBed(b)) {
                results.add(r);
            }
        }
        return results;
    }
    public ArrayList<Room> findRooms(Bed b, Integer n) {
        ArrayList<Room> results = new ArrayList<Room>();
        for(Room r : this.Rooms) {
            if(0 < r.getSpecificBedNum(b)) {
                results.add(r);
            }
        }
        return results;
    }
}
