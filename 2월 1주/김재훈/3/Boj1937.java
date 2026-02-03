package kjh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Boj1937 {
	
	static int[][] map;
	static int[][] dp;
	static int[] dy = {1,0,-1,0};
	static int[] dx = {0,1,0,-1};
	
	static int n;
	
	public static int recur(int y, int x) {
		
		if(dp[y][x] != 0 ) return dp[y][x];
		
		int max = 1;
		for(int i = 0;i<4;i++) {
			int cy = y + dy[i];
			int cx = x + dx[i];
			
			if(cy < 0 || cy >=n || cx < 0 || cx >= n) continue;
			
			if(map[y][x] < map[cy][cx]) {
				int n = recur(cy, cx)+1;
				max = Math.max(max, n);
			}
		}
		dp[y][x] = max;
		
		return max;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine().trim());
		map = new int[n][n];
		dp = new int[n][n];
		for(int i = 0;i<n;i++) {
			String[] line = br.readLine().split(" ");
			for(int j = 0;j<n;j++) {
				map[i][j] = Integer.parseInt(line[j]);
			}
		}
		
		
		
		for(int i =0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				recur(i,j);
			}
		}
		
		int max = 0;
		for(int i =0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				max = Math.max(max, dp[i][j]);
			}
		}
		System.out.println(max);
		
	}


	
}
