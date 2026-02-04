package kjh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Boj4811 {
	static int n;
	static BigInteger[][] dp;
	
	public static void recur(int w, int h) {
		
		
		if(w < 0 || h < 0) return ;
		if(w == 0) {
			dp[w][h] = BigInteger.ONE;
			return;
		}
		//이미 계산한 값은 바로 리턴
		if(dp[w][h] != null) return;

		recur(w-1, h+1);
		recur(w, h-1);
		if(h-1 < 0) {
			dp[w][h] = dp[w-1][h+1];
		}
		else dp[w][h] = dp[w-1][h+1].add(dp[w][h-1]);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String s;
		while(!(s = br.readLine().trim()).equals("0")) {
			n = Integer.parseInt(s);
			
			dp = new BigInteger[n+1][n+1];
			
			//초기값
			//w와 h가 얼마나 남았는가
			int w = n;
			int h = 0;
			
			//dp에 저장하는 값은 w,h 만큼 남았을 때, 경우
			//즉, 답은 dp[w][h]이다. 
			
			//그러면 초기값은? dp[1][1] = 1 -> w먼저 써야 하니깐
			// dp[0][1] -> 1 남은 거
			dp[1][1] = new BigInteger("2");
			dp[0][1] = new BigInteger("1");
			
			//점화식
			//w개와 h개에서 다음은 (w-1, h+1) , (w, h-1) 이 2가지다. 
			//다음 재귀로 넘어갔을 때, w가 0이면 1, w,h < 0 이면 0
			
			recur(w, h);
			
			System.out.println(dp[n][0]);

		}

	}


	
}
