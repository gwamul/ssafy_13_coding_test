package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon2294_동전2_Main {
	static int[] coins;
	static int[] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int n,k;
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		coins = new int[n];
		
		
		for(int i=0;i <n; i++) {
			coins[i] = Integer.parseInt(br.readLine());
		}
		//동전의 개수를 최소화
		//가치는 15원
		dp = new int[k+1];
		Arrays.fill(dp, 100001);
		dp[0] = 0;
		for(int i=0; i<=k; i++) {
			
			for(int j=0; j<n; j++) {
				if(i+coins[j] <= k) {
					dp[i+coins[j]] = Math.min(dp[i+coins[j]], dp[i]+1);
				}
			}
		}
		//debug();
		System.out.println(dp[k] == 100001 ? -1 : dp[k]);
	}
	static void debug() {
		System.out.println(Arrays.toString(dp));
	}
}
