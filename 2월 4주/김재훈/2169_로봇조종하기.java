import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * dfs 시간복잡도가 안되서 dp인건 맞음
 *
 * int[][][3] 이렇게 해서 위, 왼 ,오른쪽에서 온 경우를 각각 저장
 *
 * dp[i][j][0]은 dp[i-1][j] 3가지 경우에서 가장 큰 경우를 선택
 * dp[i][j][1]은 max(dp[i][j-1]의 [0], [1] 중에서 큰거 선택
 * -> dp[i][j-1][2]는 제외해야 하는게 오른쪽에서 왔다는 거는 이미 현재 dp를 지났다는 거여서 no
 *
 * 해서 초기값은 맨 윗줄로 하고
 * 다음 줄부터 3번을 보면 된다.
 * 위, 왼, 오
 *
 * 이중 for문을 왼쪽이랑 오른쪽이랑 따로 해줘야 하는 듯?
 * -> 방향에 따라서 아직 초기화가 안된 상황일 수 있기 때문
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nm = br.readLine().trim().split(" ");
        int n =  Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);

        int[][] map = new int[n][m];
        for(int i = 0;i<n;i++){
            String[] line =  br.readLine().trim().split(" ");
            for(int j = 0;j<m;j++){
                map[i][j] = Integer.parseInt(line[j]);
            }
        }

        int[][][] dp = new int[n][m][3];

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                for(int k = 0;k<3;k++){
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        dp[0][0][0] = map[0][0];
        dp[0][0][1] = map[0][0];
        //dp맨 윗줄 초기화
        for(int i = 1;i<m;i++){
            dp[0][i][1] = dp[0][i-1][1] + map[0][i];
        }

        for(int i = 1;i<n;i++){
            for(int j = 0;j<m;j++){
                //위에서 내려오는 건 바로 위 것의 3가지중 최대 + 현재 값
                dp[i][j][0] = Math.max(Math.max(dp[i-1][j][0],dp[i-1][j][1]), dp[i-1][j][2]) + map[i][j];
                //왼쪽에서 오는 건, 왼쪽것의 왼쪽, 위 의 최대값 + 현재값
                if(j-1 >= 0) dp[i][j][1] = Math.max(dp[i][j-1][0], dp[i][j-1][1]) + map[i][j];
            }
            for(int j = m-1;j>=0;j--){
                //오른쪽에서 오는 건, 오른쪽것의 오른쪽, 위 의 최대값 + 현재값
                if(j+1 < m) dp[i][j][2] = Math.max(dp[i][j+1][0], dp[i][j+1][2]) + map[i][j];
            }
        }

        int max = Math.max(Math.max(dp[n-1][m-1][0], dp[n-1][m-1][1]), dp[n-1][m-1][2]);
        System.out.println(max);

    }
}
