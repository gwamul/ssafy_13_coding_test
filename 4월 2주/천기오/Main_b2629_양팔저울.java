import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int n;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		int[] weights = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		int max = 0;
		for (int i = 0; i < n; i++) {
			weights[i] = Integer.parseInt(st.nextToken());
			max += weights[i];
		}
		boolean[] dp = new boolean[max + 1];
		dp[0] = true; // 차이 0은 항상 가능

		for (int w : weights) {
			
			// 매번 새롭게 배열을 만들어야 함. 중복 사용을 막기 위해.
			// + 현재 무게추 w를 안썼을 때 가능한 경우이기도 함.
			boolean[] next = dp.clone();

			for (int d = 0; d <= max; d++) {
				if (!dp[d])
					continue;

				if (d + w <= max)
					next[d + w] = true;
				next[Math.abs(d - w)] = true;
			}

			dp = next;
		}

		int query = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		while (query-- > 0) {
			int target = Integer.parseInt(st.nextToken());
			sb.append((target <= max) && dp[target] ? 'Y' : 'N').append(' ');
		}
		System.out.println(sb);
	}
}
