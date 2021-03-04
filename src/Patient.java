import java.util.ArrayList;
import java.util.List;

/*
    > PatientID
    > Priority (1-3)
    > Code (W or E)
    > TimeSpentWaiting
 */

public class Patient extends Person {
    
    public static int numberOfPatients = 0;
    
    private int PatientID;
    private char Code;
    private int Priority;
    public int TimeSpentWaiting;
    public List<Symptom> symptoms = new ArrayList<Symptom>();
    
    public Staff[] workedWith = new Staff[3];
    
    public Patient(String firstName, String lastName, String dateOfBirth, char code) {
	super(firstName, lastName, dateOfBirth);
	this.PatientID = numberOfPatients;
	this.Code = code;
	numberOfPatients++;
    }
    
    public static Patient random(char code) {
	return new Patient(Driver.namegen.generateName()[0], Driver.namegen.generateName()[1], "01/01/1999", code);
    }
    
    public void changeRoom(Room newRoom) {
	this.TimeSpentWaiting += newRoom.getDistance();
	this.getRoom().removeOccupant(this);
	newRoom.newOccupant(this);
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
    public int getPriority() {
	return this.Priority;
    }
}
