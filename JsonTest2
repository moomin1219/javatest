import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class JsonTest2 {

	public static void main(String[] args) throws IOException {
		
		// Read JSON File
		// 첫 번째 방법
		Gson gson1 = new Gson();
		JsonReader reader1 = new JsonReader(new FileReader("sample.json"));
		JsonObject jsonObj1 = gson1.fromJson(reader1, JsonObject.class);
		
		// 두 번째 방법 
		Gson gson2 = new Gson();
		String wholeData = new String(Files.readAllBytes(Paths.get("sample.json")));
		JsonObject jsonObj2 = gson2.fromJson(wholeData, JsonObject.class);
		
		
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("sample.json"));
		JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
		
		String name = jsonObj.get("name").getAsString();
		int age = jsonObj.get("age").getAsInt();
		
		System.out.println("name(age): " + name + "(" + age + ")");
		
		JsonArray jsonArray = jsonObj.get("children").getAsJsonArray();
		JsonObject jsonObject = jsonArray.get(1).getAsJsonObject();
		
		name = jsonObject.get("name").getAsString();
		age = jsonObject.get("age").getAsInt();
		
		System.out.println("name(age): " + name + "(" + age + ")");
		
	}

}
