package week6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_ct개구리의여행 {
	static int n;
	static char[][] board;
	static int r1, c1, r2, c2;
	static int[] dx = { 1, -1, 0, 0 }, dy = { 0, 0, -1, 1 };
	static int MAX = 1_000_000_000;

	static boolean outOfIdx(int a, int b) {
		return a < 0 || a >= n || b < 0 || b >= n;
	}

	static boolean hasEnemy(int x, int y, int p, int d) {
		int nx = x;
		int ny = y;
		for (int i = 0; i < p; i++) {
			nx += dx[d];
			ny += dy[d];
			if (board[nx][ny] == '#')
				return true;
		}
		return false;
	}

	// . 안전 S 미끄러움 # 천적 세가지 타입 돌
	static int solve() {
		int[][][] dist = new int[n][n][6];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Arrays.fill(dist[i][j], MAX);
			}
		}
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);
		pq.offer(new int[] { r1, c1, 1, 0 });
		dist[r1][c1][1] = 0;
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			int x = cur[0], y = cur[1], p = cur[2], t = cur[3];
			// 조기 종료
			if (x == r2 && y == c2)
				return t;
			if (dist[x][y][p] < t)
				continue;
			// 점프력 감소
			for (int decP = p - 1; decP >= 1; decP--) {
                if (dist[x][y][decP] > t + 1) { // 1초 소요하여 점프력 변경
                    dist[x][y][decP] = t + 1;
                    pq.offer(new int[] { x, y, decP, t + 1 });
                }
            }
			// 점프력 증가
			if (p + 1 < 6 && dist[x][y][p + 1] > t + (p + 1) * (p + 1)) {
				dist[x][y][p + 1] = t + (p + 1) * (p + 1);
				pq.offer(new int[] { x, y, p + 1, t + (p + 1) * (p + 1) });
			}
			// 4방향 이동
			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d] * p;
				int ny = y + dy[d] * p;
				if (outOfIdx(nx, ny) || hasEnemy(x, y, p, d) || board[nx][ny] == 'S' || dist[nx][ny][p] <= t + 1)
					continue;
				dist[nx][ny][p] = t + 1;
				pq.offer(new int[] { nx, ny, p, t + 1 });
			}

		}

		return -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		board = new char[n][n];
		for (int i = 0; i < n; i++) {
			board[i] = br.readLine().toCharArray();
		}
		int q = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		while (q-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			r1 = Integer.parseInt(st.nextToken()) - 1;
			c1 = Integer.parseInt(st.nextToken()) - 1;
			r2 = Integer.parseInt(st.nextToken()) - 1;
			c2 = Integer.parseInt(st.nextToken()) - 1;
			sb.append(solve()).append('\n');
		}

		System.out.println(sb);
	}
}
