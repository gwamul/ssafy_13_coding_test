package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_b2146 {
	static int[][] map;
	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, -1, 0, 1 };
	static int n;

	// 섬 번호 no로 맵에 마킹하기
	static void bfs(int x, int y, int no) {
		Deque<int[]> q = new ArrayDeque<>();
		boolean[][] visited = new boolean[n][n];
		q.offer(new int[] { x, y });
		map[x][y] = no;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (nx < 0 || ny < 0 || nx >= n || ny >= n)
					continue;
				if (map[nx][ny] != 1 || visited[nx][ny])
					continue;
				visited[nx][ny] = true;
				map[nx][ny] = no;
				q.offer(new int[] { nx, ny });
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력 및 초기화
		n = Integer.parseInt(br.readLine());
		StringTokenizer st;
		map = new int[n][n];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 0과 1로 이루어져 있으므로 2부터 섬 번호 마킹
		int no = 2;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] == 1) {
					bfs(i, j, no++);
				}
			}
		}

		// 거리 탐색용 bfs 준비
		int min = Integer.MAX_VALUE;
		Deque<int[]> q = new ArrayDeque<>();
		boolean[][] visited = new boolean[n][n];

		// 0:거리, 1:섬 번호 -> 한번에 bfs를 동시에 돌리기 위해서 섬 번호를 사용한다.
		// 어느 섬에서 확장한 값인지를 식별해야하기 때문에. 그렇지 않으면 여러번 bfs를 실행해야함.
		int[][][] dist = new int[n][n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (map[i][j] > 1) {
					q.add(new int[] { i, j, map[i][j] });
					visited[i][j] = true;
					dist[i][j][1] = map[i][j];
				}
			}
		}

		// 섬의 최단 경로를 갱신
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur[0] + dx[dir];
				int ny = cur[1] + dy[dir];
				if (nx < 0 || ny < 0 || nx >= n || ny >= n)
					continue;
				// 바다에서 아직 탐색하지 않은 경우 -> 계속해서 진행하기
				if (map[nx][ny] == 0 && !visited[nx][ny]) {
					q.add(new int[] { nx, ny, cur[2] });
					dist[nx][ny][0] = dist[cur[0]][cur[1]][0] + 1;
					dist[nx][ny][1] = dist[cur[0]][cur[1]][1];
					visited[nx][ny] = true;
				}
				// 도달했는데(이 경우 육지든 바다든 상관 x) 다른 섬에서 확장한 경우 -> 중간에서 만났기 때문에 둘의 길이 합
				else if (visited[nx][ny] && dist[nx][ny][1] != cur[2]) {
					min = Math.min(min, dist[cur[0]][cur[1]][0] + dist[nx][ny][0]);
				}

			}
		}
		System.out.print(min);
	}
}
