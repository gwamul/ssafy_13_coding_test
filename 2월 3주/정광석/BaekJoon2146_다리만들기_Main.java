package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class BaekJoon2146_다리만들기_Main {
	
	static int n;
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static int[][] board;
	static boolean[][] visited;
	static int[][] dists;
	static int min_dist = Integer.MAX_VALUE;
	static int team = 2;
	static Queue<int[]> q;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		n = Integer.parseInt(br.readLine());
		board = new int[n][n];
		visited = new boolean[n][n];
		dists = new int[n][n];
		//0 : 바다 1 : 육지
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(board[i][j] == 1 && !visited[i][j]) {
					makeTeambfs(i,j);
				}
			}
		}
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(board[i][j]>1) {
					calDistBfs(i,j,board[i][j]);
				}
			}
		}
		
		
			
		//debug();
	
		System.out.println(min_dist);
		
	}
	
	
	
	private static void makeTeambfs(int x, int  y) {
		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int[] {x,y});
		visited[x][y] = true;
		board[x][y] = team;
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int tx = temp[0];
			int ty = temp[1];
			board[tx][ty] = team;
			for(int i=0; i<4; i++) {
				int nx = tx + dx[i];
				int ny = ty + dy[i];
				
				if(0<=nx && nx<n && 0<=ny && ny<n && board[nx][ny] == 1) {
					if(visited[nx][ny]) continue;
					visited[nx][ny] = true;
					q.offer(new int[] {nx, ny});
				}
			}
			
		}
		team++;
		
	}
	
	private static void calDistBfs(int x, int y, int myTeam) {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[n][n];
        q.offer(new int[]{x, y, 0});
        v[x][y] = true;

        while (!q.isEmpty()) {
            int[] temp = q.poll();
            int tx = temp[0];
            int ty = temp[1];
            int dist = temp[2];

            if (dist >= min_dist) continue;

            for (int i = 0; i < 4; i++) {
                int nx = tx + dx[i];
                int ny = ty + dy[i];

                if (nx >= 0 && nx < n && ny >= 0 && ny < n && !v[nx][ny]) {
                    if (board[nx][ny] == myTeam) continue;

                    v[nx][ny] = true;
                    if (board[nx][ny] == 0) {
                       
                        q.offer(new int[]{nx, ny, dist + 1});
                    } else {
                        
                        min_dist = Math.min(min_dist, dist);
                    }
                }
            }
        }
    }
	
	
	
	
	static  void debug() {
		for(int i=0; i<n; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
}
