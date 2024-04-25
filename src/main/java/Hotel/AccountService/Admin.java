package main.java.Hotel.AccountService;

public class Admin extends Employee {

	public Admin(String firstName, String lastName, String email, String phoneNumber, String username, String password)
			throws Exception {
		super(firstName, lastName, email, phoneNumber, username, password);
	}
	
	public Admin(String employeeID, String firstName, String lastName, String email, String phoneNumber, String username, String password)
			throws Exception {
		super(employeeID, firstName, lastName, email, phoneNumber, username, password);
	}
	
	@Override
	public void setEmployeeID() throws Exception {
        do { employeeID = Arlow.generateAdminID(); }
        while(Person.idInFile(this));
    }
	
}
