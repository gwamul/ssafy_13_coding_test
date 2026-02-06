package kjh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Boj11049 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine().trim());
		int[] arr = new int[n+1];
		
		for(int i = 0;i<n;i++) {
			String[] ab = br.readLine().trim().split(" ");
			int a = Integer.parseInt(ab[0]);
			int b = Integer.parseInt(ab[1]);
			
			//행렬은 앞을 기준으로
			if(i == 0) arr[i] = a;
			arr[i+1] = b;
		}
		
		int[][] dp = new int[n][n];
		//dp 초기값
//		for(int i = 0;i<n;i++) {
//			dp[i][i] = 0;
//		}
		
		//점화식
		for(int i = n-1;i>=0;i--) {
			for(int j = i+1;j<n;j++) {
				int min = Integer.MAX_VALUE;
				for(int k = i;k<j;k++) {
					int cal = dp[i][k] + dp[k+1][j] + arr[i]*arr[k+1]*arr[j+1];
					min = Math.min(cal, min);
				}
				dp[i][j] = min;
			}
		}
	
		
		System.out.println(dp[0][n-1]);
		
	}
}


	

