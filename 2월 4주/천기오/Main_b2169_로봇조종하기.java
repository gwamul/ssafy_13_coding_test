package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 재방문 금지 제약으로 풀 수 있는 문제.
public class Main_b2169_로봇조종하기 {
	static int n, m;
	static int[][] board, dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		dp = new int[n][m];
		
		// 0번 row는 무조건 왼쪽에서 오른쪽으로만 갈 수 있음. 재방문 금지 제약으로 오른쪽에서 오는경우는 불가능
		dp[0][0] = board[0][0];
		for (int i = 1; i < m; i++) {
			dp[0][i] = dp[0][i - 1] + board[0][i];
		}
		
		for (int i = 1; i < n; i++) {
			int[] left = new int[m];
			int[] right = new int[m];
		
			
			// 왼 -> 오
			// 0번째는 위에서 오는 경우만 존재.
			left[0] = dp[i - 1][0] + board[i][0];
			for (int j = 1; j < m; j++) {
				// 위에서 오는 경우와 왼쪽에서 오는 경우 중 더 큰 값에 더하기
				left[j] = Math.max(dp[i - 1][j], left[j - 1]) + board[i][j];
			}

			// 오 -> 왼. 왼쪽을 구할 떄와 동일한 방식.
			right[m - 1] = dp[i - 1][m - 1] + board[i][m - 1];
			for (int j = m - 2; j >= 0; j--) {
				right[j] = Math.max(dp[i - 1][j], right[j + 1]) + board[i][j];
			}

			// 왼쪽에서 오는 경우와 오른쪽에서 오는 경우 중 더 큰값
			// 처음에는 왼쪽, 오른쪽, 위에서 오는 경우로 해서 틀렸음. 위에서 오는 경우는 왼쪽, 오른쪽을 구할 때 같이 고려해야한다.
			for (int j = 0; j < m; j++) {
				dp[i][j] = Math.max(left[j], right[j]);
			}
		}
		System.out.println(dp[n - 1][m - 1]);
	}
}
