import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 2734078233771003683L;
	private static Map<String, String> routesMap = new HashMap<String, String>(); 
	private static String port = null;
	
	@Override
	public void init() throws ServletException {
		
		port = getInitParameter("port");
		String routesConfigString = getInitParameter("routes");
		
		JsonElement routesConfig = JsonParser.parseString(routesConfigString);
		JsonArray routesArray = routesConfig.getAsJsonArray();
		
		for (int i=0; i < routesArray.size(); i++) {
			String pathPrefix = ((JsonObject)routesArray.get(i)).get("pathPrefix").getAsString();
			String url = ((JsonObject)routesArray.get(i)).get("url").getAsString();
			routesMap.put(pathPrefix, url);
		}
	}
	
	// GET
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("doGET!!!!!!!!!!!");
		handleProxy(req, res, HttpMethod.GET);
	}

	// POST
	protected void doPost(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("doPOST!!!!!!!!!!");
		handleProxy(req, res, HttpMethod.POST);
	}
	
	private void handleProxy(HttpServletRequest req, HttpServletResponse res, HttpMethod method) {
		// Service Proxy는 Service 또는 타 Proxy 호출 시
		// 요청된 HTTP Method, Path, Query를 그대로 전달
		// 수신된 응답의 Status, Content-Type Header, Body를 변경없이 응답으로 전달
		
		String requestURI = req.getRequestURI();
		System.out.println("requestURI => " + requestURI);
		
		String firstPath = "/" + requestURI.split("/")[1];
		String url = routesMap.get(firstPath);
		String targetRequestURI = url + requestURI; 
		
		// Query 그대로 전달
		String parameters = getParameters(req);
		targetRequestURI += parameters;
		
		System.out.println(" targetRequestURI => " + targetRequestURI);
		// ex) http://127.0.0.1:8081/front, http://127.0.0.1:5002/auth
		//     http://127.0.0.1:8082/auth?id=lgcns, http://127.0.0.1:8082/auth/lgcns
		
		
		try {
			
			HttpClient httpClient = new HttpClient();
			httpClient.start();
			
			ContentResponse contentResponse = httpClient.newRequest(targetRequestURI)
							.method(method).send();
			String responseBody = contentResponse.getContentAsString();
			System.out.println(" responseBody of Request => " + responseBody);
			// ex) {"result": "Auth Check.(POST)(no_parameter)"}
			//	   {"result": "Auth Check.(GET)(GET_parameter, id: lgcns)"}
			//	   {"result": "Auth Check.(GET)(Path, /auth/lgcns)"}
			//     {"result": "Service Init.", "data": [{"status": "200", "result": "Auth Check.(POST)(no_parameter)"}]}
			
			httpClient.stop();
			
			// status of response
			res.setStatus(contentResponse.getStatus());
			
			// headers of response
			copyHeaders(contentResponse, res);
			
			// return with value (body)
			res.getWriter().write(contentResponse.getContentAsString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private String getParameters(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		Enumeration<String> parameterNames = req.getParameterNames();
		while(parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if(sb.length() == 0) {
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(paramName);
			sb.append("=");
			sb.append(req.getParameter(paramName));
		}
		return sb.toString();
	}
	
	// response to response
	private void copyHeaders(ContentResponse originResponse, HttpServletResponse targetResponse) {
		HttpFields httpFields = originResponse.getHeaders();
		Iterator<HttpField> iterator = httpFields.iterator();
		while(iterator.hasNext()) {
			HttpField httpField = iterator.next();
			targetResponse.setHeader(httpField.getName(), httpField.getValue());
		}
	}
	
}
