package Hotel.AccountService;

public class Employee extends Person {
    private String employeeID;
    Employee(String employeeID, String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        this.employeeID = employeeID;
    }
    public Employee(String firstName, String lastName, String email, String phoneNumber, String username, String password) throws Exception {
        super(firstName, lastName, email, phoneNumber, username, password);
        setEmployeeID();
    }
    public void setEmployeeID() throws Exception {
        do { employeeID = Arlow.generateEmployeeID(); }
        while(Person.idInFile(this));
    }
    public String getEmployeeID() {
        return this.employeeID;
    }


    public String toString() {
        return this.employeeID + "," + super.toString();
    }
}
