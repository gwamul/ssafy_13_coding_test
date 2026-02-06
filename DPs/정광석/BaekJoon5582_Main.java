package saffy_algo.BaekJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon5582_Main {
	
	
	static int max_len = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String s1 = br.readLine();
        String s2 = br.readLine();
        
        int n1 = s1.length();
        int n2 = s2.length();
        
        int[][] dp = new int[n1 + 1][n2 + 1];
        int maxLen = 0;
        
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
              
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }else {
                	dp[i][j] = 0;
                }
                
            }
        }
        for(int i=0; i<n1+1; i++) {
        	System.out.println(Arrays.toString(dp[i]));
        }
        System.out.println(maxLen);
    }
		
	
}
