package ReservationSystem.Dependencies;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Person {
    public static void readPersons() throws Exception {
        BufferedReader reader = null;
        File myFile = null;
        Map<String, Person> people = new TreeMap<>();
        try {
            myFile = new File("Person_Profiles.csv");
            if(myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            }
            else {
                System.out.println("File already exists");
            }

            reader = new BufferedReader(new FileReader(myFile));
            String line = null;
            while((line = reader.readLine()) != null) {
                try {
                    String[] split = line.split(",");
                    if(split.length == 7 && split[0].contains("TVGI") && Arlow.isGuestIDUnique(split[0])) {
                        Arlow.addGuest(new Guest(split[0], split[1], split[2], split[3], split[4], split[5], split[6]));
                    }
                    else if (split.length == 7 && split[0].contains("TVEI") && Arlow.isEmployeeIDUnique(split[0])) {
                        Arlow.addEmployee(new Employee(split[0], split[1], split[2], split[3], split[4], split[5], split[6]));
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        catch (Exception e) {
            throw new Exception("Exception caught in readPersons()", e);
        }
        finally {
            if(reader != null)
                reader.close();
        }
    }
    public static void writePerson(Person person) throws Exception {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        File myFile = null;
        try {
            myFile = new File("Person_Profiles.csv");
            if(myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            }
            else {
                System.out.println("File already exists");
            }

            writer = new FileWriter(myFile, true);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(person.toString() + System.lineSeparator());
        }
        catch (Exception e) {
            throw new Exception("Exception caught in Employee constructor", e);
        }
        finally {
            if(bufferedWriter != null)
                bufferedWriter.close();
        }
    }
    public static boolean personInFile(Person person) throws Exception {
        BufferedReader reader = null;
        File myFile = null;
        Map<String, Person> people = new TreeMap<>();
        try {
            myFile = new File("Person_Profiles.csv");
            if(myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            }
            else {
                System.out.println("File already exists");
            }

            reader = new BufferedReader(new FileReader(myFile));
            String line = null;
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if(split.length == 7 && split[5].equals(person.getUsername()) && split[6].equals(person.getPassword())) {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            throw new Exception("Exception caught in Employee constructor", e);
        }
        finally {
            if(reader != null)
                reader.close();
        }
    }
    public static boolean idInFile(Person person) throws Exception {
        BufferedReader reader = null;
        File myFile = null;
        Map<String, Person> people = new TreeMap<>();
        try {
            myFile = new File("Person_Profiles.csv");
            if(myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            }
            else {
                System.out.println("File already exists");
            }

            reader = new BufferedReader(new FileReader(myFile));
            String line = null;
            while((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if(person instanceof Employee) {
                    if(split.length == 7 && split[0].equals(((Employee) person).getEmployeeID())) {
                        return true;
                    }
                }
                else {
                    if(split.length == 7 && split[0].equals(((Guest) person).getGuestID())) {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (Exception e) {
            throw new Exception("Exception caught in Employee constructor", e);
        }
        finally {
            if(reader != null)
                reader.close();
        }
    }
    public static class Arlow {
        private static Integer guestIDNum, employeeIDNum;
        private static Map<String, Guest> guests;
        private static Map<String, Employee> employees;
        public static void init() {
            guestIDNum = 1000000000;
            employeeIDNum = 1000000000;
            guests = new TreeMap<>();
            employees = new TreeMap<>();
        }
        public static Guest getGuest(String username, String password) {
            for(Guest guest : guests.values()) {
                if (guest.getUsername().equals(username) && guest.getPassword().equals(password)) {
                    return guest;
                }
            }
            return null;
        }

        public Map<String, Guest> getGuests() {
            return guests;
        }
        public Map<String, Employee> getEmployees() {
            return employees;
        }
        public static String generateGuestID() {
            String guestID = "TVGI";

            if (guestIDNum >= 2147483647) {
                guestIDNum = 1000000000;
            }
            return guestID + guestIDNum++;
        }
        public static String generateEmployeeID() {
            String employeeID = "TVEI";

            if (employeeIDNum >= 2147483647) {
                employeeIDNum = 1000000000;
            }
            return employeeID + employeeIDNum++;
        }


        public static boolean isGuestIDUnique(String guestID) {
            if(guests == null || !guests.containsKey(guestID)) {
                return true;
            }
            return false;
        }
        public static boolean isEmployeeIDUnique(String guestID) {
            if(employees == null || !employees.containsKey(guestID)) {
                return true;
            }
            return false;
        }
        public static void addGuest(Guest guest) {
            Arlow.guests.put(guest.getGuestID(), guest);
        }
        public static void addEmployee(Employee employee) {
            Arlow.employees.put(employee.getEmployeeID(), employee);
        }
    }
    // Constants (inclusive)
    final Integer
            NAME_MAX_LENGTH = 16,
            USERNAME_MAX_LENGTH = 16,
            PASSWORD_MAX_LENGTH = 16,
            PHONE_LENGTH = 10;
    private String
            firstName,
            lastName,
            email,
            phoneNumber,
            username,
            password;
    Person (String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
        this.setUsername(username);
        this.setPassword(password);
        System.out.println("Successfully initialized Guest");
    }

    protected void setFirstName(String firstName) throws Exception {
        if (firstName == null || firstName.isEmpty()) {
            throw new NullPointerException("First name not provided");
        }
        if (firstName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("First name must be no more than " + NAME_MAX_LENGTH + " characters");
        }
        this.firstName = firstName;
    }
    protected void setLastName (String lastName) throws Exception {
        if (lastName == null || lastName.isEmpty()) {
            throw new NullPointerException("Last name not provided");
        }
        if (lastName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Last name must be no more than " + NAME_MAX_LENGTH + " characters");
        }
        this.lastName = lastName;
    }
    protected void setEmail (String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new NullPointerException("Email not provided");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email format: Username@DomainName.TopLevelDomain");
        }
        this.email = email;
    }
    protected void setPhoneNumber(String phoneNumber) throws Exception {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new NullPointerException("No phone number provided");
        }
        if ( phoneNumber.length() != PHONE_LENGTH) {
            throw new IllegalArgumentException("Phone number format: XXXXXXXXXX");
        }
        this.phoneNumber = phoneNumber;
    }
    protected void setUsername(String username) throws Exception {
        if (username == null || username.isEmpty())
            throw new NullPointerException("No username provided");

        if (username.contains(" "))
            throw new Exception("No whitespace allowed in username");

        if(username.length() > USERNAME_MAX_LENGTH )
            throw new Exception("Username must be less than " + USERNAME_MAX_LENGTH + " characters");

        this.username = username;
    }
    protected void setPassword(String password) throws Exception {
        if (password == null || password.isEmpty())
            throw new NullPointerException("No password provided");
        if (password.contains(" "))
            throw new Exception("No whitespace allowed in password");

        if(password.length() > PASSWORD_MAX_LENGTH )
            throw new Exception("Password must be less than " + PASSWORD_MAX_LENGTH + " characters");

        this.password = password;
    }

    // Get Methods
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Guest)) {
            return false;
        }
        return this.firstName.equals(((Person) obj).firstName) &&
                this.lastName.equals(((Person) obj).lastName) &&
                this.email.equals(((Person) obj).email) &&
                this.phoneNumber.equals(((Person) obj).phoneNumber);
    }

    public String toString() {
        return this.firstName + "," + this.lastName + "," + this.email + "," + this.phoneNumber + "," + this.username + "," + this.password;
    }

    // Modifiers
}
