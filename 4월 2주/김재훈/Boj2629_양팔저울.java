import java.io.*;
import java.util.*;

/**
 * 전략
 * 
 * 1. 추의 차이를 구한다는게 팁
 * 2. Set을 활용해서 중복을 제거하면서 하면 시간내에 가능??
 */

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int[] w = new int[n];
		for(int i = 0;i<n;i++) {
			w[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(w);
		
		int t = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		st = new StringTokenizer(br.readLine(), " ");
		for(int test = 0;test<t;test++) {
			int ball = Integer.parseInt(st.nextToken());
			
			Set<Integer> diff = new HashSet<>();
			diff.add(0);
			diff.add(w[0]);
			for(int i = 1;i<n;i++) {
				Iterator<Integer> iter = diff.iterator();
				List<Integer> temp = new ArrayList<>();
				while(iter.hasNext()) {
					int next = iter.next();
					temp.add(next+w[i]);
					temp.add(Math.abs(next-w[i]));
				}
				temp.forEach(e -> diff.add(e));
			}
			
			if(diff.contains(ball)) sb.append("Y ");
			else sb.append("N ");
		}
		
		System.out.println(sb.toString());
	}

}
