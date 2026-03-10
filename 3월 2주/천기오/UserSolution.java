package week6.pro;

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
	Map<Integer, Candidate>[] candidates;

	int getPattern(int a, int b, int c) {
		return (a << 4) | (b << 2) | c;
	}

	public void init(int N, int mWidth, int mHeight, int mIDs[], int mLengths[][], int mUpShapes[][]) {
		this.N = N;
		W = mWidth;
		H = mHeight;
		fts = new FishTank[N];
		candidates = new Map[1 << 7];
		for (int i = 0; i < (1 << 7); i++) {
			candidates[i] = new HashMap<>();
		}
		for (int i = 0; i < N; i++) {
			fts[i] = new FishTank(mIDs[i], Arrays.copyOfRange(mLengths[i], 0, W),
					Arrays.copyOfRange(mUpShapes[i], 0, W));
		}
		Arrays.sort(fts, (a, b) -> a.id - b.id);

		for (int idx = 0; idx < N; idx++) {
			FishTank ft = fts[idx];
			for (int i = 0; i < W - 2; i++) {
				int key = getPattern(ft.topShapes[i], ft.topShapes[i + 1], ft.topShapes[i + 2]);
				int compressed = ft.id * 1000 + (i+1);
				candidates[key].put(compressed, new Candidate(ft.id, idx, i));
			}
		}

	}

	boolean isPossible(int idx, int i, int[] len) {
		int[] heights = fts[idx].heights;
 
        boolean isOk = true;
        for (int j = 0; j < 3; j++) {
            // 높이가 넘치지 않는지 검사.
            isOk &= (heights[i + j] + len[j] <= H);
        }
		// 연결이 되는지 검사
		// 0번과 1번 블록 연결
		boolean link12 = Math.max(heights[i], heights[i + 1]) < Math.min(heights[i] + len[0], heights[i + 1] + len[1]);
		// 1번과 2번 블록 연결
		boolean link23 = Math.max(heights[i + 1], heights[i + 2]) < Math.min(heights[i + 1] + len[1],
				heights[i + 2] + len[2]);
		
		return isOk && link12 && link23;
	}

	public int checkStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
		int cnt = 0;


		int key = getPattern(mDownShapes[0], mDownShapes[1], mDownShapes[2]);		
		for (Candidate c : candidates[key].values()) {
			if (isPossible(c.idx, c.col, mLengths)) {
				cnt++;
			}
		}

		return cnt;
	}

	// 구조물 설치 우선순위 : 어항의 id가 작을수록 + 왼쪽으로 갈수록 우선순위 높음
	public int addStructures(int mLengths[], int mUpShapes[], int mDownShapes[]) {
		int key = getPattern(mDownShapes[0], mDownShapes[1], mDownShapes[2]);
		Candidate best = null;

		
		for (Candidate c : candidates[key].values()) {
			// 실제 모양과 높이가 현재 시점에서도 유효한지 재검사
			if (isPossible(c.idx, c.col, mLengths)) {
				if (best == null) {
					best = c;
				} else {
					// 1순위: ID가 작은 것
					if (c.id < best.id) {
						best = c;
					}
					// 2순위: ID가 같다면 col이 작은 것
					else if (c.id == best.id && c.col < best.col) {
						best = c;
					}
				}
			}
		}

		if (best == null)
			return 0;
		
		// --- 실제 데이터 업데이트 ---
		FishTank selected = fts[best.idx];
		
		// 1. 기존 패턴 삭제 (설치 전 모양 기준)
		for (int i = -2; i <= 2; i++) {
		    int curCol = best.col + i;
		    // 어항 범위를 벗어나면 스킵
		    if (curCol < 0 || curCol + 2 >= W) continue;
		    
		    int pk = getPattern(selected.topShapes[curCol], selected.topShapes[curCol + 1], selected.topShapes[curCol + 2]);
		    candidates[pk].remove(selected.id * 1000 + curCol + 1);
		}

		// 2. 실제 블록 데이터 변경 (heights, topShapes 업데이트)
		for (int i = 0; i < 3; i++) {
		    selected.heights[best.col + i] += mLengths[i];
		    selected.topShapes[best.col + i] = mUpShapes[i];
		}

		// 3. 새로운 패턴 등록 (설치 후 모양 기준)
		for (int i = -2; i <= 2; i++) {
		    int curCol = best.col + i;
		    if (curCol < 0 || curCol + 2 >= W) continue;
		    
		    int nk = getPattern(selected.topShapes[curCol], selected.topShapes[curCol + 1], selected.topShapes[curCol + 2]);
		    candidates[nk].put(selected.id * 1000 + curCol + 1, new Candidate(selected.id, best.idx, curCol));
		}
		
		return selected.id * 1000 + (best.col + 1);
	}

	// 물 채우기 우선순위 : 물의 양이 많을 수록 + 어항의 id가 작을수록 우선순위 높음
	public Solution.Result pourIn(int mWater) {
	    Solution.Result ret = new Solution.Result();
	    int bestId = -1;
	    int bestUsed = -1;
	    int bestHeight = -1;
	
	    for (FishTank ft : fts) {
	        // 1. 현재 어항의 높이들을 복사해서 정렬 (O(W log W))
	        // 또는 init에서 미리 정렬된 배열을 관리하면 더 빠릅니다.
	        int[] sortedH = ft.heights.clone();
	        Arrays.sort(sortedH);
	
	        int low = 1, high = H;
	        int currentHeight = 0;
	        int totalUsed = 0;
	
	        // 2. 이분 탐색으로 물을 채울 수 있는 최대 높이(mid) 찾기
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            long required = 0;
	
	            // 높이가 mid보다 낮은 칸들에 대해 필요한 물의 양 계산
	            // sortedH에서 mid보다 작은 값들의 개수와 합을 구함
	            for (int h : sortedH) {
	                if (h < mid) {
	                    required += (mid - h);
	                } else {
	                    break; // 정렬되어 있으므로 이후는 mid보다 큼
	                }
	                if (required > mWater) break; // 이미 감당 불가면 탈출
	            }
	
	            if (required <= (long)mWater && required > 0) {
	                currentHeight = mid;
	                totalUsed = (int)required;
	                low = mid + 1;
	            } else if (required == 0) {
	                // mid가 이미 모든 바닥보다 낮거나 같아서 물을 못 채우는 경우
	                low = mid + 1;
	            } else {
	                high = mid - 1;
	            }
	        }
	
	        if (totalUsed == 0) continue;
	
	        // 3. 우선순위 비교 (높이 -> 양 -> ID)
	        boolean shouldChange = false;
	        if (bestId == -1) {
	            shouldChange = true;
	        } else if (currentHeight > bestHeight) {
	            shouldChange = true;
	        } else if (currentHeight == bestHeight && totalUsed > bestUsed) {
	            shouldChange = true;
	        }
	
	        if (shouldChange) {
	            bestUsed = totalUsed;
	            bestId = ft.id;
	            bestHeight = currentHeight;
	        }
	    }
	
	    ret.ID = bestId;
	    ret.height = bestHeight;
	    ret.used = bestUsed;
	    return ret;
	}
}