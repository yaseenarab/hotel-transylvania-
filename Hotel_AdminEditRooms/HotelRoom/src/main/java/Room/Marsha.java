package Room;

public class Marsha {
    private static Integer reservationIDNum = 1000000000;
    private static String generateReservationID() {
        String reservationID = "TVRI";

        if (reservationIDNum >= 2147483647) {
            reservationIDNum = 1000000000;
        }
        return reservationID + reservationIDNum++;
    }
}
