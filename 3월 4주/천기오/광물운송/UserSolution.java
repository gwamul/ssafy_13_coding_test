package week8.광물운송;

import java.util.ArrayList;
import java.util.List;

class Camp {
	int x;
	int y;
	int id;
	int quantity;

	Camp(int x, int y, int id, int quantity) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.quantity = quantity;
	}
}

class UserSolution {
	int l, n;
	int[] capacity;
	int[] parent;
	List<Integer>[][] bucket;
	Camp[] camps;
	int idx, b;

	boolean compare(Camp a, Camp b) {
		return a.x < b.x || (a.x == b.x && a.y < b.y);
	}

	void union(int a, int b) {
		// camp의 x,y가 작은쪽으로 통합하기
		int pa = find(a);
		int pb = find(b);
		if (pa == pb)
			return;
		Camp aCamp = camps[pa];
		Camp bCamp = camps[pb];
		if (compare(aCamp, bCamp)) {
			parent[pb] = pa;
			capacity[pa] += capacity[pb];
		} else {
			parent[pa] = pb;
			capacity[pb] += capacity[pa];
		}
	}

	int find(int a) {
		if (parent[a] == a)
			return a;
		return parent[a] = find(parent[a]);
	}

	void init(int L, int N) {
		l = L;
		n = N;
		capacity = new int[20_001];
		parent = new int[20_001];
		idx = 0;
		b = N / L + 1;
		bucket = new List[b][b];

		for (int i = 0; i < b; i++) {
			for (int j = 0; j < b; j++) {
				bucket[i][j] = new ArrayList<>();
			}
		}
		camps = new Camp[20_001];
	}

	int getDist(Camp a, Camp b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	int addBaseCamp(int mID, int mRow, int mCol, int mQuantity) {
		int id = idx++;
		capacity[id] = mQuantity;
		parent[id] = id;
		Camp cur = new Camp(mRow, mCol, mID, mQuantity);
		camps[id] = cur;
		int br = mRow / l;
		int bc = mCol / l;
		for (int r = br - 1; r <= br + 1; r++) {
			for (int c = bc - 1; c <= bc + 1; c++) {
				if (r < 0 || c < 0 || r >= b || c >= b)
					continue;
				for (int i : bucket[r][c]) {
					Camp adv = camps[i];
					if (getDist(cur, adv) <= l) {
						union(id, i);
					}
				}
			}
		}
		bucket[br][bc].add(id);
		int pCur = find(id);
		return capacity[pCur];
	}

	int findBaseCampForDropping(int K) {
		Camp bestCamp = null;

		for (int i = 0; i < idx; i++) {
			int root = find(i);
			int totalCap = capacity[root]; // 해당 캠프가 속한 그룹의 '총 채굴량'

			// 총 채굴량이 K 이상인 그룹에 속한 베이스캠프만 후보가 됩니다.
			if (totalCap >= K) {
				Camp currentCamp = camps[i];

				if (bestCamp == null) {
					bestCamp = currentCamp;
				} else {
					if (currentCamp.quantity < bestCamp.quantity) {
						bestCamp = currentCamp;
					}
					else if (currentCamp.quantity == bestCamp.quantity && compare(currentCamp, bestCamp)) {
						bestCamp = currentCamp;
					}
				}
			}
		}

		// 조건을 만족하는 캠프가 없으면 -1, 있으면 해당 캠프의 원본 ID 반환
		return bestCamp == null ? -1 : bestCamp.id;
	}

}
