package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 다양한 사이즈 조각으로 나눠짐
 * 
 * 원하는 피자 크기를 이야기하면
 * 한종류의 피자를 2조각 이상 판매할 때는 반드시 연속된 조각을 잘라서 판매한다.
 * 
 * 판매한 피자 조각의 크기 합이 주문한 크기
 * 
 * 모두 A or 모두 B or A + B
 * 
 * 어느 피자에서 얻어가는지는 상관 없다. 
 * 
 * 원형 자료니까 두배 배열로 해도 될 듯
 * 
 * A에서만 -> 1000 1000
 * B에서만 -> 1000 1000
 * A+B -> 1000 1000 1000 -> 100억
 * 
 * 400만 바이트 
 */
public class BaekJoon2632_피자판매_Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int size = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int m,n;
		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		
		int[] a,b;
		a = new int[m*2];
		b = new int[n*2];
		
		int[] suma = new int[m*2];
		int[] sumb = new int[n*2];
		
		for(int i=0; i<m; i++) {
			int aSize = Integer.parseInt(br.readLine());
			a[i] = aSize;
			a[i+m] = aSize;
		}
		for(int i=0; i<n; i++) {
			int bSize = Integer.parseInt(br.readLine());
			b[i] = bSize;
			b[i+n] = bSize;
		}
		
		int[] counta = new int[2_000_001];
		counta[0] = 1;
		int[] countb = new int[2_000_001];
		countb[0] = 1;
		for(int i=0; i<m; i++) {
			int now = 0;
			for(int j=0; j<m-1; j++) {
				now += a[i+j];
				if(now <= size) counta[now]++;
				else break;
			}
		}
		int totalA = 0;
        for(int i = 0; i < m; i++) totalA += a[i];
        if(totalA <= size) counta[totalA]++;
		for(int i=0; i<n; i++) {
			int now = 0;
			for(int j=0; j<n-1; j++) {
				now += b[i+j];
				if(now <= size) countb[now]++;
				else break;
			}
		}
		int totalB = 0;
        for(int i = 0; i < n; i++) totalB += b[i];
        if(totalB <= size) countb[totalB]++;
		int ans = 0;
		for(int i=0; i<=size; i++) {
			ans += counta[i]*countb[size-i];
		}
		System.out.println(ans);
		
	}
}
