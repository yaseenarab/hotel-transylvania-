package org.example;

import java.util.Date;

interface IReservations {
    String makeNewReservation(String userID, Room room, Date start, Date end) throws Exception;

    RoomReservation getReservation(String reservationID) throws Exception;

    boolean reservationExists(String reservationID) throws Exception;

    RoomReservation modifyExistingReservation(String reservationID, Room room, Date start, Date end) throws Exception;
}
