import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Warrior {
	int x;
	int y;
	boolean isFreeze;
	boolean isDead;

	Warrior(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Main_ct메두사와전사들 {
	static int n, m;
	static int[][] board;
	static Warrior[] warriors;
	// 이동 우선 순위 = {상,하,좌,우}
	static int[] dx = { -1, 1, 0, 0 }, dy = { 0, 0, -1, 1 };
	// 부채꼴 확장을 위해서 선언
	static int[] dlx = { 0, 0, -1, 1 }, dly = { -1, 1, 0, 0 };
	static int[] drx = { 0, 0, 1, -1 }, dry = { 1, -1, 0, 0 };
	static int sx, sy, ex, ey, x, y;

	// 메두사의 이동경로 findRoad로 채워질 예정
	static int[] nextX, nextY;

	// 메두사의 시야 범위. 전사 이동 시 이쪽은 이동 불가
	static boolean[][] visible;

	static boolean outOfBound(int a, int b) {
		return a < 0 || a >= n || b < 0 || b >= n;
	}

	// 메두사의 이동경로를 bfs로 찾는다. 처음 한번만 실행.
	static void findRoad() {
		boolean[][] visited = new boolean[n][n];
		int[][] px = new int[n][n];
		int[][] py = new int[n][n];
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { sx, sy, 0 });
		visited[sx][sy] = true;
		int len = 0;
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int cx = cur[0], cy = cur[1];
			int cost = cur[2];
			// 조기 종료
			if (cx == ex && cy == ey) {
				len = cost;
				break;
			}

			for (int d = 0; d < 4; d++) {
				int nx = cx + dx[d];
				int ny = cy + dy[d];
				if (outOfBound(nx, ny) || board[nx][ny] != 0 || visited[nx][ny])
					continue;
				q.offer(new int[] { nx, ny, cost + 1 });
				visited[nx][ny] = true;
				px[nx][ny] = cx;
				py[nx][ny] = cy;
			}
		}

		// 길이 없다면 -1 리턴 후 종료
		if (len == 0) {
			System.out.println(-1);
			System.exit(0);
		}

		// 역추적
		nextX = new int[len];
		nextY = new int[len];

		int cx = ex;
		int cy = ey;
		int idx = len;
		while (idx-- > 0) {
			nextX[idx] = cx;
			nextY[idx] = cy;
			int tmpx = cx;
			int tmpy = cy;
			cx = px[tmpx][tmpy];
			cy = py[tmpx][tmpy];
		}
	}

	// 맨해튼 거리 구하기. 전사는 장애물도 건널 수 있음
	static int getDist(int a, int b) {
		return Math.abs(a - x) + Math.abs(b - y);
	}

	// 전사의 다음 위치 리턴 -> 첫번째 이동과 두번째 이동 dx dy를 다르게 사용해야함
	// bfs가 아니라 우선순위 따라서 4방향 보고 찾기
	static int[] findNextPos(int xx, int yy, int s) {
		int rx = -1, ry = -1;
		for (int d = 0; d < 4; d++) {
			int dir = (s + d) % 4;

			int nx = xx + dx[dir];
			int ny = yy + dy[dir];
			if (!outOfBound(nx, ny) && !visible[nx][ny] && getDist(xx, yy) > getDist(nx, ny)) {
				rx = nx;
				ry = ny;
				break;
			}
		}
		if (rx != -1)
			return new int[] { rx, ry };
		
		// 이동할 수 없다면 null 리턴
		return null;
	}

	// 부채꼴 형태로 확장 (시야 확장 로직과 동일하게 len 사용)
	static void makeShadow(int sx, int sy, int type, int d, boolean[][] shadow) {
		int ax = sx;
		int ay = sy;
		int len = 1; // 전사 바로 뒤부터 시작하므로 두께는 1부터 확장

		while (true) {
			// 중심으로 직진
			ax += dx[d];
			ay += dy[d];

			if (outOfBound(ax, ay))
				break;

			// 가운데 그림자 처리
			shadow[ax][ay] = true;

			// 좌측 그림자 확장 (type이 -1일 때)
			if (type < 0) {
				int lx = ax;
				int ly = ay;
				for (int i = 0; i < len; i++) { // len만큼 좌측 대각선으로 쭉 채움
					lx += dlx[d];
					ly += dly[d];
					if (outOfBound(lx, ly))
						break;
					shadow[lx][ly] = true;
				}
			}

			// 우측 그림자 확장 (type이 1일 때)
			if (type > 0) {
				int rx = ax;
				int ry = ay;
				for (int i = 0; i < len; i++) { // len만큼 우측 대각선으로 쭉 채움
					rx += drx[d];
					ry += dry[d];
					if (outOfBound(rx, ry))
						break;
					shadow[rx][ry] = true;
				}
			}

			len++; // 한 칸 전진할 때마다 그림자의 너비도 증가
		}
	}

	// 메두사가 해당 방향으로 바라보았을 때, 전사 몇 명이 굳나?
	static int cntWarriors(int d) {
		// boolean 대신 int 배열로 해당 칸의 전사 수를 셉니다.
		int[][] warriorCnt = new int[n][n];
		boolean[][] shadow = new boolean[n][n];
		visible = new boolean[n][n];
		int freeze = 0;

		for (Warrior w : warriors) {
			if (w.isDead)
				continue;
			warriorCnt[w.x][w.y]++; // 전사의 수를 누적
		}

		// 여기서 메두사 시야 범위 체크
		int ax = x;
		int ay = y;
		int len = 1;
		while (true) {
			// 가운데 값임. 이게 바운드를 벗어났다면 탐색을 종료해야함
			ax += dx[d];
			ay += dy[d];

			if (outOfBound(ax, ay))
				break;

			// 가운데 확인
			if (!shadow[ax][ay]) {
				visible[ax][ay] = true;
				if (warriorCnt[ax][ay] > 0) { // 전사가 1명 이상 있다면
					freeze += warriorCnt[ax][ay]; // 존재하는 전사 수만큼 더해줌
					makeShadow(ax, ay, 0, d, shadow); // 그림자는 한 번만 생성하면 됨
				}
			}

			int lx = ax;
			int ly = ay;
			// 좌 확인
			for (int i = 0; i < len; i++) {
				lx += dlx[d];
				ly += dly[d];
				if (outOfBound(lx, ly))
					break;
				if (!shadow[lx][ly]) {
					visible[lx][ly] = true;

					if (warriorCnt[lx][ly] > 0) {
						freeze += warriorCnt[lx][ly];
						makeShadow(lx, ly, -1, d, shadow);
					}
				}
			}

			int rx = ax;
			int ry = ay;
			// 우 확인
			for (int i = 0; i < len; i++) {
				rx += drx[d];
				ry += dry[d];
				if (outOfBound(rx, ry))
					break;
				if (!shadow[rx][ry]) {
					visible[rx][ry] = true;

					if (warriorCnt[rx][ry] > 0) {
						freeze += warriorCnt[rx][ry];
						makeShadow(rx, ry, 1, d, shadow);
					}
				}
			}

			len++;
		}

		return freeze;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder answer = new StringBuilder();
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new int[n][n];
		warriors = new Warrior[m];

		st = new StringTokenizer(br.readLine());
		sx = Integer.parseInt(st.nextToken());
		sy = Integer.parseInt(st.nextToken());
		ex = Integer.parseInt(st.nextToken());
		ey = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < m; i++) {
			int wx = Integer.parseInt(st.nextToken());
			int wy = Integer.parseInt(st.nextToken());
			warriors[i] = new Warrior(wx, wy);
		}

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 메두사는 항상 최단경로, 집과 공원은 무조건 다른 좌표
		// 일단 메두사의 이동경로를 먼저 구하자
		findRoad();

		// 메두사가 공원에 갈때 까지 시뮬레이션
		int end = nextX.length - 1;
		int t = 0;
		x = sx;
		y = sy;
		while (t < end) {
			// 1. 메두사의 이동
			x = nextX[t];
			y = nextY[t];

			// 이동 시 겹치는 전사는 '모두' 사망
			for (Warrior w : warriors) {
				if (!w.isDead && w.x == x && w.y == y) {
					w.isDead = true;
				}
			}

			// 2. 메두사의 시선
			int dir = 0;
			int maxCnt = -1;
			for (int d = 0; d < 4; d++) {
				int curCnt = cntWarriors(d);
				if (maxCnt < curCnt) {
					maxCnt = curCnt;
					dir = d;
				}
			}
			
			int freeze = cntWarriors(dir);
			// visible 확인 후 실제로 얼리기
			for (Warrior w : warriors) {
				if (w.isDead)
					continue;
				if (visible[w.x][w.y])
					w.isFreeze = true;
			}
			
			int dead = 0;
			int move = 0;

			// 3 & 4. 전사들의 이동 및 공격
			for (Warrior w : warriors) {
				if (w.isDead)
					continue;
				if (w.isFreeze) {
					w.isFreeze = false;
					continue;
				}
				
				// 2번의 이동, i가 몇번재 이동인지를 체크하는 변수임
				for(int i=0;i<2;i++) {
					int[] next = findNextPos(w.x, w.y, i*2);
					if (next == null)
						continue;

					w.x = next[0];
					w.y = next[1];
					move++;

					// 메두사에게 도달했다면 공격
					if (w.x == x && w.y == y) { 
						dead++;
						w.isDead = true;
						break;
					}
				}
			}

			t++;
			answer.append(move).append(' ').append(freeze).append(' ').append(dead).append('\n');
		}
		answer.append(0);
		// 최종 출력
		System.out.println(answer);
	}
}
