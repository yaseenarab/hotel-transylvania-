import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;

public class Reservations {
    private final RoomStatus ROOM_STATUS = RoomStatus.VaCl;
    private final BedType BED_TYPE = BedType.KG;
    private final RoomType ROOM_TYPE = RoomType.NR;
    private final QualityLevel QUALITY_LEVEL = QualityLevel.CoL;
    private final Boolean SMOKING_ALLOWED = false;

    private Room[] firstFloorRooms, secondFloorRooms, thirdFloorRooms;

    Reservations() throws Exception {
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
}
