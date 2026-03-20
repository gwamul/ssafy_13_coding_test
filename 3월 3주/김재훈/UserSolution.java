import java.util.*;

/**
 * 일단, 다익스트라로 최단거리를 구한다음에 그 간선중 하나를 택해야 하는 건 맞다. 
 * 
 * 그
 */
class UserSolution {
	Map<Integer, int[]> lines;
	//List의 int[]는 {이어진 정점, 도로 id}
	Map<Integer, List<int[]>> vertexs;
	
	Set<Integer> idSet = new HashSet<>();
	
	int n;
	int k;
	
	int[][] map;
	
	public void init(int N, int K, int mId[], int sCity[], int eCity[], int mTime[]) {
		lines = new HashMap<>();
		vertexs = new HashMap<>();
		this.n = N;
		this.k = K;
		this.map = new int[n][n];
		for(int i = 0;i<k;i++) {
			// 간선 정보 저장
//			List<int[]> tmp =  new ArrayList<>();
//			tmp.add();
			lines.put(mId[i],new int[] {sCity[i], eCity[i], mTime[i]});
			
			//정점에서 출발하는 라인 저장
			List<int[]> list = vertexs.getOrDefault(sCity[i], new ArrayList<>());
			list.add(new int[] {eCity[i], mId[i]});
			vertexs.put(sCity[i], list);
			
			//간선 ID관리
			idSet.add(mId[i]);
			
			map[sCity[i]][eCity[i]] = mTime[i];
		}
		printmap();
		System.out.println("vertexs 초기화 후 :  =====================");
		Iterator<Integer> iter = vertexs.keySet().iterator();
		while(iter.hasNext()) {
			int next = iter.next();
			System.out.print("현재 정점 : "+ next+" ");
			List<int[]> list = vertexs.get(next);
			list.forEach(e -> System.out.print(Arrays.toString(e) + " "));
			System.out.println();
		}
		
		return;
	}
	
	public void printmap() {
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void add(int mId, int sCity, int eCity, int mTime) {
		// 간선 정보 저장
//		List<int[]> tmp =  new ArrayList<>();
//		tmp.add();
		lines.put(mId,new int[] {sCity, eCity, mTime});
		
		//정점에서 출발하는 라인 저장
		List<int[]> list = vertexs.getOrDefault(sCity, new ArrayList<>());
		list.add(new int[] {eCity, mId});
		vertexs.put(sCity, list);
		
		//간선 ID관리
		idSet.add(mId);
		this.k++;
		return;
	}

	public void remove(int mId) {
		
		lines.remove(mId);
		idSet.remove(mId);
		//vertexs에서는 cal하면서 set으로 확인 후 지워주기
		this.k--;
		return;
	}

	public int calculate(int sCity, int eCity) {
		
		//1. 최단 경로 구하기
		int[] dist = new int[n];
		int[] paths = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[sCity] = 0;
		paths[sCity] = sCity;
		
		//dist랑, paths 채우기
		dijkstra(sCity, eCity, dist, paths);
		
		System.out.println("dist : "+ Arrays.toString(dist));
		System.out.println("paths : "+Arrays.toString(paths));
		
		if(dist[eCity] == Integer.MAX_VALUE) return -1;
		
		int prevMinTime = dist[eCity];
		
		//경로 구하기
		List<Integer> pathList = new ArrayList<>();
		pathList.add(eCity);
		int prev = paths[eCity];
		while(true) {
			pathList.add(prev);
			if(prev == sCity) break;
			prev = paths[prev];
		}
		
		//좌우 반전해서 시작부터 끝까지
		pathList = pathList.reversed();
		System.out.println("경로 출력");
		pathList.forEach(e -> System.out.print(e+" "));
		
		return 0;
	}

	private void dijkstra(int sCity, int eCity, int[] dist, int[] paths) {
		
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1,o2) -> {
			return o1[1] - o2[1];
		});
		//현재 정점, dist값
		pq.add(new int[] {sCity, 0});
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int curV = cur[0];
			int distValue = cur[1];
			
			if(dist[curV] < distValue) continue;
			
			
			List<int[]> list = vertexs.get(cur[0]);
			
			List<int[]> haveToremove = new ArrayList<>();
			if(list == null) continue;
			System.out.println("curV : "+ curV+", list의 크기 : "+list.size());
			for(int[] next : list) {
				//없는 도로면 넘기고
				if(!idSet.contains(next[1])) {
					haveToremove.add(next);
					continue;
				}
				
				int nextV = next[0];
				int roadId = next[1];
				
				int[] roadInfo = lines.get(roadId);
				
				//있는 도로인데 
				if(dist[nextV] > dist[curV] + roadInfo[2]) {
					dist[nextV] = dist[curV] + roadInfo[2];
					paths[nextV] = curV;
					pq.add(new int[] {nextV, dist[nextV]});
				}
				
				
			}
			
			//지워진 도로 지우기
			for(int[] v : haveToremove) {
				list.remove(v);
			}
			
			
		}
		
	}
}







