package Hotel.Utilities;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * Utility class for handling date conversions and comparisons.
 */

public class DateProcessor {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Converts a String representation of a date in the format "MM/dd/yyyy" to a Date object.
     *
     * @param date The string representation of the date.
     * @return The corresponding Date object, or null if the conversion fails.
     */
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

    /**
     * Extracts the day from a date string formatted as "MM/dd/yyyy".
     *
     * @param date The date string.
     * @return The day as an integer.
     */
    public static int getDay(String date){
        return Integer.parseInt( date.substring(3,5));

    }

    /**
     * Extracts the month from a date string formatted as "MM/dd/yyyy".
     *
     * @param date The date string.
     * @return The month as an integer (0-based index, subtract 1 for compatibility with Calendar).
     */
    public static int getMonth(String date){
        return Integer.parseInt( date.substring(1,2))-1;
    }

    /**
     * Extracts the year from a date string formatted as "MM/dd/yyyy".
     *
     * @param date The date string.
     * @return The year as an integer.
     */
    public static int getYear(String date){
        return Integer.parseInt( date.substring(6,date.length()));
    }

    /**
     * Checks if the current date is within the specified start and end dates.
     *
     * @param start The start date.
     * @param end   The end date.
     * @return true if the current date is between the start and end dates, inclusive; false otherwise.
     */
    public static boolean inBetweenToday(Date start, Date end){

        Date currentDate = new Date();

        return end.after(currentDate) && currentDate.after(start) || dateToString(end).equals(dateToString(currentDate)) || dateToString(start).equals(dateToString(currentDate));

    }
    
    public static boolean inFuture(Date start){

        Date currentDate = new Date();

        return currentDate.before(start);

    }


    /**
     * Converts a Date object to a string representation in the format "MM/dd/yyyy".
     *
     * @param date The date to convert.
     * @return The string representation of the date, or null if conversion fails.
     */
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

    /**
     * Checks if two date ranges overlap.
     *
     * @param start1 The start date of the first range.
     * @param end1   The end date of the first range.
     * @param start2 The start date of the second range.
     * @param end2   The end date of the second range.
     * @return true if the ranges overlap, false otherwise.
     */
    public static boolean isOverlapUsingCalendarAndDuration(Calendar start1, Calendar end1, Calendar start2, Calendar end2) {
        long overlap = Math.min(end1.getTimeInMillis(), end2.getTimeInMillis()) -
                Math.max(start1.getTimeInMillis(), start2.getTimeInMillis());

        return overlap >= 0;
    }


    /**
     * Validates that a reservation starts no earlier than tomorrow and that the start date precedes the end date.
     *
     * @param reservationStart The start date of the reservation.
     * @param reservationEnd   The end date of the reservation.
     * @return true if the reservation dates are valid, false otherwise.
     */
    public static boolean dateTimeConflict(Date reservationStart, Date reservationEnd) {


        Date currentDate = new Date();
        Date today = new Date(currentDate.getTime() - Duration.ofDays(1).toMillis());
        boolean validPtrs = reservationEnd != null && reservationStart != null;
        return validPtrs && reservationEnd.after(reservationStart)&& reservationStart.after(today) && differenceinDays(reservationStart,reservationEnd) >0;
    }


    /**
     * Calculates the difference in days between two dates.
     *
     * @param start The start date.
     * @param end   The end date.
     * @return The number of days between the start and end dates.
     */
    public static Integer differenceinDays(Date start, Date end){


        long diff = end.getTime() - start.getTime();
        Integer in = 0;
        Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        in = days.intValue();
        return in;

    }
}
