import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Group {
	int px, py;
	int type = -1;
	List<int[]> students = new ArrayList<>();
}

public class Main_ct민트초코우유 {
	static int[] dx = { -1, 1, 0, 0 }, dy = { 0, 0, -1, 1 };
	static int n, t;
	static int[][] scores;

	// 001 ~ 111 까지 7가지 타입
	static int[][] types;

	static boolean outOfIdx(int x, int y) {
		return x < 0 || x >= n || y < 0 || y >= n;
	}

	static void bfs(int sx, int sy, Group group, boolean[][] visited) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { sx, sy });
		visited[sx][sy] = true;
		group.px = sx;
		group.py = sy;
		group.type = types[sx][sy];
		group.students.add(new int[] { sx, sy });

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int x = cur[0], y = cur[1];
			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];

				if (outOfIdx(nx, ny) || types[nx][ny] != group.type || visited[nx][ny])
					continue;
				visited[nx][ny] = true;
				q.offer(new int[] { nx, ny });
				int pScore = scores[group.px][group.py];
				int nScore = scores[nx][ny];
				// 그룹 추가
				group.students.add(new int[] { nx, ny });
				// 대표자 교체
				// 대표자 선정 신앙심-행-열 값 작은 순서
				if (pScore < nScore) {
					group.px = nx;
					group.py = ny;
				} else if (pScore == nScore) {
					if (nx < group.px || (nx == group.px && ny < group.py)) {
						group.px = nx;
						group.py = ny;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		scores = new int[n][n];
		types = new int[n][n];

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			char[] input = br.readLine().toCharArray();
			for (int j = 0; j < n; j++) {
				types[i][j] |= (1 << "TCM".indexOf(input[j]));
			}
		}

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				scores[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		while (t-- > 0) {
			// 1. 아침시간 - 모든 학생 신앙심 + 1
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					scores[i][j]++;
				}
			}
			// 2. 점심시간 - 신봉 음식 같은 인접학생끼리 그룹화.(상하좌우)

			// 대표자에게 나머지 학생이 신앙심 1 넘기기
			List<Group> groups = new ArrayList<>();
			boolean[][] visited = new boolean[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (!visited[i][j]) {
						Group group = new Group();
						bfs(i, j, group, visited);
						groups.add(group);
					}
				}
			}

			for (Group group : groups) {
				int px = group.px;
				int py = group.py;
				// 그룹원들에게 1씩 신앙심 받기
				scores[px][py] += group.students.size() - 1;
				for (int[] student : group.students) {
					int x = student[0];
					int y = student[1];
					if (x == px && y == py) {
						continue;
					}
					// 신앙심 -1 감소
					scores[x][y]--;
				}
			}

			// 3. 저녁시간 - 대표자의 신앙 전파
			Collections.sort(groups, (a, b) -> {
				// 가짓수가 적은 순서 우선
				int bitA = Integer.bitCount(a.type);
				int bitB = Integer.bitCount(b.type);

				if (bitA != bitB)
					return bitA - bitB;

				// 같은 가짓수라면 대표자 신앙심 큰 순
				if (scores[a.px][a.py] != scores[b.px][b.py])
					return scores[b.px][b.py] - scores[a.px][a.py];

				// 마지막으로는 행/렬 값이 작은 순서
				if (a.px != b.px)
					return a.px - b.px;

				return a.py - b.py;
			});

			boolean[][] isChanged = new boolean[n][n];
			for (Group group : groups) {
				int x = group.px;
				int y = group.py;

				// 대표자가 전파당한 경우 전파를 하지 않고 넘어감.
				if (isChanged[x][y])
					continue;

				int X = scores[x][y] - 1;
				scores[x][y] = 1;

				int d = (X + 1) % 4;

				while (X > 0) {
					int nx = x + dx[d];
					int ny = y + dy[d];
					if (outOfIdx(nx, ny))
						break;
					x = nx;
					y = ny;
					if (types[x][y] == group.type) {

						continue;
					}

					int Y = scores[x][y];
					// 강한 전파
					if (X > Y) {
						X -= (Y + 1);
						scores[x][y]++;
						// 덮어쓰기
						types[x][y] = group.type;
					}
					// 약한 전파
					else {
						scores[x][y] += X;
						// 합치기
						types[x][y] |= group.type;
						X = 0;
					}
					// 취향 변경 체크
					isChanged[x][y] = true;
				}
			}

			// 총계
			int[] result = new int[8];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					result[types[i][j]] += scores[i][j];
				}
			}

			// 민트1 초코2 우유4
			int[] order = { 7, 3, 5, 6, 4, 2, 1 };
			for (int cur : order) {
				sb.append(result[cur]).append(' ');
			}
			sb.append('\n');
		}
		System.out.println(sb);

	}
}
