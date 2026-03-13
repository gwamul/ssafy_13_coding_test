package week6.어항물채우기;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class FishTank {
	int id;
	int[] heights;
	int[] topShapes;

	FishTank(int id, int[] length, int[] shapes) {
		this.id = id;
		heights = length;
		topShapes = shapes;
	}
}

class Candidate {
	int id;
	int col;
	int idx;

	public Candidate(int id, int idx, int col) {
		this.id = id;
		this.idx = idx;
		this.col = col;
	}
}

public class UserSolution {
	int N, W, H;
	FishTank[] fts;
	// HashMap 대신 패턴(key)을 인덱스로 갖는 리스트 배열 사용
	ArrayList<Integer>[] candidates;

	int getPattern(int a, int b, int c) {
		return (a << 4) | (b << 2) | c;
	}

	public void init(int N, int mWidth, int mHeight, int mIDs[], int mLengths[][], int mUpShapes[][]) {
		this.N = N;
		this.W = mWidth;
		this.H = mHeight;

		fts = new FishTank[N];
		candidates = new ArrayList[1 << 7];
		for (int i = 0; i < (1 << 7); i++) {
			candidates[i] = new ArrayList<>();
		}

		for (int i = 0; i < N; i++) {
			fts[i] = new FishTank(mIDs[i], Arrays.copyOfRange(mLengths[i], 0, W),
					Arrays.copyOfRange(mUpShapes[i], 0, W));
		}

		// 어항을 ID 기준으로 오름차순 정렬해두면 이후 우선순위 파악이 편해짐
		Arrays.sort(fts, (a, b) -> a.id - b.id);

		for (int idx = 0; idx < N; idx++) {
			FishTank ft = fts[idx];
			for (int col = 0; col < W - 2; col++) {
				int key = getPattern(ft.topShapes[col], ft.topShapes[col + 1], ft.topShapes[col + 2]);
				// 비트 연산을 통해 객체 생성 없이 idx와 col을 하나의 int로 압축
				int info = (idx << 16) | col;
				candidates[key].add(info);
			}
		}
	}

	boolean isPossible(int idx, int col, int[] len) {
		int[] heights = fts[idx].heights;

		for (int j = 0; j < 3; j++) {
			if (heights[col + j] + len[j] > H)
				return false;
		}

		boolean link12 = Math.max(heights[col], heights[col + 1]) < Math.min(heights[col] + len[0],
				heights[col + 1] + len[1]);
		boolean link23 = Math.max(heights[col + 1], heights[col + 2]) < Math.min(heights[col + 1] + len[1],
				heights[col + 2] + len[2]);

		return link12 && link23;
	}

	public int checkStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
		int cnt = 0;
		int key = getPattern(mDownShapes[0], mDownShapes[1], mDownShapes[2]);

		// int형(원시 타입) 언박싱 순회로 매우 빠름
		for (int i = 0; i < candidates[key].size(); i++) {
			int info = candidates[key].get(i);
			int idx = info >> 16;
			int col = info & 0xFFFF;

			if (isPossible(idx, col, mLengths)) {
				cnt++;
			}
		}
		return cnt;
	}

	// 구조물 설치 우선순위 : 어항의 id가 작을수록 + 왼쪽으로 갈수록 우선순위 높음
	public int addStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
		int key = getPattern(mDownShapes[0], mDownShapes[1], mDownShapes[2]);

		int bestIdx = -1;
		int bestCol = -1;
		int bestId = Integer.MAX_VALUE;

		// 정렬(Sort) 없이 O(N) 선형 탐색으로 최적의 위치 찾기 (객체 생성 X)
		for (int i = 0; i < candidates[key].size(); i++) {
			int info = candidates[key].get(i);
			int idx = info >> 16;
			int col = info & 0xFFFF;

			if (isPossible(idx, col, mLengths)) {
				FishTank ft = fts[idx];
				if (bestIdx == -1) {
					bestIdx = idx;
					bestCol = col;
					bestId = ft.id;
				} else {
					// 1순위: ID가 작은 것
					if (ft.id < bestId) {
						bestIdx = idx;
						bestCol = col;
						bestId = ft.id;
					}
					// 2순위: ID가 같다면 col이 작은 것
					else if (ft.id == bestId && col < bestCol) {
						bestIdx = idx;
						bestCol = col;
						bestId = ft.id;
					}
				}
			}
		}

		if (bestIdx == -1)
			return 0;

		FishTank selected = fts[bestIdx];

		// 1. 기존 패턴 삭제 (설치 전 모양 기준)
		for (int i = -2; i <= 2; i++) {
			int curCol = bestCol + i;
			if (curCol < 0 || curCol + 2 >= W)
				continue;

			int pk = getPattern(selected.topShapes[curCol], selected.topShapes[curCol + 1],
					selected.topShapes[curCol + 2]);
			int targetInfo = (bestIdx << 16) | curCol;

			// Integer 객체 생성(Boxing)을 막기 위해 직접 index를 찾아 지웁니다.
			ArrayList<Integer> list = candidates[pk];
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) == targetInfo) {
					list.remove(j);
					break;
				}
			}
		}

		// 2. 실제 블록 데이터 변경 (heights, topShapes 업데이트)
		for (int i = 0; i < 3; i++) {
			selected.heights[bestCol + i] += mLengths[i];
			selected.topShapes[bestCol + i] = mUpShapes[i];
		}

		// 3. 새로운 패턴 등록 (설치 후 모양 기준)
		for (int i = -2; i <= 2; i++) {
			int curCol = bestCol + i;
			if (curCol < 0 || curCol + 2 >= W)
				continue;

			int nk = getPattern(selected.topShapes[curCol], selected.topShapes[curCol + 1],
					selected.topShapes[curCol + 2]);
			int newInfo = (bestIdx << 16) | curCol;
			candidates[nk].add(newInfo);
		}

		return selected.id * 1000 + (bestCol + 1);
	}

	public Solution.Result pourIn(int mWater) {
		Solution.Result ret = new Solution.Result();
		int bestId = 0;
		int bestHeight = 0;
		int bestUsed = 0;

		// GC 오버헤드를 막기 위해 hCount 배열을 한 번만 할당하여 재사용합니다.
		// H보다 높은 곳은 물이 차지 않으므로 크기는 H + 2로 충분합니다.
		int[] hCount = new int[H + 2];

		for (int i = 0; i < N; i++) {
			FishTank cur = fts[i];

			// 1. 현재 어항의 블록 높이 빈도수 카운팅 (O(W))
			Arrays.fill(hCount, 0); // 매 어항마다 카운트 초기화
			for (int col = 0; col < W; col++) {
				int h = cur.heights[col];
				// H 이상인 블록은 어차피 물을 채울 공간이 없으므로 카운팅에서 제외합니다.
				if (h < H) {
					hCount[h]++;
				}
			}

			int water = mWater;
			int curHighestWater = 0;
			int curUsed = 0;
			int emptyCols = 0; // 현재 row(층)에서 물이 채워질 빈 공간의 수
			boolean flag = false;

			// 2. 누적합 개념을 이용한 O(H) 시뮬레이션
			for (int row = 1; row <= H; row++) {
				// 핵심 로직: 높이가 (row-1)이었던 블록들이 이번 층(row)에서 빈 공간으로 추가됨
				emptyCols += hCount[row - 1];

				// 이번 층(row)을 채우는 데 필요한 물의 양은 emptyCols 와 같습니다.
				if (water >= emptyCols) {
					if (emptyCols != 0) {
						flag = true;
						curHighestWater = row;
						water -= emptyCols;
						curUsed += emptyCols; // mWater - water 와 동일
					}
				} else {
					break; // 물이 한 층을 다 채우지 못하면 즉시 탐색 종료
				}
			}

			// 3. 우선순위 비교 (fts 배열이 ID 오름차순이므로 ID 비교 생략 가능)
			if (flag && curHighestWater > 0) {
				boolean isBetter = false;
				if (bestId == 0)
					isBetter = true;
				else if (curHighestWater > bestHeight)
					isBetter = true;
				else if (curHighestWater == bestHeight && curUsed > bestUsed)
					isBetter = true;

				if (isBetter) {
					bestHeight = curHighestWater;
					bestId = cur.id;
					bestUsed = curUsed;
				}
			}
		}

		ret.ID = bestId;
		ret.height = bestHeight;
		ret.used = bestUsed;

		return ret;
	}
}