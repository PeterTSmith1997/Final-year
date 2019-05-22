import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
	Scanner scanner;
	File logg;
	
	public static void main (String[] args) {
		
	}
	
	public void readFile() {
		try {
			scanner = new Scanner(logg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setFile(String pathToFile) {
		logg = new File(pathToFile);
	}
}
