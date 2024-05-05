package Hotel.AccountService;



/**
 * The Arlow class is responsible for generating unique identifiers for guests and employees,
 * including administrators, in the hotel management system. It maintains internal counters to
 * ensure that each ID is unique and increments accordingly.
 */
public class Arlow {
    private static String GUEST_ID = "TVGI", EMPLOYEE_ID = "TVEI", ADMIN_ID = "TVAI";

    public static final int GUEST_ID_LENGTH = 14, EMPLOYEE_ID_LENGTH = 14;
    private static Integer guestIDNum = 1000000000, employeeIDNum = 1000000000;

    /**
     * Generates a unique guest ID based on a numerical input.
     * The method prefixes the numerical part with "TVGI" and ensures that the ID does not
     * exceed the maximum value for an Integer. If it does, it resets to a base value.
     *
     * @param num The starting number for generating guest IDs.
     * @return The uniquely generated guest ID.
     */
    public static String generateGuestID(Integer num) {
        String guestID = "TVGI";
        guestIDNum = num;

        if (guestIDNum >= 2147483647) {
            guestIDNum = 1000000000;
        }

        guestIDNum++;
        return guestID + guestIDNum;
    }

    /**
     * Generates a unique employee ID by incrementing a stored static number.
     * The method prefixes the numerical part with "TVEI" and ensures continuity and uniqueness
     * of each generated ID.
     *
     * @param num The initial number for employee IDs; not actually used in current logic.
     * @return The uniquely generated employee ID.
     */
    public static String generateEmployeeID(Integer num) {
        String employeeID = "TVEI";

        if (employeeIDNum >= 2147483647) {
            employeeIDNum = 1000000000;
        }
        
        employeeIDNum++;
        return employeeID + employeeIDNum;
    }


    public static String getProfileType(String profileID) {
        String stringIdentifier = profileID.substring(0,4);
        if (stringIdentifier.contains(GUEST_ID)) {
            return GUEST_ID;
        } else if (stringIdentifier.contains(EMPLOYEE_ID)) {
            return EMPLOYEE_ID;
        } else if (stringIdentifier.contains(ADMIN_ID)) {
            return ADMIN_ID;
        } else {
            return null;
        }
    }

    /**
     * Generates a unique administrator ID in a similar manner to employee IDs but with a
     * different prefix ("TVAI") to distinguish administrator IDs from other employee IDs.
     *
     * @param num The initial number for admin IDs; not actually used in current logic.
     * @return The uniquely generated admin ID.
     */
    public static String gerenateAdminId(Integer num) {
        String employeeID = "TVAI";

        if (employeeIDNum >= 2147483647) {
            employeeIDNum = 1000000000;
        }
        employeeIDNum++;
        return employeeID + employeeIDNum;
    }
}
