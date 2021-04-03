import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NameGenerator {
    Map<Integer, String> firstNames = new HashMap<Integer,String>();
    Map<Integer, String> lastNames = new HashMap<Integer,String>();
	    
    public NameGenerator(String file1, String file2) throws IOException {
	firstNames = readFileToMap(file1);
	lastNames = readFileToMap(file2);
    }
    
    private Map<Integer, String> readFileToMap(String file) throws IOException{
	Map<Integer, String> toReturn = new HashMap<Integer, String>();
	
	BufferedReader br = new BufferedReader(new FileReader(file));
	String line =  null;

	while((line=br.readLine())!=null){
	    toReturn.put(toReturn.size(), line);
	}
	
	br.close();
	return toReturn;
    }
    
    public String[] generateName() {
	String[] names = new String[2];
	List<Integer> keysAsArray = new ArrayList<Integer>(firstNames.keySet());
	List<Integer> secondkeysAsArray = new ArrayList<Integer>(lastNames.keySet());
		
	Random r = new Random();
	
	names[0] = firstNames.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
	names[1] = lastNames.get(secondkeysAsArray.get(r.nextInt(secondkeysAsArray.size())));
	
	return names;
    }
}
