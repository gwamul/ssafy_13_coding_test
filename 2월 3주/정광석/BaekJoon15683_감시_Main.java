package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BaekJoon15683_감시_Main {
	
	static int n, m;
	static int[][] board;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static List<int[]> list;
	static int min_blind;
	static int[][][] cctvs = {
	        {}, 
	        {{0}, {1}, {2}, {3}},                
	        {{0, 2}, {1, 3}},                   
	        {{0, 1}, {1, 2}, {2, 3}, {3, 0}},   
	        {{0, 1, 2}, {1, 2, 3}, {2, 3, 0}, {3, 0, 1}}, 
	        {{0, 1, 2, 3}}
	    };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());	
		m = Integer.parseInt(st.nextToken());	
		board = new int[n][m];
		list = new ArrayList<>();
		min_blind = Integer.MAX_VALUE;

		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] >= 1 && board[i][j] <= 5) {
					list.add(new int[] {board[i][j], i, j});
				}
			}
		}
		
		sol(0);
		System.out.println(min_blind);
	}
	
	static void sol(int cnt) {
		if(cnt == list.size()) {
			min_blind = Math.min(min_blind, checkCnt());
			return;
		}
		
		int[] now = list.get(cnt);
		int type = now[0];
		int x = now[1];
		int y = now[2];
		
		for(int i=0; i<cctvs[type].length; i++) {
			fill(x, y, cctvs[type][i], -1);
			sol(cnt + 1);
			fill(x, y, cctvs[type][i], 1);
		}
	}
	
	static void fill(int x, int y, int[] dirs, int val) {
		for(int d : dirs) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			while(nx >= 0 && nx < n && ny >= 0 && ny < m && board[nx][ny] != 6) {
				if(board[nx][ny] <= 0) {
					board[nx][ny] += val;
				}
				nx += dx[d];
				ny += dy[d];
			}
		}
	}
	
	static int checkCnt() {
		int cnt = 0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) { 
				if(board[i][j] == 0) cnt++;
			}
		}
		return cnt;
	}
}