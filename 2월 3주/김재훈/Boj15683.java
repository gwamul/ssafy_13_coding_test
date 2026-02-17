import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
CCTV의 개수가 8개
최악의 경우 4방향
4^8에 사무실 크기 64로 하면
2^16 * 2^6 = 65536 * 64 = 4백만 정도
즉, 완탐 충분히 가능

미리 map의 상하좌우를 채우는 함수를 만들어서 사용

재귀로 완탐을 돌면서 채우고 다시 지우는 걸로 하면 깔끔할 듯
-> 다시 어떻게 지우지? 중복을 확인 못하는데
=> list로 방금 채운것을 저장하고 이것만 지우면 될 듯
 */

public class Main {

    static int n;
    static int m;
    static int[][] map;
    static List<int[]> cctv = new ArrayList<>();
    static List<int[]> cctv5 = new ArrayList<>();
    static int min = Integer.MAX_VALUE;

    static void setUp(int y, int x, List<int[]> rollback){
        //y, x는 cctv의 위치
        for(int i = y; i >= 0; i--){
            if(map[i][x] == 0){
                map[i][x] = 7;
                rollback.add(new int[] {i,x});
            }
            else if(map[i][x] == 6){
                //벽 만나면 그만
                return;
            }

        }
    }

    static void setDown(int y, int x, List<int[]> rollback){
        //y, x는 cctv의 위치
        for(int i = y; i < n; i++){
            if(map[i][x] == 0){
                map[i][x] = 7;
                rollback.add(new int[] {i,x});
            }
            else if(map[i][x] == 6){
                //벽 만나면 그만
                return;
            }
        }
    }

    static void setLeft(int y, int x, List<int[]> rollback){
        //y, x는 cctv의 위치
        for(int i = x; i >= 0; i--){
            if(map[y][i] == 0){
                map[y][i] = 7;
                rollback.add(new int[] {y,i});
            }
            else if(map[y][i] == 6){
                //벽 만나면 그만
                return;
            }
        }
    }

    static void setRight(int y, int x, List<int[]> rollback){
        //y, x는 cctv의 위치
        for(int i = x; i < m; i++){
            if(map[y][i] == 0){
                map[y][i] = 7;
                rollback.add(new int[] {y,i});
            }
            else if(map[y][i] == 6){
                //벽 만나면 그만
                return;
            }
        }
    }

    static void rollBack(List<int[]> list){
        for(int[] pos : list){
            map[pos[0]][pos[1]] = 0;
        }
        list.clear();
    }

    //사각지대 수 세서 min값 업데이트
    static void countBlack(){
        int cnt = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(map[i][j] == 0)cnt++;
            }
        }
        if(min > cnt) min = cnt;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] mn = br.readLine().trim().split(" ");

        n = Integer.parseInt(mn[0]);
        m = Integer.parseInt(mn[1]);
        map = new int[n][m];
        //초기화
        for(int i = 0;i<n;i++){
            String[] row = br.readLine().trim().split(" ");
            for(int j = 0;j<m;j++){
                map[i][j] = Integer.parseInt(row[j]);
                //CCTV 5번은 따로 모아서 미리 map에 표시해두자.
                if(map[i][j] == 5){
                    cctv5.add(new int[]{i,j});
                }
                else if(map[i][j] > 0 && map[i][j] < 5){
                    cctv.add(new int[]{map[i][j], i,j});
                }
            }
        }

        for(int[] pos : cctv5){
            List<int[]> rollback = new ArrayList<>();
            setUp(pos[0],pos[1],rollback);
            setDown(pos[0],pos[1],rollback);
            setLeft(pos[0],pos[1],rollback);
            setRight(pos[0],pos[1],rollback);
        }
        //완탐
        //ind : cctv 개수
        recur(0);

        System.out.println(min);
    }


    static void recur(int ind){
        if(ind == cctv.size()){
            countBlack();
            return;
        }

        int num = cctv.get(ind)[0];
        int y = cctv.get(ind)[1];
        int x = cctv.get(ind)[2];
        List<int[]> rollback = new ArrayList<>();
        if(num == 1){
            setUp(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setDown(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setLeft(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setRight(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);
        }
        else if(num == 2){
            setUp(y,x,rollback);
            setDown(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setLeft(y,x,rollback);
            setRight(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);
        }
        else if(num == 3){
            setUp(y,x,rollback);
            setRight(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setRight(y,x,rollback);
            setDown(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setDown(y,x,rollback);
            setLeft(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setLeft(y,x,rollback);
            setUp(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);
        }
        else if(num == 4){
            setLeft(y,x,rollback);
            setUp(y,x,rollback);
            setRight(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setUp(y,x,rollback);
            setRight(y,x,rollback);
            setDown(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setRight(y,x,rollback);
            setDown(y,x,rollback);
            setLeft(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);

            setDown(y,x,rollback);
            setLeft(y,x,rollback);
            setUp(y,x,rollback);
            recur(ind+1);
            rollBack(rollback);
        }
    }
}




