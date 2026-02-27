package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main_b1655 {
	static int n;
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
		PriorityQueue<Integer> minHeap = new PriorityQueue<>(Collections.reverseOrder());
		int mid = Integer.MAX_VALUE;
		StringBuilder ans = new StringBuilder();
		while(n-- > 0) {
			int now = Integer.parseInt(br.readLine()); 
			// 첫 값일 경우 무조건 가운데
			if(mid == Integer.MAX_VALUE) {
				mid = now;
				ans.append(mid).append('\n');
				continue;
			}
			
			if(now <= mid) {
				minHeap.add(now);
			}
			else {
				maxHeap.add(now);
			}
			
			// 크기를 확인하여 가운데를 다시 찾는다.
			// 사이즈가 더 큰 쪽에서 mid를 뽑는다.
			// 크기가 더 크다는 건, 최소 값이 1개 이상 있다는 의미이므로 비어있는지 확인할 필요 없음
			if(minHeap.size() + 1 < maxHeap.size()) {
				minHeap.add(mid);
				mid = maxHeap.poll();
			}
			else if(minHeap.size() > maxHeap.size() ) {
				maxHeap.add(mid);
				mid = minHeap.poll();
			}
			ans.append(mid).append('\n');
		}
		System.out.println(ans);
	}
}
