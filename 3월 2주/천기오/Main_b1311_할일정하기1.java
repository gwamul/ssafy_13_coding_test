package week6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_b1311_할일정하기1 {
	static int[][] cost;
	static int[] dp;
	static int n;

	// person 최대 20, mask 최대 2^20 -> 중복 계산은 없으므로 최악의 경우 20 * 2^20 = 약 20,000,000
	static int dfs(int mask) {
		if (mask == (1 << n) - 1)
			return 0;
		int person = Integer.bitCount(mask);
		// 메모이제이션 - 이전에 이미 탐색한 경로라면 저장된 최소값 리턴
		// 비트마스킹을 써야하는 이유 -> 일반 boolean 배열로 방문을 검사한다면 다른 값들의 사용여부를 알 수 없어 dp 테이블 생성이
		// 불가능함
		if (dp[mask] != -1)
			return dp[mask];
		dp[mask] = Integer.MAX_VALUE;
		for (int job = 0; job < n; job++) {
			// 이미 실행한 작업은 skip
			if ((mask & (1 << job)) != 0)
				continue;
			dp[mask] = Math.min(dp[mask], dfs(mask | (1 << job)) + cost[person][job]);
		}
		return dp[mask];
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		cost = new int[n][n];
		dp = new int[1 << n];
		Arrays.fill(dp, -1);
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				cost[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int answer = dfs(0);
		System.out.print(answer);
	}
}