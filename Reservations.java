package ReservationSystem.Dependencies;

import ReservationSystem.RoomEnums.BedType;
import ReservationSystem.RoomEnums.QualityLevel;
import ReservationSystem.RoomEnums.RoomStatus;
import ReservationSystem.RoomEnums.RoomType;

import java.util.*;

interface IReservations {
    String makeNewReservation(String userID, Room room, Date start, Date end) throws Exception;
    RoomReservation getReservation(String reservationID) throws Exception;
    boolean reservationExists(String reservationID) throws Exception;
    RoomReservation modifyExistingReservation(String reservationID, Room room, Date start, Date end) throws Exception;
}
public class Reservations implements IReservations{
    public static class Marsha {
        private static Integer reservationIDNum = 1000000000;
        public static String getReservationID() {
            String reservationID = "TVRI";

            if (reservationIDNum >= 2147483647) {
                reservationIDNum = 1000000000;
            }
            return reservationID + reservationIDNum++;
        }
    }
    public static class RoomInitializer {
        private static final Integer ROOM_NUM_MIN = 0, ROOM_NUM_MAX = 35, NUM_FLOORS = 3;
        private static RoomQuality[][] floorRooms;
        static void initializeRooms() throws Exception {
            try {
                floorRooms = new RoomQuality[NUM_FLOORS][ROOM_NUM_MAX + 1];
                RoomType roomType = RoomType.SN;
                QualityLevel qualityLevel = QualityLevel.EcL;
                BedType bedType = BedType.KG;
                for (int i = 1; i <= NUM_FLOORS; ++i) {
                    for(int j = ROOM_NUM_MIN; j <= ROOM_NUM_MAX; ++j) {
                        floorRooms[i-1][j] = new RoomQuality(RoomStatus.VaCl, bedType, roomType, qualityLevel, false, ((i * 100) + j));
                        roomType = roomType.next();
                        bedType = bedType.next();
                        qualityLevel = qualityLevel.next().next();
                    }
                }
            }
            catch (Exception e) {
                throw new Exception("Exception caught in RoomInitializer", e);
            }
        }
        public static List<RoomQuality> getFirstFloorRooms() {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = ROOM_NUM_MIN; i <= ROOM_NUM_MAX; ++i) {
                rooms.add(floorRooms[0][i]);
            }
            return rooms;
        }
        public static List<RoomQuality> getSecondFloorRooms() {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = ROOM_NUM_MIN; i <= ROOM_NUM_MAX; ++i) {
                rooms.add(floorRooms[1][i]);
            }

            return rooms;
        }
        public static List<RoomQuality> getThirdFloorRooms() {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = ROOM_NUM_MIN; i <= ROOM_NUM_MAX; ++i) {
                rooms.add(floorRooms[2][i]);
            }
            return rooms;
        }
        public static Room getRoom(Integer roomNumber) {
            if(roomNumber != null && ((roomNumber / 100) > 0) &&
                    ((roomNumber / 100) < NUM_FLOORS) &&
                    ((roomNumber % 100) > ROOM_NUM_MIN) &&
                    ((roomNumber % 100) > ROOM_NUM_MAX)) {
                return floorRooms[(roomNumber / 100) - 1][(roomNumber % 100) - 1];
            }
            return null;
        }

        static List<RoomQuality> getAvailableRooms() {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = 0; i < NUM_FLOORS; ++i) {
                for(int j = ROOM_NUM_MIN; j <= ROOM_NUM_MAX; ++j) {
                    if(floorRooms[i][j].getRoomStatus().equals(RoomStatus.VaCl)) {
                        rooms.add(floorRooms[i][j]);
                    }
                }
            }
            return rooms;
        }

        public static List<RoomQuality> getAvailableRooms(BedType bedType, RoomType roomType,
                                                   QualityLevel qualityLevel, Boolean smokingStatus) {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = 0; i < NUM_FLOORS; ++i) {
                for(int j = ROOM_NUM_MIN; j <= ROOM_NUM_MAX; ++j) {
                    if(floorRooms[i][j].getRoomStatus().equals(RoomStatus.VaCl) &&
                            floorRooms[i][j].getRoomType().equals(roomType) &&
                            floorRooms[i][j].getBedType().equals(bedType) &&
                            floorRooms[i][j].getQualityLevel().equals(qualityLevel) &&
                            floorRooms[i][j].getSmokingStatus().equals(smokingStatus)) {
                        rooms.add(floorRooms[i][j]);
                    }
                }
            }
            return rooms;
        }
        public static List<RoomQuality> getAvailableRooms(RoomType roomType) {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = 0; i < NUM_FLOORS; ++i) {
                for(int j = ROOM_NUM_MIN; j <= ROOM_NUM_MAX; ++j) {
                    if(floorRooms[i][j].getRoomStatus().equals(RoomStatus.VaCl) &&
                            floorRooms[i][j].getRoomType().equals(roomType)) {
                        rooms.add(floorRooms[i][j]);
                    }
                }
            }
            return rooms;
        }
        static List<RoomQuality> getAvailableRooms(QualityLevel qualityLevel) {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = 0; i < NUM_FLOORS; ++i) {
                for(int j = ROOM_NUM_MIN; j <= ROOM_NUM_MAX; ++j) {
                    if(floorRooms[i][j].getRoomStatus().equals(RoomStatus.VaCl) &&
                            floorRooms[i][j].getQualityLevel().equals(qualityLevel)) {
                        rooms.add(floorRooms[i][j]);
                    }
                }
            }
            return rooms;
        }
        static List<RoomQuality> getAvailableRooms(BedType bedType) {
            List<RoomQuality> rooms = new ArrayList<>();
            for(int i = 0; i < NUM_FLOORS; ++i) {
                for(int j = ROOM_NUM_MIN; j <= ROOM_NUM_MAX; ++j) {
                    if(floorRooms[i][j].getRoomStatus().equals(RoomStatus.VaCl) &&
                            floorRooms[i][j].getBedType().equals(bedType)) {
                        rooms.add(floorRooms[i][j]);
                    }
                }
            }
            return rooms;
        }
    }
    // Private Variables
    private Map<String, RoomReservation> rooms;

    // Constructors
    public Reservations() throws Exception {
        try {
            RoomInitializer.initializeRooms();
            rooms = new TreeMap<>();
            System.out.println("Successfully initialized Reservations.");
        }
        catch(Exception e) {
            throw new Exception("Exception caught in Reservations()", e);
        }
    }

    // Get Functions
    public List<RoomQuality> getFirstFloorRooms() {
        return RoomInitializer.getFirstFloorRooms();
    }
    public List<RoomQuality> getSecondFloorRooms() {
        return RoomInitializer.getSecondFloorRooms();
    }
    public List<RoomQuality> getThirdFloorRooms() {
        return RoomInitializer.getThirdFloorRooms();
    }
    public static List<RoomQuality> getAvailableRooms() {
        return RoomInitializer.getAvailableRooms();
    }
    public static List<RoomQuality> getAvailableRooms(BedType bedType, RoomType roomType,
                                               QualityLevel qualityLevel, Boolean smokingStatus) {
        return RoomInitializer.getAvailableRooms(bedType,roomType,qualityLevel,smokingStatus);
    }
    public RoomReservation getReservation(String reservationID) throws Exception {
        try {
            if(reservationExists(reservationID)) {
                return rooms.get(reservationID);
            }
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in getReservation", e);
        }
        throw new Exception("Fatal error thrown in getReservation, " + reservationID + " doesn't exist");
    }
    public String getProfileID(Guest guest) {
        for(String key : rooms.keySet()) {
            if(rooms.get(key).equals(guest))
                return key;
        }
        return null;
    }

    // Modifiers
    public String makeNewReservation(String profileID, Room room, Date start, Date end) throws Exception {
        try {
            String reservationID = Marsha.getReservationID();
            //rooms.put(reservationID, new RoomReservation(Profiles.getProfile(profileID), room, start, end));
            //Profiles.getProfile(profileID).addReservation(reservationID);

            return reservationID;
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in makeNewReservation", e);
        }
    }
    public RoomReservation modifyExistingReservation(String reservationID, Room room, Date start, Date end) throws Exception {
        try {
            if(reservationExists(reservationID)) {
                RoomReservation modRoom = rooms.get(reservationID);
                modRoom.setRoom(room);
                modRoom.setStartDate(start);
                modRoom.setEndDate(end);

                rooms.put(reservationID, modRoom);
            }
        }
        catch(Exception e) {
            throw new Exception("Fatal error thrown in modifyExistingReservation", e);
        }
        throw new Exception("Fatal error thrown in getReservation, " + reservationID + " doesn't exist");
    }
    public boolean reservationExists(String reservationID) throws Exception {
        try {
            return rooms.containsKey(reservationID);
        }
        catch(Exception e) {
            throw new Exception("Exception caught in reservationExists", e);
        }
    }
}


