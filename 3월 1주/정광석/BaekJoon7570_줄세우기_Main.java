package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * 어린이들이 놀이터에 한줄로
 * 
 * 옷에 번호표
 * 
 * 번호 순서대로 줄을 세우고 싶음
 * 
 * 줄 서 있는 어린이 중 한명을 선택하여 제일 앞 혹은 제일 뒤로 보낸다.
 * 
 * 어린이가 이동해서 빈자리가 생기근 경우 빈자리 뒤 애들이 한걸음씩 앞으로 걸어서 자리 매꿈.
 * 
 * 52413 > 1을 선택(랜덤) 1 5243 > 4를 선택(랜덤) 1 523 4 
 * 
 * 
 * 
 */
public class BaekJoon7570_줄세우기_Main {
	static int n;
	static int[] board;
	static int[] dp;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int max_increase_cnt = 0;
		n = Integer.parseInt(br.readLine());
		board = new int[n];
		dp = new int[n+1];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			board[i] = Integer.parseInt(st.nextToken());
			dp[board[i]] = dp[board[i]-1]+1;
			
			max_increase_cnt = Math.max(max_increase_cnt, dp[board[i]]);
		}
		//입력
		
		
		
		System.out.println(n-max_increase_cnt);
		
		
	}
}
