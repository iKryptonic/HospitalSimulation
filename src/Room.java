import java.util.ArrayList;
import java.util.List;

public enum Room {
    WaitingRoom(0),
    TreatmentRoom1(4),
    TreatmentRoom2(6),
    TreatmentRoom3(3),
    TreatmentRoom4(12),
    TreatmentRoom5(6),
    Discharged(0);
    
    public final static int dischargeTime = 5;
    public final static int admitTime = 3;
    
    private int distanceFromWaitingRoom;
    private List<Staff> staff = new ArrayList<Staff>();
    private List<Patient> occupants = new ArrayList<Patient>();
    
    private boolean hasPatient = false;
    
    Room(int distance){
	distanceFromWaitingRoom = distance;
	if(distance!=0) { // not waiting room
    	    this.newStaff(Staff.random(Title.Dr, this)); // add doctor
    	    this.newStaff(Staff.random(Title.RN, this)); // add res. nurse
    	    this.newStaff(Staff.random(Title.CNA, this)); // add cert. nursing asst.
	}
    }
    
    public int getDistance() {
	return this.distanceFromWaitingRoom;
    }
    

    public void newStaff(Staff person) {
	staff.add(person);
    }
    
    
    public void newOccupant(Patient person) {
	occupants.add(person);
	person.setRoom(this);
	hasPatient = true;
    }
    
    public boolean removeOccupant(Patient patient) {
	// the only occupant getting removed would be a patient
	if(occupants.contains(patient)) {
	    occupants.remove(patient);
	    if(this.distanceFromWaitingRoom != 0 ) // if this is a treatment room, there would be no patients remaining.
		hasPatient = false;
	    return true;
	}
	return false;
    }
    
    public Staff getStaff(int index) {
	return this.staff.get(index);
    }
    
    public Patient getNextPatient() {
	Patient bestCandidate = null;
	int currentPatientNumber = Integer.MAX_VALUE;
	int currentPriority = Integer.MAX_VALUE;
	
	if(this.getDistance()==0) {
	    if(occupants.size()==1)
		bestCandidate = occupants.get(0);
	    
	    for(Patient e : occupants) {
		if(e.getPriority() <= currentPriority) {
		    if(e.getPatientID() < currentPatientNumber) {
			bestCandidate = e;
		    }
		}
	    }
	}
	Driver.numPatients--;
	occupants.remove(bestCandidate);
	return bestCandidate;
    }
    
}
