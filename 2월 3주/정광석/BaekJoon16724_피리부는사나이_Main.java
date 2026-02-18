package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon16724_피리부는사나이_Main {
	
	static char[][] board;
	static int n,m;
	static int[] parent;
	static int ans = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		parent = new int[n*m + 1];
		for(int i=0; i<=n*m; i++) {
			parent[i] = i;
		}
		board = new char[n][m];
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<m; j++) {
				board[i][j] = line.charAt(j);
			}
		}
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				move(i,j);
			}
		}
		
		System.out.println(ans);
		
		debug();
		
		
	}
	
	static  void debug() {
		for(int i=0; i<n; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
	static int find(int a) {
		if(a == parent[a]) return parent[a];
		return parent[a] = find(parent[a]);
	}
	
	static void union(int a, int b) {
		int fa = find(a);
		int fb = find(b);
		if(fa<=fb) {
			parent[fb] = fa;
		}else {
			parent[fa] = fb;
		}
	}
	
	static void move(int x, int y) {
		int curToOne = x*m + y;
		int nextX = x;
		int nextY = y;
		
		if(board[x][y] == 'U') nextX--;
		else if(board[x][y] == 'D') nextX++;
		else if(board[x][y] == 'R') nextY++;
		else if(board[x][y] == 'L') nextY--;
		
		int nextToOne = nextX*m + nextY;
		
		if(find(curToOne) != find(nextToOne)) {
			union(curToOne, nextToOne);
		}else {
			ans ++;
		}
	}
}
