package week6;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_b2457_공주님의정원 {
	static int n;
	static int[][] times;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		times = new int[n][2];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 2; j++) {
				// 월일을 하나의 int로 압축한다. 11월 11일 -> 1111
				int days = Integer.parseInt(st.nextToken()) * 100 + Integer.parseInt(st.nextToken());
				times[i][j] = days;
			}
		}
		Arrays.sort(times, ((a, b) -> {
			if (a[0] != b[0])
				return a[0] - b[0];
			return b[1] - a[1];
		}));
		int curEnd = 301; // 현재까지 덮은 마지막 날짜
		int maxEnd = 0; // 후보 중 가장 멀리 덮을 수 있는 날짜
		int ans = 0; // 선택한 꽃 개수
		for (int i = 0; i < n; i++) {
			int start = times[i][0];
			int end = times[i][1];
			// 현재 구간을 덮을 수 있는 후보
			if (start <= curEnd) { // 연결이 가능한가?
				maxEnd = Math.max(maxEnd, end);
			}
			// 더 이상 덮을 수 없음 -> 선택 확정
			else {
				if (curEnd <= 1130 && maxEnd <= curEnd) {
					System.out.println(0);
					return;
				}
				ans++;
				curEnd = maxEnd;
				i--; // 현재 꽃을 새 기준(curEnd)에서 다시 검사
			}

		}

		// 마지막 구간 확정
		if (curEnd <= 1130) {
			if (maxEnd > curEnd) {
				ans++;
				curEnd = maxEnd;
			}
		}

		if (curEnd <= 1130)
			ans = 0;

		System.out.println(ans);
	}
}
