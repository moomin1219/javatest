package exercise;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class JsonTest3 {

	public static void main(String[] args) throws FileNotFoundException {
		
		// sample.json 파일 읽은 후, 첫 번째 level의 key와 value type 알아내
		Gson gson = new Gson();
		
		JsonReader reader = new JsonReader(new FileReader("sample.json"));
		JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);
		
		for (String key : jsonObj.keySet()) {
			System.out.print("Key : " +key+ " / Value Type : ");
			JsonElement element = jsonObj.get(key);
			
			if(element.isJsonPrimitive()) {
				if(element.getAsJsonPrimitive().isString()) {
					System.out.println("String");
				} else if(element.getAsJsonPrimitive().isNumber()) {
					System.out.println("Number");
				} else if(element.getAsJsonPrimitive().isBoolean()) {
					System.out.println("Boolean");
				} else if(element.getAsJsonPrimitive().isJsonNull()) {
					System.out.println("null");
				} 
			} else if(element.isJsonArray()) {
				System.out.println("Array");
			} else if(element.isJsonObject()) {
				System.out.println("Object");
			} else if(element.isJsonNull()) {
				System.out.println("null");
			}
		}

	}

}
