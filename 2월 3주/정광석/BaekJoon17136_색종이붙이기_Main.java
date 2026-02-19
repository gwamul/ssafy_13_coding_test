package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon17136_색종이붙이기_Main {
	
	private final static int N = 10;
	
	static int[][] board = new int[N][N];
	static int[] papers = {0,5,5,5,5,5};
	
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
		//1이 써진 칸은 색종이로 덮여져야 한다.
		//색종이는 5개 사이즈, 사이즈 별 5개 가지고 있음
		//0이 적힌 칸에는 색종이가 있으면 안됨
		
		
		for(int len = 5; len >=1; len--) {
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(board[i][j] == 1) {
						canCover(i,j, len);
					}
				}
			}	
		}
		int answer = 25;
		for(int i=1; i<=5; i++) {
			if(papers[i] < 0) {
				answer = -1;
				break;
			}
			answer -= papers[i];
		}
		//System.out.println(Arrays.toString(papers));
		System.out.println(answer);
		
		
	}
	
	
	static void canCover(int x, int y, int len) {
		if(check(x,y,len)) {
			cover(x, y, len);
			return;
		}
	}
	
	static boolean check(int x, int y, int len) {
		
		if(papers[len]<=0) return false;
		for(int i=x;  i<x+len; i++) {
			for(int j=y; j<y+len; j++) {
				if(i>= N || j>=N) return false;
				
				if(board[i][j] == 0) return false;
			}
		}
		papers[len] -- ;
		return true;
	}
	
	static void cover(int x, int y, int len) {
		for(int i=x;  i<x+len; i++) {
			for(int j=y; j<y+len; j++) {
				
				
				board[i][j] = 0;
			}
		}
		
	}
}
