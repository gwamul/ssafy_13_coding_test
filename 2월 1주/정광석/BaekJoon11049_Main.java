package saffy_algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon11049_Main {
	
	
	static int n; //행렬의 개수
	static int[][] dp ;
	static int[][] matrix;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		n = Integer.parseInt(br.readLine());
		dp = new int[n][n];
		matrix = new int[n][n];
	
		
		for(int i=0; i<n; i++) {
			int r, c;
			st= new StringTokenizer(br.readLine());
			r = Integer.parseInt(st.nextToken());
			c = Integer.parseInt(st.nextToken());
			
			matrix[i][0] = r;
			matrix[i][1] = c;
		}
		
		
		// i : 출발 위치, j : 도착 위치
		// 역행은 없으니까 + 도착 최대치는 n-1 
		for(int len = 2; len <= n; len ++) {
			for(int i=0; i<=n-len; i++) {
				int j = i + len - 1;
				dp[i][j] = Integer.MAX_VALUE;
				
				for(int k=i; k<j; k++) {
					
					int cost = dp[i][k] + dp[k+1][j] + matrix[i][0] * matrix[k][1] * matrix[j][1];
					
					dp[i][j] = Math.min(dp[i][j], cost);
				}
				
			}
		}
		
		System.out.println(dp[0][n-1]);
		
	}
	
	
	
	
	
	
}
