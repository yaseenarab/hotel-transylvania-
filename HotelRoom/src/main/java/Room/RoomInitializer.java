package Room;

import Enums.QualityLevel;
import Enums.RoomSize;
import Enums.RoomTheme;
import LoggerPackage.MyLogger;

import java.util.logging.Level;

public class RoomInitializer implements RoomData {

    protected static Integer numFloors, roomsPerFloor, currRoomNumber;
    private static QualityLevel initQualityLevel;
    private static RoomSize initRoomSize;
    private static RoomTheme initRoomTheme;
    private static Boolean initSmokingStatus;
    private static RentableRoom[][] roomMatrix;
    private void initRooms(Integer floorCount, Integer roomCount) throws Exception {
        try {
            setNumFloors(floorCount);
            setRoomsPerFloor(roomCount);
            roomMatrix = new RentableRoom[numFloors][roomsPerFloor + 1];
            initQualityLevel = QualityLevel.EcL;
            initRoomTheme = RoomTheme.NatureRetreat;
            initSmokingStatus = false;

            for(int i = 0; i < numFloors; ++i) {
                currRoomNumber = (i + 1) * RoomData.ROOM_NUMBER_FORMAT;
                for(int j = 0; j < roomsPerFloor; ++j) {
                    setRoomSize(j);
                    assert(isValidRoomNumber(currRoomNumber));
                    //roomMatrix[i][j] = new RentableRoom(RoomID.buildRoomID(initRoomTheme, initRoomSize, initQualityLevel, initSmokingStatus, currRoomNumber), RoomData.DEF_ROOM_STAT, currRoomNumber);

                    //CentralRoom.addRoom(roomMatrix[i][j].getRoomID(),roomMatrix[i][j]);
                    ++currRoomNumber;
                    initSmokingStatus = !initSmokingStatus;
                    initQualityLevel = initQualityLevel.next();
                }
                initRoomTheme = initRoomTheme.next();
            }
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error in RoomInitializer.InitRooms.initRooms: " +
                    "Passed values were " + floorCount + "," + roomCount);
            throw new Exception();
        }
    }
    private void setNumFloors(Integer floorCount) {
        if (floorCount == null || floorCount < 0) {
            numFloors = RoomData.DEF_NUM_FLOORS;
        }
        else {
            numFloors = floorCount;
        }
    }
    private void setRoomsPerFloor(Integer roomCount) {
        if(roomCount == null || roomCount < 0) {
            roomsPerFloor = RoomData.DEF_ROOMS_PER_FLOOR;
        }
        else {
            roomsPerFloor = roomCount;
        }
    }
    private void setRoomSize(Integer ndx) {
        if(initRoomTheme.equals(RoomTheme.NatureRetreat)) {
            initRoomSize = RoomData.NATURE_ROOM_SIZES[ndx % RoomData.NATURE_ROOM_SIZES.length];
        }
        else if (initRoomTheme.equals(RoomTheme.UrbanElegance)) {
            initRoomSize = RoomData.URBAN_ELEGANCE_SIZES[ndx % RoomData.URBAN_ELEGANCE_SIZES.length];
        }
        else {
            initRoomSize = RoomData.VINTAGE_CHARM_SIZES[ndx % RoomData.VINTAGE_CHARM_SIZES.length];
        }
    }
    public Integer getNumFloors() {
        return numFloors;
    }
    public Integer getRoomsPerFloor() {
        return roomsPerFloor;
    }
    public RentableRoom[][] getRoomMatrix() {
        return roomMatrix;
    }
    public static boolean isValidRoomNumber(Integer roomNumber) {
        if(roomNumber == 0)
            return true;
        if (roomNumber < ROOM_NUMBER_FORMAT)
            return false;
        if((roomNumber / ROOM_NUMBER_FORMAT) > numFloors)
            return false;
        if((roomNumber % ROOM_NUMBER_FORMAT) >= roomsPerFloor)
            return false;

        return true;
    }


}
