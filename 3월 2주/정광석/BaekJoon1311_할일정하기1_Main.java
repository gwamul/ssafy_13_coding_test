package saffy_algo.BaekJoon.골드.골드1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon1311_할일정하기1_Main {
	/*
	 * n : 20이하
	 * 
	 * n명의 사람 n개의 일
	 * 
	 * 각 사람은 일을 하나 한다.
	 * 
	 * 각 일은 하나의 사람
	 * 
	 * 각각 1~n 이 번호가 매겨져 있음
	 * 
	 * dij 는 i번 사람이 j번 일을 할 때 필요한 비용
	 * 모든 일을 하는데 필요한 최소한 비용
	 * 
	 * dp[1111111111][1111111111] 의 최솟값을 구하겠다.
	 * 
	 */

	static int[][] board;
	static int[] dp;
	static int n;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();


		n = Integer.parseInt(br.readLine());
		board = new int[1+n][1+n];
		dp = new int[(1<<n)];
		Arrays.fill(dp,-1);

		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}


		//sol2(0,0, 0);
		//debug();

		//System.out.println(sol(0,0));
		System.out.println(sol2());
		//	System.out.println(dp[(1<<n) -1 ]);
		//
	}

	//	static void sol2(int people, int job, int cnt) {
	//		if(cnt == n) return;
	//		//if(dp[people][job]!=Integer.MAX_VALUE) return dp[people][job];	
	//		debug();
	//		for(int i=1; i<=n; i++) {
	//			
	//			if(( people & (1<<(i-1)) )== 1) continue;
	//			int nextPeople = people | (1<<(i-1));
	//			
	//			for(int j=1; j<=n; j++) {
	//				if(( job & (1<<(j-1)) )== 1) continue;
	//				int nextJob = job | (1<<(j-1));
	//				//System.out.println("nextPeople: "+Integer.toBinaryString(nextPeople) + " nextJob: " + Integer.toBinaryString(nextJob));
	//				
	//				dp[nextPeople][nextJob] = Math.min(dp[nextPeople][nextJob], dp[people][job] + board[i][j]);
	//				sol2(nextPeople, nextJob, cnt+1);
	//			}
	//		}
	//	}

	static int sol(int job, int cnt) {
		//debug(cnt, job);
		if(cnt == n) return 0;
		if(dp[job] != -1) return dp[job];
		int res = 987654321;
		for(int i=0; i<n; i++) {

			if((job & (1<<i))  != 0) continue;

			res = Math.min(res, sol( job | (1<<i) , cnt+1) + board[cnt][i]);

		}
		return dp[job] = res;
	}
	
	static int sol2() {
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for(int mask = 0; mask < (1 << n); mask++) {
			int person = Integer.bitCount(mask);
			if(person>=n) continue;
			for(int job = 0; job<n; job++) {
				if((mask & (1<<job)) == 0) {
					int nextMask = mask | (1<<job);
					
					dp[nextMask] = Math.min(dp[nextMask], dp[mask] + board[person][job]);
				}
			}
		}
		//System.out.println(dp[1<<n] - 1);
		//System.out.println(Arrays.toString(dp));
		return dp[(1<<n) - 1];
		
		
		
	}


	static void debug(int n , int job) {
		System.out.println(n + " "  + Integer.toBinaryString(job) + " " + Arrays.toString(dp));

	}
}
















