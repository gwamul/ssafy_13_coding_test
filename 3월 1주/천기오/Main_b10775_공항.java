package week5;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_b10775_공항 {
	static int[] parent;
    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            parent[rootX] = rootY; 
        }
    }
	static int find(int x) {
		if(x == parent[x]) return x;
		return parent[x] = find(parent[x]);
	}
	// 문제의 핵심은 가장 가까운 게이트중 빈 게이트를 빠르게 찾아야한다. 단순 탐색으로는 O(N^2)
	// G P 가 각각 최대 100_000이므로 최대 O(NlogN) 복잡도 이하여야 통과 가능함.
	// 문제 풀이는 단순하다. union-find를 활용. 가장 가까운 게이트를 parent에 저장하고, 사용한 게이트는 옆 게이트와 union하여 빠르게 다음 게이트를 상수항만에 찾는다.
	public static void main(String args[]) throws Exception
	{
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		 int G = Integer.parseInt(br.readLine());
		 int P = Integer.parseInt(br.readLine());
		 // 0은 존재하지 않는 게이트. 종료 조건
		 parent = new int[G+1];
		 for(int i=1;i<=G;i++) parent[i] = i;
		 int answer = 0;
		 for(int i=0;i<P;i++){
			 int target = Integer.parseInt(br.readLine());
			 int p = find(target);
			 if(p == 0) {
				 break;
			 }
			 union(p, p-1);
			 answer++;
		 }
		 System.out.print(answer);
	}
}
