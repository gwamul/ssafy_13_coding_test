package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * 
 * 먹고 있는 메뉴의 절대 수치가 아닌, 음식과 의 상대 수치
 * 
 * 스코빌 지수 5, 2, 8 일 때 느끼는 매운 정도 = 최대 - 최소 = 8 - 2 = 6
 * 
 * 최댓값과 최솟값의 차이를 주헌 고통 지수라고 부른다.
 * 
 * 좋아하는 전문점의 모든 지수 메뉴판
 * 모든 조합을 먹어 보고 싶음
 * 한번 먹어본 조합은 앙ㄴ
 * 
 * 
 * 5 2 8
 * 음식 한개 먹을 때는 어차피 최대 == 최소 니까 무시.
 * 
 * 5 2 = 3
 * 5 8  = 3
 * 2 8 = 6
 * 5 2 8 = 6
 * 3 + 3 + 6 + 6 = 18
 * 
 * 1 4 5 5 6 10
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 역시 모든 조합을 만드는건 말이 안됨 2^300_000
 * 
 * 어차피 최대 최소의 차이만 알면 된다.
 * 
 * 정렬을 하고, 
 * 
 * 투포인터로 줄여가면서, 해당 케이스의 개수를 셀 수 있지 않나?
 * 2 5 8 2개 사이에 낀게 1개(2^1)
 * 5 8 1개 
 * 8 1개
 * 2 5 1개
 * 2 1개
 * 
 * 
 * */
public class BaekJoon15824_너봄에는캡사이신이맛있단다_Main {
	static final int mod = 1_000_000_007;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		int n;
		n = Integer.parseInt(br.readLine());
		int[] board;
		board = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0;i <n; i++) board[i] = Integer.parseInt(st.nextToken());
		
		Arrays.sort(board); //nlogn
		//System.out.println(Arrays.toString(board));
		long sum = 0;
		//시간초과
		// board[0]은 2^(n-1 -0 - 1 ) + 2^(n-2-1) ... 2^(0) 번 빼진다. -> 2(n-1) -1
		// board[n-1] 은 2(n-1-0-1) 2(n-1-1-1) /// 2(0) 번 더해진다. -> 2(n-1)-1
//		for(int i=0; i<n; i++) { // n
//			int front = board[i];
//			for(int j = n-1; j>= i; j--) { //n
//				sum += (board[j] - board[i]) * Math.pow(2, j-i-1) % mod;
//				//stem.out.println(sum);
//			}
//			
//		}
		// 2 ^ n 이 말이 안된다.
		// 
		//System.out.println(sol(0));
		long[] pow = new long[n];
        pow[0] = 1;
        for (int i = 1; i < n; i++) {
            pow[i] = (pow[i - 1] * 2) % mod;
        }
        for (int i = 0; i < n; i++) {
            
            sum += board[i] * pow[i];
            sum -= board[i] * pow[n-1-i];
            sum %= mod;
        }
		System.out.println(sum%mod);
		
	}
	static long sol(int cnt) {
		if(cnt == 0) return 1;
		if(cnt <= 1) return 2;
		long res = 1;
		res = (sol(cnt/2)%mod)*(sol(cnt/2)%mod);
		if(cnt%2 == 1) res <<= 1;
		return (res%mod);
		
		
	}
}
