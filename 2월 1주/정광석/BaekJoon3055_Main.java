package saffy_algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BaekJoon3055_Main {

	
	static char[][] board;
	static boolean[][] visited;
	static int[] dx = new int[] {0,0,1,-1};
	static int[] dy = new int[] {1,-1,0,0};
	static Queue<int[]> wq = new ArrayDeque<>();
	static Queue<int[]> sq = new ArrayDeque<>();
	static int sx, sy;
	
	static int fastest = Integer.MAX_VALUE;
	static int n,m;
	static List<int []> waters = new ArrayList<>();
	public static void main(String[] args) throws  IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
	
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new char[n][m];
		visited = new boolean[n][m];
		
		for(int i=0; i<n ;i++) {
			String line = br.readLine();
			for(int j=0; j<m ; j++) {
				char tmp = line.charAt(j);
				board[i][j] = tmp;
				if(tmp == 'S') {
					sx = i;
					sy = j;
				}
				if(tmp == '*') {
					wq.offer(new int[]{i,j});
				}
			}
		}
		
		
		
		sq.offer(new int[] {sx, sy, 0});
		visited[sx][sy] = true;
		
		
		
		
		while(!sq.isEmpty()) {
			int wqSize = wq.size();
			
			
			for(int i=0; i<wqSize; i++) {
				int[] temp = wq.poll();
				int tx = temp[0];
				int ty = temp[1];
				for(int d=0; d<4; d++) {
					int nx = tx + dx[d];
					int ny = ty + dy[d];
					if(0<=nx && nx < n && 0<=ny && ny < m) {
						if(board[nx][ny] == '.' || board[nx][ny] == 'S') {
							board[nx][ny] = '*';
							wq.offer(new int[] {nx, ny});
						}
					}
				}					
			}
			
			int sqSize = sq.size();
			
			for(int i=0; i<sqSize; i++) {
				int[] temp = sq.poll();
				int tx = temp[0];
				int ty = temp[1];
				int cnt = temp[2];
				for(int d=0; d<4; d++) {
					int nx = tx + dx[d];
					int ny = ty + dy[d];
					if(0<=nx && nx < n && 0<=ny && ny < m) {
						if(board[nx][ny] == 'D') {
							fastest = cnt+1;
							System.out.println(fastest);
							return;
						}
						
 						if(board[nx][ny] == '.') {
							if(visited[nx][ny]) continue;
							sq.offer(new int[] {nx, ny, cnt+1});
							visited[nx][ny] = true;
						}
					}
				}					
			}
		}
		System.out.println("KAKTUS");

		
	}
	
	
	
	


	
}
