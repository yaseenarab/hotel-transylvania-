package Utilities;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class DateProcessor {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
    public static Date stringToDate(String date) {
        try {
            Date myDate = SDF.parse(date);
            return myDate;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in DateProcessor.stringToDate: cannot " +
                    "parse date, passed value was " + date);
            return null;
        }
    }
    public static int getDay(String date){
        return Integer.parseInt( date.substring(3,5));

    }
    public static int getMonth(String date){
        return Integer.parseInt( date.substring(1,2))-1;
    }
    public static int getYear(String date){
        return Integer.parseInt( date.substring(6,date.length()));
    }

    public static boolean inBetweenToday(Date start, Date end){

        Date currentDate = new Date();

        return end.after(currentDate) && currentDate.after(start) || dateToString(end).equals(dateToString(currentDate)) || dateToString(start).equals(dateToString(currentDate));

    }
    
    public static boolean inFuture(Date start){

        Date currentDate = new Date();

        return currentDate.before(start);

    }

    public static String dateToString(Date date) {
        try {
            String myDate = SDF.format(date);
            return myDate;
        }
        catch (Exception e) {
            MyLogger.logger.log(Level.SEVERE, "Error caught in DateProcessor.stringToDate: cannot " +
                    "parse date, passed value was " + date);
            return null;
        }
    }

    public static boolean isOverlapUsingCalendarAndDuration(Calendar start1, Calendar end1, Calendar start2, Calendar end2) {
        long overlap = Math.min(end1.getTimeInMillis(), end2.getTimeInMillis()) -
                Math.max(start1.getTimeInMillis(), start2.getTimeInMillis());

        return overlap >= 0;
    }


    public static boolean dateTimeConflict(Date reservationStart, Date reservationEnd) {


        Date currentDate = new Date();
        Date today = new Date(currentDate.getTime() - Duration.ofDays(1).toMillis());
        boolean validPtrs = reservationEnd != null && reservationStart != null;
        return validPtrs && reservationEnd.after(reservationStart)&& reservationStart.after(today) && differenceinDays(reservationStart,reservationEnd) >0;
    }

    public static Integer differenceinDays(Date start, Date end){


        long diff = end.getTime() - start.getTime();
        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        Integer in = 0;
        Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        in = days.intValue();
        return in;

    }
}
