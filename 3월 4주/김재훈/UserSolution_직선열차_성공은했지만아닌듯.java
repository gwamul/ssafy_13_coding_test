import java.util.*;

class UserSolution {
	
	Set<Integer>[] station;
	int n;
	int k;
	Map<Integer, int[]> trainMap;
	
	public void init(int N, int K, int mId[], int sId[], int eId[], int mInterval[]) {
		this.n = N;
		this.k = K;
		this.trainMap = new HashMap<>();
		this.station = new HashSet[n+1];
		for(int i = 0;i<=n;i++) {
			station[i] = new HashSet<>();
		}
		
		for(int i = 0;i<k;i++) {
			for(int j = sId[i];j<=eId[i];j+=mInterval[i]) {
				station[j].add(mId[i]);
			}
			trainMap.put(mId[i], new int[] {sId[i], eId[i], mInterval[i]});
		}
		
		return;
	}

	public void add(int mId, int sId, int eId, int mInterval) {
		trainMap.put(mId, new int[] {sId, eId, mInterval});
		for(int j = sId;j<=eId;j+=mInterval) {
			station[j].add(mId);
		}
		
		return;
	}

	public void remove(int mId) {
		int[] info = trainMap.get(mId);
		
		for(int j = info[0];j<=info[1];j+=info[2]) {
			station[j].remove(mId);
		}

		return;
	}

	public int calculate(int sId, int eId) {
		
		
		//1 일단, 시작과 끝에 정차하는 기차가 있는지 확인
		if(station[sId].isEmpty() || station[eId].isEmpty()) return -1;
		
		Set<Integer> usedTrain = new HashSet<>();
		Set<Integer> nextTrain = new HashSet<>();
		
		int[] temp = new int[n+1];
		
		//2. 시작점에서 갈수있는 모든 곳 체크
		Iterator<Integer> iter = station[sId].iterator();
		while(iter.hasNext()) {
			int trainId = iter.next();
			if(usedTrain.contains(trainId)) continue;
			usedTrain.add(trainId);
			int[] spots = trainMap.get(trainId);
			for(int i = spots[0];i<=spots[1];i+=spots[2]) {
				temp[i] = 1;
				if(i == eId) return 0;
			}
		}
		
		//3. 목표점에서 갈수있는 모든 곳 체크
		iter = station[eId].iterator();
		while(iter.hasNext()) {
			int trainId = iter.next();
			if(usedTrain.contains(trainId)) continue;
			usedTrain.add(trainId);
			int[] spots = trainMap.get(trainId);
			for(int i = spots[0];i<=spots[1];i+=spots[2]) {
				if(temp[i] == 1) {
					return 1;
				}
				temp[i] = 2;
				nextTrain.addAll(station[i]);
			}
		}
		int cnt = 2;
		boolean none = true;
		Set<Integer> currentTrain = new HashSet<>(nextTrain);
		nextTrain.clear();
		//4. 그 외 환승 한번씩 추가해야함
		while(none) {
			iter = currentTrain.iterator();
			none = false;
			while(iter.hasNext()) {
				int trainId = iter.next();
				if(usedTrain.contains(trainId)) continue;
				usedTrain.add(trainId);
				int[] spots = trainMap.get(trainId);
				for(int i = spots[0];i<=spots[1];i+=spots[2]) {
					if(temp[i] == 1) {
						return cnt;
					}
					else if(temp[i] == 2) continue;
					temp[i] = 2;
					none = true;
					nextTrain.addAll(station[i]);
				}
			}
			cnt++;
			currentTrain = new HashSet<>(nextTrain);
		}
		
		
		
		
		return -1;
	}
}