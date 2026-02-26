package week4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

// 전형적인 0-1 bfs문제
public class Main_b13549_숨바꼭질3 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		int[] dist = new int[100001];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		// 진짜 deque로 활용. 앞 뒤로 삽입이 생길거임.
		Deque<Integer> q = new ArrayDeque<>();
		q.offer(N);
		dist[N] = 1;
		while (!q.isEmpty()) {
			
			int cur = q.poll();
			
			// 순간이동을 하는 경우 큐 앞에 삽입
			int next = cur * 2;
			if (next <= 100000 && dist[next] > dist[cur]) {
				q.addFirst(next);
				dist[next] = dist[cur];
			}
			
			// 좌-우 이동시에는 큐 뒤에 삽입
			next = cur + 1;
			if (next <= 100000 && dist[next] > dist[cur]) {
				q.offer(next);
				dist[next] = dist[cur] + 1;
			}
			next = cur - 1;
			if (next >= 0 && dist[next] > dist[cur]) {
				q.offer(next);
				dist[next] = dist[cur] + 1;
			}

		}
		System.out.print(dist[K] - 1);
	}
}
