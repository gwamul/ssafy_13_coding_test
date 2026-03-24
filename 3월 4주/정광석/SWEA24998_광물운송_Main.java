package saffy_algo.SWEA.D6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class SWEA24998_광물운송_Main {
	
	/*
	 * 섬에 특수 광물이 발견됨
	 * 
	 * 행 열
	 * 
	 * 
	 * 광물이 발견된 위치마다 베이스 캠프를 설치하겠다.
	 * 
	 * 구분을 위한 ID, 한번에 채굴 가능한 광물의 채굴량 존재
	 * 
	 * 한번에 광물의 채굴량 만큼만 채굴 가능
	 * 
	 * 비행기를 이용, 이 섬 내 베이스 캠프 중 한 곳에 운송 차량 떨어뜨려 광물 수집하게 하고, 
	 * 다시 비행기에 실어 광물 가공 공장에 보내려 한ㄷ
	 * 
	 * 
	 * 차량 - 상하좌우 이동
	 * 
	 * 초기 에너지 L 만큼 가짐
	 * 한칸당 1 감소
	 * 에너지 0되면 이동 멈춤
	 * 
	 * 
	 * 에너지 충전소(베이스 캠프) 차량 도착시 다시 L 로 충전된다.
	 * 
	 * 
	 * 정부에서 k 개 이상의 광물을 가져오라고 함.
	 * 
	 * k 이상 과물을 수집할 수 있는 베캠 위치 찾고, 거기에 차량을 떨궈라.
	 * 
	 * 해당 되는 베캠이 2개 이상이면..
	 * 
	 * 채굴하는 광물의 양이 작은 베ㅐㅁ -> 행 번호 작은 -> 열번호 작은
	 *
	 * 베캠은 20000개. 배켐 그룹(도달 가능한)은 그 그룹이 가질 수 있는 최대 채굴량과, 정렬이 되어있어야 한다.
	 * 베캠 마다 구조체 형태로 모두 관리되어야 한다. 
	 * 베캠 그룹은 베캠 하나가 add되면, 합쳐질 수 있다.
	 * 합쳐진다면 베캠도 다 다시 정렬되야 하는데...
	 * 베캠 채굴량 기준으로 큐를 빼내서 새로 정렬?
	 * 
	 * 
	 * 그룹은 어떻게 만들어야 하는가?
	 * L 안에 도달한 모든 노드들은 다 연결 가능하다.
	 * 다익스트라.
	 * 
	 * 
	 * 
	 * 
	 * 그룹간 합체는 어떻게 구현하나?  union find
	 * 
	 * 하나의 그룹에 들어이다면 500회 호출에 대해서 하나의 그룹 집합을 만들어서 다 비교해도 될 것 같기도.
	 * 
	 * parents 를 순회하면서 그룹의 개수를 파악하여
	 * 그룹 리스트 생성 많아봐야 20000개.
	 * 
	 * 20000개 중 최고 우선순위인 애를 뽑는 경우 -> 20000
	 * '
	 * 
	 * 
	 * 새로 캠프가 추가될 때 마다 모든 다른 노드와 의 거리를 연산해서 board에 넣어놔야 한다.
	 * 이때 기존 캠프에는 새로 생성된 노드 까지 거리가 add
	 * 새로 생성된 노드에는 모든 노드와의 거ㅣㄹ
	 */
	
	int l;
	int n;
	
	class Bucket{
		List<Integer> idxs;

		public Bucket() {
			super();
			this.idxs = new ArrayList<>();
		}
		
	}
	
	
	class Camp{
		int id;
		int x,y;
		int energy;
		public Camp(int id, int x, int y, int energy) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
			this.energy = energy;
		}
		
		
	}
	
	int[] dx = {-1,-1,-1,0,0,1,1,1,0};
	int[] dy = {-1,0,1,1,-1,-1,0,1,0};
	
	int[] parents;
	int[] groupSum;
	int[] rank;
	int camp_cnt;
	Camp[] camps;
	
	Map<Integer, Integer> campIdx;
	
	List<int[]>[] board;
	
	Bucket[][] grid;
	
	public int find(int a) {
        if(a == parents[a]) return a;
        return parents[a] = find(parents[a]);
    }
	
	private void union(int a, int b) {
		int fa = find(a);
		int fb = find(b);
		
		if (fa != fb) {
	        if (rank[fa] < rank[fb]) {
	            parents[fa] = fb;
	            groupSum[fb] += groupSum[fa];
	        } else if (rank[fa] > rank[fb]) {
	            parents[fb] = fa;
	            groupSum[fa] += groupSum[fb];
	        } else {
	            // 높이가 같다면 아무나 부모가 되고, 부모가 된 쪽의 rank를 1 증가
	            parents[fa] = fb;
	            rank[fb]++;
	            groupSum[fb] += groupSum[fa];
	        }
	    }
		
	}
	
	
	void init(int L, int N){
		// L : 차량 에너지 , N : 한변 길이 L*30 이하. = 9 ~ 15000
		this.l = L;
		this.n = N;
		camp_cnt = 0;
		
		camps = new Camp[20001];
		rank = new int[20001];
		parents = new int[20001];
		groupSum = new int[20001];
		for(int i=0; i<=20000; i++) {
			parents[i] = i;
			groupSum[i] = 0;
			rank[i] = 0;
		}
		campIdx = new HashMap<>();
		
		
		
		
		grid = new Bucket[N/L+1][N/L+1];
		for(int i=0; i<(N/L+1); i++) {
			for(int j=0; j<(N/L+1); j++) {
				grid[i][j] = new Bucket();
			}
		}
		
		board = new ArrayList[20001];
		for(int i=0; i<=20000; i++) board[i] = new ArrayList<>();
	}
	
	int addBaseCamp(int mID, int mRow, int mCol, int mQuantity){
	    Camp base = new Camp(mID, mRow, mCol, mQuantity);
	    camps[camp_cnt] = base;
	    
	    groupSum[camp_cnt] = mQuantity; 
	    
	    
	    int gx = mRow / l;
	    int gy = mCol / l;
	    grid[gx][gy].idxs.add(camp_cnt);
	    
	    
	    
	    
	    for(int i=0; i<9; i++) {
	    	int nx= gx + dx[i];
	    	int ny = gy + dy[i];
	    	if(0<=nx && nx < (n/l+1) && 0<=ny && ny<(n/l+1)) {
	    		for(int a : grid[nx][ny].idxs) {
	    			int dist = getDist(base, camps[a]);
	    			if(dist<=l) union(a, camp_cnt);
	    		}
	    	}
	    }
	    
	    
	    
	    //이 부분이 너무 오래 걸리는 듯.
//	    for(int i=0; i<camp_cnt; i++) {
//	        int dist = getDist(base, camps[i]);
//	        if(dist <= l) {
//	            union(i, camp_cnt);
//	        }
//	    }
	    
	    campIdx.put(camp_cnt, mID);
	    
	    int root = find(camp_cnt); 
	    int max_Quantity = groupSum[root];
	    
	    camp_cnt++;
	    
	    return max_Quantity;
	}
	
	int findBaseCampForDropping(int K){
		//차량 떨어뜨렸을 대 k이상을 모아올 수 있는 베캠을 찾고, 우선순위에 맞게 베캠 id를 리턴한다.
		//500
		// 20000 * 500 -> 1000 0000
		int minRow = Integer.MAX_VALUE;
	    int minCol = Integer.MAX_VALUE;
	    int minEnergy = Integer.MAX_VALUE;
	    int tm = -1;
		for(int i=0; i<camp_cnt; i++) {
	        int root = find(i);
	        
	        if(groupSum[root] >= K) {
	            Camp c = camps[i];
	            
	            if (c.energy < minEnergy) {
	                minEnergy = c.energy;
	                minRow = c.x;
	                minCol = c.y;
	                tm = i;
	            } 
	            else if (c.energy == minEnergy) {
	                if (c.x < minRow) {
	                    minRow = c.x;
	                    minCol = c.y;
	                    tm = i;
	                } 
	                else if (c.x == minRow && c.y < minCol) {
	                    minCol = c.y;
	                    tm = i;
	                }
	            }
	        }
	    }
	    
	    if (tm == -1) {
	        return -1; 
	    }
	    return campIdx.get(tm);
	}
	
	int getDist(Camp c1 , Camp c2) {
		return Math.abs(c1.x-c2.x) + Math.abs(c1.y-c2.y);
	}
	
	int[] dij(int startIdx) {
		int[] dist = new int[camp_cnt];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[startIdx] = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1]-o2[1]);
		pq.offer(new int[] {startIdx, 0});
		
		while(!pq.isEmpty()) {
			int[] temp = pq.poll();
			int cur = temp[0];
			int d = temp[1];
			
			if(d > dist[cur]) continue;
			//if(d > l) continue;
			
			for(int[] next : board[cur]) {
				
				if(next[1] > l ) continue;
				
				if(next[1] + dist[cur] < dist[next[0]]) {
					dist[next[0]] = next[1] + dist[cur];
					pq.offer(new int[] {next[0] , dist[next[0]]});
				}
			}
		}
		
		return dist;
	}
	
}
