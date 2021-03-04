import java.util.ArrayList;
import java.util.List;

// what are we doing?
/*
 * Initialize an arraylist with all the rooms
 * Waiting room array holds all patients,
 * Treatment rooms hold all doctors and 1 patient if available
 * 
 * next >
 * add n patients to the waiting rooms simultaneously
 * 
 * next > 
 * there is a loop run asynchronously on every room for the CNA to grab a patient,
 * 	* random number 0-10. 20% chance that the patient is coming in with E status.
 * 	* E status skips assessment automatically
 * The RN assesses the patient
 * The Doctor treats the patient
 * The CNA discharges the patient
 */

public class Driver {
    
    static int numPatients = 0;

    public static void main(String[] args) throws InterruptedException {
	runSimulation(25);
    }
    
    private static void handleNextPatient(Room room, Room waitingRoom) throws InterruptedException {
	Patient patient = waitingRoom.getNextPatient();
	
    	Staff DR = room.getStaff(0);
    	Staff RN = room.getStaff(1);
    	Staff CNA = room.getStaff(2);
    		
    	CNA.admit(patient);
    	RN.assess(patient);
    	DR.treat(patient);
    	CNA.discharge(patient);
    }
    
    private static void runSimulation(int PatientNumber) throws InterruptedException {
    	List<Room> rooms = new ArrayList<Room>();
    	List<Patient> patients = new ArrayList<Patient>();
    	
    	for(Room currentRoom : Room.values()) {
    	    rooms.add(currentRoom); // initialize rooms with all possible rooms and random dr, rn, cna
    	}
    	
    	for(int x=0; x < PatientNumber; x++) {
    	    rooms.get(0).newOccupant(Patient.random('W')); // initialize PatientNumber amount of patients into the waiting room
    	    numPatients++;
    	}
    	// now our waiting room is filled, ready to be parsed; also: our treatment rooms are ready to recieve patients
    	
    	do {
        	for(Room currentRoom : rooms) {
        	    if(currentRoom != Room.WaitingRoom) // we are not in the waiting room
        		new Thread(new Runnable() {
        		    public void run() {
                		try {
				    handleNextPatient(currentRoom, 
				    	rooms.get(0));
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
        		    }
        		}).start();
        	}
    	} while (numPatients > 0);
    }

}