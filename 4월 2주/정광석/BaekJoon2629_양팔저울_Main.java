package saffy_algo.BaekJoon.골드.골드3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 양팔 저울과 몇 개의 추, 입력으로 주어진 구슬 무게를 확인할 수 있는가?
 * 
 * 양팔 저울에는 어디든 ,추 구슬 함께 넣을 수 있음
 * 1g 4g 추가 있을 때 3g 구슬 무게 확인 가능( 1+3 = 4)
 * 
 * 주어진 추를 가지고 구슬 무게 확인 가능한가? = 구슬은 하나만 올릴 수 있음
 * 
 * 추 : 30개 이하
 * 구슬 : 7개 이하
 * 
 * 주어진 각 구슬의 무게에 대해서, 확인이 가능하면 Y, N
 * 
 * 어떻게 확인해야 하나
 * 
 * 추의 합
 * 추의 차 이 모든 것들이 들어가 있도록?
 * 
 * 합은 어떻게 구하고 차는 어떻게 구하나?
 * 
 * 추 무게가 오름차순으로 들어가 있음
 * 모든 경우에 대한 합이 필요한가?
 * 
 * 가능한 합의 경우의 수 2^30 -> 말이 안됨
 * 
 * 
 * 만약 set 같은 곳에 합을 넣는다고하면, 구슬 무게 w가 주어졌을 때, 
 * 
 * dp
 * 배낭의 무게가 구슬 무게
 * 추가 아이템 무게
 * 
 * dp : 차이를 최소로 만들어 가기?
 * 
 * 
 * */
public class BaekJoon2629_양팔저울_Main {
	
	static int[] weights;
	static int[] balls;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		int n;
		n = Integer.parseInt(br.readLine());
		weights = new int[n];
		int total = 0;
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<n; i++) {
			weights[i] = Integer.parseInt(st.nextToken());
			total += weights[i];
		}
		
		
		
		int m; 
		
		m = Integer.parseInt(br.readLine());
		balls = new int[m];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<m; i++) balls[i] = Integer.parseInt(st.nextToken());
		
		
		boolean[] dp = new boolean[total+1];
		dp[0] = true;
		//for(int a : weights) dp[a] = true;
		//System.out.println(Arrays.toString(dp));
		
		for(int i = n-1; i>=0; i--) {
			int weight = weights[i];
			for(int j = total; j >=weight; j--) {
				if(dp[j-weight]) dp[j] = true;
			}
		}
		//System.out.println(Arrays.toString(dp));
		
		
		for(int ball : balls) {
			if(ball>total) {
				sb.append("N").append(" ");
				continue;
			}
			if( dp[ball]) sb.append("Y").append(" ");
			else {
				boolean flag = true;
				for(int i=1; ball+i<=total; i++) {
					if(dp[i] && dp[ball+i]) {
						flag = false;
						sb.append("Y").append(" ");
						break;
					}
				}
				if(flag) sb.append("N").append(" ");
			}
		}
		//
		System.out.println(sb);
		
	}
	
	
}
