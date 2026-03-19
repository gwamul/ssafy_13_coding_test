package saffy_algo.SWEA.D6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/*
 * 
 * N개의 도시가 주어진다.
 * 
 * 0~N-1 까지의 ID를 가진다.
 * 
 * 단방향 도로 -> 도로 ID와 소요시간 주어짐
 * 
 * 적 군대는 항상 `최단 경로`로 이동
 * 
 * 적군의 출발지 , 목적지가 주어지면 - 하나의 도로를 파괴해서 적군의 이동을 지연시킬 수 있는 최대 시간을 구하고 싶다.
 * 
 * 
 * 도시 개수 1000
 * 도로 개수 5000
 * 
 * 단방향 도로임
 * 
 * 다익스트라 구현해서 최단 시간 구하는건 거의 확정.
 * 경로 5000개가 있는데, 어떻게 거기서 가장 치명적인 경로를 찾을 수 있나
 * 하나씩 돌려보면서 5000 * O(dijk) 하는게 맞나?
 * 하나 지우고 a -> b 경로 계산?
 * 
 * 다익스트라에서 from - to 에 영향을 주는 곳 제외하고 inf 인 곳들은 무시해도 된다
 * 
 * 생각보다 횟수가 적을수도 있음.
 * 
 * 그래프를 잇는 방법
 * board 인접 리스트로 관리한다.
 * 
 * 근데 각 Road를 어떻게 관리하나?
 * 그냥 해시맵으로 mId - road 연결
 */




public class SWEA25938_도로파괴_Main {


	class Road{
		int id;
		int from;
		int to;
		int weight;
		boolean destroyed = false;
		public Road(int id, int from, int to, int weight) {
			super();
			this.id = id;
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		@Override
		public String toString() {
			return "Road [id=" + id + ", from=" + from + ", to=" + to + ", weight=" + weight + ", destroyed="
					+ destroyed + "]";
		}
		
		
	}
	int n;
	int k;

	List<Road>[] board;
	Map<Integer, Road> roadMap;
	int[] parentEdge;
	int[] parentNode;

	public void init(int N, int K, int mId[], int sCity[], int eCity[], int mTime[]) {
		n = N;
		k = K;
		//parentEdge = new int[N];
		//  parentNode = new int[N];
		board = new ArrayList[n];
		for(int i=0; i<n; i++) board[i] = new ArrayList<>();
		roadMap = new HashMap<>();

		for(int i=0; i<k; i++) {
			Road r = new Road(mId[i], sCity[i], eCity[i], mTime[i]);
			board[sCity[i]].add(r);
			roadMap.put(mId[i], r);
		}
	}

	public void add(int mId, int sCity, int eCity, int mTime) {
		//System.out.println("add: " + mId+"번 도로 from: " + sCity + " to: " + eCity + " cost: " + mTime );
		Road r = new Road(mId, sCity, eCity, mTime);
		board[sCity].add(r);
		roadMap.put(mId, r);
	}

	public void remove(int mId) {
		//도로 제거 -> 그래프에서 맘에 안드는거 
		//500
		//System.out.println("remove: " + mId + "번 도로 제거");
		Road r = roadMap.get(mId);
		r.destroyed = true;
		return;
	}

	public int calculate(int sCity, int eCity) {
		//도로 제거해서 지연시킬 수 있는 최대 시간 반환
		//원래 이동이 불가능했거나, 도로 파괴 후 이동이 불가능하면 return -1
		//200
		//적군이 최단 
		parentEdge = new int[n];
		parentNode = new int[n];
		int[] enemy = dijkstra(sCity, eCity);
		//System.out.println("enemy: " +Arrays.toString(enemy));
		//System.out.println(Arrays.toString(parentEdge));
		//System.out.println(Arrays.toString(parentNode));
		if (enemy[eCity] == Integer.MAX_VALUE) {
	        return -1;
	    }
		List<Integer> shortestPathEdges = new ArrayList<>();
		int curr = eCity;
		
		while (curr != sCity) {
			if(curr == -1) break;
			int usedEdgeId = parentEdge[curr];
			shortestPathEdges.add(usedEdgeId);
			curr = parentNode[curr];
		}
		int maxDelay = 0;

		for (int edgeIdToDestroy : shortestPathEdges) {
			if(edgeIdToDestroy == -1) continue;
			roadMap.get(edgeIdToDestroy).destroyed = true;
			//System.out.println(roadMap.get(edgeIdToDestroy));
			int[] dist = dijkstra(sCity, eCity);
			//System.out.println(Arrays.toString(dist));
			int newTime = dist[eCity];
			if (newTime == Integer.MAX_VALUE) {
			//	System.out.println(-1);
				roadMap.get(edgeIdToDestroy).destroyed = false;
				return -1;
			}

			maxDelay = Math.max(maxDelay, newTime - enemy[eCity]);
			roadMap.get(edgeIdToDestroy).destroyed = false;

		}
		//System.out.println(maxDelay);
		return maxDelay;
		
	}


	private int[] dijkstra(int start, int to) {

		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
		int[] dist = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(parentEdge, -1); // 경로 추적 배열 초기화
		Arrays.fill(parentNode, -1);
		dist[start] = 0;
		pq.offer(new int[] {start, 0});
		while(!pq.isEmpty()) {
			int[] temp = pq.poll();
			int cur = temp[0];
			int d = temp[1];
			if(cur == to) {
				return dist;
			}
			if(d > dist[cur]) continue;

			for(Road r : board[cur]) {
				if(r.destroyed) continue;

				int next = r.to;
				int nd = d + r.weight;

				if(nd < dist[next]) {
					dist[next] = nd;
					parentEdge[next] = r.id;
					parentNode[next] = cur;

					pq.offer(new int[] {next, nd});
				}
			}
		}






		return dist;
	}
}
