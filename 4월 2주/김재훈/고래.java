import java.io.*;
import java.util.*;

/**
 * 전략
 * 
 * 구현
 */

public class Main {
	
	//상,좌,우,하
	static int[][][] step1_dir = {{{-1,0},{0,-1},{0,1},{1,0}},
							{{1,0},{0,1},{0,-1},{-1,0}},
							{{0,-1},{1,0},{-1,0},{0,1}},
							{{0,1},{-1,0},{1,0},{0,-1}}};
	static int[][] step1_d = {
		    {1, 3, 4, 2}, // 현재 상(1)
		    {2, 4, 3, 1}, // 현재 하(2)
		    {3, 2, 1, 4}, // 현재 좌(3)
		    {4, 1, 2, 3}  // 현재 우(4)
		};
	static int[] dy = {-1,1,0,0};
	static int[] dx = {0,0,-1,1};
	
	static int[][] map;
	
	static List<int[]> path = new ArrayList<>();
	
	static int N;
	static int r;
	static int c;
	static int d;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken())-1;
		c = Integer.parseInt(st.nextToken())-1;
		d = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for(int i = 0;i<N;i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0;j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		bfs();
		
		path.forEach(e -> {
			System.out.println(e[0]+1+" "+(e[1]+1));
		});
	}

	private static void bfs() {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {r,c, d});
		boolean[][] visited = new boolean[N][N];
		visited[r][c] = true;
		path.add(new int[] {r, c, d});
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();

			//step1
			for(int i = 0;i<4;i++) {
				int nd = step1_d[cur[2] - 1][i];
				int y = cur[0] + step1_dir[cur[2]-1][i][0];
				int x = cur[1] + step1_dir[cur[2]-1][i][1];
				
				if(x < 0 || x >= N || y <0 || y >= N) continue;
				if(map[y][x] == 1 || visited[y][x]) continue;
				
				q.add(new int[] {y, x, nd});
				path.add(new int[] {y, x});
				visited[y][x] = true;
				break;
			}
			
			if(q.size() >= 1) continue;
			
			//step2
			int[] next = innerBfs(cur, visited);
			if(next != null) {
				q.add(next);
				visited[next[0]][next[1]] = true;
			}
		}
		
	}

	private static int[] innerBfs(int[] cur, boolean[][] visited) {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {cur[0],cur[1], cur[2]});
		boolean[][] visit = new boolean[N][N];
		visit[cur[0]][cur[1]] = true;
		
		while(!q.isEmpty()) {
			
			int size = q.size();
			List<int[]> closeSpot = new ArrayList<>();
			while(size-- > 0) {
				int[] c = q.poll();
				
				for(int i = 0;i<4;i++) {
					int y = c[0] + dy[i];
					int x = c[1] + dx[i];
					
					if(y == cur[0] && x == cur[1]) continue;
					if(y < 0 || y >= N || x < 0 || x >= N) continue;
					if(map[y][x] == 1 || visit[y][x]) continue;
					
					q.add(new int[] {y, x});
					visit[y][x] = true;//visit[c[0]][c[1]]+1;
					
					if(!visited[y][x]) {
						closeSpot.add(new int[] {y, x, i+1});
					}
				}
			}
			
			if(!closeSpot.isEmpty()) {
				Collections.sort(closeSpot, (o1,o2) ->{
					if(o1[0] != o2[0]) return o1[0] - o2[0];
					return o1[1] - o2[1];
				});
				
				int[] goal = closeSpot.get(0);
				path.add(goal);
				return new int[] {goal[0], goal[1], goal[2]};
			}
			
			
			
			
			
		}
		return null;
		
	}

}
