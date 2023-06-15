import java.util.List;

public class Woker extends AbstractWorker {
	
	/*
	 * Worker 생성
	 * - <Queue 번호>를 파라미터로 하여 Worker 인스턴스 생성 
	 */
	public Worker (int queueNo) {
		super(queueNo);
	}
	
	/*
	 * 만료된 Store Item 제거
	 * - 입력된 Timestamp와 Store Item의 Timestamp간의 차이가 만료시간(3000)을 초과하면 Store에서 제거
	 */
	public void removeExpiredStoreItem(long timestamp, List<String> store) {
		// 아래 라인을 지우고 만료된 Store Item 제거 기능을 구현하세요.
		// throw new UnsupportedOperationException("removeExpiredStoreItems()를 2번 문항에서 복사하세요.");
		for (int i=0; i < store.size(); i++) {
			long ts = Long.parseLong(store.get(i).split("#")[0]);
			if(timestamp - ts > 3000) {
				store.remove(i);
				i = i - 1;
			}
		}
	}

}
