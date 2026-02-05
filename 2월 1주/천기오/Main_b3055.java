package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 확장 문제
 * 고슴도치의 이동 큐와 물의 이동 큐를 병행하여 실행한다.
 */
public class Main_b3055 {
	static int n, m;
	static char[][] board;
	static int[] dx = {0,1,0,-1}, dy = {1,0,-1,0};
	static boolean OutOfBound(int x, int y) {
		return x < 0 || x >= n || y < 0 || y >= m;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		int sx=-1,sy=-1,ex=-1,ey=-1;
		board = new char[n][m];
		Queue<int[]> waters = new ArrayDeque<>();
		for(int i=0;i<n;i++) {
			board[i] = br.readLine().toCharArray();
			for(int j=0;j<m;j++) {
				if(board[i][j] == 'D') {
					ex = i;
					ey = j;
				}
				else if(board[i][j] == 'S') {
					sx = i;
					sy = j;
					board[i][j] = '.';
				}
				else if(board[i][j] == '*') {
					waters.offer(new int[] {i,j});
				}
			}
		}
		Deque<int[]> q = new ArrayDeque<>();
		boolean[][] visited = new boolean[n][m];
		q.offer(new int[] {sx,sy});
		visited[sx][sy] = true;
		int t = 0;
		while(!q.isEmpty()) {
			// 먼저 물부터 업데이트
			int waterSize = waters.size();
			for(int i=0;i<waterSize;i++) {
				int[] cur = waters.poll();
				for(int d=0;d<4;d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];
					if(OutOfBound(nx, ny) || board[nx][ny] == '*' || board[nx][ny] == 'X' || board[nx][ny] == 'D') continue;
					board[nx][ny] = '*';
					waters.offer(new int[] {nx,ny});
				}
			}

			// 비버 이동
			int size = q.size();
			for(int i=0;i<size;i++) {
				int[] cur = q.poll();
				if(cur[0] == ex && cur[1] == ey) {
					System.out.println(t);
					return;
				}
				for(int d=0;d<4;d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];
					if(OutOfBound(nx, ny) || board[nx][ny] == '*' || board[nx][ny] == 'X') continue;
					if(visited[nx][ny]) continue;
					visited[nx][ny] = true;
					q.offer(new int[] {nx,ny});
				}
			}
			t++;
		}
		System.out.println("KAKTUS");
	}
}
