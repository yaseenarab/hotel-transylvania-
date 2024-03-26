package org.example;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation {


    private Room[] rooms;
    private Float cost;
    private Date startDate;
    private Date endDate;


    Reservation(){

    }

    Reservation(Room[] rooms, Date startDate, Date endDate){
        this.rooms = rooms;
        this.startDate = startDate;
        this.endDate = endDate;

        long duration = endDate.getTime() - startDate.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        this.cost = (float)(minutes * 8);

    }

    public void setLength(Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void exendReservation(Integer length){

    }


    public void setRooms(Room[] rooms){
        this.rooms = rooms;
    }

    public Room[] getRooms() {
        return rooms;
    }

}
