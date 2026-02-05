package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b11049 {
	static int n;
	static int[][] dp;
	static final int INF = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		int[] x = new int[n+1];
		int[] y = new int[n+1];
		for(int i=1;i<=n;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			x[i] = Integer.parseInt(st.nextToken());
			y[i] = Integer.parseInt(st.nextToken());
		}
		dp = new int[n+1][n+1];
		//길이가 1인 경우는 불가능
		for(int i=1;i<=n;i++) {
			dp[i][i] = 0;
		}
		for (int len = 2; len <= n; len++) {      
		    for (int i = 1; i + len - 1 <= n; i++) {  
		        int j = i + len - 1;                  
		        dp[i][j] = INF;

		        //dp(0~k) + [k,k+1] + dp(k+1,n)을 계산해본다.
		        for (int k = i; k < j; k++) {
		            dp[i][j] = Math.min(
		                dp[i][j],
		                dp[i][k] + dp[k + 1][j]
		                + x[i] * y[k] * y[j]
		            );
		        }
		    }
		}
		System.out.println(dp[1][n]);
	}
}
