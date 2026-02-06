package saffy_algo.BaekJoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BaekJoon1655_Main {
	static int n;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		PriorityQueue<Integer> left_Q = new PriorityQueue<>(); //음수로 넣으면 맥스큐가 될 수 있다.
		PriorityQueue<Integer> right_Q = new PriorityQueue<>();
		
		
		//Queue 의 길이를 항상 일정하게 맞춰야 하는데 중간값이 두개라면 그 중 작은 걸 출력해야 한다
		//left_Q의 peek이 중간값을 항상 가지고 있도록 한다.
		//m
		
		
		for(int i=0; i<n; i++) {
			int k = Integer.parseInt(br.readLine());
			
			if(left_Q.size() == right_Q.size()) left_Q.add(-k);
			else right_Q.add(k);
			
			check(left_Q, right_Q);
			System.out.println(-left_Q.peek());
		}
		
	}
	
	static void check(PriorityQueue<Integer> lq, PriorityQueue<Integer> rq) {
		
		if(lq.isEmpty() || rq.isEmpty()) return;
		if(-lq.peek() > rq.peek()) {
			int tmp = -lq.poll();
			lq.offer(-rq.poll());
			rq.offer(tmp);
		}
	}
}
