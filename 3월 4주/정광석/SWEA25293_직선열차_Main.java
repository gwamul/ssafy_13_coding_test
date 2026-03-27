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
<<<<<<< HEAD

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
=======
	/*
	 * 
	 * 어떤 스테이션에는 지나치는 기차가 많이 있을 것 
	 * 그 모두를 queue에 넣어야 한다.
	 * 
	 * 어떤 역에서 시작한다 - 그 역을 지나는 모든 기차가 큐에 자기가 갈 수 있는 모든 역을 집어 넣는다.
	 * 큐에서 역을 뺀다. 그 역에서 
	 * 
	 */

	public int calculate(int sId, int eId) {
		// 50회
		// 최소 환승 횟수를 반환한다.
		// 환승 할 필요가 없으면 0을 반환한다.
		// 갈수 있는 방법이 없으면 -1을 반환한다.
		Deque<int[]> q = new ArrayDeque<>();
		boolean[] visited = new boolean[n+1];
		boolean[] rided = new boolean[train_cnt];
		
		for(int a : stations[sId]) {
			System.out.println(a);
			//a는 역 안에 기차들의 cnt 번호.
			Train t = trains[a]; 
			for(int i=t.start; i<=t.end; i+=t.interval) {
				q.addFirst(null);
			}
		}
		
		return -1;
>>>>>>> 47de6dd3c876df5ba5485f318c26d55d62a796bc
	}
	
	
	
}
