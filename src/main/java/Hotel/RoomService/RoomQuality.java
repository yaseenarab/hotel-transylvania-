package Hotel.RoomService;


import Hotel.RoomService.Enums.BedType;
import Hotel.RoomService.Enums.QualityLevel;
import Hotel.RoomService.Enums.RoomStatus;
import Hotel.RoomService.Enums.RoomType;

public class RoomQuality extends Room {
    // Private Variables
    private RoomType roomType;
    private QualityLevel qualityLevel;

    // Constructor
    public RoomQuality(RoomStatus roomStatus, BedType bedType, RoomType roomType,
                       QualityLevel qualityLevel, Boolean smokingStatus, Integer roomNumber) throws Exception {
        super(roomStatus, bedType, smokingStatus, roomNumber);
        this.setRoomType(roomType);
        this.setQualityLevel(qualityLevel);
        System.out.println("Successfully initialized RoomQuality");
    }

    // Set Functions
    public void setRoomType(RoomType roomType) throws Exception {
        if(roomType == null) {
            throw new Exception("Fatal error thrown in setRoomType, value is NULL");
        }
        try {
            this.roomType = roomType;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setRoomType", e);
        }
    }
    public void setQualityLevel(QualityLevel qualityLevel) throws Exception {
        if(qualityLevel == null) {
            throw new Exception("Fatal error thrown in setQualityLevel, value is NULL");
        }
        try {
            this.qualityLevel = qualityLevel;
        }
        catch (Exception e) {
            throw new Exception("Fatal error thrown in setQualityLevel", e);
        }
    }

    // Get Functions
    public RoomType getRoomType() {
        return this.roomType;
    }
    public QualityLevel getQualityLevel() {
        return this.qualityLevel;
    }

    public boolean match(RoomQuality rm) {
        if (bedType.equals(rm.bedType) && roomType.equals(rm.roomType) &&
                qualityLevel.equals(rm.qualityLevel) && smokingStatus.equals(rm.smokingStatus)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return super.toString() + ", " + this.roomType + ", " + this.qualityLevel;
    }
}
