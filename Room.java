import java.util.ArrayList;

// final int[] primes = {2, 79, 191, 311, 439, 577, 709 };
public class Room {
    public static final int NOT_FOUND = -1;
    private int ID;
    private QL Quality;
    private RoomStatus State;
    private ArrayList<BedInfo> Beds;
    private boolean Smoke = false;

    public int hashCode() {
        final int[] primes = {2, 79, 191, 311, 439, 577, 709 };
        // 'i' accesses indexes of primes[], starts at -1 due to ++prefix
        int hash = 1, i = -1;

        hash += this.ID * primes[++i];
        hash += this.Quality.hashCode() * primes[++i];
        hash += this.State.hashCode() * primes[++i];
        for(BedInfo bed : this.Beds) {
            hash += Integer.valueOf(bed.numBeds).hashCode() * primes[i];
            i = ++i % primes.length;
        }
        hash += Boolean.hashCode(this.Smoke) * primes[i];

        return hash;
    }

    public Integer getID() { return this.ID; }
    public void setID(Integer i) { this.ID = i; }
    public QL getQuality() { return this.Quality; }
    public void setQuality(QL q) { this.Quality = q; }
    public RoomStatus getRoomState() { return this.State; }
    public void setState(RoomStatus s) { this.State = s; }
    public Boolean getSmoke() { return this.Smoke; }
    public void setSmoke(Boolean b) { this.Smoke = b; }
    public ArrayList<BedInfo> getBeds() { return this.Beds; }
    public Integer getTotalNumBeds() {
        int totalNumBeds = 0;
        for (BedInfo bed : this.Beds) {
            totalNumBeds += bed.numBeds;
        }
        return totalNumBeds;
    }
    public void setBeds(ArrayList<BedInfo> b) { this.Beds = b; }
    public Integer hasBed(Bed b) {
        for(int i = 0; i < this.Beds.size(); ++i) {
            if(this.Beds.get(i).bedType == b && 0 < this.Beds.get(i).numBeds) {
                return i;
            }
        }
        return NOT_FOUND;
    }
    public Integer getSpecificBedNum(Bed b) {
        int ndx = hasBed(b);
        return(NOT_FOUND <= ndx ? 0 : this.Beds.get(ndx).numBeds);
    }
}
