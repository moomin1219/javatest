package exercise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

public class HashMapTest {

	public static void main(String[] args) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		map.put("apple", 1);
		map.put("strawberry", 2);
		map.put("melon", 3);
		map.put("pear", 4);
		map.put("grape", 5);
		map.put("kiwi", 5);

		// 특정 key에 대한 value 출력
		System.out.println(map.get("melon"));

		// 모든 value 출력 (1)
		System.out.println(map.values());

		// 모든 value 출력 (2)
		Collection<Integer> values = map.values();
		for (Integer value : values) {
			System.out.println(value);
		}

		// 특정 value의 key 값
		System.out.println(getKey(1, map));

		// 특정 value의 모든 key 값 (value 중복 가능하므로)
		System.out.println(getKeys(5, map));

		// 모든 key 값 출력 (1)
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println("KEY : " + key);
		}

		// 모든 key 값 출력 (2)
		for (String key : map.keySet()) {
			System.out.println("키 : " + key);
		}

		// 모든 key, value 값 출력
		Iterator<HashMap.Entry<String, Integer>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Integer> entry = (Entry<String, Integer>) entries.next();
			System.out.println("key : " + entry.getKey() + " , value : " + entry.getValue());
		}

		// 순서 유지되는 LinkedHashMap
		LinkedHashMap<String, Integer> map2 = new LinkedHashMap<String, Integer>();

		map2.put("apple", 1);
		map2.put("strawberry", 2);
		map2.put("melon", 3);
		map2.put("pear", 4);
		map2.put("grape", 5);
		map2.put("kiwi", 5);

		// 모든 key, value 값 출력
		System.out.println("=============================================");
		map2.forEach((key, value) -> System.out.println("key : " + key + ", value : " + value));

	}

	private static String getKey(Integer value, HashMap<String, Integer> map) {
		for (String key : map.keySet()) {
			if (Objects.equals(map.get(key), value)) {
				return key; // return the first found
			}
		}
		return null;
	}

	private static List<String> getKeys(Integer value, HashMap<String, Integer> map) {
		List<String> keys = new ArrayList<String>();
		for (String key : map.keySet()) {
			if (Objects.equals(map.get(key), value)) {
				keys.add(key);
			}
		}
		return keys;
	}

}
