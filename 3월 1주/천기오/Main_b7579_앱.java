package week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b7579_앱 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] memories = new int[n + 1];
		int[] cost = new int[n + 1];
		int maxCost = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			memories[i] = Integer.parseInt(st.nextToken());
		}
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			cost[i] = Integer.parseInt(st.nextToken());
			maxCost += cost[i];
		}
		
		// dp[b] -> b 비용을 써서 얻을 수 있는 최대 메모리
		int[] dp = new int[maxCost + 1];
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = maxCost; j >= cost[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j - cost[i]] + memories[i]);
			}
		}
		// 최소 비용을 찾아야 하므로, 0부터 차례대로 확인하다가 조건 만족 시 바로 종료.
		for (int i = 0; i <= maxCost; i++) {
			if (dp[i] >= m) {
				ans = i;
				break;
			}
		}
		System.out.print(ans);
	}
}
