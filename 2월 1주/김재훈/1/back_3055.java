package kjh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class back_3055 {
	
	static String[][] map;
	static boolean[][] visited;
	
	static int[] dy = {1,0,-1,0};
	static int[] dx = {0,1,0,-1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] rc = br.readLine().trim().split(" ");
		
		int R = Integer.parseInt(rc[0]);
		int C = Integer.parseInt(rc[1]);
		
		int sy = 0;
		int sx = 0;
		
		Queue<int[]> water = new ArrayDeque<>();
		map = new String[R][C];
		visited = new boolean[R][C];
		
		for(int i = 0;i<R;i++) {
			String line = br.readLine().trim();
			for(int j = 0;j<C;j++) {
				map[i][j] = line.charAt(j)+"";
				if(map[i][j].equals("S")) {
					sy = i;
					sx = j;
				}
				else if(map[i][j].equals("*")) {
					water.add(new int[] {i, j});				}
			}
		}
		boolean goal = false;
		
		Queue<int[]> man = new ArrayDeque<>();
		
		man.add(new int[] {sy, sx});
		visited[sy][sx] = true;
		int time = 0;
		//기준은 고슴도치가 이동하는 것
		//queue가 2개인 만큼 while을 맞춰줘야 한다. 
		while(!man.isEmpty() && !goal) {
			
			
			int water_pool = water.size();
			//물이 먼저 차야 하는데
			//고슴도치의 queue와 동기화가 필요
			while(water_pool-- > 0) {
				int[] wat = water.poll();
				
				//물이 차오르는게 먼저
				for(int i = 0;i<4;i++) {
					int y = dy[i] + wat[0];
					int x = dx[i] + wat[1];
					
					if(y < 0 || y >= R || x < 0 || x >= C) continue;
					if(map[y][x].equals("X") || map[y][x].equals("*") || map[y][x].equals("D")) continue;
					
					if(map[y][x].equals(".")) {
						water.add(new int[] {y, x});
						map[y][x] = "*";
						
					}
				}
			}
			//고슴도치도 물의 이동을 한 이후 한번에 다 해야 한다.
			int man_pool = man.size();
			while(man_pool-- > 0) {
				int[] cur = man.poll();
				
				//민혁이 움직이기
				for(int i = 0;i<4;i++) {
					int y = dy[i] + cur[0];
					int x = dx[i] + cur[1];
					
					if(y < 0 || y >= R || x < 0 || x >= C) continue;
					if(map[y][x].equals("X") || visited[y][x] || map[y][x].equals("*")) continue;
					
					if(map[y][x].equals("D")) {
						goal = true;
						break;
					}
					man.add(new int[] {y, x});
					visited[y][x] = true;	
				}
			}
			
			time++;
		}
		
		StringBuffer sb = new StringBuffer();
		
		if(goal) sb.append(time);
		else sb.append("KAKTUS");
			
		System.out.println(sb.toString());
	}
	
	
}
