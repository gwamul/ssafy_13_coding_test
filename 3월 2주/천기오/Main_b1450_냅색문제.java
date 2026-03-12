package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_b1450_냅색문제 {
	static int N, C;
	static int[] arr;

	static void dfs(int idx, int end, long sum, List<Long> list) {
		if (sum > C)
			return; // 가지치기
		if (idx == end) {
			list.add(sum);
			return;
		}
		dfs(idx + 1, end, sum, list); // 선택 X
		dfs(idx + 1, end, sum + arr[idx], list); // 선택 O
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		// 집합을 둘로 나눠서 풀이한다. 2^30 -> 2^15 * 2 로 처리 가능해짐.
		List<Long> left = new ArrayList<>();
		List<Long> right = new ArrayList<>();
		dfs(0, N / 2, 0, left);
		dfs(N / 2, N, 0, right);
		
		// 투포인터 사용을 위한 정렬
		Collections.sort(left);
		Collections.sort(right);
		System.out.println(left.toString());
		int l = 0, r = right.size() - 1;
		int answer = 0;
		// 투포인터. 단순 비교는 2^15 * 2^15 = 2^30 터져버린다.
		// 2^15만에 가능
		while (l < left.size() && r >= 0) {
			if (left.get(l) + right.get(r) <= C) {
				answer += (r + 1);
				l++;
			} else {
				r--;
			}
		}
		System.out.println(answer);
	}
}
