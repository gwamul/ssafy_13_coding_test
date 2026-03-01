package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_b2294_동전2 {
	static final int MAX = 123_456_789;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] coins = new int[n];
		for (int i = 0; i < n; i++) {
			coins[i] = Integer.parseInt(br.readLine());
		}
		int[] dp = new int[k + 1];

		Arrays.fill(dp, MAX);
		dp[0] = 0;
		// coin을 한 개씩 추가해가면서 경우의 수 확인하기
		// 경우의 수를 구하는 문제는 이 순서가 굉장히 중요함. 이 문제에서는 중복이 생겨도 상관이 없어서 문제 x.
		for (int coin : coins) {
			for (int i = coin; i <= k; i++) {
				// 기존에 사용했던 개수와 i-coin을 만드는 개수에 coin 1개를 합친것과 비교 후 업데이트
				// 작은 수부터 순차적으로 사용하여 중복도 허용이 됨. 동전이 무한개이므로 가능
				dp[i] = Math.min(dp[i], dp[i - coin] + 1);
			}
		}
		System.out.println(dp[k] == MAX ? -1 : dp[k]);
	}
}
