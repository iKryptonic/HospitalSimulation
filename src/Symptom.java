import java.util.Random;

public enum Symptom {
    RunnyNose(1),
    Headache(3),
    Pain(2),
    SoreThroat(1),
    Fever(4),
    Bleeding(5);
    
    private int value;
    Random randomGenerator = new Random();
    
    Symptom(int severity){
	value = randomGenerator.nextInt(severity); // any symptom is given a random severity
    }
    
    public int getSeverity() {
	return this.value;
    }
    
}
