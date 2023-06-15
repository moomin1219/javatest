import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RunManager {

	public static void main(String[] args) {
		
		try {
			
			// Queue 정보를 Controller로부터 HTTP 응답으로 수신
			// URI: GET http://127.0.0.1:8080/queueInfo
			// 호출방향: SP_TEST -> MOCK.BAT
			// 응답 Body: JSON 문자열 형식
			HttpClient httpClient = new HttpClient();
			httpClient.start();
			ContentResponse contentResponse = httpClient.newRequest("http://127.0.0.1:8080/queueInfo")
					.method(HttpMethod.GET).send();
			String responseBody = contentResponse.getContentAsString();
			
			Gson gson = new Gson();
			JsonObject jsonObj = gson.fromJson(responseBody, JsonObject.class);
			
			int inputQueueCount = jsonObj.get("inputQueueCount").getAsInt();
			JsonArray inputQueueURIsArray = jsonObj.get("inputQueueURIs").getAsJsonArray();
			String outputQueueURI = jsonObj.get("outputQueueURI").getAsString();
			
			httpClient.stop();
			
			// Worker 생성
			Map<Integer, Worker> workerMap = new HashMap<Integer, Worker>();
			
			for (int i=0; i < inputQueueCount ; i++) {
				Worker worker = new Worker(i);
				workerMap.put(i, worker);
			}
			
			// 멀티 쓰레드를 이용하여 모든 Input Queue들에 대해 벙렬로 Input Queue 데이터를 요청/대기하고 응답이 수신되면 즉시 재요청해야 함
			for (int i=0; i < inputQueueCount ; i++) {
				String inputQueueURI = inputQueueURIsArray.get(i).getAsString();
				
				QueueRunner workerRunner = new QueueRunner(i, inputQueueURI, outputQueueURI, workerMap.get(i));
				
				Thread t = new Thread(workerRunner);
				t.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
