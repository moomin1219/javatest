package com.lgcns.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RunManager {

	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		
		while(true) {
			input = br.readLine();
			String[] inputList = input.split("#");
			
			String device = inputList[1];
			String[] deviceList = device.split(",");
			
			String parameter = inputList[2];
			
			ArrayList<String> list = new ArrayList<String>();
			
			String line = null;
			
			BufferedReader reader = new BufferedReader(new FileReader("./INFO/SERVER_COMMAND.TXT"));
			while((line = reader.readLine()) != null){
				list.add(line);
			}
			reader.close();
			
			String newCommand = null;
			String result = "";
			
			for(int i=0; i < deviceList.length; i++) {
				
				File reqFile = new File("./DEVICE/REQ_TO_" + deviceList[i] + ".TXT");
				BufferedWriter bw = new BufferedWriter(new FileWriter(reqFile, false));
				bw.write(newCommand + "#" + parameter);
				bw.flush();
				bw.close();
				
				Thread.sleep(500);
				
				BufferedReader reader2 = new BufferedReader(new FileReader("./DEVICE/RES_FROM_" + deviceList[i] + ".TXT"));
				while((line = reader2.readLine()) != null){
					if ( i != deviceList.length-1 ) {
						result = result.concat(line).concat(",");
					} else {
						result = result.concat(line);
					}
				}
				reader2.close();
			}
			
			System.out.println(result);
			
			break;
			
			
		}

	}

}
