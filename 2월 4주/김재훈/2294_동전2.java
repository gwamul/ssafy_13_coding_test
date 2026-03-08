import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *dp[i] : i원일때의 최소 동전의 개수
 *
 * dp[i] = min(dp[i], dp[i-동전가치] + 1);
 *
 * 초기화 : set으로 돌면서 dp[i] = 1로 초기화
 *
 * 나머지는 Max_Value로 초기화
 *
 * 근데 한번 dp를 계산할 때마다 set을 다 봐야해서 100번 걸림
 *
 * 그래도 100만 정도라 가능
 *
 * 만약 dp[k]가 Max_value에서 안변했으면 -1
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nk =  br.readLine().split(" ");
        int n =  Integer.parseInt(nk[0]);
        int k =  Integer.parseInt(nk[1]);
        int[] dp = new int[100_001];
        Arrays.fill(dp, 200_000);
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < n; i++) {
            int coin = Integer.parseInt(br.readLine());
            set.add(coin);
            dp[coin] = 1;
        }

        for(int i = 1; i <= k; i++) {
            for(int coin : set) {
                if(i- coin >= 0) dp[i] = Math.min(dp[i], dp[i-coin]+1);
            }

        }

        System.out.println(dp[k] == 200_000 ? -1 : dp[k]);
    }
}
