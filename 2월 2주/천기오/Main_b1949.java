package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_b1949 {
	static int n;
	static List<Integer>[] graph;
	static int[][] dp;
	static int[] weight;
	static void dfs(int cur, int parent) {
		// 기저 조건이면서 초기화
        dp[cur][0] = 0;
        dp[cur][1] = weight[cur];
        
		for(int child : graph[cur]) {
			if(child == parent) continue;
			dfs(child, cur);
			
            // cur 선택 X → child 선택O/선택X 중 더 큰 값을 선택
            dp[cur][0] += Math.max(dp[child][0], dp[child][1]);

            // cur 선택 O → child 반드시 선택X
            dp[cur][1] += dp[child][0];
		}
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		graph = new ArrayList[n+1];
		dp = new int[n+1][2];
		weight = new int[n+1];
		for(int i=1;i<=n;i++) graph[i] = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= n; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }
		for(int i=1;i<n;i++) {
			st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            graph[b].add(a);
		}
		dfs(1,0);
		System.out.println(Math.max(dp[1][0], dp[1][1]));
	}
}
