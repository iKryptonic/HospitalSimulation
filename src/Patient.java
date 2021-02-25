/*
    > PatientID
    > Priority (1-3)
    > Code (W or E)
    > TimeSpentWaiting
 */

public class Patient extends Person {
    
    private int PatientID;
    private char Code;
    private int Priority;
    public int TimeSpentWaiting;
    
    public Patient(String firstName, String lastName, String dateOfBirth, int PatientID, char Code, int Priority) {
	super(firstName, lastName, dateOfBirth);
	this.PatientID = PatientID;
	this.Code = Code;
	this.Priority = Priority;
    }
    
    public void changeRoom(Room newRoom) {
	this.TimeSpentWaiting += newRoom.getDistance();
	this.setRoom(newRoom);
    }
    
    public void setPriority(int newPriority) {
	this.Priority = newPriority;
    }
    
    public int getPatientID() {
	return this.PatientID;
    }
    public char getCode() {
	return this.Code;
    }
    public int TimeSpentWaiting() {
	return this.Priority;
    }
    public int getTimeWaiting() {
	return this.TimeSpentWaiting;
    }
}
