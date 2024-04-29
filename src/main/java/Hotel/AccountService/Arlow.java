package Hotel.AccountService;
public class Arlow {
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
    public static String gerenateAdminId(Integer num) {
        String employeeID = "TVAI";

        if (employeeIDNum >= 2147483647) {
            employeeIDNum = 1000000000;
        }
        employeeIDNum++;
        return employeeID + employeeIDNum;
    }
}
