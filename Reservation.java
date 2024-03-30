package edu.baylor.hoteltransylvania;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Reservation {
    private int room;
    private Float cost;
    private Date startDate;
    private Date endDate;
    private GuestProfile guest;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");



    Reservation(int room, String startDate, String endDate, GuestProfile guest) throws ParseException{
        this.room = room;
        this.startDate = sdf.parse(startDate);
        this.endDate = sdf.parse(endDate);
        this.guest = guest;

        long duration = this.endDate.getTime() - this.startDate.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        this.cost = (float)(minutes * 8);

    }

    public void setLength(Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void exendReservation(Integer length){

    }

    public void setRoom(int room){
        this.room = room;
    }

    public int getRoomNumber() {
        return room;
    }
    
    public String csvOutput() {
    	String str = "";
    	str = str + room + "," + guest.getUsername() + "," + sdf.format(startDate) + "," + sdf.format(endDate) + "\n"; 
    	return str;
    }
    
    public boolean conflicts(Reservation r) {
    	if (r.getRoomNumber() != this.room) {
    		return false;
    	}
		if ((this.startDate.before(r.endDate) && this.endDate.after(r.startDate)) || (this.startDate.equals(r.startDate))) {
			System.out.println("Conflicts with: " + r.getRoomNumber() + ", " + sdf.format(r.startDate) + " - " + sdf.format(r.endDate));
			return true;
		}
    	return false;
    }

}
