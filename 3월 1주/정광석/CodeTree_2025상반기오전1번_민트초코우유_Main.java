package saffy_algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

public class CodeTree_2025상반기오전1번_민트초코우유_Main {
	/*
	 * 초기엔 셋중하나만 신봉
	 * 다른 사람의 영향 -> TC, CM, MT, TCM 으로 진화 가능하다.
	 * 
	 * 초기 신앙심
	 * 
	 * T일동안 ... 
	 * 아침 점심 저녁이 있음
	 * 
	 * 아침 : 모든 학생 +=1 
	 * 
	 * 점심 : 인접 학생과 신봉 음식이 완전히 같으면 그룹이다.(상하좌우 인접)
	 * 
	 * 그룹 내 대표자 한명 뽑는다. 
	 * - 그룹에서 신앙심이 가장 높은 사람
	 * - 동일한 경우, i가 작은 사람
	 * - 동일한 경우, j가 작은 사람
	 * 그룹원은 신앙심을 1씩 대표자에게 넘긴다.
	 * 
	 * 대표자는 (그룹원수 - 본인=1) 만큼 신앙심이 올라가
	 * 
	 * 저녁 : 대표자가 신앙 전파
	 * 
	 * 단일 음식 : 미트 초코 우유
	 * 이중 : 초코우유, 민트 우유, 민트 초코
	 * 삼중 : 민트초코우유
	 * 
	 * 같은 그룹 내에서 순서
	 * 

	 * 
	 */

	static class Group{

		int GroupType;
		int leaderX;
		int leaderY;
		List<int[]> members;
		public Group(int gt) {
			this.GroupType = gt;
			members = new ArrayList<>();
		}
		void add(int i, int j) {
			members.add(new int[] {i,j});

			if(members.size() == 1) {
				this.leaderX = i;
				this.leaderY = j;
			}else {
				if(belive[i][j] < belive[leaderX][leaderY]) return;
				else if(belive[i][j] > belive[leaderX][leaderY]) {
					leaderX = i;
					leaderY = j;
				}else {
					//같은 경우
					if(leaderX > i) { leaderX = i; leaderY = j; }
					else if(leaderX == i) {
						if(leaderY > j) { leaderX = i; leaderY = j; }
					}
				}
			}

		}


		void lunch() {
			if(members.size() == 1) return;

			for(int[] m : members) {
				if(m[0]== leaderX && m[1] == leaderY) {
					belive[m[0]][m[1]] += members.size()-1;
				}else {
					belive[m[0]][m[1]] -= 1;
				}

			}


		}

		@Override
		public String toString() {
			return "Group [GroupType=" + GroupType + ", leaderX=" + leaderX + ", leaderY=" + leaderY + ", members="
					+ members + "memberCnt: " + members.size() + "]";
		}




	}
	private static final int[][] foodMemo = {
		{0, 5, 4, 6, 4, 5, 6},
		{5, 1, 3, 3, 6, 5, 6},
		{4, 3, 2, 3, 4, 6, 6},
		{6, 3, 3, 3, 6, 6, 6},
		{4, 6, 4, 6, 4, 6, 6},
		{5, 5, 6, 6, 6, 5, 6},
		{6, 6, 6, 6, 6, 6, 6}
	};

	static List<Group>[] groupMap;
	static int[][] food;
	static int[][] belive;
	static int[] dx = {-1,1,0,0}; //위 아래 왼쪽 오른쪽
	static int[] dy = {0,0,-1,1};
	static String[] types = {"T", "C", "M", "CM", "TM", "TC", "TCM"};
	static Map<String, Integer> Food2Num = new HashMap<>();
	static int N,T;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int fn = 0;
		for(String s : types) {
			Food2Num.put(s, fn++);
		}

		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());

		food = new int[N][N];
		for(int i=0; i<N; i++) {
			String line = br.readLine();
			for(int j=0; j<N; j++) {
				food[i][j] = Food2Num.get(String.valueOf(line.charAt(j))); // T : 민트, C : 초코, M : 우유
			}
		}

		belive = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				belive[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for(int i=0; i<T; i++) {
			morning();
			lunch();
			//debug();
			dinner();
			//debug();
			for(int a=6; a>=0; a--) {
				System.out.print(cal(a) + " ");
			}
			System.out.println();
		}
		
		
		
	}
	static void sdebug() {
		for(int i=0; i<N; i++) {
			System.out.println(Arrays.toString(belive[i]));
		}
	}

	static void debug() {

		System.out.println("group Map");
		for(int i=0; i<7; i++) {
			System.out.println("-- groupMap " + i +" --");
			for(Group g : groupMap[i]) {
				System.out.println(g.toString());
			}
		}


		System.out.println("\nBelive Map");
		for(int i=0; i<N; i++) {
			System.out.println(Arrays.toString(belive[i]));
		}
		
		System.out.println("\nFood Map");
		for(int i=0; i<N ;i++) {
			System.out.println(Arrays.toString(food[i]));
		}
		// {"T", "C", "M", "CM", "TM", "TC", "TCM"};
		for(int i=6; i>=0; i--) {
			System.out.print(cal(i) + " ");
		}
		System.out.println();
	}


	static void morning() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				belive[i][j]++;
			}
		}
	}



	@SuppressWarnings("unchecked")
	static void lunch() {
		boolean[][] visited = new boolean[N][N];
		groupMap = new ArrayList[7];
		for(int i=0; i<7; i++) {
			groupMap[i] = new ArrayList<>();
		}

		//그룹 정하고 대표자까지 정하기
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(visited[i][j]) continue;
				bfs(i,j, visited);
			}
		}

		for(int i=0; i<7; i++) {
			if(groupMap[i].size() == 0) continue;
			for(Group g: groupMap[i]) {
				g.lunch();
			}
		}

	}



	private static void bfs(int i, int j, boolean[][] visited) {
		// TODO Auto-generated method stub
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {i,j});
		visited[i][j] = true;
		int type = food[i][j];
		Group g = new Group(type);
		g.add(i, j);
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int tx = temp[0];
			int ty = temp[1];
			for(int d=0; d<4; d++) {
				int nx = tx + dx[d];
				int ny = ty + dy[d];

				if(0<=nx && nx < N && 0<=ny && ny < N) {
					if(visited[nx][ny]) continue;
					if(food[nx][ny] != type) continue;
					visited[nx][ny] = true;
					g.add(nx, ny);
					q.offer(new int[] {nx, ny});

				}
			}

		}
		groupMap[type].add(g);

	}

	static void dinner() {
		boolean[][] visited = new boolean[N][N];
	    
	    int[][] categories = {{0, 1, 2}, {3, 4, 5}, {6}};

	    for (int[] category : categories) {
	        List<Group> combined = new ArrayList<>();
	        
	        for (int type : category) {
	            combined.addAll(groupMap[type]);
	        }

	        if (combined.isEmpty()) continue;

	        combined.sort((g1, g2) -> {
	            int b1 = belive[g1.leaderX][g1.leaderY];
	            int b2 = belive[g2.leaderX][g2.leaderY];
	            if (b1 != b2) return Integer.compare(b2, b1);
	            if (g1.leaderX != g2.leaderX) return Integer.compare(g1.leaderX, g2.leaderX);
	            return Integer.compare(g1.leaderY, g2.leaderY);
	        });

	        for (Group g : combined) {
	            if (visited[g.leaderX][g.leaderY]) continue;
	            
	            int currentBelief = belive[g.leaderX][g.leaderY];
	            if (currentBelief <= 0) continue; // 신앙심이 없으면 전파 불가

	            int dir = currentBelief % 4;
	            int x = currentBelief - 1;
	            
	            belive[g.leaderX][g.leaderY] = 1;
	            
	           // System.out.println(g.GroupType + "타입 리더 " + g.leaderX + " " + g.leaderY + 
	           //                    " 출발, 방향: " + dir + ", 간절함: " + x);
	            
	            propagate(g.GroupType, dir, g.leaderX, g.leaderY, x, visited);
	        }
	    }
	}


	private static void propagate(int gt, int d, int x, int y, int power, boolean[][] visited) {
		// TODO Auto-generated method stub
		int tx = x;
		int ty = y;
		int p = power;
		while(true) {
			
			int nx = tx + dx[d];
			int ny = ty + dy[d];
			if(0<=nx && nx < N && 0<=ny && ny < N) {
				//영역 내에서//
				if(food[nx][ny] == gt) {
					//나랑 같은 신봉 음식
					tx = nx;
					ty = ny;
					continue;
				}

				int targetBelive = belive[nx][ny];
				if(targetBelive < p) {
					//강한 전파에 성공한다.
					p -= (targetBelive+1);
					belive[nx][ny]+=1;
					food[nx][ny] = gt; //완전히 동일한 음식 신봉하기
					visited[nx][ny] = true;
					if(p == 0) return;
				}else if(targetBelive >= p) {
					//약한 전파에 성공한다.

					food[nx][ny] = sumfood(gt, food[nx][ny]);
					belive[nx][ny] += p;
					visited[nx][ny] = true;


					return;
				}
				tx = nx;
				ty = ny;

			}
			else {
				return;
			}
		}

	}


	private static int sumfood(int gt, int i) {
		// TODO Auto-generated method stub
		// {"T", "C", "M", "CM", "TM", "TC", "TCM"};
		return foodMemo[gt][i];
	}
	
	static int cal(int gt) {
		int result = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(food[i][j]==gt) result += belive[i][j];
			}
		}
		return result;
	}
}
