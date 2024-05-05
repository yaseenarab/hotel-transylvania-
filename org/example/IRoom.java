package org.example;

import RoomEnums.BedType;
import RoomEnums.RoomStatus;

interface IRoom {
    void setBedType(BedType bedType) throws Exception;
    void setRoomStatus(RoomStatus roomStatus) throws Exception;
    void setSmokingStatus(Boolean smokingStatus) throws Exception;
    void setRoomNumber(Integer roomNumber) throws Exception;

    BedType getBedType();
    RoomStatus getRoomStatus();
    Boolean getSmokingStatus();
    Integer getRoomNumber();
}