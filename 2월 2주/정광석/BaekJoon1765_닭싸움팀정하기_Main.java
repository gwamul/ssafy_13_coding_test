package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class BaekJoon1765_닭싸움팀정하기_Main {

	static int[] parent;
	static List<Integer>[] board;
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		parent = new int[n+1];

		board = new ArrayList[n+1];
		for(int i=0; i<n+1; i++) {
			board[i] = new ArrayList<>();
		}
		for(int i=0; i<=n; i++) parent[i] = i;

		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			char relation = st.nextToken().charAt(0);
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			if(relation=='E') {
				board[a].add(-b);
				board[b].add(-a);

			}
			else {
				board[a].add(b);
				board[b].add(a);
				

			}
			

			

		}
		for(int i=1; i<=n; i++) {
			// 각 학생의 친구/적 에 대해서
			for(Integer a : board[i]) {
				if(a>0) continue;
				//적이라면..
				//System.out.println("i가 " + i + " 일때 " +"a: " + a);
				for(Integer b : board[-a]) {
					if(b>0) continue;
					union(i,-b);
					//System.out.println("i가 " + i + " 일때 " +"a: " + a + "b: " + b);
				}
			}
		}
		
		for(int i=1; i<=n; i++) {
			for(Integer a : board[i]) {
				if(a>0) union(i, a);
			}
		}
		
		Set<Integer> a = new HashSet<>();
		
		for(int i=1; i<=n; i++) {
			a.add(parent[i]);
		}
		
		System.out.println(a.size());
		
		//System.out.println(Arrays.toString(parent));


//		for(int i=0; i<=n; i++) {
//			System.out.println(board[i].toString());
//		}



	}

	public static int find(int a) {
		if(parent[a] == a) return a;
		return parent[a] = find(parent[a]);
	}

	public static void union(int a, int b) {
		int fa = find(a);
		int fb = find(b);
		if(fa>=fb) {
			parent[fa] = fb;
		}else {
			parent[fb] = fa;
		}
	}

	
}
