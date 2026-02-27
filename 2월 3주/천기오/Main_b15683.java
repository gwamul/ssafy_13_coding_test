package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_b15683 {
	static int N, M;
	static ArrayList<int[]> cctvs;
	static int[][][] directions = { 
			{}, // 0번 CCTV 없음 (패딩용)
			{ { 0 }, { 1 }, { 2 }, { 3 } }, // 1번 CCTV: 한 방향
			{ { 0, 2 }, { 1, 3 } }, // 2번 CCTV: 상하 / 좌우
			{ { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 0 } }, // 3번 CCTV: ㄴ자 방향
			{ { 0, 1, 3 }, { 0, 1, 2 }, { 1, 2, 3 }, { 0, 2, 3 } }, // 4번: 세 방향
			{ { 0, 1, 2, 3 } } // 5번: 네 방향
	};
	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, -1, 0, 1 };
	static int minBlind = 9999;

	static void dfs(int depth, int[][] visited) {
		if (depth == cctvs.size()) {
			minBlind = Math.min(minBlind, count(visited));
			return;
		}
		int[] cctv = cctvs.get(depth);
		int[][] dirs = directions[cctv[0]];
		for (int[] dirCombo : dirs) {
			int[][] copy = new int[N][M];
			for (int i = 0; i < N; i++) {
				System.arraycopy(visited[i], 0, copy[i], 0, M);
			}
			for (int dir : dirCombo) {
				watch(copy, cctv[1], cctv[2], dir);
			}
			dfs(depth + 1, copy);
		}

	}

	static int count(int[][] map) {
		int cnt = 0;
		for (int[] row : map) {
			for (int val : row) {
				if (val == 0)
					cnt++;
			}
		}
		return cnt;
	}

	static void watch(int[][] board, int x, int y, int dir) {
		while (true) {
			x += dx[dir];
			y += dy[dir];
			if (x < 0 || y < 0 || x >= N || y >= M || board[x][y] == 6)
				break; // 벽이면 중단
			if (board[x][y] == 0)
				board[x][y] = 7; // 감시 가능 영역 표시
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		int[][] map = new int[N][M];
		cctvs = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] > 0 && map[i][j] < 6) {
					cctvs.add(new int[] { map[i][j], i, j });
				}
			}
		}
		dfs(0, map);
		System.out.println(minBlind);
	}
}
