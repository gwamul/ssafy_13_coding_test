package saffy_algo.BaekJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon12886_Main {

	static int a,b,c;
	static int sum;
	
	static int max_size;
	static int[][] dp;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		a = Integer.parseInt(st.nextToken());
		b = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		sum = a+b+c;
		max_size = sum+1;
		dp = new int[max_size][max_size];
		sol(a,b,c);
		System.out.println(dp[sum/3][sum/3] == sum/3 ? 1 : 0);
		//System.out.println(sol(a,b,c));
		
	}
	
	public static void sol(int x, int y, int z) {
		
		
		if(x>sum || y>sum || z>sum) return;
		if(x<0 || y<0 || z<0) return;
		//System.out.println(x + " " + y + " " + z);
		if(dp[x][y] != 0) return;
		dp[x][y] = sum-x-y; 
		
		
		if(x>y) {
			sol(x-y, y+y, z);
		}else if(y<x) {
			sol(x+x, y-x, z);
		}
		if(y>z) {
			sol(x, y-z, z+z);
		}else if(z > y) {
			sol(x, y+y, z-y);
		}
		if(z>x) {
			sol(x+x, y, z-x);
		}else if( x > z){
			sol(x-z, y, z+z);
		}
		

		
		return;
	}
}
