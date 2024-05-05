package org.example;

import java.util.Date;

interface IRoomReservation {
    void setGuest(Guest guest) throws Exception;
    void setRoom(Room room) throws Exception;
    void setStartDate(Date startDate) throws Exception;
    void setEndDate(Date endDate) throws Exception;

    Guest getGuest();
    Room getRoom();
    Date getStartDate();
    Date getEndDate();
}