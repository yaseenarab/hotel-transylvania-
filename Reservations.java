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
        Integer roomNumber = 0;
        int count = 0;
        firstFloorRooms = new Room[Room.getFirstFloorMax() - Room.getFirstFloorMin() + 1];
        for(Room modRoom : firstFloorRooms) {
            ++count;
            modRoom = new Room(roomStatus, bedType, roomType, qualityLevel, smokingAllowed, (Room.getFirstFloorMin() + (roomNumber++)));
            if(count % 6 == 0) {
                bedType = BedType.KG;
                qualityLevel = QualityLevel.CoL;
            }
            else if(count == 1) {
                bedType = BedType.QN;
                qualityLevel = QualityLevel.BuL;
            }
            else if(count == 2) {
                bedType = BedType.FL;
            }
            else if(count == 3) {
                bedType = BedType.TW;
            }
        }

        bedType = BedType.KG;
        roomType = RoomType.UE;
        qualityLevel = QualityLevel.CoL;
        smokingAllowed = false;
        roomNumber = 0;
        count = 0;
        secondFloorRooms = new Room[Room.getSecondFloorMax() - Room.getSecondFloorMin() + 1];

        bedType = BedType.KG;
        roomType = RoomType.VC;
        qualityLevel = QualityLevel.CoL;
        smokingAllowed = false;
        roomNumber = 0;
        count = 0;
        thirdFloorRooms = new Room[Room.getThirdFloorMax() - Room.getThirdFloorMin() + 1];

    }
    protected void setVCRooms(Room[] rooms) {

    }
}
