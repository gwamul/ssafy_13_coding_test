import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] acc = new int[N + 1]; // 누적합
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            acc[i + 1] = acc[i] + arr[i];
        }

        int M = Integer.parseInt(br.readLine());
        int B = N - M + 1; // M길이 구간 개수

        int[][] dp = new int[B + 1][4]; // dp[i][k] = 0~i번째 구간까지 k대 최대합

        for (int i = 1; i <= B; i++) { // i번째 구간
            for (int k = 1; k <= 3; k++) {
                // 1. 선택하지 않는 경우
                dp[i][k] = dp[i - 1][k];

                // 2. 선택하는 경우
                int sumM = acc[i + M - 1] - acc[i - 1]; // i번째 구간 합
                if (i - M >= 0) sumM += dp[i - M][k - 1]; // 겹치지 않게 이전 최대합
                else if (k > 1) sumM = 0;

                dp[i][k] = Math.max(dp[i][k], sumM);
            }
        }

        System.out.println(dp[B][3]);
    }
}
