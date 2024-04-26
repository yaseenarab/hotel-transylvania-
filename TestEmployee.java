import AccountService.Employee;
import AccountService.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class TestEmployee extends TestPerson{
    public static Person employee;
    @Override
    @BeforeEach
    public void init() {
        try {
            employee = new Employee("TVEI1000000000", "Dave",
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
    public void testSetEmployeeID() {
        try {
            Method setEmployeeID = Employee.class.getDeclaredMethod("setEmployeeID", String.class);
            setEmployeeID.setAccessible(true);

            assertThrows(Exception.class, () -> setEmployeeID.invoke(person, null));
            assertThrows(Exception.class, () -> setEmployeeID.invoke(person, ""));
            assertThrows(Exception.class, () -> setEmployeeID.invoke(person, "TV3I1000123456"));
            assertThrows(Exception.class, () -> setEmployeeID.invoke(person, "TVEI100012A456"));
            assertThrows(Exception.class, () -> setEmployeeID.invoke(person, "TVEI100012345"));
            assertThrows(Exception.class, () -> setEmployeeID.invoke(person, "TVEI10001234567"));
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
