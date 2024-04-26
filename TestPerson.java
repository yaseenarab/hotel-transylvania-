import AccountService.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPerson {
    public static Person person;
    @BeforeEach
    public void init() {
        try {
            person = new Person("Dave","Chappelle","DaveChappelle@gmail.com",
                    "1234567890","username","password");
        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetFirstName() {
        try {
            Method setFirstName = Person.class.getDeclaredMethod("setFirstName", String.class);
            setFirstName.setAccessible(true);

            assertDoesNotThrow(() -> setFirstName.invoke(person, "D"));

            assertThrows(Exception.class, () -> setFirstName.invoke(person, null));
            assertThrows(Exception.class, () -> setFirstName.invoke(person, ""));
            assertThrows(Exception.class, () -> setFirstName.invoke(person, "DaveDaveDaveDaveDaveDave"));
        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetLastName() {
        try {
            Method setLastName = Person.class.getDeclaredMethod("setLastName", String.class);
            setLastName.setAccessible(true);

            assertDoesNotThrow(() -> setLastName.invoke(person, "C"));

            assertThrows(Exception.class, () -> setLastName.invoke(person, null));
            assertThrows(Exception.class, () -> setLastName.invoke(person, ""));
            assertThrows(Exception.class, () -> setLastName.invoke(person, "ChappelleChappelleChappelle"));
        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetEmail() {
        try {
            Method setEmail = Person.class.getDeclaredMethod("setEmail", String.class);
            setEmail.setAccessible(true);

            assertDoesNotThrow(() -> setEmail.invoke(person, "DaveChappelle@gmail.com"));
            assertDoesNotThrow(() -> setEmail.invoke(person, "DaveChappelle@gmail.net"));
            assertDoesNotThrow(() -> setEmail.invoke(person, "DaveChappelle@gmail.gov"));
            assertDoesNotThrow(() -> setEmail.invoke(person, "DaveChappelle@gmail.org"));
            assertDoesNotThrow(() -> setEmail.invoke(person, "DaveChappelle@gmail.edu"));

            assertThrows(Exception.class, () -> setEmail.invoke(person, "DaveChappelle@gmail.co"));
            assertThrows(Exception.class, () -> setEmail.invoke(person, "DaveChappelle@gmail.ne"));
            assertThrows(Exception.class, () -> setEmail.invoke(person, "DaveChappelle@gmail.go"));
            assertThrows(Exception.class, () -> setEmail.invoke(person, "DaveChappelle@gmail.or"));
            assertThrows(Exception.class, () -> setEmail.invoke(person, "DaveChappelle@gmail.ed"));
            assertThrows(Exception.class, () -> setEmail.invoke(person, "DaveChappellegmail.com"));

        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetPhoneNumber() {
        try {
            Method setPhoneNumber = Person.class.getDeclaredMethod("setPhoneNumber", String.class);
            setPhoneNumber.setAccessible(true);

            assertThrows(Exception.class, () -> setPhoneNumber.invoke(person, "111111111"));
            assertThrows(Exception.class, () -> setPhoneNumber.invoke(person, "11111111111"));
            assertThrows(Exception.class, () -> setPhoneNumber.invoke(person, "111-111-1111"));
            assertThrows(Exception.class, () -> setPhoneNumber.invoke(person, "          "));
            assertThrows(Exception.class, () -> setPhoneNumber.invoke(person, ""));
            assertThrows(Exception.class, () -> setPhoneNumber.invoke(person, null));
        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetUsername() {
        try {
            Method setUsername = Person.class.getDeclaredMethod("setUsername", String.class);
            setUsername.setAccessible(true);

            assertThrows(Exception.class, () -> setUsername.invoke(person, null));
            assertThrows(Exception.class, () -> setUsername.invoke(person, ""));
            assertThrows(Exception.class, () -> setUsername.invoke(person, " "));
            assertThrows(Exception.class, () -> setUsername.invoke(person, "h"));
            assertThrows(Exception.class, () -> setUsername.invoke(person, "ThisIsAReallyLongUsernameIsntIt"));
            assertThrows(Exception.class, () -> setUsername.invoke(person, "user name"));
            assertThrows(Exception.class, () -> setUsername.invoke(person, "username "));
        }
        catch (Exception e) {
            fail();
        }
    }
    @Test
    public void testSetPassword() {
        try {
            Method setPassword = Person.class.getDeclaredMethod("setPassword", String.class);
            setPassword.setAccessible(true);

            assertThrows(Exception.class, () -> setPassword.invoke(person, null));
            assertThrows(Exception.class, () -> setPassword.invoke(person, ""));
            assertThrows(Exception.class, () -> setPassword.invoke(person, " "));
            assertThrows(Exception.class, () -> setPassword.invoke(person, "q"));
            assertThrows(Exception.class, () -> setPassword.invoke(person, "ThisIsAReallyLongPasswordIsntIt"));
            assertThrows(Exception.class, () -> setPassword.invoke(person, "pass word"));
            assertThrows(Exception.class, () -> setPassword.invoke(person, "password "));
        }
        catch (Exception e) {
            fail();
        }
    }
    @AfterEach
    public void tearDown() {
        person = null;
    }

}
