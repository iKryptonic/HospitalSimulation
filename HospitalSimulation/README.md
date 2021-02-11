# Person class
	- First Name
	- Last Name
	- DOB	
	- Location
	
	- Staff extends person
		> Title (Doctor, Nurse, CNA)
		> DoctorID
		> Efficiency 0-1 (Multiplier)
		
	- Patient extends person
		> PatientID
		> Priority (1-3)
		> Code (W or E)
		> TimeSpentWaiting
			
# Room class
	- Treament room extends room
		- Distance from waiting room
	- Waiting room extends room
		- Occupants (HashMap/ArrayList)
		
# Database class
	- Interfaces with CSV .txt file to store user information
	- Database (Minus SQLite)
	- Contains personalities and how long it takes for a certain person to do anything
	
	
# Run simulation on the csv of patients & doctors
	- how long each patient took to be treated
	- who treated the patient
	- how to patient arrived
	
# Simulating 
	"Arrival of patient"
	"Assessment of patient"
	"Waiting time for patient"
	"Treatment time for patient"
	"Discharge time of patient"
	"Total time spent"