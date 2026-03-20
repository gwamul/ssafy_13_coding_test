package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b16918_봄버맨 {
	static char[][] map;
	static int R, C, N;

	static char[][] explode(char[][] src) {
		char[][] result = new char[R][C];
		int[] dx = new int[] { -1, 0, 1, 0 };
		int[] dy = new int[] { 0, 1, 0, -1 };
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++)
				result[i][j] = 'O';
		}
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (src[i][j] == 'O') {
					result[i][j] = '.';
					for (int d = 0; d < 4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						if (nx < 0 || nx >= R || ny < 0 || ny >= C)
							continue;
						result[nx][ny] = '.';
					}
				}
			}
		}
		return result;
	}

	static void output(char[][] src, StringBuilder sb) {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				sb.append(src[i][j]);
			}
			sb.append('\n');
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		map = new char[R][C];
		for (int i = 0; i < R; i++) {
			map[i] = br.readLine().toCharArray();
		}

		StringBuilder sb = new StringBuilder();

		if (N == 1) {
			output(map, sb);
		} else if (N % 2 == 0) {
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++)
					sb.append('O');
				sb.append("\n");
			}
		} else if (N % 4 == 3) {
			map = explode(map); // 1차 폭발
			output(map, sb);
		} else if (N % 4 == 1) {
			map = explode(explode(map)); // 2차 폭발
			output(map, sb);
		}
		System.out.println(sb);
	}
}
