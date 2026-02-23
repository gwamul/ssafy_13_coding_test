package week3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b16724 {
	static int N, M;
	static char[][] board;
	static int[][] state; // 0=unvisited, 1=visiting, 2=done
	static int answer = 0;

	static void dfs(int x, int y) {
		state[x][y] = 1; // visiting

		int nx = x, ny = y;
		if (board[x][y] == 'U')
			nx--;
		else if (board[x][y] == 'D')
			nx++;
		else if (board[x][y] == 'L')
			ny--;
		else if (board[x][y] == 'R')
			ny++;

		if (state[nx][ny] == 0) {
			dfs(nx, ny);
		} else if (state[nx][ny] == 1) {
			answer++; // cycle
		}

		state[x][y] = 2; // done
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new char[N][M];
		state = new int[N][M];
		for (int i = 0; i < N; i++) {
			String input = br.readLine();
			for (int j = 0; j < M; j++) {
				board[i][j] = input.charAt(j);
			}
		}
		answer = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (state[i][j] == 0) {
					dfs(i, j);
				}
			}
		}
		System.out.println(answer);
	}
}
