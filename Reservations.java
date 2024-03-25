import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;

public class Reservations {
    public static class Cashiering {
        Cashiering() {}
        public void addCard(GuestProfile guest, Card card) {
            guest.setCard(card);
        }
        public void removeCard(GuestProfile guest) {
            guest.setCard(null);
        }
        public boolean isCardOnFile(GuestProfile guest) {
            return guest.getCard() != null;
        }
        public Card getCardOnFile(GuestProfile guest) {
            return guest.getCard();
        }
    }
    private final RoomStatus ROOM_STATUS = RoomStatus.VaCl;
    private final BedType BED_TYPE = BedType.KG;
    private final RoomType ROOM_TYPE = RoomType.NR;
    private final QualityLevel QUALITY_LEVEL = QualityLevel.CoL;
    private final Boolean SMOKING_ALLOWED = false;
    private Map <Integer, RoomReservation> roomReservations;
    private Map <Integer, GuestProfile> profiles;

    private Room[] firstFloorRooms, secondFloorRooms, thirdFloorRooms;

    Reservations() throws Exception {
        roomReservations = new TreeMap<>();

        firstFloorRooms = new Room[Room.getFirstFloorMax() - Room.getFirstFloorMin() + 1];
        for(int i = Room.getFirstFloorMin(); i < Room.getFirstFloorMax(); ++i) {
            firstFloorRooms[i] = new Room(ROOM_STATUS, BED_TYPE, ROOM_TYPE, QUALITY_LEVEL, SMOKING_ALLOWED, i);
        }

        secondFloorRooms = new Room[Room.getSecondFloorMax() - Room.getSecondFloorMin() + 1];
        for(int i = Room.getSecondFloorMin(); i < Room.getSecondFloorMax(); ++i) {
            secondFloorRooms[i] = new Room(ROOM_STATUS, BED_TYPE, ROOM_TYPE, QUALITY_LEVEL, SMOKING_ALLOWED, i);
        }

        thirdFloorRooms = new Room[Room.getThirdFloorMax() - Room.getThirdFloorMin() + 1];
        for(int i = Room.getThirdFloorMin(); i < Room.getThirdFloorMax(); ++i) {
            thirdFloorRooms[i] = new Room(ROOM_STATUS, BED_TYPE, ROOM_TYPE, QUALITY_LEVEL, SMOKING_ALLOWED, i);
        }
    }
    public void setRoomStatus(RoomStatus roomStatus, Room[] rooms) throws Exception{
        for (Room modRoom : rooms) {
            try {
                modRoom.setRoomStatus(roomStatus);
            }
            catch (Exception e) {
                throw new Exception("Error in setRoomStatus when passed " + modRoom, e);
            }
        }
    }
    public void setBedType(BedType bedType, Room[] rooms) throws Exception{
        for (Room modRoom : rooms) {
            try {
                modRoom.setBedType(bedType);
            }
            catch (Exception e) {
                throw new Exception("Error in setBedType when passed " + modRoom, e);
            }
        }
    }
    public void setRoomType(RoomType roomType, Room[] rooms) throws Exception{
        for (Room modRoom : rooms) {
            try {
                modRoom.setRoomType(roomType);
            }
            catch (Exception e) {
                throw new Exception("Error in setRoomType when passed " + modRoom, e);
            }
        }
    }
    public void setSmokingAllowed(Boolean smokingAllowed, Room[] rooms) throws Exception{
        for (Room modRoom : rooms) {
            try {
                modRoom.setSmokingAllowed(smokingAllowed);
            }
            catch (Exception e) {
                throw new Exception("Error in setSmokingAllowed when passed " + modRoom, e);
            }
        }
    }
    public void setQualityLevel(QualityLevel qualityLevel, Room[] rooms) throws Exception{
        for (Room modRoom : rooms) {
            try {
                modRoom.setQualityLevel(qualityLevel);
            }
            catch (Exception e) {
                throw new Exception("Error in setQualityLevel when passed " + modRoom, e);
            }
        }
    }

    public RoomReservation getReservation(Integer reservationID) { return roomReservations.get(reservationID); }

    public RoomReservation getReservationID(GuestProfile guest, Room roomChoice, ZonedDateTime startDate, ZonedDateTime endDate) {
        for(Integer reservationID : guest.getRoomNights()) {
            if(roomReservations.containsKey(reservationID)) {
                if(roomReservations.get(reservationID).getGuest().equals(guest)) {
                    if(roomReservations.get(reservationID).getAssignedRoom().equals(roomChoice)) {
                        if(roomReservations.get(reservationID).getStartDate().equals(startDate)) {
                            if (roomReservations.get(reservationID).getEndDate().equals(endDate)) {
                                return roomReservations.get(reservationID);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public Room getRoom(Integer reservationID) {
        if(roomReservations.containsKey(reservationID)) {
            return roomReservations.get(reservationID).getAssignedRoom();
        }
        return null;
    }
    public GuestProfile getGuest(Integer reservationID) {
        if(roomReservations.containsKey(reservationID)) {
            return roomReservations.get(reservationID).getGuest();
        }
        return null;
    }
    public GuestProfile getGuestNoReservationID(Integer userID) {
        if(profiles.containsKey(userID)) {
            return profiles.get(userID);
        }
        return null;
    }
    public Integer makeReservation(GuestProfile guest, Room roomChoice, ZonedDateTime startDate, ZonedDateTime endDate) throws Exception {
        Integer reservationID = null;
        reservationID = this.hashCode();
        roomReservations.put(reservationID, new RoomReservation(guest, roomChoice, startDate, endDate ));

        guest.addReservation(reservationID);

        return reservationID;
    }
    public Integer makeProfile(String firstName, String lastName, String email, String phoneNumber, Card card) throws Exception {
        GuestProfile profile = new GuestProfile(firstName, lastName,email,phoneNumber, card);
        Integer profileID = profile.getUserID();
        if(! profiles.containsKey(profileID)) {
            profiles.put(profileID, profile);
        }
        return profileID;
    }
}
