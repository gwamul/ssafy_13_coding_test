package saffy_algo.BaekJoon.골드.골드1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon1300_K번째수_Main {
	/*
	 * 약수 개수 만큼 더해가면 될 거 같다. : 시간초과 확정
	 * 10^ 5 
	 * 
	 */

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());
		
		
		
		long  left = 1;
		long right = 1000000000;
		long  ans = -1;
		while(left<=right) {
			
			long h = left + (right-left)/2;
		//	System.out.println("left: " + left + " mid: " + h + " right: " + right );
			long sum = 0;
			for(int i=1; i<=n; i++) {
				sum += Math.min(n, h/i);
			}
			if(sum >= k) {
				//sum이 크다 = 개수가 많다 = h가 너무 컸다 = h를 줄여야 한다.
				right = h - 1;
				ans = h;
			}else if(sum < k) {
				left = h + 1;
			}
		}
		
		System.out.println(ans);
		
		
		
		
		
	}
	
}
