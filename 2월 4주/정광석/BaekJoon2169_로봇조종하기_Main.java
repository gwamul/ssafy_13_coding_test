package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon2169_로봇조종하기_Main {
	/*
	 * 지형을 단순화 한 입력
	 * 왼, 오, 아 로만 이동 가능(위 안됨)
	 * 한번 방문한 지역은 탐사 못함
	 * 
	 * 
	 * 왼쪽 위 -> 오른쪽 아래 
	 * 가치 합이 최대가 되야 함.
5 5
10 25 7 8 13
68 24 -78 63 32
12 -69 100 -29 -25
-16 -22 -57 -33 99
7 -76 -11 77 15
	 */
	static int n,m;
	static int[][] board;
	static int[][][] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
	
		
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board= new int[n+1][m+1];
		dp = new int[n+1][m+1][3];
		for(int i=0; i<=n; i++) {
			for(int j=0; j<=m; j++) {
				Arrays.fill(dp[i][j], -1000000);
			}
		}
		for(int i=1; i<=n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=m; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				
			}
		}
		
		/*
		 * 
		 * 0,0 -> 1,0 0,1
		 * 1,0 -> 2,0, 1,1,
		 * 
		 *  0 : 아래서 내려옴
		 *  1 : 왼쪽에서 옴
		 *  2 : 오른쪽에서 옴
		 */
		dp[1][1][0] = dp[1][1][1] = dp[1][1][2] = board[1][1];
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=m; j++) {

				if(i>1)
					dp[i][j][0] = board[i][j]+Math.max(dp[i-1][j][0], Math.max(dp[i-1][j][1], dp[i-1][j][2]));
				if(j>1) 
					dp[i][j][1] = board[i][j]+Math.max(dp[i][j-1][0], dp[i][j-1][1]);
			}
			for(int j=m-1; j>=1; j--) {
				dp[i][j][2] = board[i][j]+Math.max(dp[i][j+1][0], dp[i][j+1][2]);			
			}
		}
		
		
	
		System.out.println(Math.max(dp[n][m][0], dp[n][m][1]));
			
		
		
		//debug();
		
		
	}
	static void debug() {
		for(int i=0; i<=n; i++) {
			for(int j=0; j<=m; j++) {
				System.out.print(Arrays.toString(dp[i][j]) + " ");
			}
			System.out.println();
		}
	}
}
