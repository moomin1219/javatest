import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RunManager {

	public static void main(String[] args) throws IOException {
		
		// Worker 생성 및 실행 Sample  - 아래 2개 라인을 지우고 구현하세요.
		// Worker worker = new Worker(0);
		// worker.run("VIEW_AD1");
		
		Map<Integer, Worker> workerMap = new HashMap<Integer, Worker>();
		
		for (int i=0; i < 2; i++) {
			Worker worker = new Worker(i);
			workerMap.put(i, worker);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		
		while(true) {
			input = br.readLine();
			
			String queueNum = input.split(" ")[0];
			String value = input.split(" ")[1];
			
			String result = null;
			
			Worker worker = workerMap.get(Integer.parseInt(queueNum));
			
			result = worker.run(value);
			
			if(result != null) {
				System.out.println(result);
			}
		}
	}

}
