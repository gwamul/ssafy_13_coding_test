package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b7570_줄세우기 {
	static int n;
	static int[] arr,lis;
	static int findLis() {
		int[] dp = new int[n+1]; // 학생 번호 1~n
		int lisLen = 0;

		for(int i=0;i<n;i++){
		    dp[arr[i]] = dp[arr[i]-1] + 1;
		    lisLen = Math.max(lisLen, dp[arr[i]]);
		}

		return lisLen;
	}

	/**
	 * 정렬 시 최소 움직임은 1씩 증가하는 lis의 길이를 제외한 것이다. 앞 뒤에 순서대로 나머지를 이동하는 게 최소 이동.
	 * 5 2 4 3 1 이라면 lis는 2 3 이므로 최소 3회를 움직여야한다. 4 뒤로 - 1 앞으로 - 5 뒤로
	 * 즉 이 문제는 1씩 증가하는 lis의 길이를 구하는 문제
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		arr = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		int ans = n - findLis();
		System.out.println(ans);
	}
}
