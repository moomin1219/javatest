import java.io.FileNotFoundException;
import java.io.FileReader;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RunManager {

	public static void main(String[] args) {
		
		String routingConfigPath = args[0];
		//String routingConfigPath = "./Proxy-1.json";
		
		FileReader fileReader;
		
		Gson gson = new Gson();
		JsonObject jsonObj  = null;
		
		try {
			fileReader = new FileReader(routingConfigPath);
			jsonObj = gson.fromJson(fileReader, JsonObject.class);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int port = jsonObj.get("port").getAsInt();
		
		Server server = new Server();
		ServerConnector httpConnector = new ServerConnector(server);
		httpConnector.setHost("127.0.0.1");
		httpConnector.setPort(port);
		server.addConnector(httpConnector);
		
		ServletHandler servletHandler = new ServletHandler();
		ServletHolder servletHolder = servletHandler.addServletWithMapping(MyServlet.class, "./*");
		
		servletHolder.setInitParameter("routes", jsonObj.get("routes").toString());
		servletHolder.setInitParameter("port", Integer.toString(port));
		
		server.setHandler(servletHandler);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
