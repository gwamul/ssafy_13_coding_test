package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon17136_색종이붙이기_2_Main {
	private final static int N = 10;

	static int[][] board = new int[N][N];
	static int[] papers = {0,5,5,5,5,5};
	static int answer = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		
		sol(0,0,0);
		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}
	
	static void sol(int x, int y, int cnt) {
        if (x == 10) {
            answer = Math.min(answer, cnt);
            return;
        }

        if (cnt >= answer) return;

        if (y == 10) {
            sol(x + 1, 0, cnt);
            return;
        }

        if (board[x][y] == 1) {
            for (int i = 5; i >= 1; i--) { 
                if (papers[i] > 0 && check(x, y, i)) {
                    cover(x, y, i, 0);  
                    papers[i]--;
                    sol(x, y + 1, cnt + 1);  
                    
                    papers[i]++;
                    cover(x, y, i, 1);
                }
            }
        } else {
            sol(x, y + 1, cnt);
        }
    }
	
	static boolean check(int x, int y, int len) {
		if(x+len> N || y+len>N) return false;
		
		for(int i=x;  i<x+len; i++) {
			for(int j=y; j<y+len; j++) {
				if(board[i][j] == 0) return false;
			}
		}
		
		return true;
	}
	
	static void cover(int x, int y, int len, int val) {
		for(int i=x;  i<x+len; i++) {
			for(int j=y; j<y+len; j++) {
				board[i][j] = val;
			}
		}
	}

}

