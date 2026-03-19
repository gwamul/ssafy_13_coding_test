import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 200*200 => bfs한번에 4천
 * 
 * 그냥 다 돌아도 80만 가능
 * 
 * 전략
 * n이 짝수라면 모든곳이 폭탄
 * 
 * 홀수일 때,
 * 
 * 이전에 폭탄인곳 + 상하좌우 4방향 => 다음 홀수에 .인 지점
 * 
 * 즉, 다음에 .인 지점이외에는 다 폭탄이므로 다음 홀수초 폭탄 리스트를 계산가능
 * 
 */
public class Main {
	
	static int[] dy = {-1,1,0,0};
	static int[] dx = {0,0,-1,1};
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		
		char[][] map = new char[r][c];
		char[][] dot = new char[r][c];
		List<int[]> posList = new ArrayList<>();
		for(int i = 0;i<r;i++) {
			map[i] = br.readLine().trim().toCharArray();
			for(int j = 0;j<c;j++) {
				if(map[i][j] == 'O') posList.add(new int[] {i ,j});
				dot[i][j] = '.';
			}
		}
		
		if(n == 1) {
			for(int i = 0;i<r;i++) {
				for(int j = 0;j<c;j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			return;
		}
		
		if(n % 2 == 0 ) {
			for(int i = 0;i<r;i++) {
				for(int j = 0;j<c;j++) {
					System.out.print('O');
				}
				System.out.println();
			}
			return;
		}
		
		//각 홀수초마다 list에 들어있는 위치의 4방향을 포함한 곳이 없어진 상태, 나머지는 폭탄
		int rotate = (n-1)/2;
		while(rotate-- > 0) {
			//다음 회차때 .인 곳 리스트
			boolean[][] visited = new boolean[r][c];
			for(int[] cur : posList) {
				visited[cur[0]][cur[1]] = true;
				for(int i = 0;i<4;i++) {
					int y = cur[0] + dy[i];
					int x = cur[1] + dx[i];
					
					if(y < 0 || y >= r || x < 0 || x >= c || visited[y][x]) continue;
					visited[y][x] = true;
				}
			}
			
			posList = new ArrayList<>();
			for(int i = 0;i<r;i++) {
				for(int j = 0;j<c;j++) {
					if(!visited[i][j]) posList.add(new int[] {i, j});
				}
			}
			
		}
		for(int[] cur : posList) {
			dot[cur[0]][cur[1]] = 'O';
		}
		
		for(int i = 0;i<r;i++) {
			for(int j = 0;j<c;j++) {
				System.out.print(dot[i][j]);
			}
			System.out.println();
		}
		
		
	}
}
