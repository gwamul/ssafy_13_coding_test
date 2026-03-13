import java.io.*;
import java.util.*;

/**
 * dp[i]는 i의 비트마스크 상태에서의 최소값인데 
 * 
 * 점화식이 mask의 1의 개수가 1개씩 증가하면서 비교하는 거니깐 
 * dp[다음 마스크] = Math.min(dp[다음 마스크], dp[현재 마스크] + 해당 일을 했을때의 비용);
 * 
 */


public class Main{
	static int n;
	static int[] dp;
	static int[][] map;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine().trim());
		map = new int[n][n];
		for(int i = 0;i<n;i++) {
			String[] line = br.readLine().trim().split(" ");
			for(int j = 0;j<n;j++) {
				map[i][j] = Integer.parseInt(line[j]);
			}
		}
		
		//20개를 비트마스크로 표현하는 거니깐 2^20
		dp = new int[1 << n];
		Arrays.fill(dp, Integer.MAX_VALUE);
		//초기값 
		dp[0] = 0;

		for(int mask = 0;mask< (1 << n);mask++) {
			//일단 1의 개수가 이번에 일할 사람의 index 번호
			int worker = Integer.bitCount(mask);
			//다음에 할 일 정하기
			for(int job = 0;job<n;job++) {
				if((mask & (1 << job)) == 0) {
					//해당 일을 한 경우에 mask의 상태
					int nextBitMask = mask | (1 << job);
					
					dp[nextBitMask] = Math.min(dp[nextBitMask], dp[mask] + map[worker][job]);
				}
			}
		}
		
		System.out.println(dp[(1 << n)-1]);
	}
}