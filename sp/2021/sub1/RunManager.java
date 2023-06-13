import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class RunManager {

	public static void main(String[] args) throws IOException {
		
		Queue<String> queue = new LinkedList<String>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		
		while(true) {
			input = br.readLine();
			
			if(input.startsWith("SEND")) {
				queue.add(input.split(" ")[1]);
			} else if (input.equals("RECEIVE")) {
				if(!queue.isEmpty()) {
					System.out.println(queue.element()); //Retrieves, but does not remove, the head of this queue. 
					queue.remove(); //Retrieves and removes the head of this queue. if queue is empty, throws NoSuchElementException
				}
			}
				
		}

	}

}
