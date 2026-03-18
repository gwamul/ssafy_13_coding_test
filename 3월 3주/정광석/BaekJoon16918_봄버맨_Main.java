package saffy_algo.BaekJoon.실버;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 폭탄이 있는 칸 3초 후 폭발
 * 폭탄 칸 파괴, 빈칸, 인접칸 네칸 도 파괴
 * 
 * 인접 칸에 폭타닝 있는 경우 -> 폭발 없이 파괴 됨. 연쇄 반응이 없음
 * 
 * 
 * 봄버맨 : 면역, 모든 칸 자유롭게
 * 
 * 
 * 맨 처음 일부 칸에 폭탄 설치 모든 시간 같다.
 * 
 * 1초간 x
 * 
 * 폭탄이 설치되지 않은 모든 칸에 폭탄 설치
 * 
 * 1초가 지난 후 3초 전에 설치된 폭탄이 모두 폭발
 *  
 * 
 */
public class BaekJoon16918_봄버맨_Main {

	
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static boolean inBound(int x, int y) {
		if(0<=x && x<r && 0<=y && y<c) return true;
		return false;
	}
	static int r,c;
	static int board[][];
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine());
		int n;
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		board = new int[r][c];
		for(int i=0; i<r; i++) {
			String line = br.readLine().trim();
			for(int j=0; j<c; j++) {
				char t = line.charAt(j);
				if(t=='.') board[i][j] = -1;
				else board[i][j] = 3;
			}
		}
		/*
		0 		1	2	 3	  4	 5	  6	 	7  		 8	 	9   10  11    12
		초기 설치 x   설치  0폭발, 설치 2폭발 설치   4폭발    설치     6폭발 설치  8폭발  설치
		 * 
		 */
		for(int time = 2; time<=n; time++) {
			//time 이 폭탄이 터지는 시점
			if(time % 2 == 0) {
				//짝수 시간마다 빈 자리에 폭탄을 채운다.
				fill(time+3);
			}
			
			// 각 시간 358
			else {
				bomb(time);
			}
//			for(int i=0; i<r; i++) {
//				System.out.println(Arrays.toString(board[i]));
//			}
			
		}
		
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(board[i][j] > 0) sb.append('O');
				else sb.append('.');
			}
			sb.append("\n");
		}
		
		
		
		System.out.println(sb.toString());
	}

	private static void fill(int timeplus3) {
		// TODO Auto-generated method stub
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(board[i][j] == -1) {
					board[i][j] = timeplus3;
				}
			}
		}
		
	}

	private static void bomb(int timeplus3) {
		// TODO Auto-generated method stub
		int[][] result = new int[r][c];
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(board[i][j] == timeplus3) {
					for(int d=0; d<4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						if(inBound(nx, ny)) {
							result[nx][ny] = -1;
						}
					}
					result[i][j] = -1;
				}
			}
		}
		
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(result[i][j] == -1) {
					board[i][j] = -1;
				}
			}
		}
		
	}
}
