import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class RunManager {

	public static void main(String[] args) throws IOException {
		
		HashMap<String, LinkedBlockingQueue<String>> queueMap = new HashMap <String, LinkedBlockingQueue<String>>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		
		while(true) {
			input = br.readLine();
			String[] inputArray = input.split(" ");
			
			if(input.startsWith("CREATE")) { //CREATE <Queue Name> <Queue Size>
				String queueName = inputArray[1];
				int queueSize = Integer.parseInt(inputArray[2]);
				
				if(queueMap.containsKey(queueName)) {
					System.out.println("Queue Exist");
				} else {
					 LinkedBlockingQueue<String> queue = new  LinkedBlockingQueue<String>(queueSize);
					 queueMap.put(queueName, queue);
				}
			
			} else if(input.startsWith("SEND")) { //SEND <Queue Name> <Message>
				LinkedBlockingQueue<String> queue = queueMap.get(inputArray[1]);
				if(queue.remainingCapacity() == 0) {
					System.out.println("Queue Full");
				} else {
					queue.add(inputArray[2]);
				}
				
			} else if (input.equals("RECEIVE")) { //RECEIVE  <Queue Name>
				LinkedBlockingQueue<String> queue = queueMap.get(inputArray[1]);
				if(!queue.isEmpty()) {
					System.out.println(queue.element()); //Retrieves, but does not remove, the head of this queue. 
					queue.remove(); //Retrieves and removes the head of this queue. if queue is empty, throws NoSuchElementException
				}
			}
				
		}

	}

}
