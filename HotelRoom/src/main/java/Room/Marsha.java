package Room;

public class Marsha {
    private static Integer reservationIDNum = 1000000000;
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
