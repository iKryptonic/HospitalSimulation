
public class Person {

    // the person's name
    private String[] name = new String[2];
    private String dateOfBirth;
    private Room Location;
    
    // Person p = new Person()
    // default constructor
    public Person() {
	// set name
	this.name[0] = "John"; // first name
    	this.name[1] = "Doe"; // last name
    }
    
    // Person p = new Person("Foo", "Bar", "07/13/2000")
    // constructor with patient information
    public Person(String firstName, String lastName, String dateOfBirth) {
	// set name
	this.name[0] = firstName;
	this.name[1] = lastName;
	// set dob
	this.dateOfBirth = dateOfBirth;
    }
    
    // get methods
    public String[] getName() {
	return this.name;
    }
    
    public String getDateOfBirth() {
	return this.dateOfBirth;
    }
    
    public Room getLocation() {
	return this.Location;
    }
    
    // set methods
    public void setRoom() {
	this.Location = null;
    }
    public void setRoom(Room newRoom) {
	this.Location = newRoom;
    }
    
    public void setName(String firstName, String lastName) {
	this.name[0] = firstName;
	this.name[1] = lastName;
    }
    
    public void setDateOfBirth(String newDateOfBirth) {
	this.dateOfBirth = newDateOfBirth;
    }
}
