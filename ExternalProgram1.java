import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class ExternalProgram1 {

	public static void main(String[] args) throws IOException {
		
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader("NUM.TXT"));
		
		while ((line = reader.readLine()) != null){
			String[] lines = line.split(" ");
			String output = getProcessOutput(Arrays.asList("add_2sec.exe", lines[0], lines[1]));
			System.out.println(lines[0] + " + " + lines[1] + " = " + output);
		}
		reader.close();
		
	}

	private static String getProcessOutput(List<String> cmdList) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(cmdList);
		Process process = builder.start();
		InputStream psout = process.getInputStream();
		byte[] buffer = new byte[1024];
		psout.read(buffer);
		return (new String(buffer));
	}

}
