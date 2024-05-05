package Hotel.Room;


/**
 * This class is responsible for managing reservation identifiers for the hotel.
 * It generates unique reservation IDs based on a given starting point.
 */
public class Marsha {
    private static Integer reservationIDNum = 1000000000;

    /**
     * Generates a unique reservation ID by incrementing a base ID number.
     * If the ID reaches the upper limit of an integer, it resets to a predefined start value.
     *
     * @param id The starting ID number for generating the next reservation ID.
     * @return A string representing the unique reservation ID prefixed by "TVRI".
     */
    public static String generateReservationID(Integer id) {
        String reservationID = "TVRI";
        reservationIDNum = id;

        if (reservationIDNum >= 2147483647) {
            reservationIDNum = 1000000000;
        }
        reservationIDNum++;
        return reservationID + reservationIDNum;
    }
}
