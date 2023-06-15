import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class QueueRunner implements Runnable {

	private int queueNum;
	private String inputQueueURI;
	private String outputQueueURI;
	private Worker worker;
	
	public QueueRunner(int queueNum, String inputQueueURI, String outputQueueURI, Worker worker) {
		this.queueNum = queueNum;
		this.inputQueueURI = inputQueueURI;
		this.outputQueueURI = outputQueueURI;
		this.worker = worker;
	}

	@Override
	public void run() {
		
		while (true) {
			
			try {
				
				// HTTP 응답으로 Input Queue 입력
				// URI: GET <Input Queue URI>
				// 호출방향: SP_TEST -> MOCK.BAT
				// 응답 Body: {"timestamp": <Timestamp>, "value": "<value>"}
				// 예) {"timestamp" : 1000, "value" : "VIEW_AD1"}
				HttpClient httpClient = new HttpClient();
				httpClient.start();
				ContentResponse contentResponse = httpClient.newRequest(this.inputQueueURI)
						.method(HttpMethod.GET).send();
				String responseBody = contentResponse.getContentAsString();
				
				Gson gson = new Gson();
				JsonObject jsonObj = gson.fromJson(responseBody, JsonObject.class);
				
				long timestamp = jsonObj.get("timestamp").getAsLong();
				String value = jsonObj.get("value").getAsString();
				
				httpClient.stop();
				
				String result = this.worker.run(timestamp, value);
				
				if(result != null) {
					System.out.println("Worker Result : " + result);
					
					// HTTP 요청으로 Output Queue 출력
					// URI: POST <Output Queue URI>
					// 호출방향: SP_TEST -> MOCK.BAT
					// 요청 Body : {"result" : "<worker 실행 결과>"}
					// 예) {"result" : "Worker(0):Matched AD1"}
					// 응답 Body: 없음
					httpClient.start();
					
					JsonObject resultObj = new JsonObject();
					resultObj.addProperty("result", result);
					
					String resultJsonStr = gson.toJson(resultObj);
					
					Request request = httpClient.newRequest(this.outputQueueURI).method(HttpMethod.POST);
					request.header(HttpHeader.CONTENT_TYPE, "application/json");
					request.content(new StringContentProvider(resultJsonStr, "utf-8"));
					request.send();

					httpClient.stop();
				}
				
				
			} catch (Exception e) {
				
			}
		}
		
	}
	

}
