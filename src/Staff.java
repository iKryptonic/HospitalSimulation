import java.util.Random;

/*
    > Title (Doctor, Nurse, CNA)
    > DoctorID
    > Efficiency 0-1 (Multiplier)
 */
public class Staff extends Person {
    
    static int currentDoctorNumber = 0;
    
    private Title Title;
    private int IDNumber;
    private double Efficiency;
    
    public Staff(String firstName, String lastName, String dateOfBirth, Room currentRoom, Title title, double Efficiency) {
	super(firstName, lastName, dateOfBirth);
	this.Title = title;
	this.IDNumber = currentDoctorNumber;
	this.Efficiency = Efficiency;
	this.setRoom(currentRoom);
	currentDoctorNumber++;
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
    public void assess(Patient patient) {
	adjustEfficiency(0.02);
	patient.workedWith[1] = this;
	// assign the patient random symptoms
	double totalScore = 0;
	for(Symptom currentSymptom : Symptom.values()) {
	    final int likelyHood = Math.max(1, new Random().nextInt(50));
	    final int equalChance = Math.max(1, new Random().nextInt(likelyHood));
	    if(equalChance > (likelyHood/2)) {
		patient.symptoms.add(currentSymptom);
		totalScore+= currentSymptom.getSeverity();
	    }
	}
	// divide total score by 18 to get a practical 0-1 double
	totalScore /= 18;
	
	if(totalScore < 0.33) // they can wait
	    patient.setPriority(3);
	if((totalScore > 0.33) && (totalScore < 0.66)) // they're doing OK
	    patient.setPriority(2);
	if((totalScore > 0.66)) // they're in bad shape
	    patient.setPriority(1);

	// reverse the priorities, time waiting is determined by how bad they are looking
	patient.TimeSpentWaiting += (int)(10*(1-totalScore));
	
    }
    public void admit(Patient patient) throws InterruptedException {
	patient.workedWith[0] = this;
	adjustEfficiency(0.01);
	if(patient.getCode()=='E') // emergency patients are priority 1
	    patient.setPriority(1);
	patient.setRoom(this.getRoom());
	patient.TimeSpentWaiting += (int)((double)Room.admitTime * this.getEfficiency());
	//Thread.sleep(Math.max(1, new Random().nextInt(5000)));
    }
    public void treat(Patient patient) throws InterruptedException {
	adjustEfficiency(0.05);
	patient.workedWith[2] = this;
	double criticalMultiplier = (patient.getCode() == 'W' ? 1.25 : patient.getCode() == 'E' ? 1.5 : 1);
	if(patient.getCode() == 'W')
	    criticalMultiplier+= 1-(patient.getPriority()/3); // higher priority patients take longer to treat
	patient.TimeSpentWaiting += (int)((double)((double)Math.max(15, new Random().nextInt(55)) * criticalMultiplier) * this.getEfficiency());
	//Thread.sleep(Math.max(1, new Random().nextInt(5000)));
    }
    public void discharge(Patient patient) throws InterruptedException {
	adjustEfficiency(0.01);
	patient.TimeSpentWaiting += this.getRoom().getDistance();
	//patient.changeRoom(Room.WaitingRoom);
	patient.TimeSpentWaiting += (int)((double)Room.dischargeTime * this.getEfficiency());
	patient.changeRoom(Room.Discharged);
	System.out.println("Patient"+patient.getPatientID()+" discharged after "+patient.TimeSpentWaiting+" minutes.");
	//Thread.sleep(Math.max(1, new Random().nextInt(5000)));
    }
    
    public static Staff random(Title title, Room room) {
	// TODO: random name and DOB generation
	return new Staff(Driver.namegen.generateName()[0], Driver.namegen.generateName()[1], "07/13/1995", room, title, 2+(double)(new Random().nextInt(100)/100));
    }
}
