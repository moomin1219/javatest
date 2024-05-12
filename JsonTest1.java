package exercise;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonTest1 {

	public static void main(String[] args) throws IOException {
		
		// json 파일 만들기  
		/*
		{
		    "name": "Spiderman",
		    "age": 45,
		    "married": true,
		    "specialty": [
		        "martial art",
		        "gun"
		    ],
		    "vaccine": {
		        "1st": "done",
		        "2nd": "expected",
		        "3rd": null
		    },
		    "children": [
		        {
		            "name": "Spiderboy",
		            "age": 10
		        },
		        {
		            "name": "Spidergirl",
		            "age": 8
		        }
		    ],
		    "address": null
		}
		*/
		
		Gson gson = new Gson();
		//Gson gson = new GsonBuilder().serializeNulls().create();  // value가 null 인 것도 포함할 경우  
		JsonObject resObj = new JsonObject();
		
		// 1레벨 추가  
		resObj.addProperty("name", "Spiderman");
		resObj.addProperty("age", 45);
		resObj.addProperty("married", true);
		resObj.add("address", null);
		
		// String Array 추가 
		JsonArray jsonArray1 = new JsonArray();
		jsonArray1.add("martial art");
		jsonArray1.add("gun");
		resObj.add("specialty", jsonArray1);
		
		// Object 추가 
		JsonObject obj1 = new JsonObject();
		obj1.addProperty("1st", "done");
		obj1.addProperty("2nd", "expected");
		obj1.add("3rd", null);
		resObj.add("vaccine", obj1);
		
		// Object Array 추가 
		JsonObject obj2 = new JsonObject();
		obj2.addProperty("name", "Spiderboy");
		obj2.addProperty("age", 10);
		
		JsonObject obj3 = new JsonObject();
		obj3.addProperty("name", "Spidergirl");
		obj3.addProperty("age", 8);
		
		JsonArray jsonArray2 = new JsonArray();
		jsonArray2.add(obj2);
		jsonArray2.add(obj3);
		resObj.add("children", jsonArray2);
		
		// Json Object -> Json String
		String jsonString = gson.toJson(resObj);
		
		// File Write
		FileWriter writer = new FileWriter("sample.json");
		writer.write(jsonString);
		writer.close();
		
		
	}

}
