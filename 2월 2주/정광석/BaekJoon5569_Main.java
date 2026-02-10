package saffy_algo.BaekJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon5569_Main {
	
	static int w, h;
	static int[][][] dp;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb= new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		w = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());
		
		dp = new int[w+1][h+1][4];
		/*

		* k = 0 : 오른쪽 진입, 위쪽 방향 전환 가능(오, 위)

		* k = 1 : 오른쪽 진입, 위쪽 방향 전환 불가(오) = 들어온지 한칸됨

		* k = 2 : 위쪽 진입, 오른쪽 방향 전환 가능(오, 위)

		* k = 3 : 위쪽 진입, 오른쪽 방향 전환 불가.(위) = 들어온지 한칸됨

		*

		*

		*

		*

		*/
		
		for (int i = 2; i <= w; i++) dp[i][1][0] = 1;
		for (int j = 2; j <= h; j++) dp[1][j][2] = 1;
		for (int i = 2; i <= w; i++) {
		    for (int j = 2; j <= h; j++) {
		        
		        dp[i][j][0] = (dp[i-1][j][0] + dp[i-1][j][1]) % 100000;
		        dp[i][j][1] = dp[i-1][j][2]; 

		       
		        dp[i][j][2] = (dp[i][j-1][2] + dp[i][j-1][3]) % 100000;
		        dp[i][j][3] = dp[i][j-1][0]; 
		    }
		}
		
//		for(int i=0; i<=w; i++) {
//			for(int j=0; j<=h; j++) {
//				System.out.print(Arrays.toString(dp[i][j]) + " ");
//				
//			}
//			System.out.println();
//		}
		System.out.println((dp[w][h][0]+dp[w][h][1]+dp[w][h][2]+dp[w][h][3] ) % 100000);
	}
}
