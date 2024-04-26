import AccountService.Employee;
import AccountService.Guest;
import AccountService.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class TestGuest extends TestPerson{
    public static Person employee;
    @Override
    @BeforeEach
    public void init() {
        try {
            employee = new Employee("TVGI1000000000", "Dave",
                    "Chappelle", "DaveChappelle@gmail.com", "1234567890",
                    "username","password");
            person = new Person("Dave","Chappelle","DaveChappelle@gmail.com",
                    "1234567890","username","password");
        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetGuestID() {
        try {
            Method setGuestID = Guest.class.getDeclaredMethod("setGuestID", String.class);
            setGuestID.setAccessible(true);

            assertThrows(Exception.class, () -> setGuestID.invoke(person, null));
            assertThrows(Exception.class, () -> setGuestID.invoke(person, ""));
            assertThrows(Exception.class, () -> setGuestID.invoke(person, "TV0I1000123456"));
            assertThrows(Exception.class, () -> setGuestID.invoke(person, "TVGI100012A456"));
            assertThrows(Exception.class, () -> setGuestID.invoke(person, "TVGI100012345"));
            assertThrows(Exception.class, () -> setGuestID.invoke(person, "TVGI10001234567"));
        }
        catch (Exception e) {
            fail();
        }
    }
    @AfterEach
    public void teardown() {
        person = null;
    }
}
