package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BaekJoon11967_불켜기_Main {
	
	static int n,m;
	static List<int[]>[][] board;
	static boolean[][] light;
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static boolean[][] visited;
	static int max_room = 1;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new ArrayList[n+1][n+1];
		light = new boolean[n+1][n+1];
		visited = new boolean[n+1][n+1];
		for(int i=0; i<=n; i++) {
			for(int j=0; j<=n; j++) {
				board[i][j] = new ArrayList<>();
			}
		}
		
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int x,y,a,b;
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			
			board[x][y].add(new int[] {a,b});
			
		}
		
		bfs();
		System.out.println(max_room);
		
		
		
	}
	
	static boolean inBound(int x, int y) {
		if(1<=x && x<=n && 1<=y && y<=n) return true;
		return false;
	}
	static void debug() {
		System.out.println("----------------");
		for(int i=1; i<=n; i++) {
			System.out.println(Arrays.toString(light[i]));
		}
		System.out.println("----------------");
	}
	
	static void bfs() {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {1,1});
		visited[1][1] = true;
		light[1][1] = true;
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int tx = temp[0];
			int ty = temp[1];
			//System.out.println(tx + " " + ty);
			for(int[] a : board[tx][ty]) {
				int nnx = a[0];
				int nny = a[1];
				if(!light[nnx][nny]) {
					max_room ++;
					light[nnx][nny] =  true;
				}
				if (!visited[nnx][nny] && light[nnx][nny]) {
	                for (int i = 0; i < 4; i++) {
	                    int nx = nnx + dx[i];
	                    int ny = nny + dy[i];
	                    if (inBound(nx, ny) && visited[nx][ny]) {
	                        visited[nnx][nny] = true;
	                        q.offer(new int[] {nnx, nny});
	                        break; 
	                    }
	                }
	            }
			}
			//debug();
			
			
			for(int i=0; i<4; i++) {
				int nx = tx + dx[i];
				int ny = ty + dy[i];
				if (inBound(nx, ny) && !visited[nx][ny] && light[nx][ny]) {					
					visited[nx][ny] = true;
					q.offer(new int[] {nx,ny});
				}
				
			}
		}
	}
}
