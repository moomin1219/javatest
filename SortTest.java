import java.util.*;

public class SortTest {

	public static void main(String[] args) {
		/* ArrayList */
		ArrayList<String> stringList = new ArrayList<String>();
		stringList.add("zebra");
		stringList.add("banana");
		stringList.add("coconut");
		stringList.add("apple");
		
		//오름차순 1
		Collections.sort(stringList);
		System.out.println(stringList);
	
		//오름차순 2
		stringList.sort(Comparator.naturalOrder());
		System.out.println(stringList);
		
		//내림차순
		stringList.sort(Comparator.reverseOrder());
		System.out.println(stringList);
		
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(1);
		intList.add(3);
		intList.add(100);
		intList.add(50);
		
		//오름차순
		intList.sort(Comparator.naturalOrder());
		System.out.println(intList);
		
		//내림차순
		intList.sort(Comparator.reverseOrder());
		System.out.println(intList);
		
		
		
		/* Map */
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1,"apple");
		map.put(2,"strawberry");
		map.put(3,"melon");
		map.put(4,"pear");
		map.put(5,"grape");
		map.put(6,"kiwi");
		
		//key 기준 오름차순
		System.out.println("======= key 기준 오름차순 ==========");
		List<Integer> keyList = new ArrayList<Integer>(map.keySet());
		Collections.sort(keyList);
		
		for (int key : keyList) {
			System.out.println(map.get(key));
		}
		
		
		//key 기준 내림차순
		System.out.println("======= key 기준 내림차순 ==========");
		keyList.sort(Collections.reverseOrder());
		for (int key : keyList) {
			System.out.println(map.get(key));
		}
		
		//value 기준 오름차순
		System.out.println("======= Value 기준 오름차순  (value: String) ==========");
		keyList.sort((o1,o2) -> map.get(o1).compareTo(map.get(o2)));
		for (int key : keyList) {
			System.out.println(map.get(key));
		}
		
		//value 기준 내림차순
		System.out.println("======= Value 기준 내림차순  (value: String)==========");
		keyList.sort((o1,o2) -> map.get(o2).compareTo(map.get(o1)));
		for (int key : keyList) {
			System.out.println(map.get(key));
		}
		
		Map<String,Integer> map2 = new HashMap<String, Integer>();
		map2.put("A", 120);
		map2.put("B", 80);
		map2.put("C", 5000);
		map2.put("D", 500);
		map2.put("E", 100);
		
		//value 기준 오름차순
		System.out.println("======= Value 기준 오름차순  (value: Integer) ==========");
		List<String> keyList2 = new ArrayList<String>(map2.keySet());
		keyList2.sort((o1,o2) -> map2.get(o1) - map2.get(o2));
		for (String key : keyList2) {
			System.out.println(map2.get(key));
		}
		
		//value 기준 내림차순
		System.out.println("======= Value 기준 내림차순  (value: Integer) ==========");
		keyList2.sort((o1,o2) -> map2.get(o2) - map2.get(o1));
		for (String key : keyList2) {
			System.out.println(map2.get(key));
		}
		
		
		
	}

}
