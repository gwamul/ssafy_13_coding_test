package saffy_algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 고래
 * 0 : 바다, 1 : 암초
 * 목표 : 헤엄칠 수 있는 모든 바다 헤엄치기. 
 * 
 * 1. 인접탐험
 * 상하좌우 인접 칸 중 아직 방문하지 않은 곳이 있다면 우선순위에 맞게 이동한다.
 * 현재 바라보는 방향
 * 좌회전 후 직진
 * 우회전 후 직진
 * 180 후 직진
 * 이동하면, 바라보는 방향 = 이동한 방향으로 갱신 됨.
 * 
 * 인접한 곳 방문할 수 없을 때 까지. 1 반복
 * 
 * 
 * 2. 가장 가까운 바다로.
 * 인접 모두 방문 가능한 곳이 없다면..
 * 아직 방문하지 않은 곳 중 가장 가까운 곳으로 간다.
 * 
 * 거리 = 상하좌우 인접한 칸을 한칸씩 이동하여 도달하는데 필요한 최소 이동 횟수
 * 암초는 못감. 지났던 바다 다시 가기는 가능.
 * 
 * 가장 가까운 곳 이 여러개라면, 행번호 -> 열 번호 작은 순
 * 
 * 선택한 칸까지 최단 거리로 이동. 매 이동 마다 선택한 칸 까지 거리가 1 줄어드는 인접한 칸 
 * 
 * 그런 칸이 여러개라면 좌하우상 순서
 * 도착 후 바라보는 방향 = 마지막 이동 방향.
 * 
 * 고래가 방문하는 바다 칸의 위치를 방문 순서대로 출력. 시작 위치도 출력에 포함.
 * 
 * 
 * 
 */
public class CodeTree_2026상반기오후1번_아기고래의첫항해_Main {
	static int n,r,c,d;
	static int[][] board;
	static boolean[][] visited;
	static int x, y;
	static int[] dx = {-1, 0, 1, 0}; // 상 우 하 좌
	static int[] dy = {0, 1, 0, -1};
	static StringBuilder sb;
	static int cnt;
	static int targetX, targetY, minDistance;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken())-1;
		c = Integer.parseInt(st.nextToken())-1;
		int tempD = Integer.parseInt(st.nextToken());
		if (tempD == 1) d = 0;
		else if (tempD == 4) d = 1;
		else if (tempD == 2) d = 2;
		else if (tempD == 3) d = 3;
		
		visited = new boolean[n][n];
		board = new int[n][n];
		visited[r][c] = true;
		sb.append((r+1)).append(" ").append((c+1)).append("\n");
		cnt = 0;
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				cnt += board[i][j];
			}
		}
		
		// 인접 탐험 할 수 있는 대로 하다가 
		// 더이상 없으면 가장 가까운 바다로 이동한다.
		//System.out.println(cnt);
		
		while(true) {
			//모든 바다를 경험할 때 까지
			if(cnt == n*n) break;
			
			//인접 바다 가기.
			while(true) {
				if(!moveAround()) break; 
				cnt++;
			}
			moveClosest();
			if(targetX!=Integer.MAX_VALUE && targetY!=Integer.MAX_VALUE) {
				sb.append(targetX+1).append(" ").append(targetY+1).append("\n");
				r = targetX;
				c = targetY;
				visited[r][c] = true;
				//cnt++;
			}else {
				break;
			}
			
			
		}
		
		System.out.println(sb.toString());
		
	}
	private static void moveClosest() {
		// TODO Auto-generated method stub
		Queue<int[]> q = new ArrayDeque<>();
		boolean [][][] jumpvisited = new boolean[n][n][4];
		q.offer(new int[] {r,c,d,0});
		jumpvisited[r][c][d] = true;
		targetX = Integer.MAX_VALUE;
	    targetY = Integer.MAX_VALUE;
	    minDistance = Integer.MAX_VALUE;
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int tx = temp[0];
			int ty = temp[1];
			int dir = temp[2];
			int dist = temp[3];
			
			if (dist > minDistance) break;

	        // 바다(0)이면서 아직 고래가 한 번도 방문하지 않은 곳 발견!
	        if (board[tx][ty] == 0 && !visited[tx][ty]) {
	            if (dist < minDistance) {
	                minDistance = dist;
	                targetX = tx;
	                targetY = ty;
	                d = dir;
	            } else if (dist == minDistance) {
	                // 거리가 같다면 행 번호 -> 열 번호 작은 순
	                if (tx < targetX) {
	                    targetX = tx;
	                    targetY = ty;
	                    d = dir;
	                } else if (tx == targetX) {
	                    if (ty < targetY) {
	                        targetY = ty;
	                        d = dir;
	                    }
	                }
	            }
	            continue; // 같은 거리의 다른 후보들도 확인해야 하므로 계속 진행
	        }
			
			
			
			for(int i=3; i>=0; i--) {
				int nx = tx + dx[i];
				int ny = ty + dy[i];
				if(0<= nx && nx < n && 0<=ny && ny < n) {
					if(jumpvisited[nx][ny][i]) continue;
					if(board[nx][ny] == 1) continue;
					jumpvisited[nx][ny][i] = true;
					q.offer(new int[] {nx,ny,i,dist+1});
				}
			}
		}
		
		
	}
	private static boolean moveAround() {
		// TODO Auto-generated method stub
		
		// 상하좌우 인접한 칸으로 이동하기. 
		// 현재 위치는 전역으로 관리하겠다.
		int[] pIdx = {0, 3, 1, 2}; 

	    for (int i = 0; i < 4; i++) {
	        int nextDir = (d + pIdx[i]) % 4;
	        int nx = r + dx[nextDir];
	        int ny = c + dy[nextDir];

	        if (nx>=0 && nx<n && ny>=0 && ny<n && board[nx][ny] == 0 && !visited[nx][ny]) {
	            r = nx;
	            c = ny;
	            d = nextDir; // 방향 갱신
	            
	            visited[r][c] = true;
	            sb.append((r + 1)).append(" ").append((c + 1)).append("\n");
	            cnt++;
	            return true;
	        }
	    }
	    return false;
		
	}

}
