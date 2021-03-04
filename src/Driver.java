import java.io.IOException;
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

    static NameGenerator namegen;
    
    public static void main(String[] args) throws InterruptedException, IOException {
	namegen = new NameGenerator("first_names.txt", "last_names.txt");
	runSimulation(25);
    }
    
    private static Patient handleNextPatient(Room room, Room waitingRoom) throws InterruptedException {
	Patient patient = waitingRoom.getNextPatient();
	
    	Staff DR = room.getStaff(0);
    	Staff RN = room.getStaff(1);
    	Staff CNA = room.getStaff(2);
    		
    	CNA.admit(patient);
    	RN.assess(patient);
    	DR.treat(patient);
    	return patient;
    }
    
    private static void runSimulation(int PatientNumber) throws InterruptedException {
    	List<Room> rooms = new ArrayList<Room>();
    	
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
        	    if(currentRoom != Room.WaitingRoom && currentRoom != Room.Discharged) // we are not in the waiting room
        		new Thread(new Runnable() { // multi-threaded
        		    public void run() {
				    try {
					Patient patient = handleNextPatient(currentRoom, 
						rooms.get(0));

					Staff patientCNA = patient.workedWith[0];
					Staff patientRN = patient.workedWith[1];
					Staff patientDR = patient.workedWith[2];

					System.out.println("\n>>>>>>>>>>>>>> BEGIN <<<<<<<<<<<<<<<");
					System.out.printf("Patient %s %s spent %d minutes in the hospital.%n\tThey were treated by Dr. %s %s (ID: %d)%n\t\tRN %s %s (ID: %d) and CNA %s %s (ID: %d)%n%s %s was a code %s. They were treated as priority %d%n",
						patient.getName()[0], patient.getName()[1], patient.TimeSpentWaiting, 
						patientDR.getName()[0], patientDR.getName()[1], patientDR.IDNumber(), 
						patientRN.getName()[0], patientRN.getName()[1], patientRN.IDNumber(),
						patientCNA.getName()[0], patientCNA.getName()[1], patientCNA.IDNumber(),
						patient.getName()[0], patient.getName()[1], patient.getCode(), patient.getPriority());
					System.out.println("\tThis is because the patient "+patient.getName()[1]+" presented the following symptoms: ");
					patient.symptoms.forEach(value -> System.out.println("\t\t"+value));
					System.out.println(">>>>>>>>>>>>>> END <<<<<<<<<<<<<<<\n");
				    } catch (InterruptedException e) {
					e.printStackTrace();
				    }
        		    }
        		}).start();
        	    Thread.sleep(100);
        	}
    	} while (numPatients > 0);
    }

}