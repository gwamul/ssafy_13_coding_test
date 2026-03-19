package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct로봇청소기 {
	// k= 로봇청소기 대수? l번반복
	static int n, k, l;
	static int[][] board;
	static int[][] cleaners;
	static boolean[][] hasCleaner;
	static int t;

	static int[] dx = { 0, 1, 0, -1 }, dy = { 1, 0, -1, 0 };

	static boolean outOfIdx(int x, int y) {
		return x < 0 || x >= n || y < 0 || y >= n;
	}

	// 다음위치 찾는 bfs
	static int[] bfs(int sx, int sy) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { sx, sy, 0 });

		boolean[][] visited = new boolean[n][n];
		visited[sx][sy] = true;

		int[] bestTarget = new int[] { sx, sy };
		int minDist = Integer.MAX_VALUE;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int cx = cur[0];
			int cy = cur[1];
			int cdist = cur[2];

			if (cdist > minDist) {
				break;
			}

			// 먼지를 찾은 경우
			if (board[cx][cy] > 0) {
				// 더 짧은 거리를 최초로 찾은 경우 무조건 갱신
				if (cdist < minDist) {
					bestTarget[0] = cx;
					bestTarget[1] = cy;
					minDist = cdist;
				}
				// 거리가 같을 때만 우선순위(행 작은 순 -> 열 작은 순) 비교 후 갱신
				else if (cdist == minDist) {
					if (cx < bestTarget[0] || (cx == bestTarget[0] && cy < bestTarget[1])) {
						bestTarget[0] = cx;
						bestTarget[1] = cy;
					}
				}

				continue;
			}

			for (int d = 0; d < 4; d++) {
				int nx = cx + dx[d];
				int ny = cy + dy[d];

				if (outOfIdx(nx, ny) || board[nx][ny] == -1 || hasCleaner[nx][ny] || visited[nx][ny])
					continue;

				visited[nx][ny] = true;
				q.offer(new int[] { nx, ny, cdist + 1 });
			}
		}

		return bestTarget;
	}

	static int check(int x, int y) {
		int max = 0;
		int maxDir = 0;
		for (int d = 0; d < 4; d++) {
			int acc = 0;
			int[] dirs = { d, (d + 1) % 4, (d + 3) % 4 };
			for (int dir : dirs) {
				int nx = x + dx[dir];
				int ny = y + dy[dir];
				if (!outOfIdx(nx, ny) && board[nx][ny] != -1) {
					acc += Math.min(20, board[nx][ny]);
				}
			}
			if (acc > max) {
				maxDir = d;
				max = acc;
			}
		}
		return maxDir;
	}

	static int result() {
		int sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == -1)
					continue;
				sum += board[i][j];
			}
		}
		return sum;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		t = 0;
		StringBuilder sb = new StringBuilder();
		board = new int[n][n];
		cleaners = new int[k][2];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				// -1 물건, 먼지 1~100
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		hasCleaner = new boolean[n][n];
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 2; j++) {
				cleaners[i][j] = Integer.parseInt(st.nextToken()) - 1;
			}
			hasCleaner[cleaners[i][0]][cleaners[i][1]] = true;
		}
		while (t < l) {
			// 1. 청소기 이동 (가장 가까운 위치 = bfs), 같은 위치라면 행번호 열번호 작은 값. 청소기도 보드 위에 표기할것
			for (int i = 0; i < k; i++) {
				int x = cleaners[i][0];
				int y = cleaners[i][1];
				hasCleaner[x][y] = false;
				int[] next = bfs(x, y);
				hasCleaner[next[0]][next[1]] = true;
				cleaners[i] = next;
			}
			// 2. 청소 (자기 위치 왼쪽 위쪽 오른쪽) - 가장 먼지합 많은 순서, 오른쪽,아래쪽,왼쪽,위쪽
			for (int i = 0; i < k; i++) {
				int x = cleaners[i][0];
				int y = cleaners[i][1];
				int dir = check(x, y);
				int[] dirs = { (dir + 1) % 4, dir, (dir + 3) % 4 };
				board[x][y] = Math.max(0, board[x][y] - 20);
				for (int d : dirs) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					if (outOfIdx(nx, ny) || board[nx][ny] == -1)
						continue;
					board[nx][ny] = Math.max(0, board[nx][ny] - 20);
				}
			}
			// 3. 먼지 축적
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] > 0) {
						board[i][j] += 5;
					}
				}
			}
			// 4. 먼지 확산
			int[][] nBoard = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] != 0) {
						nBoard[i][j] = board[i][j];
						continue;
					}
					int acc = 0;
					for (int d = 0; d < 4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						if (outOfIdx(nx, ny) || board[nx][ny] == -1)
							continue;
						acc += board[nx][ny];
					}
					nBoard[i][j] = acc / 10;
				}
			}
			board = nBoard;
			// 다음 시간으로 넘어간다. 결과 저장
			t++;
			sb.append(result()).append('\n');
		}
		System.out.println(sb);
	}
}
