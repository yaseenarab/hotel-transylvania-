package org.example;

import RoomEnums.BedType;
import RoomEnums.QualityLevel;
import RoomEnums.RoomStatus;
import RoomEnums.RoomType;


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
        firstFloorRooms = new Room[Room.getFirstFloorMax() - Room.getFirstFloorMin()+1];
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

    public Room getRoom(Integer roomNumber) throws Exception{
        Room room = new Room();
        if(roomNumber >= 100 && roomNumber <= 135){
            return firstFloorRooms[roomNumber-100];
        }
        else if(roomNumber >= 200 && roomNumber <= 235){
            return secondFloorRooms[roomNumber-200];
        }
        else if(roomNumber >= 300 && roomNumber <= 335){
            return thirdFloorRooms[roomNumber-300];
        }
        else{
            throw new NullPointerException();
        }


    }
    protected void setVCRooms(Room[] rooms) {

    }
}
