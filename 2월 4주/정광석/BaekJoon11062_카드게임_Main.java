package saffy_algo.BaekJoon.골드.골드3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon11062_카드게임_Main {
	/*
	 * 카드게임
	 * N개의 일렬로 놓인 카드
	 * 각 카드에 점수가 쓰임
	 * 한턴에 가장 왼쪽 혹은 가장 오른쪽 한장 가져갈 수 있음
	 * 카드가 0장 남을 때 까지 반복
	 * 점수 : 내가 가져간 점수 합
	 * 
	 * 최선의 전략으로 게임에 임한다.
	 * 
	 * 카드는 1000개
	 * 적힌 수는 1~10000
	 * 
2
4
1 2 5 2
9
1 1 1 1 2 2 2 2 2
먼저 시작하는 근우의 점수를 출력해.
6
8
	 */
	static int[] cards;
	static int max_score;
	static boolean[] visited;
	static int n, total_sum;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			n = Integer.parseInt(br.readLine());
			st = new StringTokenizer(br.readLine());
			cards = new int[n];
			max_score = Integer.MIN_VALUE;
			total_sum = 0;
			visited = new boolean[n];
			for(int i=0; i<n; i++) {
				cards[i] = Integer.parseInt(st.nextToken());
				total_sum += cards[i];
			}
			
			max_score = sol(0,n-1,true);
			sb.append(max_score).append("\n");
		}
		System.out.println(sb.toString());
	}
	
	static int sol(int front, int back, boolean myturn) {
		if(front > back) return 0;
		if(myturn) {
			int pickLeft = cards[front] + sol(front+1, back, false);
			int pickRight = cards[back] + sol(front, back-1, false);
			return Math.max(pickRight, pickLeft);
		}else {
			int pickLeft = sol(front+1, back, true);
			int pickRight = sol(front, back-1, true);
			return Math.min(pickRight, pickLeft);
		}
	}
}
