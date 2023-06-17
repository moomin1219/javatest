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
			String serviceFileName = null;
			
			String[] inputArray = input.split(" ");
			String proxyName = inputArray[0];
			String path = inputArray[1];
			
			BufferedReader reader = new BufferedReader(new FileReader("./" + proxyName + ".txt"));
			
			String line = null;
			String line2 = null;
			
			while ((line = reader.readLine()) != null) {
				if(line.split("#")[0].equals(path)) {
					if(line.split("#")[1].startsWith("Proxy-")) {
						BufferedReader reader2 = new BufferedReader(new FileReader("./" + line.split("#")[1]));
						while ((line2 = reader2.readLine()) != null) {
							if(line2.split("#")[0].equals(path)) {
								serviceFileName = line2.split("#")[1];
								break;
							}
						}
						reader2.close();
					} else {
						serviceFileName = line.split("#")[1];
					}
					break;
				}
			}
			
			BufferedReader reader3 =  new BufferedReader(new FileReader("./" + serviceFileName));
			
			System.out.println(reader3.readLine());
			
			reader.close();
			reader3.close();
			
			break;
		}

	}

}

