package coding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon1937_Main {
	
	static int n;
	static int[][] board;
	static int[][] dp;
	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		n = Integer.parseInt(br.readLine());
		
		board = new int[n][n];
		dp = new int[n][n];
		
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int max_dist = 0;
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				max_dist = Math.max(max_dist, DFS(i, j));
			}
		}
		
		System.out.println(max_dist);
	}
	
	static int DFS(int x, int y) {
		
		if(dp[x][y] != 0) {
			return dp[x][y];
		}
		
		dp[x][y] = 1;
		
		for(int i=0; i<4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(nx >= 0 && nx < n && ny >= 0 && ny < n) {
				if(board[nx][ny] > board[x][y]) {
					dp[x][y] = Math.max(dp[x][y], DFS(nx, ny) + 1);
				}
			}
		}
		
		return dp[x][y];
	}
}