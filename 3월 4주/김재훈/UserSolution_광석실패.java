import java.util.*;

class UserSolution {
	
	int[] dy = {-1,1,0,0};
	int[] dx = {0,0,-1,1};
	
	int energy;
	int n;
	int[][] map;
	int[][] quantityMap;
	
	//그룹의 rootid, 해당 그룹의 광석 량
	Map<Integer, Integer> groupQuantity;
	
//	boolean[][] visited;
	//현재pos, 루트pos
	Map<Integer, Integer> parent;
	//위치, ID
	Map<Integer, Integer> baseCampPos_ID;
	//ID, 위치
	Map<Integer, Integer> baseCampID_Pos;
	
	int find(int pos) {
		if(parent.get(pos) == pos) return pos;
		
		int root = find(parent.get(pos));
	    parent.put(pos, root);
	    return root;
	}
	
	boolean union(int p1, int p2) {
		int root1 = find(p1);
		int root2 = find(p2);
		
		if(root1 == root2) return false;
		if(root1 < root2) {
			parent.put(root2, root1);
		}
		else parent.put(root1, root2);
		return true;
	}
	
	int genKey(int y, int x) {
		return y*15001 + x;
	}
	
	int[] backTopos(int key) {
		int y = key/15001;
		int x = key%15001;
		return new int[] {y, x};
	}
	
	void init(int L, int N){
		this.energy = L;
		this.n = N;
		this.parent = new HashMap<>();
		this.baseCampPos_ID = new HashMap<>();
		this.baseCampID_Pos = new HashMap<>();
		this.groupQuantity = new HashMap<>();
		
		this.map = new int[n][n];
		this.quantityMap = new int[n][n];
//		this.visited = new boolean[n][n];
	}
	
	int addBaseCamp(int mID, int mRow, int mCol, int mQuantity){
		
		//처음 떨어졌을 때, map에 본인이 갈수 있는 모든 곳을 먼저 표시
		//그럼 그 다음부터는 만약 표시가 되어 있다면 그 베이스랑 연결되었다는 의미
		
		//1. 현재 위치가 표시되어있는지
		if(map[mRow][mCol] != 0) {
			
			//1-2. 해당 표시 베이스 캠프 그룹에 합치기
			//정보 업데이트
			int curPos = genKey(mRow, mCol);
			baseCampPos_ID.put(curPos, mID);
			baseCampID_Pos.put(mID, curPos);
			quantityMap[mRow][mCol] = mQuantity;
			int prevId = map[mRow][mCol];
			//1-3.  map에 내 ID로 채우기 -> 이미 있어도 걍 합쳐
			fillMapMyId(mID, mRow, mCol);
			
			//4. prevId가 포함된 그룹의 광석 양을 구해야 함.
			Integer prevPos = baseCampID_Pos.get(prevId);
			int rootPos = find(prevPos);
			Integer rootId = baseCampPos_ID.get(rootPos);
			int groupQ = groupQuantity.get(rootId);
			//root 바꾸기
			if(curPos < rootPos) {
				parent.put(curPos, curPos);
				parent.put(rootPos, curPos);
				//이전꺼는 지워주기
				groupQuantity.remove(rootId);
				groupQuantity.put(mID, groupQ+ mQuantity);
			}
			else {
				parent.put(curPos, rootPos);
				groupQuantity.put(rootId, groupQ+ mQuantity);
			}
			
			
			
			
			return groupQ + mQuantity;
		}
		
		//2. 내가 그룹의 처음 
		//2-1. map에 내 영역 표시 -> 표시값은 mID
		fillMapMyId(mID, mRow, mCol);
		int pos = genKey(mRow, mCol);
		baseCampPos_ID.put(pos, mID);
		baseCampID_Pos.put(mID, pos);
		quantityMap[mRow][mCol] = mQuantity;
		parent.put(pos, pos);
		groupQuantity.put(mID, mQuantity);
		return mQuantity;
	}
	

	private void fillMapMyId(int mID, int mRow, int mCol) {
		// TODO Auto-generated method stub
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {mRow, mCol});
//		visited[mRow][mCol] = true;
		map[mRow][mCol] = mID;
		int dist = 0;
		while(dist < this.energy) {
			int size = q.size();
			while(size-- > 0) {
				int[] cur = q.poll();
				for(int i = 0;i<4;i++) {
					int y = cur[0] + dy[i];
					int x = cur[1] + dx[i];
					
					if(y < 0 || y >= n || x< 0 || x >= n || map[y][x] == mID) continue;
					
//					visited[y][x] = true;
					map[y][x] = mID;
					q.add(new int[] {y, x});
				}
			}
			dist++;
		}
		
	}


	int findBaseCampForDropping(int K){
		int answer = -1;
		for(int rootId : groupQuantity.keySet()) {
			int quantity= groupQuantity.get(rootId);
			if(quantity >= K) {
				if(answer == -1) {
					answer = rootId;
				}
				else {
					if(rootId < answer) answer = rootId;
				}
			}
			
		}
		
		
		return answer;
	}

}
