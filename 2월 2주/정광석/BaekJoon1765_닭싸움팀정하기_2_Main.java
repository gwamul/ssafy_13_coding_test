package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BaekJoon1765_닭싸움팀정하기_2_Main {
	
	static int[] parent;
	
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		parent = new int[n+1];
		// 이 부분을 추가해주세요!
		parent = new int[n+1];
		for (int i = 1; i <= n; i++) {
		    parent[i] = i;
		}
		int[] enemy = new int[n + 1]; // 각자의 '첫 번째' 원수만 저장

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			char type = st.nextToken().charAt(0);
			int p1 = Integer.parseInt(st.nextToken());
			int p2 = Integer.parseInt(st.nextToken());

			if (type == 'F') {
				union(p1, p2);
			} else {
				// p1의 이전 원수가 있다면, 그 원수와 p2는 친구
				if (enemy[p1] != 0) union(enemy[p1], p2);
				else enemy[p1] = p2;

				// p2의 이전 원수가 있다면, 그 원수와 p1은 친구
				if (enemy[p2] != 0) union(enemy[p2], p1);
				else enemy[p2] = p1;
			}
		}
		int count = 0;
		for (int i = 1; i <= n; i++) {
		    if (parent[i] == i) count++; // find(i) == i 와 동일한 논리
		}
		System.out.println(count);
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
