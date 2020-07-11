package exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class ArrayListDeduplication {

	public static void main(String[] args) {
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		arrayList.add("apple");
		arrayList.add("apple");
		arrayList.add("peach");
		arrayList.add("kiwi");
		arrayList.add("banana");
		arrayList.add("plum");
		arrayList.add("peach");
		
		System.out.println(arrayList);
		
		// ArrayList 중복 제거
		// HashSet 사용한 중복 제거
		HashSet<String> hashSet = new HashSet<String>(arrayList); //HashSet에 ArrayList 데이터 삽입
		ArrayList<String> newArrayList = new ArrayList<String>(hashSet); //중복이 제거된 HashSet을 다시 ArrayList에 삽입
		
		System.out.println(newArrayList);
		
		// TreeSet 사용한 중복 제거 (오름차순)
		TreeSet<String> treeSet = new TreeSet<String>(arrayList); //TreeSet에 ArrayList 데이터 삽입
		ArrayList<String> newArrayList2 = new ArrayList<String>(treeSet); //중복이 제거된 TreeSet을 다시 ArrayList에 삽입
		
		System.out.println(newArrayList2);
		
		
		
	}

}
