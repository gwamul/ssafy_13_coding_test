package week8.직선열차;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Train {
	int id;
	int s;
	int interval;
	int e;
	boolean isDeleted;
	Train(int mId, int start, int mInterval, int end) {
		s = start;
		id = mId;
		interval = mInterval;
		e = end;
	}
}

class UserSolution {
	int n, size;
	boolean[][] graph;
	List<Train> trains;
	Map<Integer, Integer> map;

	boolean isConnected(Train a, Train b) {
		// 1. 두 열차가 공통으로 달릴 수 있는 유효 구간 설정
		int start = Math.max(a.s, b.s);
		int end = Math.min(a.e, b.e);

		// 유효 구간이 성립하지 않으면 아예 겹치지 않음
		if (start > end)
			return false;

		// 2. 열차 A가 유효 구간 내에서 처음으로 정차하는 역(firstA) 계산
		int firstA = a.s;
		if (firstA < start) {
			// start 이상이 되기 위해 몇 번(ia) 더 가야 하는지 올림 계산
			int jump = (start - firstA + a.interval - 1) / a.interval;
			firstA += jump * a.interval;
		}

		// 3. 열차 A의 정차역을 순회하며 열차 B의 조건에 맞는지 확인 (최대 b.interval 번만 반복)
		// b.interval은 최대 50이므로 사실상 O(1) 시간에 가까움
		for (int i = 0; i < b.interval; i++) {
			long currentP = (long) firstA + (long) i * a.interval;

			// 공통 유효 구간을 벗어나면 더 이상 볼 필요 없음
			if (currentP > end)
				break;

			// currentP가 열차 B의 정차역인지 수학적으로 확인
			// (현재 역 - B의 시작역)이 B의 간격으로 나누어 떨어지면 환승 가능!
			if ((currentP - b.s) % b.interval == 0) {
				return true;
			}
		}

		return false;
	}

	public void init(int N, int K, int mId[], int sId[], int eId[], int mInterval[]) {
		map = new HashMap<>();
		// 넉넉하게 잡기 500 + 250
		graph = new boolean[800][800];
		trains = new ArrayList<>();
		size = 0;
		for (int k = 0; k <= K; k++) {
			Train train = new Train(mId[k], sId[k], mInterval[k], eId[k]);
			trains.add(train);
			map.put(mId[k], size++);
		}
		for (int i = 0; i < K; i++) {
			for (int j = i + 1; j < K; j++) {
				if (isConnected(trains.get(i), trains.get(j))) {
					graph[i][j] = true;
					graph[j][i] = true;
				}
			}
		}
		return;
	}

	public void add(int mId, int sId, int eId, int mInterval) {
		Train train = new Train(mId, sId, mInterval, eId);
		for (int i = 0; i < size; i++) {
			if (isConnected(train, trains.get(i)) && !trains.get(i).isDeleted) {
				graph[i][size] = true;
				graph[size][i] = true;
			}
		}
		trains.add(train);
		map.put(mId, size++);
		return;
	}

	public void remove(int mId) {
		int idx = map.get(mId);
		trains.get(idx).isDeleted = true;
		for (int i = 0; i < size; i++) {
			graph[i][idx] = false;
			graph[idx][i] = false;
		}
		return;
	}

	boolean isReachable(Train a, int x) {
		if (x > a.e || x < a.s)
			return false;
		return (x - a.s) % a.interval == 0;
	}

	public int calculate(int sId, int eId) {
		Queue<int[]> q = new ArrayDeque<>();
		boolean[] visited = new boolean[size + 1];
		for (int i = 0; i < size; i++) {
			if (isReachable(trains.get(i), sId)) {
				q.offer(new int[] { i, 0 });
				visited[i] = true;
			}
		}
		while (!q.isEmpty()) {
			int[] cur = q.poll();
			if(isReachable(trains.get(cur[0]), eId)) {
				return cur[1];
			}
			for (int next = 0; next < size; next++) {
				if (graph[cur[0]][next] && !visited[next]) {
					q.offer(new int[] { next, cur[1] + 1 });
					visited[next] = true;
				}
			}
		}
		return -1;
	}
}