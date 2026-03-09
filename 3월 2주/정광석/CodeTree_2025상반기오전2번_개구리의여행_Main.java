package saffy_algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
 * 개구리한마리
 * 호수에 돌이 있음
 * 
 * 돌위에서 돌아다닐 수 있음
 * 
 * 왼쪽 위 돌 좌표가 1,1 오른쪽 아래 n,n
 * 
 * 개구리가 갈 수 없는 위험한 돌도 있다.
 * 
 * 세 종류의 돌이 있음
 * 1. 안전한 돌 : .
 * 2. 미끄러운 돌 : S
 * 3. 천적이 사는 돌 : #
 * 
 * i,j 위치의 돌 종류를 board에 기록해둠
 * 
 * 초기 위치와 도착 위치를 정하고, 시작 위치에서 도착 위치까지 가는게 목표이다.
 * 초기 점프력 : 1
 * 
 * 
 * 개구리가 할 수 있는 행동
 * 
 * 1. 점프
 *  . 상하좌우 방향 중 하나를 골라 현재 점프력 만큼
 *  dx, dy 방향으로 k만큼 갈 수 있음
 *  
 *  이동하려는 위치에 돌이 있어야 갈 수 있음
 *  
 *  미끄러운돌(S)로는 못감
 *  도착위치나, 경로에 천적이 사는 돌 (#) 이 있으면 못감
 *  ...S 못감
 *  .#.. 못감
 *  
 *  2. 점프력 증가
 *  점프력 1증가 시키기
 *  최대 점프력은 5
 *  점프력 증가 후 점프력이 k라고 하면 k^2 만큼 시간이 걸린다.
 *  
 * 
 * 3. 점프력 감소
 * 1,2,3,4.. k-1 까지로 감소시킬 수 있음
 * 1의 시간이 걸린다.
 * 
 * 
 * 
 * 개구리는 q번의 여행 계획을 세웠고
 * 각 계획마다 최대한 짧은 시간에 여행을 마치고자 한다.
 * 
 * 도착 불가능한 계획이 있으면 -1 출력
 * 
 * 
 * 
 */
public class CodeTree_2025상반기오전2번_개구리의여행_Main {
	static int n;
	static char[][] board;
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		n = Integer.parseInt(br.readLine());
		board = new char[n][n];
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<n; j++) {
				board[i][j] = line.charAt(j);
			}
		}
		int q = Integer.parseInt(br.readLine());
		for(int i=0; i<q; i++) {
			st = new StringTokenizer(br.readLine());
			int r1,c1,r2,c2;
			r1 = Integer.parseInt(st.nextToken())-1;
			c1 = Integer.parseInt(st.nextToken())-1;
			r2 = Integer.parseInt(st.nextToken())-1;
			c2 = Integer.parseInt(st.nextToken())-1;
			sb.append(sol(r1,c1,r2,c2));
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	
	private static int sol(int r1, int c1, int r2, int c2) {
		// TODO Auto-generated method stub
		
		PriorityQueue<int[]> pq  = new PriorityQueue<>((o1,o2) -> o1[2] - o2[2]);
		int [][][] visited = new int[n][n][6];
		for(int i=0; i<n ;i++) {
			for(int j=0; j<n; j++) {
				Arrays.fill(visited[i][j], Integer.MAX_VALUE);
			}
		}
		pq.offer(new int[] {r1, c1, 0, 1});
		visited[r1][c1][1] = 0;
		
		
		while(!pq.isEmpty()) {
			int temp[] = pq.poll();
			int tx = temp[0];
			int ty = temp[1];
			int time = temp[2]; //제일 시간이 가까운 
			int power = temp[3];
			//System.out.println("tx: " + tx + " ty: " + ty + " time: " + time + " power: " + power);
			if(tx == r2 && ty == c2) return time;
			
			
			for(int i=0; i<4; i++) {
				//미끄러운 칸, 천적 있는 칸에 대해 처리 필요
				
				int nx = tx + dx[i]*power;
				int ny = ty + dy[i]*power;
				
				if(!inBound(nx,ny)) continue;
				if(visited[nx][ny][power] <= time + 1) continue;
				if(canJump(tx, ty, i, power)) {
					visited[nx][ny][power] = time+1;
					pq.offer(new int[] {nx, ny, time+1, power});					 
				}
				//점프 할 수 있거나 점프할 수 없을 때
				//1. 점프력 1 증가
			}
			if(power!=5) {
				int newpower = power+1;
				int newtime = time + newpower*newpower;
				visited[tx][ty][newpower] = newtime;
				pq.offer(new int[] {tx,ty, newtime, newpower});
			}
			
			for(int p=1; p<=power-1; p++) {
				if(visited[tx][ty][p] <= time+1) continue;
				visited[tx][ty][p] = time+1;
				pq.offer(new int[] {tx,ty,time+1, p});
			}
							
				
				
				
			
			
		}
		
		
		
		return -1;
		
	}


	private static boolean canJump(int x, int y, int d, int power) {
		// TODO Auto-generated method stub
		int tx = x;
		int ty = y; 
		for(int i=1; i<=power; i++) {
			int nx = tx + i*dx[d];
			int ny = ty + i*dy[d];
			if(!inBound(nx, ny)) return false;
			if(board[nx][ny] == '#') return false; //천적이 있는 칸
		}
		if(board[x+dx[d]*power][y+dy[d]*power] == '.') return true;
		
		return false;
	}


	private static boolean inBound(int x, int y) {
		// TODO Auto-generated method stub
		if(0<=x && x<n && 0<=y && y<n) return true;
		return false;
	}
	
	
}
