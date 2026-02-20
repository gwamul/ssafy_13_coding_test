import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 일단, 완탐이긴 한데
 * 
 * 사전 작업
 * 1. 대륙을 번호 붙이기
 * 
 * step1.
 * 모든 대륙을 bfs해서 영역을 넓히가 가장 먼저 마주치는 곳에 최단거리
 * 
 * step2.
 * 모든 대륙의 값을 queue에 넣고 모든 map의 가능한 거리를 구해서 최소값 구하기
 * 
 * 
 * 
 * 중요!!!
 * bfs로 거리를 잰다고 해서 가장 먼저 만나는게 최소는 아니다. 
 * 
 * 거리가 2와 3이라고 해도 실제 다른 대륙에 도착하는 건 같은 싸이클이다. 
 * 
 * 때문에 전체 대륙의 만나는 부분을 모두 체크해서 최소값을 찾아야 한다. 
 * 
 * => 이거 땜시 몇시간을 날린겨
 * 
 * */

public class Main {
	
	static int n;
	static int[][][] map;
	
	static int[] dy = {0,1,0,-1};
	static int[] dx = {1,0,-1,0};
	
	static int identity;
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine().trim());
		map = new int[n][n][2];
		
		for(int i = 0;i<n;i++) {
			String[] line = br.readLine().trim().split(" ");
			for(int j = 0;j<n;j++) {
				map[i][j][0] = Integer.parseInt(line[j]);
			}
		}
		//섬 고유 번호로 교체
		//섬은 2번부터 시작하게 됨.
		identity = setIslandIdentity();
		bfs();
		System.out.println(min);
	}
	

	static void bfs() {
		
		Queue<int[]> q = new ArrayDeque<>();
		
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(map[i][j][0] > 0) {
					q.add(new int[] {i, j, map[i][j][1]});
				}
			}
		}

		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			int identity = map[cur[0]][cur[1]][0];
			for(int i = 0;i<4;i++) {
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				if(y < 0 || y >= n || x <0 || x >= n) continue;
				if(map[y][x][0] == identity) continue; //같은 대륙
				
				if(map[y][x][0] == 0) {
					map[y][x][0] = identity;
					map[y][x][1] = map[cur[0]][cur[1]][1]+1;
					
					q.add(new int[] {y, x, map[y][x][1]});
				}
				else {
					//다른 대륙과 맞닿았을시
					int dist = map[y][x][1] + cur[2];
					if(min > dist) min = dist;
				}
			}
		}
	}


	private static int setIslandIdentity() {
		// TODO Auto-generated method stub
		int identity = 1;
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<n;j++) {
				if(map[i][j][0] == 1) {
					//bfs로 돌면서 identity값으로 채우기
					identity++;
					fillIdentity(identity,i,j);
				}
			}
		}
		return identity;
	}

	private static void fillIdentity(int identity, int y, int x) {
		// TODO Auto-generated method stub
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {y, x});
		map[y][x][0] = identity;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i = 0;i<4;i++) {
				int ny = cur[0] + dy[i];
				int nx = cur[1] + dx[i];
				
				if(ny < 0 || ny >= n || nx < 0 || nx >=n) continue;
				if(map[ny][nx][0] == 1) {
					map[ny][nx][0] = identity;
					q.add(new int[] {ny, nx});
				}
			}
		}
	}

}

