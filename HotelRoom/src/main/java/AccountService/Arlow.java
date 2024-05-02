package AccountService;
public class Arlow {
    private static String GUEST_ID = "TVGI", EMPLOYEE_ID = "TVEI", ADMIN_ID = "TVAI";

    public static final int GUEST_ID_LENGTH = 14, EMPLOYEE_ID_LENGTH = 14;
    private static Integer guestIDNum = 1000000000, employeeIDNum = 1000000000;
    public static String generateGuestID(Integer num) {
        String guestID = "TVGI";
        guestIDNum = num;

        if (guestIDNum >= 2147483647) {
            guestIDNum = 1000000000;
        }
        guestIDNum++;
        return guestID + guestIDNum;
    }
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
    public static String gerenateAdminId(Integer num) {
        String employeeID = "TVAI";

        if (employeeIDNum >= 2147483647) {
            employeeIDNum = 1000000000;
        }
        employeeIDNum++;
        return employeeID + employeeIDNum;
    }
}
