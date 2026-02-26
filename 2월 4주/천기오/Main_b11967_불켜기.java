package week4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_b11967_불켜기 {
	static int N, M;
	static boolean[][] visited, unlock;

	static int[] dx = { -1, 0, 1, 0 }, dy = { 0, 1, 0, -1 };
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 각 방에서 열 수 있는 위치 저장할 리스트
		List<int[]>[][] switches = new ArrayList[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				switches[i][j] = new ArrayList<>();
			}
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			switches[x][y].add(new int[] { a, b });
		}
		
		visited = new boolean[N][N];
		unlock = new boolean[N][N];
		
		// 0,0에서 시작
		visited[0][0] = true;
		unlock[0][0] = true;
		Deque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { 0, 0 });
		
		int answer = 1;
		
		// 일반 bfs와 달리 2가지 경우가 존재함. 
		// 불을 먼저 켜고 도달하는 경우, 도달 가능하지만 불이 켜져있지 않아 나중에 방문하는 경우. 
		// 그래서 순회가 2번 필요하다.
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int x = cur[0];
			int y = cur[1];
			// 1. 현재 방에서 켤수 있는 불부터 켜기
			for (int[] next : switches[x][y]) {
				int nx = next[0];
				int ny = next[1];
				// 이미 불이 켜졌으면 생략
				if (unlock[nx][ny])
					continue;
				unlock[nx][ny] = true;
				answer++;
				// 불이 켜진 방의 4방향 탐색 -> 이미 방문 했다면, 즉 이동 가능한 위치라면 큐에 삽입
				for (int i = 0; i < 4; i++) {
					int nx1 = nx + dx[i];
					int ny1 = ny + dy[i];
					if (nx1 < 0 || ny1 < 0 || nx1 >= N || ny1 >= N)
						continue;
					// 아직 방문하지 않았다면 현재 길과 연결 x. 나중에 방문해야함.
					if (!visited[nx1][ny1])
						continue;
					visited[nx][ny] = true;
					q.offer(new int[] { nx, ny });
					break;
				}

			}
			//2. 현재 위치에서 갈 수 있는 위치의 방 불이 켜져 있다면 후보에 넣기. 이전에 불만 켜고 방문하지 못한 경우 여기에서 방문할거임.
			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if (nx < 0 || ny < 0 || nx >= N || ny >= N)
					continue;
				if (visited[nx][ny])
					continue;
				if (!unlock[nx][ny])
					continue;

				visited[nx][ny] = true;
				q.offer(new int[] { nx, ny });
			}
		}
		System.out.println(answer);
	}
}
