package saffy_algo.SWEA.D6;

import java.util.*;

public class SWEA25293_직선열차_Main {
	
	
	
	int n, k;
	int train_no;
	boolean[] removedTrain; // 삭제된 열차 관리
	
	List<Integer>[] stopStation; // 각 기차가 정차하는 모든 역
	
	Map<Integer, List<Integer>> stationTrains; // 각 역을 지나는 모든 기차 번호
	Map<Integer, Integer> idToNo; // id 와 no 를 매핑, 순서 보장
	
	boolean[][] canMeet; // 각 열차가 만날 수 있는 열차인지 체크한다.
	
	
	public void init(int N, int K, int mId[], int sId[], int eId[], int mInterval[]) {
		n= N;
		k= K;
		
		removedTrain = new boolean[201];
		
		stopStation = new ArrayList[201];
		for(int i=0; i<=200; i++) stopStation[i] = new ArrayList<>();
		
		stationTrains = new HashMap<>();
		
		idToNo = new HashMap<>();
		train_no = 0;
		
		
		canMeet = new boolean[201][201]; 
		
		for(int i=0; i<k; i++) {
			idToNo.put(mId[i], train_no);
			int start = sId[i];
			int end = eId[i];
			int interval = mInterval[i];
			for(int j=start; j<=end; j+=interval) {
				stopStation[train_no].add(j);
				List<Integer> trainList = stationTrains.getOrDefault(j, new ArrayList<>());
				trainList.add(train_no);
				stationTrains.put(j, trainList);
			}
			train_no++;
		}
		
		for(int i=0; i<train_no; i++) {
			//0번 열차 부터 마지막 열차까지..
			List<Integer> stations = stopStation[i]; //i번 열차가 지나는 모든 역
			for(int station : stations) {
				//i 번 열차가 지나는 역
				List<Integer> trains = stationTrains.get(station);
				// 그 역을 지나는 모든 기차들
				for(int train : trains) {
					canMeet[i][train] = true;
				}
				
			}
		}
		// debug();
		return;
	}

	public void add(int mId, int sId, int eId, int mInterval) {
		idToNo.put(mId, train_no);
		for(int j=sId; j<=eId; j+=mInterval) {
			stopStation[train_no].add(j);
			List<Integer> trainList = stationTrains.getOrDefault(j, new ArrayList<>());
			
			for(int train : trainList) {
				canMeet[train_no][train] = true;
				canMeet[train][train_no] = true;
			}
			
			trainList.add(train_no);
			stationTrains.put(j, trainList);
		}
		train_no++;
		// debug();
		return;
	}
	
	private void debug() {
		System.out.println("-----------debug----");
		for(int i=0; i<train_no; i++) {
			for(int j=0; j<train_no; j++) {
				System.out.print(canMeet[i][j]+ " " );
			}
			System.out.println();
		}
		System.out.println("--------------------");
	}

	public void remove(int mId) {
		int tno = idToNo.get(mId);
		removedTrain[tno] = true;
		return;
	}

	public int calculate(int sId, int eId) {
		List<Integer> startTrains = stationTrains.get(sId);
	    List<Integer> endTrains = stationTrains.get(eId);
	    
	    if (startTrains == null || endTrains == null) {
	    	//System.out.println(-1);
	    	return -1;
	    }

	    Queue<int[]> q = new ArrayDeque<>();
	    int[] dist = new int[train_no]; 
	    Arrays.fill(dist, -1);

	    for (int train : startTrains) {
	        if (!removedTrain[train]) {
	            dist[train] = 0;
	            q.offer(new int[]{train, 0});
	        }
	    }

	    for (int startT : startTrains) {
	        if (removedTrain[startT]) continue;
	        for (int endT : endTrains) {
	            if (startT == endT) return 0; 
	        }
	    }

	    while (!q.isEmpty()) {
	        int[] curr = q.poll();
	        int curT = curr[0];
	        int cnt = curr[1];

	        for (int targetT : endTrains) {
	            if (curT == targetT) return cnt;
	        }

	        for (int nextT = 0; nextT < train_no; nextT++) {
	            if (canMeet[curT][nextT] && !removedTrain[nextT] && dist[nextT] == -1) {
	                dist[nextT] = cnt + 1;
	                q.offer(new int[]{nextT, cnt + 1});
	            }
	        }
	    }

	    return -1;
	}
	
	
	
}
