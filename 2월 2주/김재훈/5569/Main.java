
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Main {

    static int[] dy = {1, 0};
    static int[] dx = {0,1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] wh = br.readLine().trim().split(" ");

        int w = Integer.parseInt(wh[0]);
        int h = Integer.parseInt(wh[1]);

        int[][][][] dp = new int[h][w][2][2];
        //0이 -> 1이 위
        //0이 직전 방향 안바뀜, 1이 직전 방향 바뀜

        //초기화
        //0,0은 0
        //1,0은
        dp[1][0][1][0] = 1;
        //0,1은
        dp[0][1][0][0] = 1;

        for(int i = 2;i<h;i++) {
            dp[i][0][1][0] = 1;
        }

        for(int i = 2;i<w;i++) {
            dp[0][i][0][0] = 1;
        }

        for(int i = 1;i<h;i++) {
            for(int j = 1;j<w;j++) {

                dp[i][j][0][0] = dp[i][j-1][0][0] + dp[i][j-1][0][1];
                dp[i][j][0][1] = dp[i][j-1][1][0];
                dp[i][j][1][0] = dp[i-1][j][1][0] + dp[i-1][j][1][1];
                dp[i][j][1][1] = dp[i-1][j][0][0];

                dp[i][j][0][0] %= 100000;
                dp[i][j][0][1] %= 100000;
                dp[i][j][1][0] %= 100000;
                dp[i][j][1][1] %= 100000;
            }
        }
        int answer = dp[h-1][w-1][0][0] + dp[h-1][w-1][0][1] + dp[h-1][w-1][1][0] + dp[h-1][w-1][1][1];
        answer %= 100000;
        System.out.println(answer);
    }
}




