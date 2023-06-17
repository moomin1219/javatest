import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunManager {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		
		while (true) {
			
			input = br.readLine();
			
			BufferedReader reader = new BufferedReader(new FileReader("./" + input + ".txt"));
			
			String serviceFileName = reader.readLine();
			
			BufferedReader reader2 =  new BufferedReader(new FileReader("./" + serviceFileName + ".txt"));
			
			System.out.println(reader2.readLine());
			
			reader.close();
			reader2.close();
			
			break;
		}

	}

}
