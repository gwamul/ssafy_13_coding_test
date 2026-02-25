package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BaekJoon13549_숨바꼭질3_Main {
	/*
	 * 수빈 : n에 있음
	 * 동생 : k에 있음
	 * 
	 * 걷거나 순간이동
	 * x일 때 걷기 : 한칸 앞 뒤
	 * 
	 * 순간이동 : 0초 후 2x 로 이동
	 * 
	 * 가장 빠르게 동생을 찾기
	 */
	
	
	static int n,k;
	static int[] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine());
		 
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
	
		
		dp = new int[100001];
		Arrays.fill(dp,  -1);
		sol();
		//System.out.println(Arrays.toString(dp));
		System.out.println(dp[k]);
	}
	
	static void sol() {
		PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> a[1] - b[1]);
	    q.offer(new int[]{n, 0});
	    
	    Arrays.fill(dp, Integer.MAX_VALUE);
	    dp[n] = 0;

	    while (!q.isEmpty()) {
	        int[] curr = q.poll();
	        int now = curr[0];
	        int cnt = curr[1];

	        if (cnt > dp[now]) continue;

	        if (now * 2 < dp.length && dp[now * 2] > cnt) {
	            dp[now * 2] = cnt;
	            q.offer(new int[]{now * 2, cnt});
	        }

	        if (now + 1 < dp.length && dp[now + 1] > cnt + 1) {
	            dp[now + 1] = cnt + 1;
	            q.offer(new int[]{now + 1, cnt + 1});
	        }

	        if (now - 1 >= 0 && dp[now - 1] > cnt + 1) {
	            dp[now - 1] = cnt + 1;
	            q.offer(new int[]{now - 1, cnt + 1});
	        }
	    }
		
		
		
	}
	
	
	
}
