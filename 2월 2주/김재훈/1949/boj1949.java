import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class boj1949 {

    static int n;
    static int[] people;
    static int[][] dp;
    static List<Integer>[] tree;
    static void recur(int node, int p)
    {
        //이미 왔던 거라면
        if(dp[node][0] != 0 || dp[node][1] != 0) {
            return;
        }

        //리프 노드라면
        if(node != 0 && tree[node].size() == 1) {
            //0 : 자신이 우수가 아닌 경우
            //1 : 우수인 경우
            dp[node][0] = 0;
            dp[node][1] = people[node];
            return ;
        }

        //i번째 노드인 경우
        //자신이 우수 노드라면 자식들이 모두 우수 노드가 아닌 경우
        int sum = 0;
        for(Integer child : tree[node]) {
            if(child !=p) {//자식 노드 라면
                recur(child, node);
                sum += dp[child][0];
            }
        }
        dp[node][1] = sum + people[node];

        //자신이 우수 노드가 아닌 경우 자식의 최대값의 합
        sum = 0;
        for(Integer child : tree[node]) {
            if(child != p) {//자식 노드 라면
                recur(child, node);
                sum += Math.max(dp[child][0], dp[child][1]);
            }
        }
        dp[node][0] = sum;


    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine().trim());

        String[] speople = br.readLine().trim().split(" ");
        people = Arrays.stream(speople).mapToInt(Integer::parseInt).toArray();
        tree = new List[n];
        dp = new int[n][2];
        for(int i = 0;i<n;i++) {
            tree[i] = new ArrayList<>();
        }

        for(int i = 0;i<n-1;i++) {
            String[] ab = br.readLine().trim().split(" ");
            int a = Integer.parseInt(ab[0]);
            int b = Integer.parseInt(ab[1]);

            tree[a-1].add(b-1);
            tree[b-1].add(a-1);
        }

        recur(0,-1);

        System.out.println(Math.max(dp[0][0], dp[0][1]));

    }
}