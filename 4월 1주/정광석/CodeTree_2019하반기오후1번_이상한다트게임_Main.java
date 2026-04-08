package saffy_algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class CodeTree_2019하반기오후1번_이상한다트게임_Main {
	/*
	 * 다트 게임 중심  모두 같고 반지름 차이나는 원판
	 * 반지름 1,2, ... n 으로 차례대로 커지는 원판
	 * 
	 * 반지름 r 이면  r번째 원판	
	 * 각 원판에 m개 정수
	 * m번째 정수 = r,m
	 * 
	 * m개의 정수가 12시 방향 부터 시계방향으로 매겨짐.
	 * 
	 * 
	 * 
	 * 각  원판을 회전 시킨다.
	 * 
	 * 회전은 독립적으로 이루어짐 = 다른 원판에 영향을 주지 않는다
	 * 
	 * 회전 요청 - 
	 * x : 원판 종류
	 * d : 방향
	 * k : 회전 칸 수
	 * 
	 * x : 회전 원판의 번호가 x 의 배수인 경우 회전을 한다는 뜻
	 * d : 시계 반시계,  
	 * k : 몇 칸 회전 시킬지.
	 * 시계방향일 경우 k이면, 1번째 정수가 1+k 정수 위치에 두겠다
	 * 
	 * 회전 시킨 후, 원판에 수가 남아 있으면, 인접하면서 숫자가 같은 수 지운다.
	 * 
	 * 
	 * 인접하다 
	 * = 같은 원판에 있으면 내 왼쪽, 오른쪽
	 * = 나 다음 원판의 같은 위치
	 * = 나 이전 원판의 같은 위치
	 * 맨 앞, 맨 뒤 제외하면 거의 4개씩 인접한다.
	 * 
	 * 
	 * 1. 1~n번 원판 지워지는 수가 없다 = 원판 전체 적힌 수의 평균을 구해서 정규화
	 * 
	 * 평균보다 큰 수는 1 빼고,작은 수는 1 더해주겠다.
	 * 평균과 같은 수는 변형x  원판에 남은 수가  없으면 정규화하지 않는다.
	 * 
	 * 평균 구할 때, 정수로
	 * 
	 * 
	 * q번 회전 시킨 후  게임 판에 남아 있는 수의 총합을 구하는 프로그램
	 */

	static int[] top;
	static int[][] board;
	static int n,m;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {1,-1,0,0};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine());
		int q;
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		q = Integer.parseInt(st.nextToken());
		top = new int[n]; //초기 상태 0 
		board = new int[n][m];


		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}


		for(int i=0; i<q; i++) {
			st = new StringTokenizer(br.readLine());
			int x, d, k;
			x = Integer.parseInt(st.nextToken()); //x의 배수 원판 회전
			d = Integer.parseInt(st.nextToken()); //회전 방향
			k = Integer.parseInt(st.nextToken()); //몇칸


			rotate(x,d,k); //회전 시킨다.
			//debug();
			int count = erase(); //지울 수 있는 건 지운다.

		//	debug();
			if(count == 0) normalize(); // 지울 수 있는 게 없으면 정규화 한다.
		}

		System.out.println(count()); // 다 했으면 수 총합을 구한다.
	}

	private static int count() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				sum += board[i][j];
			}
		}
		return sum;
	}

	private static void normalize() {
		// TODO Auto-generated method stub
		//System.out.println("정규화 시작");
		int sum = 0;
		int cnt = 0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				sum += board[i][j];
				if(board[i][j]!=0) cnt++;
			}
		}
		if(sum == 0) return;
		int avg = sum / cnt;
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				if(board[i][j] == 0 ) continue;
				if(board[i][j] > avg) board[i][j]--;
				else if(board[i][j] < avg) board[i][j]++;
			}
		}
		
	}

	private static int erase() {
	    boolean[][] visited = new boolean[n][m];
	    boolean flag = false;
	    int cnt = 0;

	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < m; j++) {
	            // 이미 지워졌거나 방문했다면 패스
	        	if (getRealVal(i, j) == 0 || visited[i][j]) continue;

	        	if (sol(i, j, visited)) {
	                flag = true;
	            }
	        }
	    }

	    if (flag) {
	        for (int i = 0; i < n; i++) {
	            for (int j = 0; j < m; j++) {
	                if (visited[i][j]) {
	                	setRealVal(i, j, 0);
	                    cnt++;
	                }
	            }
	        }
	    }

	    return cnt;
	}
	// r번째 원판의 가상 위치 idx에 있는 실제 값을 가져옴
	static int getRealVal(int r, int idx) {
	    int realIdx = (idx + top[r] + m) % m;
	    return board[r][realIdx];
	}

	// r번째 원판의 가상 위치 idx에 실제 값을 저장함 (0으로 지울 때 사용)
	static void setRealVal(int r, int idx, int val) {
	    int realIdx = (idx + top[r] + m) % m;
	    board[r][realIdx] = val;
	}
	private static boolean sol(int r, int c, boolean[][] visited) {
	    Queue<int[]> q = new ArrayDeque<>();
	    int target = getRealVal(r, c);
	    q.add(new int[]{r, c});
	    
	    List<int[]> group = new ArrayList<>();
	    boolean[][] localVisited = new boolean[n][m];
	    
	    localVisited[r][c] = true;
	    group.add(new int[]{r, c});

	    while (!q.isEmpty()) {
	        int[] curr = q.poll();

	        for (int d = 0; d < 4; d++) {
	            int nr = curr[0] + dx[d];
	            int nc = (curr[1] + dy[d] + m) % m;

	            if (nr >= 0 && nr < n && !localVisited[nr][nc] && getRealVal(nr, nc) == target) {
	                localVisited[nr][nc] = true;
	                q.add(new int[]{nr, nc});
	                group.add(new int[]{nr, nc});
	            }
	        }
	    }

	    if (group.size() > 1) {
	        for (int[] pos : group) {
	            visited[pos[0]][pos[1]] = true;
	        }
	        return true;
	    }
	    return false;
	}

	private static void rotate(int x, int d, int k) {
		// TODO 회전 시킨다. 진짜 회전 시키면 머리  아프니까, top 배열로 관리하겠다.
		for(int i=x; i<=n; i+=x) {
			//i-1을 관리해야 함
			//d == 0 -> 시계방향
			if(d==0) {
				top[i-1] = (top[i-1] - k + m) % m;
			}else {
				top[i-1] = (top[i-1] + k) % m;
			}
		}
	}

	static int getRealNum(int r, int idx) {
		int a = top[r];
		
		
		
		return board[r][(idx+a+m)%m];
		
		
	}
	
	
	static void debug() {
		for(int i=0; i<n; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		System.out.println("---top---");
		System.out.println(Arrays.toString(top));
	}


}
