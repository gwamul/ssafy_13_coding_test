package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class BaekJoon10775_공항_Main {
	static int g,p;
	static int[] gates;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 1 2 3 4
		g = Integer.parseInt(br.readLine());
		p = Integer.parseInt(br.readLine());
		int answer = -1;
		gates = new int[g+1];
		
		for(int i=0; i<=g; i++) gates[i] = i;
		int cnt = 0;
		
		for(int i=1; i<=p; i++) {
			//gate로 입력 받는 게이트 번호 이하의 게이트에 비행기를 채울 수 있다.
			int gate = Integer.parseInt(br.readLine());
			
			
			if(answer == -1) {
				int fgate = find(gate);
				if(fgate != 0) {
					union(fgate-1, fgate);
					cnt ++;
				}else {
					answer = cnt;
				}
				
			}
			
			
			//System.out.println(Arrays.toString(gates));
		}
		
		
		if(answer == -1) System.out.println(p);
		else {
			System.out.println(answer);
		}
		//System.out.println(answer);
		//System.out.println(Math.min(cnt, p));
	}
	
	static int find(int a) {
		if(a == gates[a]) return a;
		return gates[a] = find(gates[a]);
	}
	
	static void union(int a, int b) {
		int fa = find(a);
		int fb = find(b);
		
		if (fa>fb) {
			gates[fa] = fb;
			
		}else {
			gates[fb] = fa;
		}
	}
}
