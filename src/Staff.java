import java.util.Random;

/*
    > Title (Doctor, Nurse, CNA)
    > DoctorID
    > Efficiency 0-1 (Multiplier)
 */
public class Staff extends Person {
    
    private Title Title;
    private int IDNumber;
    private double Efficiency;
    
    public Staff(String firstName, String lastName, String dateOfBirth, Room currentRoom, Title title, int IDNumber, float Efficiency) {
	super(firstName, lastName, dateOfBirth);
	this.Title = title;
	this.IDNumber = IDNumber;
	this.Efficiency = Efficiency;
	this.setRoom(currentRoom);
    }
    
    // get methods
    public int IDNumber() {
	return this.IDNumber;
    }
    public double getEfficiency() {
	return this.Efficiency;
    }
    public Title getTitle() {
	return this.Title;
    }
    // set methods
    public void adjustEfficiency(double adjustment) {
	this.Efficiency = Math.max(0.5, this.Efficiency + adjustment); // never let the efficiency degenerate below 0.5
    }
    
    // patient methods
    public void admit(Patient patient) {
	patient.setRoom(Room.WaitingRoom);
	patient.TimeSpentWaiting += (int)((double)Room.admitTime * this.getEfficiency());
    }
    public void treat(Patient patient) {
	double criticalMultiplier = (patient.getCode() == 'W' ? 1.25 : patient.getCode() == 'E' ? 1.5 : 1);
	Random rndNum = new Random();
	patient.TimeSpentWaiting += (int)((double)((double)Math.max(15, rndNum.nextInt(55)) * criticalMultiplier) * this.getEfficiency());
    }
    public void discharge(Patient patient) {
	patient.TimeSpentWaiting += this.getLocation().getDistance();
	patient.setRoom(Room.WaitingRoom);
	patient.TimeSpentWaiting += (int)((double)Room.dischargeTime * this.getEfficiency());
	patient.setRoom(null);
    }
}
