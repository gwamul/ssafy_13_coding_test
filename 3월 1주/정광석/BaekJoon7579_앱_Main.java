package saffy_algo.BaekJoon.골드.골드3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon7579_앱_Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int n,m;
		int[] app, cost;
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		app = new int[n];
		cost = new int[n];
		
		st = new StringTokenizer(br.readLine());
		int sum = 0;
		for(int i=0; i<n; i++) {
			app[i] = Integer.parseInt(st.nextToken());
			sum += app[i];
		}
		st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<n; i++) {
			cost[i] = Integer.parseInt(st.nextToken());
		}
		
		int[] dp = new int[Math.max(m+1, 1+sum)];
		Arrays.fill(dp,  Integer.MAX_VALUE);
		
		dp[0] = 0;
		
		for(int j=0; j<n; j++) {
			for(int i=dp.length-1; i-app[j]>=0; i--) {
				if(dp[i-app[j]] == Integer.MAX_VALUE) continue;
				dp[i] = Math.min(dp[i], dp[i-app[j]] + cost[j]);
			}
			
		}
		//System.out.println(Arrays.toString(dp));
		//System.out.println(sb.toString());
		Arrays.sort(cost);
		int answer = Integer.MAX_VALUE;
		for(int i=m; i<dp.length; i++) {
			answer = Math.min(answer, dp[i]);
		}
		System.out.println(answer); //반례 1개 존재
	}
}
