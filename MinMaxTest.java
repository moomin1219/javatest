public class MinMax {

	public static void main(String[] args) {
		
		int[] array = { 123, 5678, 456, 12, 345};
		
		//최대값 구하기
		int maxNum = Integer.MIN_VALUE;
		for (int i=0; i < array.length; i++) {
			if(array[i] > maxNum) {
				maxNum = array[i];
			}
		}
		
		//최소값 구하기
		int minNum = Integer.MAX_VALUE;
		for (int i=0; i < array.length; i++) {
			if(array[i] < minNum) {
				minNum = array[i];
			}
		}

		System.out.println("최대값: " + maxNum);
		System.out.println("최소값: " + minNum);
	}

}
