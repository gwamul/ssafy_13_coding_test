import java.io.*;
import java.util.*;

/**
 * 한번 bfs에 2500
 * 
 * 모든 로봇을 하면 125,000
 * 
 * 전체 테스트 : 6,250,000
 * 시간은 충분
 */

public class Main{

	static int n;
	static int k;
	static int l;
	
	static int[][] map;
	//static boolean[][]
	static List<int[]> robots;
	static Set<Integer> robotSet;
	
	static int[] dy = {-1,1,0,0};
	static int[] dx = {0,0,-1,1};
	
	//오른쪽, 아래쪽, 왼쪽, 위쪽 순서
	static int[][] ddy = {{0,0,-1,1},{0,1,0,0},{0,0,-1,1},{0,-1,0,0}};
	static int[][] ddx = {{0,1,0,0},{0,0,-1,1},{0,-1,0,0},{0,0,-1,1}};
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		robots = new ArrayList<>();
		robotSet = new HashSet<>();
		map = new int[n][n];
		for(int i = 0;i<n;i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0;j<n;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 0;i<k;i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int y = Integer.parseInt(st.nextToken())-1;
			int x = Integer.parseInt(st.nextToken())-1;
			robots.add(new int[] {y, x});
			int key = y*51 + x;
			robotSet.add(key);
		}
		
		//테스트 과정
		for(int t = 0;t<l;t++) {
			
			//1. 각 로봇이 순서대로 이동. -> list에 저장된 순서가 망가지면 안됨.
			for(int i = 0;i<robots.size();i++) {
				int[] curPos = robots.get(i);
//				System.out.println("=-====="+i+"번째 로봇이 이동===============");
				int[] nextPos = findShortestPath(curPos);
				
				if(nextPos == null) continue;
				
				int prevKey = curPos[0]*51 + curPos[1];
				robotSet.remove(prevKey);
				curPos[0] = nextPos[0];
				curPos[1] = nextPos[1];
				int nextKey = curPos[0]*51 + curPos[1];
				robotSet.add(nextKey);
			}
			
			//2. 먼지 청소
			for(int i = 0;i<robots.size();i++) {
				int[] curPos = robots.get(i);
//				System.out.println("로봇들 위치 : "+ Arrays.toString(curPos));
				removeDust(curPos);
			}
			
			
			//3. 먼지 축적
			add5Dust();
			
			//4.먼지 확산
			diffuseDust();
			
			//5. 먼지 출력
			printDustSum();

		}
	}
	
//	static void printMap() {
//		System.out.println("========================");
//		for(int i = 0;i<n;i++) {
//			for(int j = 0;j<n;j++) {
//				System.out.print(map[i][j]+" ");
//			}
//			System.out.println();
//		}
//	}
//	
//	static void printMap(int[][] map) {
//		System.out.println("========================");
//		for(int i = 0;i<n;i++) {
//			for(int j = 0;j<n;j++) {
//				System.out.print(map[i][j]+" ");
//			}
//			System.out.println();
//		}
//	}
	
	private static void printDustSum() {
		int sum = 0;
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(map[i][j] > 0) {
					sum += map[i][j];
				}
			}
		}
		System.out.println(sum);
	}

	private static void diffuseDust() {
		
		int[][] tmpMap = new int[n][n];
		
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(map[i][j] != 0) continue;
				
				int sum = 0;
				for(int dir = 0;dir<4;dir++) {
					int y = i + dy[dir];
					int x = j + dx[dir];
					if(y < 0 || y >= n || x <0 || x >= n || map[y][x] < 0) continue;
					sum += map[y][x];
				}
				
				tmpMap[i][j] = sum/10;
			}
		}

		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(tmpMap[i][j] == 0) continue;
				
				map[i][j] = tmpMap[i][j];
			}
		}
	}

	private static void add5Dust() {
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(map[i][j] > 0) {
					map[i][j] += 5;
					
				}
			}
		}
		
	}

	private static void removeDust(int[] curPos) {
		//1. 가장 먼지 합이 큰 방향
		
		int dir = 0;
		int max = 0;
		for(int i = 0;i<4;i++) {
			int sum = 0;
			for(int j = 0;j<4;j++) {
				int y = curPos[0] + ddy[i][j];
				int x = curPos[1] + ddx[i][j];
				
				if(y < 0 || y >= n || x <0 || x >= n || map[y][x] < 0) continue;
				sum += Math.min(20,map[y][x]);
			}
			
			if(max < sum) {
				dir = i;
				max = sum;
			}
		}
		
		//청소, 격자마다 최대 20개씩 청소
		for(int j = 0;j<4;j++) {
			int y = curPos[0] + ddy[dir][j];
			int x = curPos[1] + ddx[dir][j];
			if(y < 0 || y >= n || x <0 || x >= n || map[y][x] < 0) continue;
			int dust = map[y][x];
			map[y][x] = (dust > 20) ? dust - 20 : 0;
		}
		
		
	}

	static int[] findShortestPath(int[] pos) {
		
		if(map[pos[0]][pos[1]] > 0) return null;
		
		boolean[][] visited = new boolean[n][n];
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {0, pos[0], pos[1]});
		visited[pos[0]][pos[1]] = true;
		boolean findDust = false;
		List<int[]> dustList = new ArrayList<>();
//		int path = 0;
		int dist = Integer.MAX_VALUE;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i = 0;i<4;i++) {
				int y = cur[1]+ dy[i];
				int x = cur[2]+ dx[i];
				
				int key = y*51 + x;
				if(y < 0 || y >= n || x <0 || x >= n || visited[y][x] || map[y][x] == -1 || robotSet.contains(key) )continue;
				
				if(findDust && dist < cur[0]+1) {
					//이미 먼지를 찾았고
					//queue싸이클도 한번 돌았다면
					
					//가장 우선순위가 높은 위치 반환
					
					return closestDustPos(dustList);
				}
				
				if(map[y][x] > 0) {
					dustList.add(new int[] {cur[0]+1, y, x});
					findDust = true;
					dist = cur[0]+1;
					visited[y][x] = true;
				}
				else {
					q.add(new int[] {cur[0]+1 ,y, x});
					visited[y][x] = true;
				}
				
			}
		}
		
		if(findDust) {
			return closestDustPos(dustList);
		}
		
		//못찾은 경우 null
		
		return null;
	}

	private static int[] closestDustPos(List<int[]> dustList) {

		Collections.sort(dustList, (o1,o2) -> {
			if(o1[0] != o2[0]) return o1[0] - o2[0];
			if(o1[1] != o2[1]) return o1[1] - o2[1];
			return o1[2] - o2[2];
		});
		
//		System.out.println("dustList 목록 : ======================");
//		for(int[] d : dustList) {
//			System.out.println(Arrays.toString(d));
//		}
		
		return new int[]{dustList.get(0)[1],dustList.get(0)[2]};
	}
	

}
