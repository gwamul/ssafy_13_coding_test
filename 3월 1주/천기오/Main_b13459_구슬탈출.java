package week5;

import java.io.*;
import java.util.*;

public class Main_b13459_구슬탈출 {
	static int n, m;
	static char[][] board;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	// 구슬을 구멍이나 벽을 만날때까지 이동시킨다. 최종 좌표와 이동 길이 리턴
	static int[] move(int x, int y, int d) {
		int cnt = 0;
		while (true) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			if (board[nx][ny] == '#')
				break;
			x = nx;
			y = ny;
			cnt++;
			if (board[x][y] == 'O')
				break;
		}
		return new int[] { x, y, cnt };
	}

	// BFS 문제이지만 까다로운 구현 문제.
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new char[n][m];
		boolean[][][][] visited = new boolean[n][m][n][m];
		int rx = 0, ry = 0, bx = 0, by = 0;
		int tx = 0, ty = 0;
		for (int i = 0; i < n; i++) {
			String line = br.readLine();
			for (int j = 0; j < m; j++) {
				char c = line.charAt(j);
				// 구슬은 좌표를 저장하고 보드에 표현하지 않는다. 그래야 이동 시뮬이 편하다.
				if (c == 'B') {
					bx = i;
					by = j;
					board[i][j] = '.';
				} else if (c == 'R') {
					rx = i;
					ry = j;
					board[i][j] = '.';
				} else if (c == 'O') {
					tx = i;
					ty = j;
					board[i][j] = c;
				} else {
					board[i][j] = c;
				}
			}
		}
		// 문제 핵심 1: 4가지 상태를 활용한 방문 처리. 두 구슬을 모두 저장해야한다.
		visited[rx][ry][bx][by] = true;
		Deque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { rx, ry, bx, by, 0 });
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			rx = cur[0];
			ry = cur[1];
			bx = cur[2];
			by = cur[3];
			// 10회 이상인데 성공하지 못했다면 종료. 불가능함.
			if (cur[4] >= 10)
				break;
			for (int d = 0; d < 4; d++) {
				int[] rNext = move(rx, ry, d);
				int[] bNext = move(bx, by, d);
				// 파란 구슬이 들어가면 안되니까 바로 스킵.
				if (bNext[0] == tx && bNext[1] == ty)
					continue;
				// 종료 조건.
				if (rNext[0] == tx && rNext[1] == ty) {
					System.out.print(1);
					return;
				}
				// 문제 핵심 2: 두 구슬이 겹친 경우 처리하기. 바로 다음 이동위치로 이동하는게 아니라, 먼저 계산하고 겹친다면 한칸 뒤로 빼기를 해야함.
				if (rNext[0] == bNext[0] && rNext[1] == bNext[1]) {
					if (rNext[2] < bNext[2]) {
						bNext[0] -= dx[d];
						bNext[1] -= dy[d];
					} else {
						rNext[0] -= dx[d];
						rNext[1] -= dy[d];
					}
				}
				if (visited[rNext[0]][rNext[1]][bNext[0]][bNext[1]])
					continue;
				visited[rNext[0]][rNext[1]][bNext[0]][bNext[1]] = true;
				q.offer(new int[] { rNext[0], rNext[1], bNext[0], bNext[1], cur[4] + 1 });
			}
		}

		System.out.print(0);
	}
}
