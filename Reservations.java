import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;

import java.util.List;

public class Reservations {
    private Room[]
            firstFloorRooms,
            secondFloorRooms,
            thirdFloorRooms;
    Reservations() throws Exception {
        RoomStatus roomStatus = RoomStatus.VaCl;
        BedType bedType = BedType.KG;
        RoomType roomType = RoomType.NR;
        QualityLevel qualityLevel = QualityLevel.CoL;
        Boolean smokingAllowed = false;

        Integer roomNumber = 100;
        firstFloorRooms = new Room[Room.getFirstFloorMax() - Room.getFirstFloorMin() + 1];
        for(Room modRoom : firstFloorRooms) {
            modRoom = new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, roomNumber++);
        }

        roomNumber = 200;
        secondFloorRooms = new Room[Room.getSecondFloorMax() - Room.getSecondFloorMin() + 1];
        for(Room modRoom : secondFloorRooms) {
            modRoom = new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, roomNumber++);
        }

        roomNumber = 300;
        thirdFloorRooms = new Room[Room.getThirdFloorMax() - Room.getThirdFloorMin() + 1];
        for(Room modRoom : thirdFloorRooms) {
            modRoom = new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, roomNumber++);
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
