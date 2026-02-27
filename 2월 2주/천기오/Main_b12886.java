package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_b12886 {
	static int n;

	static boolean bfs(int[] stones) {
		int a = stones[0];
		int b = stones[1];
		int c = stones[2];
		int total = a + b + c;
		// 3 그룹 중 한 그룹은 전체에서 두 그룹을 빼면 구할 수 있다.
		boolean[][] visited = new boolean[1501][1501];
		if ((a + b + c) % 3 != 0)
			return false;
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] { a, b, c });
		visited[a][b] = true;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			a = cur[0];
			b = cur[1];
			c = cur[2];
			if (a == b && b == c)
				return true;

			// 개수가 다르다면 분배를 시도한다. 
			// 3가지 방식 모두 확인해야함.
			
			if (a != b) {
				int na = a > b ? a - b : a + a;
				int nb = a > b ? b + b : b - a;

				if (!visited[na][nb]) {
					q.add(new int[] { na, nb, c });
					visited[na][nb] = true;
				}
			}
			if (b != c) {
				int nb = c > b ? b + b : b - c;
				int nc = c > b ? c - b : c + c;
				if (!visited[a][nb]) {
					q.add(new int[] { a, nb, nc });
					visited[a][nb] = true;
				}
			}
			if (c != a) {
				int na = a > c ? a - c : a + a;
				int nc = a > c ? c + c : c - a;
				if (!visited[na][b]) {
					q.add(new int[] { na, b, nc });
					visited[na][b] = true;
				}
			}
		}

		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int ans = 0;
		int[] stones = new int[3];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 3; i++) {
			stones[i] = Integer.parseInt(st.nextToken());
		}
		System.out.println(bfs(stones) ? 1 : 0);
	}
}
