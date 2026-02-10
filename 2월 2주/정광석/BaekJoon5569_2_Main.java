package saffy_algo.BaekJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon5569_2_Main {
	//출근 경로
	
	static int[][][][] dp;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		int w,h;
		w= Integer.parseInt(st.nextToken()); //세로
		h= Integer.parseInt(st.nextToken()); //가로
		dp = new int[w+1][h+1][2][2];
		
		//dp : 위치 방향 0,1 , can turn 0,1
		
		
		for(int i=2; i<=h; i++) {
			dp[1][i][0][1] = 1;
		}
		
		
		for(int i=2; i<=w; i++) {
			dp[i][1][1][1] = 1;
		}
		/*
		 * dp[i][j][0][0] = 위를 바라보는 상태, 위로
		 * dp[i][j][0][1] = 위를 바라보는 상태, 오른쪽
		 * dp[i][j][1][0] = 오른쪽 바라보는 상태, 위로
		 * dp[i][j][1][1] = 오른쪽을 바라보는 상태, 오른쪽
		 * 
		 */
		for(int i=2; i<=w; i++) {
			for(int j=2; j<=h; j++) {
				//i,j 번째가 위쪽 방향으로 꺾는 경우 = 오른쪽으로 들어온 것 중  직진으로 들어온 것
				dp[i][j][0][0] = dp[i][j-1][1][1];
				//i,j 가 위쪽 방향으로 직진하는 경우 = 오른쪽으로 들어온 것 중 
				dp[i][j][0][1] = (dp[i][j-1][0][0] + dp[i][j-1][0][1])%100000;
				dp[i][j][1][0] = dp[i-1][j][0][1];
				dp[i][j][1][1] = (dp[i-1][j][1][0] + dp[i-1][j][1][1])%100000;
			}
		}
		
		
		
		
		System.out.println((dp[w][h][0][0]+dp[w][h][0][1]+dp[w][h][1][0]+dp[w][h][1][1])%100000);
		
		
		
		
	}
}
