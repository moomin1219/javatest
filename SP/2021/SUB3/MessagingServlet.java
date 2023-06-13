import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MessagingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	HashMap<String, LinkedBlockingQueue<Message>> queueMap = new HashMap<String, LinkedBlockingQueue<Message>>();
	
	// GET 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		String requestURI = req.getRequestURI();
		System.out.println("doGet!!!!!");
		System.out.println("requestURI => " + requestURI);
		
		if(requestURI.startsWith("/RECEIVE")) { // GET http://127.0.0.1:8080/RECEIVE/<Queue Name>
			System.out.println("##### RECEIVE #####");
			receiveMessage(req, res);
		}
		
	}
	
	// POST
	protected void doPost(HttpServletRequest req, HttpServletResponse res) {
		String requestURI = req.getRequestURI();
		System.out.println("doPost!!!!!");
		System.out.println("requestURI => " + requestURI);
		
		if(requestURI.startsWith("/CREATE")) { // POST http://127.0.0.1:8080/CREATE/<Queue Name>
			System.out.println("##### CREATE #####");
			createQueue(req, res);
		} else if(requestURI.startsWith("/SEND")) { // POST http://127.0.0.1:8080/SEND/<Queue Name>
			System.out.println("##### SEND #####");
			sendMessage(req, res);
		} else if(requestURI.startsWith("/ACK")) { // POST http://127.0.0.1:8080/ACK/<Queue Name>/<Message ID>
			System.out.println("##### ACK #####");
			handleCompleted(req, res);
		} else if(requestURI.startsWith("/FAIL")) { // POST http://127.0.0.1:8080/FAIL/<Queue Name>/<Message ID>
			System.out.println("##### FAIL #####");
			handleFail(req, res);
		}
		
	}
	
	// Queue 생성
	private void createQueue(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String reqBody = getJsonBodyFromRequest(req);
			System.out.println(reqBody); // {"QueueSize": <Queue Size>}
			
			String queueName = req.getRequestURI().split("/")[2];
			System.out.println("QueueName => " + queueName);
			
			Gson gson = new Gson();
			JsonObject resObj = new JsonObject();
			
			if (queueMap.containsKey(queueName)) {
				resObj.addProperty("Result", "Queue Exist");
			} else {
				JsonObject jsonObj = gson.fromJson(reqBody, JsonObject.class);
				int queueSize = jsonObj.get("QueueSize").getAsInt();
				
				LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<Message>(queueSize);
				queueMap.put(queueName, queue);
				
				resObj.addProperty("Result", "OK");
			}
			
			String jsonStr = gson.toJson(resObj);
			res.setStatus(200);
			res.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Message 송신
	private void sendMessage(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String reqBody = getJsonBodyFromRequest(req);
			System.out.println(reqBody); // {"Message": "<Message>"}
			
			String queueName = req.getRequestURI().split("/")[2];
			System.out.println("QueueName => " + queueName);
			
			Gson gson = new Gson();
			JsonObject resObj = new JsonObject();
			
			LinkedBlockingQueue<Message> queue = queueMap.get(queueName);
			
			if (queue.remainingCapacity() == 0) {
				resObj.addProperty("Result", "Queue Full");
			} else {
				JsonObject jsonObj = gson.fromJson(reqBody, JsonObject.class);
				String queueMessage = jsonObj.get("Message").getAsString();
				
				Message message = new Message(queueName, queueMessage);
				queue.add(message);
				
				resObj.addProperty("Result", "OK");
			}
			
			String jsonStr = gson.toJson(resObj);
			res.setStatus(200);
			res.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	// Message 수신
	private void receiveMessage(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String queueName = req.getRequestURI().split("/")[2];
			System.out.println("QueueName => " + queueName);
			
			Gson gson = new Gson();
			JsonObject resObj = new JsonObject();
			
			LinkedBlockingQueue<Message> queue = queueMap.get(queueName);
			
			Message returnMessage = null;
			
			Iterator<Message> iterator = queue.iterator();
			while(iterator.hasNext()) {
				Message message = iterator.next();
				if(message.isReceivable()) {
					message.setReceivable(false);
					returnMessage = message;
					break;
				}
			}
			
			if(returnMessage == null) {
				resObj.addProperty("Result", "No Message");
			} else {
				resObj.addProperty("Result", "OK");
				resObj.addProperty("MessageID", returnMessage.getMessageId());
				resObj.addProperty("Message", returnMessage.getQueueMessage());
			}
			
			String jsonStr = gson.toJson(resObj);
			res.setStatus(200);
			res.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Message 핸들링 완료
	private void handleCompleted(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String queueName = req.getRequestURI().split("/")[2];
			String messageID = req.getRequestURI().split("/")[3];
			
			System.out.println("QueueName => " + queueName);
			System.out.println("MessageID => " + messageID);
			
			LinkedBlockingQueue<Message> queue = queueMap.get(queueName);
			Iterator<Message> iterator = queue.iterator();
			
			while(iterator.hasNext()) {
				Message message = iterator.next();
				if(message.getMessageId().equals(messageID)) {
					queue.remove(message);
					break;
				}
			}
			
			Gson gson = new Gson();
			JsonObject resObj = new JsonObject();
			
			resObj.addProperty("Result", "OK");
			
			String jsonStr = gson.toJson(resObj);
			res.setStatus(200);
			res.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Message 핸들링 실패
	private void handleFail(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			String queueName = req.getRequestURI().split("/")[2];
			String messageID = req.getRequestURI().split("/")[3];
			
			System.out.println("QueueName => " + queueName);
			System.out.println("MessageID => " + messageID);
			
			LinkedBlockingQueue<Message> queue = queueMap.get(queueName);
			Iterator<Message> iterator = queue.iterator();
			
			while(iterator.hasNext()) {
				Message message = iterator.next();
				if(message.getMessageId().equals(messageID)) {
					message.setReceivable(true);
					break;
				}
			}
			
			Gson gson = new Gson();
			JsonObject resObj = new JsonObject();
			
			resObj.addProperty("Result", "OK");
			
			String jsonStr = gson.toJson(resObj);
			res.setStatus(200);
			res.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private String getJsonBodyFromRequest(HttpServletRequest req) {
		String strBody = "";
		try (InputStreamReader isr = new InputStreamReader(req.getInputStream());
				BufferedReader br = new BufferedReader(isr)) {
			String buffer;
			StringBuilder sb = new StringBuilder();
			while ((buffer = br.readLine()) != null) {
				sb.append(buffer + "\n");
			}
			strBody = sb.toString();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return strBody;
	}
}
