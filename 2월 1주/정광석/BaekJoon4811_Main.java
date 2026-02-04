package saffy_algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon4811_Main {
	
	static long[] dp = new long[31];
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		dp[0] =1L;
		for(int i=1;i <=30; i++) {
			for(int k=0; k<i; k++) {
				dp[i] += dp[k]*dp[i-k-1];
			}
		}
		
		
		while(true) {
			int n = Integer.parseInt(br.readLine());
			long answer = 0;
			if(n==0) {
				System.out.println(sb.toString());
				break;
			}
			/*
			 * 1알 : WH 만 가능
			 * 2알 : WWHH , WHWH
			 * 3알 : WWWHHH, WHWHWH, WWH
			 * i알 : 
			 * i+1알 : WH(i) W(i)H
			 */
			answer = dp[n];
			
			
			sb.append(answer).append("\n");
		}
		
	
	}
}
