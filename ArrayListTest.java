package exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ArrayListTest {

	public static void main(String[] args) {
		
		/* String Array */
		ArrayList<String> stringList = new ArrayList<String>();
		
		stringList.add("apple");
		stringList.add("grape");
		stringList.add("banana");
		stringList.add("melon");
		stringList.add("peach");
		
		//오름차순 정렬
		Collections.sort(stringList); 
		print1(stringList);
		
		//내림차순 정렬
		stringList.sort(Collections.reverseOrder()); //1안
		Collections.sort(stringList, new AscendingString()); //2안 
		print1(stringList);
		
		/* int Array */
		ArrayList<Integer> intList = new ArrayList<Integer>();
		
		intList.add(1);
		intList.add(789);
		intList.add(33);
		intList.add(34);
		intList.add(1000);
		
		//오름차순 정렬
		Collections.sort(intList); 
		print2(intList);
		
		//내림차순 정렬
		intList.sort(Collections.reverseOrder()); //1안
		Collections.sort(intList, new AscendingInteger()); //2안 
		print2(intList);
	}

	public static void print1(ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}
	
	public static void print2(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}

	
}

class AscendingInteger implements Comparator<Integer> {
	@Override
	public int compare(Integer a, Integer b) {
		return b.compareTo(a);
	}
}

class AscendingString implements Comparator<String> {
	@Override
	public int compare(String a, String b) {
		return b.compareTo(a);
	}
}
