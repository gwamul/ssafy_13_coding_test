package saffy_algo.BaekJoon.골드.골드1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BaekJoon13459_구슬탈출_Main {
	/*
	 * 파란 구슬은 구멍에 들어가면 안된다.
	 * 구슬은 기울인 방향 끝까지 이동해야 한다.
	 * 11번째에서는 실패
5 5
#####
#...#
#.#.#
#ROB#
#####
	 * 
	 */
	static char[][] board;
	static int n,m;
	static int rx, ry, bx, by, ox, oy;
	static boolean[][][][] visited = new boolean[10][10][10][10];
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new char[n][m];
		
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<m; j++) {
				board[i][j] = line.charAt(j);
				if(board[i][j] == 'R') {
					rx = i;
					ry = j;
				}else if(board[i][j] == 'B') {
					bx = i;
					by = j;
				}
				else if(board[i][j] == 'O') {
					ox = i;
					oy = j;
				}
			}
		}
		
		System.out.println(bfs());
		
	
	}
	
	static int bfs() {
		
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {rx, ry, bx, by, 0});
		visited[rx][ry][bx][by] = true;
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			int rtx = curr[0];
			int rty = curr[1];
			int btx = curr[2];
			int bty = curr[3];
			int cnt = curr[4];
			if(cnt >= 10) continue;
			
			for(int i=0; i<4; i++) {
				int rnx = rtx;
				int rny = rty;
				int bnx = btx;
				int bny = bty;
				
				int bmove = roll(btx, bty, i);
				if(bmove == -1) continue;
				int rmove = roll(rtx, rty, i);
				if(rmove == -1) return 1;
				rnx += rmove*dx[i];
				rny += rmove*dy[i];
				bnx += bmove*dx[i];
				bny += bmove*dy[i];
				if(rnx == bnx && rny == bny) {
					if(bmove < rmove) {
						rnx -= dx[i];
						rny -= dy[i];
					}else {
						bnx -= dx[i];
						bny -= dy[i];
					}
				}
				if(!visited[rnx][rny][bnx][bny]) {
	                visited[rnx][rny][bnx][bny] = true;
	                q.offer(new int[] {rnx, rny, bnx, bny, cnt + 1});
	            }
			}
					
		}
		
		
		
		
		return 0;
	}

	static int roll(int x, int y, int d) {
		int tx = x;
		int ty = y;
		int cnt = 0;
		while(true) {
			int nx = tx + dx[d];
			int ny=  ty + dy[d];
			
			if(board[nx][ny] == '#') break;
			if(nx == ox && ny == oy) return -1;
			
			tx = nx;
			ty = ny;
			cnt++;
		}
		
		return cnt;
	}

	
}
