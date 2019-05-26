package comparator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComparatorTest {

	public static void main(String[] args) throws IOException {
		
		ArrayList<Grade> gradeList = new ArrayList<>();	
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("grade.txt"));

			String str;
		
			while ((str = in.readLine()) != null) {
				String line[] = str.split(" ");
				Grade grade = new Grade(line[0], Integer.parseInt(line[1]),Integer.parseInt(line[2]) ,Integer.parseInt(line[3]));
				gradeList.add(grade);
			}
			
			in.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			String input = br.readLine();
			if(input.equals("PRINT")) {
				Comparator<Grade> com = new Comparator<Grade>() {
					@Override
					public int compare(Grade o1, Grade o2) {
						return o1.getName().compareTo(o2.getName()); //오름차순
						//return o2.getName().compareTo(o1.getName()); //내림차순
					}
				};
			    Collections.sort(gradeList, com);
			} else if(input.equals("KOREAN")) {
				Comparator<Grade> com = new Comparator<Grade>() {
					@Override
					public int compare(Grade o1, Grade o2) {
						return o2.getKorean() - o1.getKorean(); //내림차순
					}
				};
				Collections.sort(gradeList, com);
			} else if(input.equals("ENGLISH")) {
				Comparator<Grade> com = new Comparator<Grade>() {
					@Override
					public int compare(Grade o1, Grade o2) {
						return o2.getEnglish() - o1.getEnglish();
					}
				};
				Collections.sort(gradeList, com);
			} else if(input.equals("MATH")) {
				Comparator<Grade> com = new Comparator<Grade>() {
					@Override
					public int compare(Grade o1, Grade o2) {
						return o2.getMath() - o1.getMath();
					}
				};
				Collections.sort(gradeList, com);
			} else if(input.equals("QUIT")) {
				break;
			}
			
			for (Grade g : gradeList) {
				System.out.println(String.format("%s %d %d %d", g.getName(), g.getKorean(), g.getEnglish(), g.getMath()));
			}
		}
		
	}

}
