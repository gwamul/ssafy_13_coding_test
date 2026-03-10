package saffy_algo.SWEA.D5;

import java.util.*;

public class SWEA24703_어항물채우기_Main {
	static class Tank {
		int id, idx;
		int[] structure, bond;


		public Tank(int id, int idx, int[] structure, int[] bond) {
			this.id = id;
			this.idx = idx;
			this.structure = structure;
			this.bond = bond;
		}


		@Override
		public String toString() {
			return "Tank [id=" + id + ", idx=" + idx + ", structure=" + Arrays.toString(structure) + ", bond="
					+ Arrays.toString(bond) + "]";
		}
		
		
	}

	Map<Integer, Tank> board; // ID -> Tank
	int H, W, N;
	Map<Integer, List<int[]>> canBuild;
	Tank[] tanks;
	public void init(int N, int mWidth, int mHeight, int mIDs[], int mLengths[][], int mUpShapes[][]) {
		////System.out.println("init start");
		this.N = N; this.W = mWidth; this.H = mHeight;
		this.board = new TreeMap<>();
		this.canBuild = new HashMap<>();
		this.tanks = new Tank[N];
		for (int i = 0; i < N; i++) {
			int[] length = new int[W];
			int[] shape = new int[W];;
			
			for(int j=0; j<W; j++) {
				length[j] = mLengths[i][j];
			}
			for(int j=0; j<W; j++) {
				shape[j] = mUpShapes[i][j];
			}
			
			Tank t = new Tank(mIDs[i], i, length, shape);
			board.put(mIDs[i], t);
		}

		int ptr = 0;
		for (Tank t : board.values()) {
			tanks[ptr] = t;
			t.idx = ptr++;
			for (int j = 0; j <= W - 3; j++) {
				addPattern(t.id, j);
			}
		}
		////System.out.println("init finish");
	}

	private int getPattern(int[] shapes, int s) {
		return (shapes[s] << 20) | (shapes[s + 1] << 10) | shapes[s + 2];
	}

	private void addPattern(int id, int idx) {
		int key = getPattern(board.get(id).bond, idx);
		canBuild.computeIfAbsent(key, k -> new ArrayList<>()).add(new int[]{id, idx});
	}

	private void removePattern(int id, int idx) {
		int key = getPattern(board.get(id).bond, idx);
		List<int[]> list = canBuild.get(key);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i)[0] == id && list.get(i)[1] == idx) {
					list.remove(i);
					break;
				}
			}
		}
	}

	private boolean isPossible(Tank t, int start, int[] len) {
		for (int i = 0; i < 3; i++) {
			if (t.structure[start + i] + len[i] > H) return false;
		}

		int b0 = t.structure[start];
		int t0 = b0 + len[0];
		int b1 = t.structure[start + 1];
		int t1 = b1 + len[1];
		if (t0 <= b1 || t1 <= b0) return false;
		
		int b2 = t.structure[start + 2];
		int t2 = b2 + len[2];
		if (t1 <= b2 || t2 <= b1) return false;



		return true;
	}

	public int checkStructures(int[] mLengths, int[] mUpShapes, int[] mDownShapes) {
		////System.out.println("check Structure start");
		int key = (mDownShapes[0] << 20) | (mDownShapes[1] << 10) | mDownShapes[2];

		List<int[]> list = canBuild.get(key);

		if (list == null) {
			//System.out.println(0);
			////System.out.println("check Structure finish");
			return 0;
		}

		int cnt = 0;

		for (int[] info : list) {
			if (isPossible(board.get(info[0]), info[1], mLengths)) cnt++;
		}

		//System.out.println(cnt);
		//System.out.println("check Structure finish");
		return cnt;
	}

	public int addStructures(int[] mLengths, int[] mUpShapes, int[] mDownShapes) {
		//System.out.println("add Structure start");
		int key = (mDownShapes[0] << 20) | (mDownShapes[1] << 10) | mDownShapes[2];

		List<int[]> list = canBuild.get(key);
		if (list == null) return 0;

		list.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);

		for (int[] info : list) {
			Tank t = board.get(info[0]);
			int start = info[1];

			if (isPossible(t, start, mLengths)) {
				for (int i = -2; i <= 2; i++) {
					if (start + i >= 0 && start + i <= W - 3) removePattern(t.id, start + i);
				}
				for (int j = 0; j < 3; j++) {
					t.structure[start + j] += mLengths[j];
					t.bond[start + j] = mUpShapes[j];
				}
				for (int i = -2; i <= 2; i++) {
					if (start + i >= 0 && start + i <= W - 3) addPattern(t.id, start + i);
				}
				//System.out.println(t.id * 1000 + start + 1);
				////System.out.println("add Structure finish");
				return t.id * 1000 + start + 1;
			}
		}
		//System.out.println(0);
		//System.out.println("add Structure finish");
		return 0;
	}

	public Solution.Result pourIn(int mWater) {
		//100회 호출
		//System.out.println("pourIn start");
		
//		board.forEach((key, value) -> {
//			System.out.println("key: " + key + " "  + value);
//		});
//
//
//
//
//		canBuild.forEach((key, valueList) -> {
//			System.out.print("Key " + key + " : ");
//
//			
//			for (int[] info : valueList) {
//				
//				System.out.print(Arrays.toString(info) + " ");
//			}
//			System.out.println();
//		});
		Solution.Result ret = new Solution.Result();
		ret.ID = ret.height = ret.used = 0;
		//첫번째 호출 시 , 가장 높게 물 채울 수 있는 곳 : Id 50 height 2 used 1
		//for()
		
		int tempID = 0, tempHeight = 0, tempUsed = 0;
		//System.out.println("mWater: " + mWater);
		for(int i=0; i<tanks.length; i++) {
			int water = mWater;
			
			Tank cur = tanks[i];
			int curshighestwater = 0;
			int curUsed = 0;
			//System.out.println((i+1) +"번째 어항: " + cur);
			boolean flag = false;
			for(int row = 1; row <= H; row ++) {
				int rowSum = 0;
				for(int col = 0; col < W; col++) {
					if(cur.structure[col] < row) {
						rowSum ++;
					}
				}
				//System.out.println("row: " + row + " rowSum: " + rowSum);
				if(water >= rowSum) {
					if (rowSum != 0) {
		                flag = true;
		                curshighestwater = row;  
		                water -= rowSum;
		                curUsed = mWater - water;
		            }
					
				}
				else {
					break;
				}
			}
			if (flag && curshighestwater != -1) {
		        boolean isBetter = false;
		        if (tempID == 0) isBetter = true;
		        else if (curshighestwater > tempHeight) isBetter = true;
		        else if (curshighestwater == tempHeight && curUsed > tempUsed) isBetter = true;
		        // tanks가 ID순 정렬되어 있다면 ID 비교는 생략 가능

		        if (isBetter) {
		            tempHeight = curshighestwater;
		            tempID = cur.id;
		            tempUsed = curUsed;
		        }
		    }
			//System.out.println("cur higest water: " + curshighestwater);
			if(tempHeight < curshighestwater) {
				tempHeight = curshighestwater;
				tempID = cur.id;
				tempUsed = mWater - water;
			}
			//System.out.println("현 상태 " + tempID + " " + tempHeight + " " + tempUsed);			
		}
		//System.out.println("-- pourIn 결과 --");
		//System.out.println(tempID + " " + tempHeight + " " + tempUsed);
		ret.ID = tempID;
		ret.height = tempHeight;
		ret.used = tempUsed;
		


		
		
		
		
		
		
		
		
		
		//System.out.println("pourIn finish");
		return ret;
	}


}