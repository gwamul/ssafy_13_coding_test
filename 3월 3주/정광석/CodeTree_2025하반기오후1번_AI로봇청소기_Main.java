package saffy_algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 좌상단 = 1,1 우하단 = N,N
 * 
 * 1. 먼지가 있다  1~100
 * 
 * 
 * 2. 먼지가 없다
 * 
 * 3. 물건이 있다.
 * 
 * 
 * 각 청소기는 초기 위치가 있고, 해당 위치에는 먼지가 없다.
 * 
 *##### 청소기 이동
 *
 * 순서대로, 이동 거리가 가장 가까운 오염 격자로 이동한다.
 *물건이 있는격자, 청소기가 있는 격자는 지나갈 수 없다.
 *가장 가까운 곳이 여러개라면, 행 번호가 작은곳, 행번호가 작은 곳은 열번호가 작은 곳
 *
 *
 *##### 청소기 청소
 *
 *바라보는 방향 기준
 *본인 위치한 곳, 왼, 오 , 위 청소 가능하다.
 *
 *4가지 격자에서 청소할 수 있는 먼지량이 가장 큰 방향에서 청소 시작
 *
 *격자당 최대 20 치울 수 있음
 *
 *우선순위도 있음 = 오 -> 아래 -> 왼쪽 -> 위쪽
 *
 *
 *
 *##### 먼지 축적
 *
 *먼지가 있는 모든 격자에 동시에 5씩 추가된다.
 *
 *
 *##### 먼지 확산
 *
 *깨끗한 격자 주변 4방향 격자의 먼지량 합 / 10 만큼 먼지가 확산된다.
 *
 *깨끗한 모든 격자에 대해 동시에
 *
 *
 *
 *##### 출력
 *전체 공간 총 먼지량.
 *없으면 0
 *
 *
 *이 테스트를 L번 반복한다.
 *
 *
 * 
 * 
 */
public class CodeTree_2025하반기오후1번_AI로봇청소기_Main {
	
	static int n,k,l;
	static int[][] dust;
	static int[][] robot;
	static boolean[][] where;
	static int[] dx = {-1,0,0,1}; // 상 좌 우 하
	static int[] dy = {0,-1,1,0};
	
	
	static int[] cdx = {0,1,0,-1};
	static int[] cdy = {1, 0, -1, 0};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		
		dust = new int[n][n];
		robot = new int[k][2];
		where = new boolean[n][n];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				dust[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0; i<k; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			robot[i][0] = r;
			robot[i][1] = c;
			
			where[r][c] = true;
		}
		
		
		for(int i=0; i<l; i++) {
			//청소기 이동
			move();
			//청소
			clean();
			//먼지 축적
			dustPlus();
			//먼지 확산
			dustSpread();
			//출력
			System.out.println(count());
		}
	}
	private static void move() {
		// TODO Auto-generated method stub
		// 각 청소기로부터. 가장 가까운 먼지쌓인 곳을 찾는다.
		// 이때 bfs로 거리를 찾아야 할 듯하다.
		// 짐이 있는 곳이나, 청소기가 위치한 곳에는 갈 수 없다.
		// 순서대로 간다.
		//System.out.println("이동 전");
	//debug();
		
		for(int i=0; i<k; i++) {
			int[] dest = findClosest(i); //가장 가까운 청소 가능한 위치
			int nr = dest[0];
			int nc = dest[1];
			//System.out.println(nr + " " + nc);
			where[robot[i][0]][robot[i][1]] = false;
			//위치 업데이트
			where[nr][nc] = true;
			robot[i][0] = nr;
			robot[i][1] = nc;
			
		}
		
		//System.out.println("이동 후");
		//debug();
		
		
	}
	private static int[] findClosest(int i) {
		// TODO Auto-generated method stub
		Queue<int[]> q = new ArrayDeque<>();
	    boolean[][] visited = new boolean[n][n];
	    int r = robot[i][0];
	    int c = robot[i][1];
	    
	    q.offer(new int[] {r, c, 0});
	    visited[r][c] = true;

	    int minR = n, minC = n, minDist = Integer.MAX_VALUE;

	    while (!q.isEmpty()) {
	        int[] curr = q.poll();
	        int tx = curr[0], ty = curr[1], dist = curr[2];

	        if (dist > minDist) break;

	        if (dust[tx][ty] > 0) {
	            if (dist < minDist) {
	                minDist = dist;
	                minR = tx; minC = ty;
	            } else if (dist == minDist) {
	                if (tx < minR || (tx == minR && ty < minC)) {
	                    minR = tx; minC = ty;
	                }
	            }
	            continue;
	        }

	        for (int d = 0; d < 4; d++) {
	            int nx = tx + dx[d], ny = ty + dy[d];
	            if (nx < 0 || nx >= n || ny < 0 || ny >= n || visited[nx][ny]) continue;
	            if (dust[nx][ny] == -1 || where[nx][ny]) continue;

	            visited[nx][ny] = true;
	            q.offer(new int[] {nx, ny, dist + 1});
	        }
	    }
	    return minDist == Integer.MAX_VALUE ? new int[]{r, c} : new int[]{minR, minC};
	}
	private static void clean() {
		// TODO Auto-generated method stub
		// 오 아 왼 위
		for(int i=0; i<k; i++) {
			int tx = robot[i][0];
			int ty = robot[i][1];
			
			int targetDirection = -1;
			int sum = -1;
			
			
			
			for(int d=0; d<4; d++) {
				//현 위치에서 각 방향에 대하여,
				int result = directionClean(tx, ty, d);
			//	System.out.println(result + " : " + d + "방향 청소");
				if(sum < result) {
					sum = result;
					targetDirection = d;
					//우선순위를 적용한 for문.
				}
			}
			dust[tx][ty] -= Math.min(dust[tx][ty], 20);
			for(int d=0; d<4; d++) {
				if((d^2) == targetDirection) continue; // 반대 방향
				int nx = tx + cdx[d];
				int ny = ty + cdy[d];
				if(0<=nx && nx<n && 0<=ny && ny < n) {
					if(dust[nx][ny] == -1) continue;
					dust[nx][ny] -= Math.min(dust[nx][ny], 20);					
				}
			}
			
		}
		//System.out.println("청소 후");
		//debug();
		
		
	}
	
	private static int directionClean(int tx, int ty, int d) {
		// TODO Auto-generated method stub
		int sum = Math.min(dust[tx][ty], 20);
		for(int i=0; i<4; i++) {
			if((d^2) == i) continue; // 반대 방향
			int nx = tx + cdx[i];
			int ny = ty + cdy[i];
			if(0<=nx && nx<n && 0<=ny && ny < n) {
				if(dust[nx][ny] == -1) continue;
				sum += Math.min(dust[nx][ny], 20);
			}
		}
		return sum;
	}
	private static void dustPlus() {
		// TODO Auto-generated method stub
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(dust[i][j] > 0) dust[i][j] += 5;
			}
		}
		//debug();
		
	}
	private static void dustSpread() {
		// TODO Auto-generated method stub
		int[][] temp = new int[n][n];
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(dust[i][j] == 0) {
					
					int sum = 0;
					for(int d=0; d<4; d++) {
						int nx = i+dx[d];
						int ny = j+dy[d];
						if(0<=nx && nx<n && 0<=ny && ny < n) {
							//범위 안에 있으면..
							if(dust[nx][ny]==-1) continue;
							sum += dust[nx][ny];
						}
					}
					
					temp[i][j] = sum / 10;
					
					
				}
			}
		}
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(dust[i][j] == 0) {
					dust[i][j] += temp[i][j];
				}
			}
		}
		
	//	debug();
		
	}
	private static int count() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(dust[i][j] == -1) continue;
				sum += dust[i][j];
			}
		}
		return sum;
	}
	
	static void debug() {
		System.out.println("--------------------------");
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				System.out.printf("%3d", dust[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------------------------");
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(where[i][j]) {
					System.out.printf("%3c" , 'O');
				}else {
					System.out.printf("%3c" , '-');
				}
			}
			System.out.println();
		}
	}
}
