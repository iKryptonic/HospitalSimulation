
public enum Room {
    WaitingRoom(0),
    TreatmentRoom1(4),
    TreatmentRoom2(6),
    TreatmentRoom3(3),
    TreatmentRoom4(12),
    TreatmentRoom5(6);
    
    public final static int dischargeTime = 5;
    public final static int admitTime = 3;
    
    private int distanceFromWaitingRoom;
    
    Room(int distance){
	distanceFromWaitingRoom = distance;
    }
    
    public int getDistance() {
	return this.distanceFromWaitingRoom;
    }
    
}
