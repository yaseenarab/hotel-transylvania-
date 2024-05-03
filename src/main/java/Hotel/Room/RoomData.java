package Hotel.Room;

import Hotel.Enums.RoomSize;
import Hotel.Enums.RoomStatus;

public interface RoomData {
    Integer ROOM_THEME = 0;
    Integer ROOM_SIZE = 1;
    Integer QUALITY_LEVEL = 2;
    Integer SMOKING_STATUS = 3;
    Integer NATURE_RETREAT = 1;
    Integer URBAN_ELEGANCE = 2;
    Integer VINTAGE_CHARM = 3;
    Integer SINGLE = 1;
    Integer STANDARD = 2;
    Integer DOUBLE = 3;
    Integer FAMILY = 4;
    Integer SUITE = 5;
    Integer DELUXE = 6;
    Integer ECONOMY = 1;
    Integer COMFORT = 2;
    Integer BUSINESS = 3;
    Integer EXECUTIVE = 4;
    Integer TRUE = 1;
    Integer FALSE = 2;
    Integer ROOM_ID_SIZE = 7;
    RoomSize[] NATURE_ROOM_SIZES = { RoomSize.Single, RoomSize.Double, RoomSize.Family };
    RoomSize[] URBAN_ELEGANCE_SIZES = { RoomSize.Suite, RoomSize.Deluxe };
    RoomSize[] VINTAGE_CHARM_SIZES = { RoomSize.Standard, RoomSize.Deluxe };
    RoomStatus DEF_ROOM_STAT = RoomStatus.VaCl;
    Integer ROOM_NUMBER_FORMAT = 100;
    Integer DEF_NUM_FLOORS = 3;
    Integer DEF_ROOMS_PER_FLOOR = 35;
    String FILE_NAME = "Rooms.csv";
}
