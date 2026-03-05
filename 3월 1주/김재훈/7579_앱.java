import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nm = br.readLine().split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        String[] s1 = br.readLine().split(" ");
        String[] s2 = br.readLine().split(" ");

        int[] arrm = new int[n];
        int[] arrc = new int[n];
        int total_cost = 0;
        for(int i=0;i<n;i++){
            arrm[i] = Integer.parseInt(s1[i]);
            arrc[i] = Integer.parseInt(s2[i]);
            total_cost += arrc[i];
        }
        //dp[i][c] = i번째 까지 했을 때, 비용이 c인 경우의 메모리 최대값
        int[][] dp = new int[n][total_cost+1];
        Arrays.fill(dp[0], arrc[0],total_cost+1,arrm[0]);

        for(int i=1;i<n;i++){
            for(int j=0;j<=total_cost;j++){
                if(j-arrc[i]>=0){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-arrc[i]] +  arrm[i]);
                }
                else dp[i][j] = dp[i-1][j];

            }
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0;i<n;i++){
            for(int j=0;j<=total_cost;j++){
                if(dp[i][j] >= m){
                    min = Math.min(min,j);
                }
            }
        }
        System.out.println(min);
    }

}




