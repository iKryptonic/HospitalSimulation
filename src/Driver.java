import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Driver {
    
    static int numPatients = 0;
    static NameGenerator namegen;
    static boolean PrettyColors = false;
    
    public static void main(String[] args) throws InterruptedException, IOException {
	namegen = new NameGenerator("first_names.txt", "last_names.txt");
	runSimulation(25);
    }
    
    private static Patient handleNextPatient(Room room, Room waitingRoom) throws InterruptedException {
	return handleNextPatient(waitingRoom.getNextPatient(), room, waitingRoom);
    }
    
    private static Patient handleNextPatient(Patient patient, Room room, Room waitingRoom) throws InterruptedException {
	
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
    	
    	StringBuilder totalEmergency = new StringBuilder();
            	try {
            	do {
                	for(Room currentRoom : rooms) {
                	    if(currentRoom != Room.WaitingRoom && currentRoom != Room.Discharged) // we are not in the waiting room
                		new Thread(new Runnable() { // multi-threaded
                		    public void run() {
        				    try {
        					Patient patient = null;
        					switch(new Random().nextInt(4)){
        					case 0:
        					   totalEmergency.append("1");
        					   patient = handleNextPatient(Patient.random('E'), 
        						   currentRoom, 
        						   rooms.get(0));
        					   break;
        					default:
        					   patient = handleNextPatient(currentRoom,
        						    rooms.get(0));
        					   break;
        					}
        
        					Staff patientCNA = patient.workedWith[0];
        					Staff patientRN = patient.workedWith[1];
        					Staff patientDR = patient.workedWith[2];
        
        					System.out.println("\n>>>>>>>>>>>>>> BEGIN <<<<<<<<<<<<<<<");
        					System.out.printf(CColor.CYAN+"Patient "+CColor.MAGENTA_UNDERLINED+"%s %s"+CColor.CYAN+" spent "+CColor.RED+"%d"+CColor.CYAN+" minutes in the hospital.%n\tThey were treated by Dr. "+CColor.MAGENTA+"%s %s "+CColor.CYAN+"(ID: %d) in "+CColor.RED+"%s"+CColor.CYAN+" %n\t\tRN "+CColor.MAGENTA+"%s %s "+CColor.CYAN+" (ID: %d) and CNA "+CColor.MAGENTA+"%s %s "+CColor.CYAN+"(ID: %d)%n"+CColor.MAGENTA+"%s %s "+CColor.CYAN+"was a code "+CColor.RED+"%s."+CColor.CYAN+" They were treated as priority "+CColor.RED+"%d%n"+CColor.RESET,
        						patient.getName()[0], patient.getName()[1], patient.TimeSpentWaiting, 
        						patientDR.getName()[0], patientDR.getName()[1], patientDR.IDNumber(), patient.treatedRoom.name(), 
        						patientRN.getName()[0], patientRN.getName()[1], patientRN.IDNumber(),
        						patientCNA.getName()[0], patientCNA.getName()[1], patientCNA.IDNumber(),
        						patient.getName()[0], patient.getName()[1], patient.getCode(), patient.getPriority());
        					System.out.println(CColor.CYAN+"\tThis is because the patient "+CColor.MAGENTA_UNDERLINED+patient.getName()[1]+CColor.CYAN+" presented the following symptoms: "+CColor.RESET);
        					patient.symptoms.forEach(value -> System.out.println("\t\t"+CColor.RED+value+CColor.RESET));
        					System.out.println(">>>>>>>>>>>>>> END <<<<<<<<<<<<<<<\n");
        				    } catch (InterruptedException e) {
        					e.printStackTrace();
        				    }
                		    }
                		}).start();
                	    Thread.sleep(1000);
                	}
            	} while (numPatients > 0);
            	System.out.println(CColor.GREEN+"Finished processing. There were "+CColor.RED+totalEmergency.length()+CColor.GREEN+" emergency patients in today's simulation."+CColor.RESET);
    
            	} catch (NullPointerException e) {
            	    // do nothing
            	}
    }
}