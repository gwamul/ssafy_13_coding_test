package saffy_algo.BaekJoon.골드.골드3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 기차 : 맨 앞 기관차 1대가 손님이 탄 객차 여러칸을 끌고 간다.
 * 기관차가 고장나면 안됨
 * 
 * 기관차 고장에 대비하여 역에 소형 기관차 3대 배치
 * 
 * 소형 기관차는 적은 객차를 끌 수 있다.
 * 
 * 
 * 객차 모두를 나눠 끌 수는 없다.
 * 
 * 소형 기관차들이 어떤 객차를 끌고 가는 것이 좋을까?
 * - 
 * 
 * 소형 기관차가 최대로 끌 수 있는 객차 수 미리 정해놓고, 그보다 많은 객차는 끌 수 없도록. 최대 객차 수는 같다
 * 3대 이용, 최대한 많은 손님을 운송.
 * 번호가 연속적으로 이어진 객차를 끌게 한다. 기관차 바로 뒤 부터 1번 ~ n번
 * 
 * 3대 운용하여 최대로 운송할 수 있는 손님 수 구하기/
 * 
 * 연속된 3덩어리를 만들고, 각 덩어리의 손님 수 합이 최대가 되야 한다.
 * 정렬 할 수 없다.
 * n == 50000
 * m < 50000/3
 * 
 * 못끄는 열차는 무조건 나온다.
 * 
 * XXX AAA XXX BBB XXX CCC XXX 이런식으로 갈 듯.
 * 이 때 XXX 는 0이어도 됨.
 * i j k 시작점을 잡는다.
 * 
 * 완탐?
 * 
 */
public class BaekJoon2616_소형기관차_Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine()); //객차 전체 개수
		int[] board = new int[n]; // 각 객차 내 손님 수
		int[] sumboard = new int[n+1];
		int[][] dp = new int[4][n+1];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			board[i] = Integer.parseInt(st.nextToken());
			sumboard[i+1] = sumboard[i] + board[i];
		}
		int m = Integer.parseInt(br.readLine()); // 소형 기관차가 최대로 끌 수 있는 객차의 수
		
		//System.out.println(Arrays.toString(sumboard));
		
		for(int i=1; i<4; i++) {
			for(int j = i*m; j<=n; j++) {
				dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j-m] + sumboard[j] - sumboard[j-m]);
				//debug(dp);
			}
		}
		System.out.println(dp[3][n]);
		
		
	}
	static void debug(int[][] board) {
		for(int i=0; i<board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
}
