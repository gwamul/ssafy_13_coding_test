import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 완탐으로 되나?
 * 1. 각 섬마다 다른 모든 섬을 연결하는 최소 다리를 건설
 * 2. 비트마스크로 되는 경우와 안되는 경우 전부해서
 * 3. 마지막에 최소값 찾기
 * 
 * 섬의 개수가 6개 이하라 충분히 가능할 듯.
 * 
 * step1. 
 * 섬에 고유 번호 붙여서 구분
 * 
 * step2. 
 * 세로, 가로 한번씩 맵을 훑어서 가능한 모든 다리를 확인하고
 * 각 섬을 연결하는 최소 길이의 다리 하나만 남기기
 * 
 * 이때, 각 다리는 Map으로 저장, key값은 8*작은 섬 번호 + 큰섬 번호 이렇게 해서 최소를 저장
 * Set으로 이은 섬 번호 유지
 * 
 * step3.
 * 모든 섬을 연결 불가능 한 경우 찾기
 * step2에서 유지한 set의 크기랑 섬의 개수인 identity-1 과 다르다면 안되는 경우이므로 -1
 * 
 * -- 여기까지 시간복잡도 해봐야 n*n*3 정도라 무시 가능
 * 
 * step4. 
 * 완탐으로 현재 다리중에 모든 걸 잇는 최소 구하기
 * 
 * 전체 다리에서 하나씩 빼서 가능한지 확인
 * -> 어떻게 확인? union find
 * 
 * 
 * 후기 : 힘들었다... 많은 개념을 사용하게한 좋은 문제
 * 
 * */

public class Main {
	
	static int n;
	static int m;
	static int[][] map;
	
	static int[] dy = {0,1,0,-1};
	static int[] dx = {1,0,-1,0};
	
	static Map<Integer, Integer> hmap;
	static Set<Integer> set;
	static int min = Integer.MAX_VALUE;
	static int identity;

	static int[] parent;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] nm = br.readLine().trim().split(" ");
		n = Integer.parseInt(nm[0]);
		m = Integer.parseInt(nm[1]);
		
		map = new int[n][m];
		hmap = new HashMap<>();
		set = new HashSet<>();
		
		for(int i = 0;i<n;i++) {
			String[] line = br.readLine().trim().split(" ");
			for(int j = 0;j<m;j++) {
				map[i][j] = Integer.parseInt(line[j]);
			}
		}
		//섬 고유 번호로 교체
		//섬은 2번부터 시작하게 됨.
		identity = setIslandIdentity();
		//print();
		
		//2. 가능한 모든 다리 찾기
		findAllBridge();
		Set<Integer> keySet = hmap.keySet();
		List<int[]> bridges = new ArrayList<>();
		for(int key : keySet) {
			int small = key/8;
			int big = key%8;
			set.add(small);
			set.add(big);
			bridges.add(new int[] {small, big, hmap.get(key)});
		}
		//모든 섬에 다리가 놓여있지 않다면 
		if(set.size() != identity-1) {
			System.out.println(-1);
			return;
		}
		//연결 되어 있는지 확인 용
		parent =new int[identity+1];
		unionInit();
		//그냥 하나씩 없애보면서 가능한 경우 탐색
		//백트래킹
		recur(bridges);
		
		if(min == Integer.MAX_VALUE) min = -1;
		System.out.println(min);
	}
	
	static void unionInit() {
		for(int i = 2;i<identity+1;i++) {
			parent[i] = i;
		}
	}
	
	static int find(int x) {
		if(parent[x] == x) return x;
		return parent[x] = find(parent[x]);
	}
	
	static void union(int x1, int x2) {
		int rx1 = find(x1);
		int rx2 = find(x2);
		if(rx1 == rx2) return;
		
		if(rx1 < rx2) parent[rx2] = rx1;
		else parent[rx1] = rx2;
	}
	
	private static void recur(List<int[]> bridges) {
		unionInit();
		// TODO Auto-generated method stub
		if(bridges.isEmpty()) return;
		
		int distance = 0;
		for(int[] cur : bridges) {
			union(cur[0], cur[1]);
			
			//다리 길이 더해주기
			distance += cur[2];
		}
		//모든 섬 연결 확인
		//서로의 부모가 다르면 모든 섬이 연결 안됨
		for(int i =2;i<identity;i++) {
			if(find(i) != find(i+1)) return;
		}
		
		
		if(min > distance) min = distance;
//		System.out.println("=========================================================");
//		for(int[] cur : bridges) {
//			System.out.println("작은 섬 : "+cur[0]+", 큰 섬 : "+cur[1]+", 거리 : "+cur[2]);
//		}
//		System.out.println(Arrays.toString(parent));
//		System.out.println("현재 최소길이 : "+min);
		
		for(int i = 0;i<bridges.size();i++) {
			int[] pick = bridges.remove(i);
			recur(bridges);
			bridges.add(i, pick);
		}
	}

	private static void findAllBridge() {
		// 1. 가로로 모든 다리 찾기
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				if(j-1 >= 0) {
					//왼쪽이 섬이고 현재가 바다일 경우
					if(map[i][j-1] > 1 && map[i][j] == 0) {
						int island1 = map[i][j-1];
						int island2 = 0;
						int cnt = 0;
						for(int k = j;k<m;k++) {
							if(map[i][k] == 0) {
								cnt++;
							}
							else if(map[i][k] > 1) {
								island2 = map[i][k];
								j = k;
								break;
							}
						}
						//만약 섬을 못찾고 지도 끝까지 간 경우
						if(island2 == 0) break;
						if(island1 == island2) continue;
						//다리 길이는 2 이상이어야 함
						if(cnt <2) continue;

						int small = Math.min(island1, island2);
						int big = Math.max(island1, island2);
						int key = small*8 + big;
						
						//이미 연결된게 있다면 가장 작은 걸로 선정
						if(hmap.containsKey(key)) {
							if(cnt < hmap.get(key)) hmap.put(key, cnt);
						}
						else hmap.put(key, cnt);
					}
				}
			}
		}
		
		// 1. 세로로 모든 다리 찾기
		for(int i = 0;i<m;i++) {
			for(int j = 0;j<n;j++) {
				if(j-1 >= 0) {
					//왼쪽이 섬이고 현재가 바다일 경우
					if(map[j-1][i] > 1 && map[j][i] == 0) {
						int island1 = map[j-1][i];
						int island2 = 0;
						int cnt = 0;
						for(int k = j;k<n;k++) {
							if(map[k][i] == 0) {
								cnt++;
							}
							else if(map[k][i] > 1) {
								island2 = map[k][i];
								j = k;
								break;
							}
						}
						//만약 섬을 못찾고 지도 끝까지 간 경우
						if(island2 == 0) break;
						if(island1 == island2) continue;
						if(cnt <2) continue;
						int small = Math.min(island1, island2);
						int big = Math.max(island1, island2);
						int key = small*8 + big;
						//이미 연결된게 있다면 가장 작은 걸로 선정
						if(hmap.containsKey(key)) {
							if(cnt < hmap.get(key)) hmap.put(key, cnt);
						}
						else hmap.put(key, cnt);
					}
				}
			}
		}
		
	}

	static void print() {
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static int setIslandIdentity() {
		// TODO Auto-generated method stub
		int identity = 1;
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				if(map[i][j] == 1) {
					//bfs로 돌면서 identity값으로 채우기
					identity++;
					fillIdentity(identity,i,j);
				}
			}
		}
		return identity;
	}

	private static void fillIdentity(int identity, int y, int x) {
		// TODO Auto-generated method stub
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {y, x});
		map[y][x] = identity;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i = 0;i<4;i++) {
				int ny = cur[0] + dy[i];
				int nx = cur[1] + dx[i];
				
				if(ny < 0 || ny >= n || nx < 0 || nx >=m) continue;
				if(map[ny][nx] == 1) {
					map[ny][nx] = identity;
					q.add(new int[] {ny, nx});
				}
			}
		}
	}

}





















