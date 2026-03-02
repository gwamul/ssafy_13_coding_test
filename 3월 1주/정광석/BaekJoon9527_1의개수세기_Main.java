package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon9527_1의개수세기_Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		long a,b;
		a = Long.parseLong(st.nextToken());
		b = Long.parseLong(st.nextToken());
		System.out.println(sol(b) - sol(a-1));
	}
	
	static long sol(long n) {
		if(n<=0) return 0;
		long total = 0;
		for (long power = 1; power <= n; power <<= 1) {
	        long jugi = power * 2;
			
			total += (n + 1) / jugi * power;
	        
	         
	        long remain = (n + 1) % jugi;
	        total += Math.max(0, remain - power);
	    }
	    return total;
	}
	
	
}
