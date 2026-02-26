package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 게임이론 문제 - 양쪽이 최선의 선택을 했을 때 나올 수 있는 케이스를 확인하는 문제
// 최선의 선택이라는 건 하나의 상태에서 해야하는 행동이 정해져 있다는 의미. 즉 하나의 상태에서 나타나는 경우의 수는 단 한가지 -> dp문제.
public class Main_b11062_카드게임 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		while (T-- > 0) {
			int N = Integer.parseInt(br.readLine());
			int[] cards = new int[N];
			StringTokenizer st = new StringTokenizer(br.readLine());
			int total = 0;
			for (int i = 0; i < N; i++) {
				cards[i] = Integer.parseInt(st.nextToken());
				total += cards[i];
			}
			// dp[l][r] = [l,r] 구간에서 얻을 수 있는 최대 점수 차
			// 구간 dp 문제
			int[][] dp = new int[N][N];

			// 길이가 1인 경우 근우가 선공이므로 혼자 먹음. 카드 점수로 초기화
			for (int i = 0; i < N; i++) {
				dp[i][i] = cards[i];
			}

			for (int len = 2; len <= N; len++) {
				for (int l = 0; l + len - 1 < N; l++) {
					int r = l + len - 1;
					dp[l][r] = Math.max(
							// 왼쪽을 선택한 경우 -> 상대는 [l+1,r] 구간의 최대값
							cards[l] - dp[l + 1][r],
							// 오른쪽을 선택한 경우 -> 상대는 [l,r-1] 구간의 최대값
							cards[r] - dp[l][r - 1]);
				}
			}
			// (총점 + 점수차) / 2 == 근우의 점수
			sb.append((total + dp[0][N - 1]) / 2).append("\n");
		}
		System.out.print(sb);
	}
}
