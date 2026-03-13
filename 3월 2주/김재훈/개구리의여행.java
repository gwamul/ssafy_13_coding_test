import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 
 * 각 부분에서 점프력에 따라서 갈 수 있는 곳을 넣기
 * 
 * 각 부분에서 cost를 기준으로 다익스트라...
 * 
 * 시간복잡도
 * 한 지점에 4*5
 * 2500지점
 * 1000번
 * 
 * 2백만 50만
 * 
 * 한 4천만 걸림
 */

public class Main{
	
	static int n;
	static int[] dy = {-1,1,0,0};
	static int[] dx = {0,0,-1,1};
	static char[][] map;
	static int r1;
	static int c1;
	static int r2;
	static int c2;
	
	static class PosInfo{
		int y, x, jump, cost;

		public PosInfo(int y, int x, int jump, int cost) {
			this.y = y;
			this.x = x;
			this.jump = jump;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine().trim());
		map = new char[n][n];
		for(int i = 0;i<n;i++) {
			map[i] = br.readLine().trim().toCharArray();
		}
		
		int q = Integer.parseInt(br.readLine().trim());
		for(int journey = 0;journey < q;journey++) {
			String[] js = br.readLine().trim().split(" ");
			r1 = Integer.parseInt(js[0])-1;
			c1 = Integer.parseInt(js[1])-1;
			r2 = Integer.parseInt(js[2])-1;
			c2 = Integer.parseInt(js[3])-1;
			
			
			journey();
		}
	}
	static void print(int[][] visited) {
		
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(visited[i][j] == Integer.MAX_VALUE) {
					System.out.printf("%3d",-1);
				}
				else System.out.printf("%3d",visited[i][j]);
			}
			System.out.println();
		}
	}

	private static void journey() {
		int[][][] visited = new int[n][n][6];
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				Arrays.fill(visited[i][j], Integer.MAX_VALUE);
			}
		}
		
		PriorityQueue<PosInfo> q = new PriorityQueue<>((o1,o2) -> {
			return o1.cost - o2.cost;
		});
		q.add(new PosInfo(r1, c1, 1, 0));
		visited[r1][c1][1] = 0;
		
		while(!q.isEmpty()) {
			PosInfo cur = q.poll();
//			System.out.println("cur 상태 : y : "+cur.y + ", x : "+cur.x+", jump : "+cur.jump+", cost :  "+cur.cost);
//			print(visited);
			
			//이미 더 낮은 비용이라면 넘기기
			if(cur.cost > visited[cur.y][cur.x][cur.jump])continue;

			for(int i = 0;i<4;i++) { //상하좌우로
				//각 점프력마다
				for(int j = 1;j<=5;j++) {
					int y = cur.y + dy[i]*j;
					int x = cur.x + dx[i]*j;
					
					if(y < 0 || y >= n || x < 0|| x >= n) break;
					
					//1. 현재 칸 갈 수 있는지 체크
					//천적이 있으면 어짜피 더 멀리 못뛰니 끝
					if(map[y][x] == '#') break;
					//미끄러운 곳 까지 못 뜀
					if(map[y][x] == 'S') continue;
					
					//2. 옮기기
					int cost = cur.cost;
					int jump = cur.jump;
					//점프 비용
					cost++;
					if(j < jump) { //점프 줄이기 비용
						cost++;
						jump = j;
					}
					else if(j > jump) { //점프 늘리기 비용
						while(j > jump) {
							jump++;
							cost += jump*jump;
						}
					}
					if(visited[y][x][jump] <= cost) continue;
					q.add(new PosInfo(y, x, jump, cost));
					visited[y][x][jump] = cost;
				}
				
			}
		}
		int min = Integer.MAX_VALUE;
		for(int i = 0;i<6;i++) {
			min = Math.min(min, visited[r2][c2][i]);
		}
		if(min == Integer.MAX_VALUE) System.out.println(-1);
		else System.out.println(min);
	}

}