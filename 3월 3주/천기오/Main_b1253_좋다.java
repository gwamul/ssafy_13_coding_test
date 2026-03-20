package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_b1253_좋다 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int ans = 0;
		int n = Integer.parseInt(br.readLine());
		int[] seq = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		// 투 포인터 사용 위해서 정렬하기
		Arrays.sort(seq);
		for (int i = 0; i < n; i++) {
			int left = 0;
			int right = n - 1;
			while (left < right) {
				// 현재 조사하는 값을 가리킨다면 skip
				if (left == i) {
					left++;
					continue;
				}
				if (right == i) {
					right--;
					continue;
				}

				// 두 수의 합 검사
				int sum = seq[left] + seq[right];
				if (sum == seq[i]) {
					ans++;
					break;
				} else if (sum > seq[i]) {
					right--;
				} else {
					left++;
				}
			}
		}
		System.out.println(ans);
	}
}
