package exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MultiSortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<User> users = getUsers();
		
		//name 오름차순
		Comparator<User> com1 = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getName().compareTo(o2.getName());
			}
			
		};
		
		Collections.sort(users, com1);
		System.out.println("name 오름차순");
		for(User user : users) {
			System.out.println(user.getName() + ", " + user.getGrade() + ", " + user.getNum());
		}
		
		
		//name 내림차순
		Comparator<User> com2 = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o2.getName().compareTo(o1.getName());
			}
			
		};
		
		Collections.sort(users, com2);
		System.out.println("name 내림차순");
		for(User user : users) {
			System.out.println(user.getName() + ", " + user.getGrade() + ", " + user.getNum());
		}
		
		
		//num 오름차순
		Comparator<User> com3 = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getNum() - o2.getNum();
			}
			
		};
		
		Collections.sort(users, com3);
		System.out.println("num 오름차순");
		for(User user : users) {
			System.out.println(user.getName() + ", " + user.getGrade() + ", " + user.getNum());
		}
		
		//num 내림차순
		Comparator<User> com4 = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o2.getNum() - o1.getNum();
			}
			
		};
		
		Collections.sort(users, com4);
		System.out.println("num 내림차순");
		for(User user : users) {
			System.out.println(user.getName() + ", " + user.getGrade() + ", " + user.getNum());
		}
		
		
		//grade 내림차순 + name 오름차순
		Comparator<User> com5 = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				if(o2.getGrade() > o1.getGrade()) {
					return 1;
				} else if(o2.getGrade() == o1.getGrade()) {
					return o1.getName().compareTo(o2.getName());
				} else if (o2.getGrade() < o1.getGrade()) {
					return -1;
				} else {
					return 0;
				}
			}
			
		};
		
		Collections.sort(users, com5);
		System.out.println("grade 내림차순 + name 오름차순");
		for(User user : users) {
			System.out.println(user.getName() + ", " + user.getGrade() + ", " + user.getNum());
		}
		
			
		
		
		

	}
	
	private static List<User> getUsers(){
		List<User> userList = new ArrayList<User>();
		
		User user1 = new User("나", 50, 1);
		User user2 = new User("라", 100, 2);
		User user3 = new User("가", 70, 3);
		User user4 = new User("바", 100, 4);
		User user5 = new User("하", 50, 5);

		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		userList.add(user4);
		userList.add(user5);
		
		return userList;
		
		
	}
	
	public static class User {
		private String name;
		private int grade;
		private int num;
		
		public User(String name, int grade, int num) {
			super();
			this.name = name;
			this.grade = grade;
			this.num = num;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getGrade() {
			return grade;
		}
		public void setGrade(int grade) {
			this.grade = grade;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		
	}

}
