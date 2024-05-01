package AccountService;

public class Arlow {
    public static final int ID_LENGTH = 14;
    private static String GUEST_ID = "TVGI", EMPLOYEE_ID = "TVEI", ADMIN_ID = "TVAI";
    private static Integer guestIDNum = 1000000000, employeeIDNum = 1000000000, adminID = 1000000000;
    private static String generateGuestID() {
        String guestID = "TVGI";

        if (guestIDNum >= 2147483647) {
            guestIDNum = 1000000000;
        }
        return GUEST_ID + guestIDNum++;
    }
    private static String generateEmployeeID() {
        String employeeID = "TVEI";

        if (employeeIDNum >= 2147483647) {
            employeeIDNum = 1000000000;
        }
        return EMPLOYEE_ID + employeeIDNum++;
    }
    private static String generateAdminId() {
        String employeeID = "TVAI";

        if (adminID >= 2147483647) {
            adminID = 1000000000;
        }
        return ADMIN_ID + adminID++;
    }
    public static boolean isValidID(String profileID) {
        if(profileID.length() != ID_LENGTH) {
            return false;
        }
        String stringIdentifier = profileID.substring(0,4);
        if(!(stringIdentifier.contains(GUEST_ID) ||
            stringIdentifier.contains(EMPLOYEE_ID) ||
            stringIdentifier.contains(ADMIN_ID))) {
            return false;
        }
        for(int i = 4; i < ID_LENGTH; ++i) {
            if(!Character.isDigit(profileID.charAt(i))) {
                return false;
            }
        }
        return true;
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
}
