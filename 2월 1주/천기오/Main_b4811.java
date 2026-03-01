package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_b4811 {
	static long[][] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = -1;
		dp = new long[31][61];

		// 기저조건
		for(int b=0; b<=60; b++) dp[0][b] = 1;

		for(int a=1; a<=30; a++) {
		    for(int b=0; b<=60; b++) {
		        dp[a][b] = 0;
		        if(a > 0 && b+1 <= 60) dp[a][b] += dp[a-1][b+1]; // 정수 알약
		        if(b > 0) dp[a][b] += dp[a][b-1];               // 반 알약
		    }
		}
		
		StringBuilder sb = new StringBuilder();
		while((n = Integer.parseInt(br.readLine())) != 0) {
			sb.append(dp[n][0]).append('\n');
		}
		System.out.println(sb);
	}
}
