package week7.도로파괴;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class UserSolution {
	int n, k;
	List<int[]>[] graph;
	Map<Integer, Integer> idx;

	public void init(int N, int K, int mId[], int sCity[], int eCity[], int mTime[]) {
		n = N;
		k = K;
		graph = new ArrayList[n];
		idx = new HashMap<>();
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int i = 0; i < k; i++) {
			graph[sCity[i]].add(new int[] { eCity[i], mTime[i], mId[i] });
			idx.put(mId[i], sCity[i]);
		}
		return;
	}

	public void add(int mId, int sCity, int eCity, int mTime) {
		graph[sCity].add(new int[] { eCity, mTime, mId });
		idx.put(mId, sCity);
		return;
	}

	public void remove(int mId) {
		int target = idx.get(mId);
		for (int i = 0; i < graph[target].size(); i++) {
			if (graph[target].get(i)[2] == mId) {
				graph[target].remove(i);
				break;
			}
		}
		return;
	}

	public int calculate(int sCity, int eCity) {
		int[] dist = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE);
		int[] prev = new int[n];
		prev[sCity] = -1;
		dist[sCity] = 0;
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
			return a[1] - b[1];
		});
		pq.offer(new int[] { sCity, 0 });
		while (!pq.isEmpty()) {
			int[] cur = pq.poll();
			int pos = cur[0];
			int val = cur[1];
			if(pos == eCity) break;
			if (dist[pos] < val)
				continue;
			for (int[] next : graph[pos]) {
				int nPos = next[0];
				int nVal = val + next[1];
				if (dist[nPos] <= nVal)
					continue;
				dist[nPos] = nVal;
				prev[nPos] = pos;
				pq.offer(new int[] { nPos, nVal });
			}
		}
		if (dist[eCity] == Integer.MAX_VALUE)
			return -1;
		int originalShort = dist[eCity];
		int cur = eCity;
		int ret = -1;
		while (cur != sCity) {
			int next = prev[cur];
			pq.clear();
			Arrays.fill(dist, Integer.MAX_VALUE);
			dist[sCity] = 0;
			pq.offer(new int[] { sCity, 0 });
			while (!pq.isEmpty()) {
				int[] cur1 = pq.poll();
				int pos = cur1[0];
				int val = cur1[1];
				if(pos == eCity) break;
				if (dist[pos] < val)
					continue;
				for (int[] next1 : graph[pos]) {
					int nPos = next1[0];
					int nVal = val + next1[1];
					// 지우려는 간선은 검사 패스
					if(pos == next && nPos == cur) continue;
					if (dist[nPos] <= nVal)
						continue;
					dist[nPos] = nVal;
					pq.offer(new int[] { nPos, nVal });
				}
			}
			cur = next;
			if(dist[eCity] == Integer.MAX_VALUE) return -1;
			ret = Math.max(ret, dist[eCity] - originalShort);
		}
		return ret;
	}
}