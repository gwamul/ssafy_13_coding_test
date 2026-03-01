package week3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_b17472 {
	static int N, M;
	static int[][] map;
	// 상 우 하 좌
	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, 1, 0, -1 };
	// Kruskal을 위한 Union-Find
	static int[] parent, rank;

	static int find(int x) {
		if (parent[x] == x)
			return x;
		// 경로 압축
		return parent[x] = find(parent[x]);
	}

	static void union(int x, int y) {
		int px = find(x), py = find(y);
		if (px == py)
			return;
		if (rank[px] < rank[py]) {
			parent[px] = py;
		} else if (rank[px] > rank[py]) {
			parent[py] = px;
		} else {
			parent[py] = px;
			rank[px]++;
		}
	}

	// dfs로 마킹 진행
	static void marking(int x, int y, int idx) {
		if (x < 0 || y < 0 || x >= N || y >= M || map[x][y] != 1)
			return;
		map[x][y] = idx;
		for (int d = 0; d < 4; d++) {
			marking(x + dx[d], y + dy[d], idx);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력 및 초기화
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int idx = 2;
		// 섬 번호 마킹 (2~)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// visited 역할, 마킹 되면 1이 아니므로
				if (map[i][j] == 1) {
					marking(i, j, idx++);
				}
			}
		}

		// 각 섬에서 다른 섬까지 최단거리 구한 후 그래프 구성하기
		int numIslands = idx - 2;
		// 그래프가 주어지지 않았으므로 직접 만들자.
		// 최대 6*6 36개밖에 안되므로 인접 행렬 사용
		int[][] graph = new int[numIslands][numIslands];
		for (int[] row : graph)
			Arrays.fill(row, Integer.MAX_VALUE);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int curIdx = map[i][j];
				if (map[i][j] == 0)
					continue;
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d], ny = j + dy[d];
					int dist = 0;
					while (nx >= 0 && ny >= 0 && nx < N && ny < M && map[nx][ny] != curIdx) {
						if (map[nx][ny] != 0) {
							// 거리가 2 이상인 경우에만 반영
							if (dist >= 2) {
								graph[curIdx - 2][map[nx][ny] - 2] = Math.min(graph[curIdx - 2][map[nx][ny] - 2], dist);
							}
							break;
						}
						nx += dx[d];
						ny += dy[d];
						dist++;
					}
				}
			}
		}
		// UF 초기화
		parent = new int[numIslands];
		rank = new int[numIslands];
		for (int i = 0; i < numIslands; i++) {
			parent[i] = i;
		}

		// {x,y,dist} 로 구성된 pq, 거리가 최소인 값을 poll
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		for (int i = 0; i < numIslands; i++) {
			for (int j = i + 1; j < numIslands; j++) {
				if (graph[i][j] != Integer.MAX_VALUE) {
					pq.offer(new int[] { i, j, graph[i][j] });
				}
			}
		}
		int ans = 0;
		int connected = 1;
		// 크루스칼 - 연결되어 있지 않은 두 노드를 최소값을 가진 edge로 연결한다.
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			if (find(cur[0]) == find(cur[1]))
				continue;
			union(cur[0], cur[1]);
			ans += cur[2];
			connected++;
		}
		System.out.println(connected != numIslands ? -1 : ans);
	}
}
