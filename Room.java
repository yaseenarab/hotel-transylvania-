package edu.baylor.hoteltransylvania;


//import RoomEnums.BedType;
//import RoomEnums.QualityLevel;
//import RoomEnums.RoomStatus;
//import RoomEnums.RoomType;

//import java.util.Date;

public class Room {
    // Constants (inclusive values)
    public static final Integer
            FIRST_FLOOR_MIN = 100, FIRST_FLOOR_MAX = 135,
            SECOND_FLOOR_MIN = 200, SECOND_FLOOR_MAX = 235,
            THIRD_FLOOR_MIN = 300, THIRD_FLOOR_MAX = 335;

    // Class Variables
    private RoomStatus roomStatus;
    private BedType bedType;
    private RoomType roomType;
    private QualityLevel qualityLevel;
    private Boolean smokingAllowed;
    private Integer roomNumber;

    // Constructors
    Room(){}
    Room(RoomStatus roomStatus, BedType bedType, RoomType roomType,
         QualityLevel qualityLevel, Boolean smokingAllowed, Integer roomNumber) throws Exception {
        this.setRoomStatus(roomStatus);
        this.setBedType(bedType);
        this.setRoomType(roomType);
        this.setQualityLevel(qualityLevel);
        this.setSmokingAllowed(smokingAllowed);
        //System.out.println(roomNumber);
        this.setRoomNumber(roomNumber);
    }

    // Set Methods
    protected void setRoomStatus(RoomStatus roomStatus) throws Exception {
        if (roomStatus == null) {
            throw new NullPointerException("Room status not provided");
        }
        boolean found = false;
        for (RoomStatus check : RoomStatus.values()) {
            if (check.equals(roomStatus)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Valid room statuses: VaCl,VaDi,OcCl,OcDi,OOO");
        }

        this.roomStatus = roomStatus;
    }
    protected void setBedType(BedType bedType) throws Exception {
        if (bedType == null) {
            throw new NullPointerException("Bed type not provided");
        }
        boolean found = false;
        for (BedType check : BedType.values()) {
            if (check.equals(bedType)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Valid bed types: TW,FL,QN,KG");
        }

        this.bedType = bedType;
    }
    protected void setRoomType(RoomType roomType) throws Exception {
        if (roomType == null) {
            throw new NullPointerException("Room type not provided");
        }
        boolean found = false;
        for (RoomType check : RoomType.values()) {
            if (check.equals(roomType)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Valid room types: NR,UE,VC");
        }

        this.roomType = roomType;
    }
    protected void setQualityLevel(QualityLevel qualityLevel) throws Exception {
        if (qualityLevel == null) {
            throw new NullPointerException("Quality level not provided");
        }
        boolean found = false;
        for (QualityLevel check : QualityLevel.values()) {
            if (check.equals(qualityLevel)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Valid quality levels: ExL,BuL,CoL,EcL");
        }

        this.qualityLevel = qualityLevel;
    }
    protected void setSmokingAllowed(Boolean smokingAllowed) throws Exception {
        if (smokingAllowed == null) {
            throw new NullPointerException("Smoking status not provided");
        }
        this.smokingAllowed = smokingAllowed;
    }
    protected void setRoomNumber(Integer roomNumber) throws Exception {
        if(roomNumber == null) {
            throw new NullPointerException("Room number not provided");
        }
        boolean validRoomNumber = false;


        if(roomNumber <= FIRST_FLOOR_MAX) {

            if(roomNumber >= FIRST_FLOOR_MIN) {
                validRoomNumber = true;
            }
        }
        else if(roomNumber <= SECOND_FLOOR_MAX) {
            if(roomNumber >= SECOND_FLOOR_MIN) {
                validRoomNumber = true;
            }
        }
        else if(roomNumber <= THIRD_FLOOR_MAX) {
            if(roomNumber >= THIRD_FLOOR_MIN) {
                validRoomNumber = true;
            }
        }
        if(!validRoomNumber) {
            throw new IllegalArgumentException("Room number format: [1,3][00,35]");
        }
        this.roomNumber = roomNumber;
    }
    
    public boolean match(Room rm) {
    	if (bedType.equals(rm.bedType) && roomType.equals(rm.roomType) && 
    		qualityLevel.equals(rm.qualityLevel) && smokingAllowed.equals(rm.smokingAllowed)) {
    		return true;
    	}
    	return false;
    }
    
    public static Integer getFirstFloorMin() {
        return FIRST_FLOOR_MIN;
    }
    public static Integer getFirstFloorMax() {
        return FIRST_FLOOR_MAX;
    }
    public static Integer getSecondFloorMin() {
        return SECOND_FLOOR_MIN;
    }
    public static Integer getSecondFloorMax() {
        return SECOND_FLOOR_MAX;
    }
    public static Integer getThirdFloorMin() {
        return THIRD_FLOOR_MIN;
    }
    public static Integer getThirdFloorMax() {
        return THIRD_FLOOR_MAX;
    }

    // Get Methods
    protected RoomStatus getRoomStatus() {
        return this.roomStatus;
    }
    protected BedType getBedType() {
        return this.bedType;
    }
    protected RoomType getRoomType() {
        return this.roomType;
    }
    protected QualityLevel getQualityLevel() {
        return this.qualityLevel;
    }
    protected Boolean getSmokingAllowed() {
        return this.smokingAllowed;
    }
    protected Integer getRoomNumber() {
        return this.roomNumber;
    }

}
