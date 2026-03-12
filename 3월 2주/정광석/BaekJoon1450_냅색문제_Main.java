package saffy_algo.BaekJoon.골드.골드1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


/*
 * 가방 사이즈를 dp로 잡으면, 10^9 로 잡아야 하는데, 불가능하다. 4gb 
 * 
 * 물건 개수를 dp로 잡을 수 있나?
 * 포함/비포함으로 잡으면 2^30 연산을 해야 한다. = 1000 1000 1000 = 10^9 
 * 안됨
 * 
 * 그리디 하게 ?
 * 
 * 크기 내림차순 정렬?
 * 
 * 
 * 
 * 
 */
public class BaekJoon1450_냅색문제_Main {
	
	
	static int[] dp;
	static long[] cost;
	static int n,c;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); //물건 개수 <= 30 , 2^30 == 1 000 000 000
		c = Integer.parseInt(st.nextToken()); //가방 용량 <= 10^9
		cost = new long[n];
		//dp = new int[c];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			cost[i] = Long.parseLong(st.nextToken());
		}
		
		
		List<Long> front = new ArrayList<>();
		List<Long> back = new ArrayList<>();
		
		sol(0, n/2, 0, front);
		sol(n/2, n, 0, back);
		int ans = 0;
		Collections.sort(front);
		Collections.sort(back, Collections.reverseOrder());
		int left = 0;
		int right = 0;
		while(left < front.size() && right < back.size()) {
			long sum = front.get(left) + back.get(right);
		//	System.out.println(sum);
			if(sum <= c) {
				ans+=(back.size() - right);
				left++;
			}else {
				right++;
			}
		}
		
		System.out.println(ans);
		
		
	}
	
	static void debug() {
		System.out.println(Arrays.toString(dp));
	}
	
	static void sol(int idx, int limit, long sum, List<Long> result) {
		if(idx == limit) {
			result.add(sum);
			return;
		}
		
		sol(idx+1, limit , sum , result);
		if(sum + cost[idx] <= c) {
			sol(idx+1,  limit, sum+cost[idx], result);
		}
	}
}
