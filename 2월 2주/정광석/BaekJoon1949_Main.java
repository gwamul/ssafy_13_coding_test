package saffy_algo.BaekJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BaekJoon1949_Main {
	
	
	static int N;
	static int[] tree;
	static List<Integer>[] roads;
	static int max_man = Integer.MIN_VALUE;
	static boolean[] visited;
	
	static int[][] dp; 
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
	
	
		N = Integer.parseInt(br.readLine());
		tree = new int[N+1];
		visited = new boolean[N+1];
		
		/// dp
		dp = new int[N+1][2];
		//dp 초기화
		for(int i=0; i<N+1; i++) {
			Arrays.fill(dp[i], -1);
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=1;i <= N; i++) {
			tree[i] = Integer.parseInt(st.nextToken());
		}
		
		roads = new ArrayList[N+1];
		for(int i=0; i<=N; i++) {
			roads[i] = new ArrayList<>();
		}
		
		
		
		for(int i=0; i<N-1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			roads[a].add(b);
			roads[b].add(a);
		}
		
//		for(int i=1; i<roads.length; i++) {
//			
//			max_man = sol(i,i,true);
//			//System.out.println(max_man);
//		}
//		
//		
//		
//		System.out.println(max_man);
		System.out.println(Math.max(sol2(0,1,1), sol2(0,1,0)));
		
		
	}
	
	//dfs 완전 탐색
	public static int sol(int cur, int parent, boolean isGood) {
		int totalSum = isGood ? tree[cur] : 0;
		
		for(int child : roads[cur]) {
			if(child == parent) continue;
			
			if(isGood) {
				totalSum += sol(child, cur, false);
			
			}else {
				totalSum += Math.max(sol(child, cur, true), sol(child, cur, false));
			}
		}
		
		return totalSum;
	}
	
	// 상태를 저장할 dp만들기
		// 함수 인자 중 값이 변하는 것을 배열로 만들어
		// cur -> N
		// isGood -> 2, 0 : 일반마을, 1 : 우수마을
	// 초기값으로 채우기
		// 계산 된적이 있는지 파악하기 위해 -1로 초기화
		// 이건 값 계산하는 거니까
	// 함수 입구와 출구에 코드 삽입
		// 함수 시작 부분에서 이미 계산했으면 패스, 
		// 함수 끝 부분에서 결과 저장!
	
	public static int sol2(int root, int cur, int isGood) {
		

		if(dp[cur][isGood] != -1) return dp[cur][isGood];
		
		int totalSum;
		if(isGood == 0) totalSum = 0;
		else totalSum = tree[cur];
		
		for(int child : roads[cur]) {
			if(child == root) continue;
			if(isGood == 1) {
				totalSum += sol2(cur, child, 0);
			}else {
				totalSum += Math.max(sol2(cur, child, 1), sol2( cur, child, 0));
			}
		}
		return dp[cur][isGood] = totalSum;
		
		
		
		
		
	}
	

	
	
	

}
