package com.lgcns.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("doPOST()");
		System.out.println("REQUEST URL => " + req.getRequestURL()); // => http://127.0.0.1:8010/fromServer
		
		String requestURI = req.getRequestURI();
		System.out.println("REQUEST URI => " + requestURI); // => /fromServer
		
		if(requestURI.startsWith("/fromServer")){
			String reqBody = getJsonBodyFromRequest(req);
			System.out.println("REQ BODY => " + reqBody);
			// => {"command":"CMD_001", "targetDevice":[DEVICE_069:], "param":"fe303904"}
			
			Gson gson = new Gson();
			
			JsonObject jsonObj = gson.fromJson(reqBody, JsonObject.class);
			JsonArray jsonArray = jsonObj.get("targetDevice").getAsJsonArray();
			int deviceLength = jsonArray.size();
			ArrayList<String> deviceList = new ArrayList<String>();
			for (int i=0; i < deviceLength; i++) {
				deviceList.add(jsonArray.get(i).getAsString());
			}
			
			
			// DEVICE INFO
			JsonReader reader = new JsonReader(new FileReader("./INFO/DEVICE.JSON"));
			JsonObject deviceObj = gson.fromJson(reader, JsonObject.class);
			
			JsonArray deviceArray = deviceObj.get("deviceInfo").getAsJsonArray();
			
			HashMap<String, String> map = new HashMap<String, String>();
			
			for(int i=0; i < deviceArray.size(); i++) {
				JsonObject jsonObject = deviceArray.get(i).getAsJsonObject();
				String device = jsonObject.get("device").getAsString();
				String hostname = jsonObject.get("hostname").getAsString();
				String port = jsonObject.get("port").getAsString();
				map.put(device, hostname+":"+port);
			}
			reader.close();
			
			
			// NEW COMMAND
			BufferedReader reader2 = new BufferedReader(new FileReader("./INFO/SERVER_COMMAND.JSON"));
			JsonObject serverObj = gson.fromJson(reader2, JsonObject.class);
			
			JsonArray serverArray = serverObj.get("serverCommandInfo").getAsJsonArray();
			
			HashMap<String, String> map2 = new HashMap<String, String>();
			
			for(int i=0; i < serverArray.size(); i++) {
				JsonObject jsonObject = serverArray.get(i).getAsJsonObject();
				String command = jsonObject.get("command").getAsString();
				String forwardCommand = jsonObject.get("forwardCommand").getAsString();
				map2.put(command, forwardCommand);
			}
			reader2.close();
			
			
			String command = jsonObj.get("command").getAsString();
			String newCommand = "";
			for (int i=0; i < map2.size(); i++) {
				if(map2.containsKey(command)) {
					newCommand = map2.get(command);
					 break;
				}
			}
			
			
			// MAKE NEW JSON
			JsonObject newJsonObj = new JsonObject();
			newJsonObj.addProperty("command", newCommand);
			newJsonObj.addProperty("param", jsonObj.get("param").getAsString());
			String newJsonStr = gson.toJson(newJsonObj);
			
			
			ArrayList<String> resultList = new ArrayList<String>();
			
			for (int i=0; i < deviceList.size(); i++) {
				HttpClient httpClient = new HttpClient();
				try {
					httpClient.start();
					String url = map.get(deviceList.get(i));
					
					Request request = httpClient.newRequest("http://"+url+"/fromEdge").method(HttpMethod.POST);
					request.content(new StringContentProvider(newJsonStr, "utf-8"));
					
					// client가 호출하고 받은 응답  
					ContentResponse contentResponse = request.send();
					String responseBody = contentResponse.getContentAsString();
					System.out.println("RESPONSE BODY TO STRING => " + responseBody); // => {"result": ["20b95f9c"]}
					
					JsonObject responseJsonObj = gson.fromJson(responseBody, JsonObject.class);
					JsonArray resArr = responseJsonObj.get("result").getAsJsonArray();
					String result = resArr.get(0).getAsString();
					resultList.add(result);
					
					httpClient.stop();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			JsonObject resultObj = new JsonObject();
			JsonArray arr = new JsonArray();
			for (int i=0; i < resultList.size(); i++) {
				arr.add(resultList.get(i));
			}
			resultObj.add("result", arr);
			
			String finalResultStr = gson.toJson(resultObj);
			
			res.setStatus(200);
			res.getWriter().write(finalResultStr);
			
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
