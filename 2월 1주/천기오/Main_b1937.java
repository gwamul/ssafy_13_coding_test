package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_b1937 {
	static int n;
	static int[][] board, dp;
	static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
	static int dfs(int x, int y) {
		if(dp[x][y] != -1) return dp[x][y];
		
		dp[x][y] = 1;
		
		for(int d=0; d<4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
			if(board[nx][ny] > board[x][y]) {
				dp[x][y] = Math.max(dp[x][y], dfs(nx,ny) + 1);
			}
		}
		return dp[x][y];
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		board = new int[n][n];
		for(int i=0;i<n;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<n;j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		dp = new int[n][n];
		for(int[] row : dp) Arrays.fill(row, -1);
		
		int ans = 0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				ans = Math.max(ans, dfs(i,j));
			}
		}
		System.out.println(ans);
	}
}
