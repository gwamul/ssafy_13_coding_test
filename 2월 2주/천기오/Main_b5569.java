package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b5569 {
	static int w, h;
	static int[][][][] dp;
	static final int MOD = 100_000;

	// 문제 포인트 = 일반적인 2차원 경우의수 저장에서 + 이전에 어느 방향에서 왔는지도 검사해야한다.
	// 4차원 배열 사용
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		w = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());
		dp = new int[w + 1][h + 1][2][2];
		for (int i = 1; i <= w; i++)
			dp[i][1][0][0] = 1;
		for (int i = 1; i <= h; i++)
			dp[1][i][1][0] = 1;
		//00 -> 왼쪽에서 옴 && 방향 전환 가능 
		//01 -> 왼족에서 옴 && 직전에 방향 전환
		//10 -> 아래에서 옴 && 방향 전환 가능
		//11 -> 아래에서 옴 && 직전에 방향 전환
		for (int i = 2; i <= w; i++) {
			for (int j = 2; j <= h; j++) {
				// 왼쪽에서 오는 케이스 저장
				dp[i][j][0][0] = (dp[i - 1][j][0][0] + dp[i - 1][j][0][1]) % MOD;
				dp[i][j][0][1] = dp[i - 1][j][1][0];
				// 아래에서 오는 케이스 저장
				dp[i][j][1][0] = (dp[i][j - 1][1][0] + dp[i][j - 1][1][1]) % MOD;
				dp[i][j][1][1] = dp[i][j - 1][0][0];
			}
		}
		int ans = 0;
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				ans += dp[w][h][i][j];
		
		System.out.println(ans % MOD);
	}
}
