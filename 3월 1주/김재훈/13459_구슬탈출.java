import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**

일단, 완탐 가능할 듯
한번 최대 20
10번
상하좌우 4번


4^10 * 20 으로 해도 2천만 정도
*/

public class Main {

    static int n;
    static int m;
    static char[][] map;

    static int[][] dir = {{-1,0},{1,0},{0,-1},{0,1}};

    static int ry;
    static int rx;
    static int by;
    static int bx;
    static int oy;
    static int ox;

    static boolean good;
    static boolean fail;

    //현재 이 코드의 문제점.
    //-> 빨간공과 파란공이 부딪혔을때, 안움직인 공에 부딪히면 버그
    //때문에 RBmove하기 전에 상하좌우에 따라서 더 앞쪽에 있는 공 먼저 움직여야 한다. 

    //2번 인덱스가 0이면 이동만 함, 1이면 파란공이 홀에, 2면 빨간공이 홀에
    //0,1번 인덱스는 옮겨진 이후의공의 위치
    static int[] RBmove(int rb, int my, int mx, int direc) {

        //해당 방향으로 벽이 나오거나 R,B공이 있거나 구멍이 있을 때까지 계속 이동
        int result = 0;
        int y = my;
        int x = mx;
        while(true) {
            y += dir[direc][0];
            x += dir[direc][1];
            if(map[y][x] == '#') break;
            if(map[y][x] == 'O') {
                if(rb == 0) result = 2;
                else result = 1;
                
                return new int[] {y, x, result};
            }
        }

        return new int[] {y-dir[direc][0], x-dir[direc][1], result};
    }



static void rollback(int[] prevPos, int[] nextPos) {
    
    //구멍에 안들어갔다면 결국 빨간공, 파란공이랑, '.'이 바뀐것일뿐이니
    //1. 빨간공 롤백
    //안움직인 경우는 안함
    if(prevPos[0] == nextPos[0] && prevPos[1] == nextPos[1]) {
        
    }
    else {
        map[prevPos[0]][prevPos[1]] = 'R';
        map[nextPos[0]][nextPos[1]] = '.';
    }
    
    //2. 파란공도 동일하게 
    if(prevPos[2] == nextPos[2] && prevPos[3] == nextPos[3]) {
        
    }
    else {
        map[prevPos[2]][prevPos[3]] = 'B';
        map[nextPos[2]][nextPos[3]] = '.';
    }
}

private static void recur(int cnt) {
    // TODO Auto-generated method stub
    if(cnt == 10) return;
    if(good) return;
    
    for(int i = 0;i<4;i++) {
    	int[] RedResult = RBmove(0, ry, rx, i);
    	int[] BlueResult = RBmove(1, by, bx, i);
    	if(RedResult[0] == BlueResult[0] && RedResult[1] == BlueResult[1]) {
    		if(i == 0) {
    			//위로 갔는데 
    			if(ry < by) {//빨간색이 먼저 갔다면 
    				//파란색은 한칸 뒤로 교체
    				BlueResult[0]++;
    			}
    			//같을리는 없음 (같으면 x값이 다르다는 거라 위 if문에 안걸림)
    			else RedResult[0]++;
    			
    		}
    		else if(i == 1) {//하
    			if(ry > by) BlueResult[0]--;
    			else RedResult[0]--;
    		}
    		else if(i == 2) {
    			if(rx < bx) BlueResult[1]++;
    			else RedResult[1]++;
    		}
    		else {
    			if(rx > bx) BlueResult[1]--;
    			else RedResult[1]--;
    		}
    	}
    	
    	if(RedResult[2] == 2 && BlueResult[2] == 0) {
    		//성공
    		good = true;
    		return;
    	}
    	else if(BlueResult[2] == 1) continue; //실패

    	
    	//위치 교체
    	//switchPos(0, ry, rx, RedResult[0], RedResult[1]);
    	//switchPos(1, by, bx, BlueResult[0], BlueResult[1]);
    	int prevRy = ry;
    	int prevRx = rx;
    	int prevBy = by;
    	int prevBx = bx;
    	int nextRy = RedResult[0];
    	int nextRx = RedResult[1];
    	int nextBy = BlueResult[0];
    	int nextBx = BlueResult[1];
    	ry = nextRy;
    	rx = nextRx;
    	by = nextBy;
    	bx = nextBx;
    	recur(cnt+1);
    	//0,1은 빨강, 2,3인덱스는 파랑
    	//rollback(new int[] {ry, rx, by, bx}, new int[] {RedResult[0],RedResult[1],BlueResult[0],BlueResult[1]});
    	ry = prevRy;
    	rx = prevRx;
    	by = prevBy;
    	bx = prevBx;
    	
    }
    
    
    
    
}

static void switchPos(int rb, int prevY, int prevX, int nextY, int nextX) {
	if(rb == 0) {
		map[nextY][nextX] = 'R';
		map[prevY][prevX] = '.';
	}
	else {
		map[nextY][nextX] = 'B';
		map[prevY][prevX] = '.';
	}
}

public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    String[] nm = br.readLine().trim().split(" ");
    n = Integer.parseInt(nm[0]);
    m = Integer.parseInt(nm[1]);
    
    map = new char[n][m];
    
    for(int i = 0;i<n;i++) {
        char[] line = br.readLine().trim().toCharArray();
        for(int j = 0;j<m;j++) {
            map[i][j] = line[j];
            if(map[i][j] == 'R') {
                ry = i;
                rx = j;
            }
            else if(map[i][j] == 'B') {
                by = i;
                bx = j;
            }
            else if(map[i][j] == 'O') {
                oy = i;
                ox = j;
            }
        }
    }
    
    good = false;

    
    recur(0);
    int answer = 0;
    if(good) answer = 1;
    else answer = 0;
    
    System.out.println(answer);
}
}
