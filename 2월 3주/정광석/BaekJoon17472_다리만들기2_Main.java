package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 
모든 섬을 연결하는 다리
다리 길이의 최솟값 구하기
다리
- 다리는 직선이다.
- 다리는 2칸 이상이다.
- 다리는 교차할 수 있다.

 * 
 */
public class BaekJoon17472_다리만들기2_Main {
	static int n,m;
	static int[][] board;
	static boolean[][] visited;
	static int team = 1;
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static int[] parent;
	static boolean[] v;
	static int min_dist = Integer.MAX_VALUE;
	static int[][] adj;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st  = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		visited = new boolean[n][m];
		board = new int[n][m];
		
		
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//debug();
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				if(board[i][j] == 1 && !visited[i][j]) {
					makeTeambfs(i,j);
				}
			}
		}
		adj = new int[team][team];
		for(int i=1; i<team; i++) Arrays.fill(adj[i], Integer.MAX_VALUE);
		//System.out.println(team);
		
		//debug();
		findEdge();
		//System.out.println("findEdge 실행");
		boolean[] v = new boolean[team];
		int[] cost = new int[team];
		Arrays.fill(cost, Integer.MAX_VALUE);
		int result = 0;
		int count = 0;
		cost[1] = 0;
		for(int i=1; i<=team-1; i++) {
			int min = Integer.MAX_VALUE;
			int u = -1;
			for(int j=1; j<=team-1; j++) {
				if(!v[j] && cost[j] < min) {
					min = cost[j];
					u = j; // 가장 저렴한 팀을 u로 업데이트
					
				}
			}
			
			if(u==-1) break;
			v[u] = true;
			result +=min;
			count ++;
			
			for(int k = 1; k<=team-1; k++) {
				if(adj[u][k] != Integer.MAX_VALUE && !v[k]) {
					if(adj[u][k] < cost[k]) cost[k] = adj[u][k];
				}
			}
		}
		if(count == team-1) System.out.println(result);
		else System.out.println(-1);
		
		
		
	}
	
	static void debug() {
		for(int i=0; i<n; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
	// 팀을 구분한다.
	private static void makeTeambfs(int x, int  y) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {x,y});
		visited[x][y] = true;
		board[x][y] = team;
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int tx = temp[0];
			int ty = temp[1];
			board[tx][ty] = team;
			for(int i=0; i<4; i++) {
				int nx = tx + dx[i];
				int ny = ty + dy[i];
				
				if(0<=nx && nx<n && 0<=ny && ny<m && board[nx][ny] == 1) {
					if(visited[nx][ny]) continue;
					visited[nx][ny] = true;
					q.offer(new int[] {nx, ny});
				}
			}
			
		}
		team++;
		
	}
	
	static void findEdge() {
		for(int i=0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				if(board[i][j] > 0) {
					int start = board[i][j];
					
					for(int d = 0 ; d<4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						
						int dist = 0;
						
						while(true) {
							if(0 > nx || nx >= n || 0 > ny || ny >= m) break;
							if(board[nx][ny] == start) break;
							
							if(board[nx][ny] == 0) {
								dist ++ ;
								nx += dx[d];
								ny += dy[d];
							}else {
								int end = board[nx][ny];
								if(dist>=2) {
									adj[start][end] = Math.min(adj[start][end], dist);
									adj[end][start] = Math.min(adj[end][start], dist);
								}
								break;
							}
							
						}
					}
				}
			}
		}
	}
	
	
	
	
	
}
