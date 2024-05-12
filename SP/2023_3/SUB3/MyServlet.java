package com.lgcns.test;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static boolean variableRead = false;
	private static HashMap<String, String> variableMap = new HashMap<String, String>();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println();
		System.out.println("doGet()");
		
		String requestURI = req.getRequestURI();
		System.out.println("REQUEST URI => " + requestURI); // => /create, add, fetch
		
		Gson gson = new Gson();
		
		// VARIABLE.JSON 파일은 최초 1회만 로드
		if(!variableRead) {
			JsonReader reader1 = new JsonReader(new FileReader("VARIABLE.JSON"));
			JsonObject variableJsonObj = gson.fromJson(reader1, JsonObject.class);
			
			for(String key : variableJsonObj.keySet()) {
				variableMap.put(key, variableJsonObj.get(key).getAsString());
			}
			variableRead = true;
		}
		
		
		JsonReader reader2 = new JsonReader(new FileReader("STATE.JSON"));
		JsonObject jsonObj = gson.fromJson(reader2, JsonObject.class);
		JsonObject jsonObject = jsonObj.get("state").getAsJsonObject();
		
		for(String key: jsonObject.keySet()) {
			if(key.equals(requestURI.substring(1))) { // create, add, fetch
				JsonObject stateObj = jsonObject.get(key).getAsJsonObject();
				String url = stateObj.get("url").getAsString();
				
				JsonArray params = stateObj.get("parameters").getAsJsonArray();
				if(params.size() > 0) {
					url = url.concat("?");
					for ( int i=0; i < params.size(); i++) {
						url = url.concat(params.get(i).getAsString()).concat("=").concat(variableMap.get(params.get(i).getAsString()));
						if ( i < params.size() -1) {
							url = url.concat("&");
						}
					}
				}
				System.out.println("REQ URL!!!!!!! => " + url);
				
				HttpClient httpClient = new HttpClient();
				try {
					httpClient.start();
					
					// client가 호출하고 받은 응답
					ContentResponse contentResponse = httpClient.newRequest(url).method(HttpMethod.POST).send();
					String responseBody = contentResponse.getContentAsString();
					System.out.println("RESPONSE BODY OF REQUEST => " + responseBody); // => {"key": ["Wn8812n1"]}
					
					// update variableMap
					JsonObject responseJsonObj = gson.fromJson(responseBody, JsonObject.class);
					for (String k: responseJsonObj.keySet()){
						variableMap.put(k, responseJsonObj.get(k).getAsString());
					}
					
					httpClient.stop();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		res.setStatus(200);
		//res.getWriter().close();
		
	}
	
}
