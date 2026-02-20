import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
딱 봤을 때, 완탐인거 같긴 한데 가지치기가 필요할 거 같음

#방법1
일단 무식하게 완탐
-> 이중 for문 돌다가 1이 보이면 recur()
-> 길이가 1인, 2인, 3,4,5 인 경우를 한 후에
길이 만큼 오른쪽으로 가서 계속
이거 반복하면 가능은 한데... 어디서 가지치기를 하나..

#방법2
근데 위처럼 완탐 안하면
연결된 1을 찾고 해당 경우에서 정사각형으로 채울 수 있는 경우의 수를
각각 찾은 이후에 그 개수로 완탐하면서 가능한지 판별
-> 어떻게 잘 구현할지 모르겠음
-> 어짜피 방법1처럼 탐색해야 하는거 아닌가?

방법1의 시간 복잡도
각 사각형이 5개씩이니
총은 25!을 5! 5개로 나누는 건데 623,360,743,125,120
가지치기로 충분한지 애매하네...


가지치기1.
사각형 개수를 각 5개 이상 했다면 끝

가지치기 2.
이미 min을 넘어 섰을 때

가지치기 3.
1의 숫자를 유지해서 0개가 되면 min값 업데이트

### 가장 큰 포인트

1인 부분부터 조합을 구현하는데
첫 1인 부분에서 모든 분기를 확인하고 끝나야 한다. 

=> 다음 1로 넘어가면 이전 1이 안지워진 상태여서 의미없는 탐색이 진행.

 */

public class Main {

    static int[][] map;
    static int min = Integer.MAX_VALUE;
    static List<Integer> list;
    static int oneCnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        map = new int[10][10];
        list = new ArrayList<>();
        oneCnt = 0;
        //초기화
        for(int i = 0;i<10;i++){
            String[] row = br.readLine().trim().split(" ");
            for(int j = 0;j<10;j++){
                map[i][j] = Integer.parseInt(row[j]);
                if(map[i][j] == 1)oneCnt++;
            }
        }
        //파라미터는 각 길이의 정사각형 사용 개수 5개랑 중복 조합을 위한 시작 y,x값, 사각형 사용 개수
        recur(0,0,0,0,0,0,0, oneCnt);

        if(min == Integer.MAX_VALUE) min = -1;
        System.out.println(min);
    }

    static void recur(int one, int two, int three, int four, int five, int startY, int cnt, int oneCnt) {

        //5개 이상 사용 X
        if(one > 5 || two > 5 || three > 5 || four > 5 || five > 5){
            return;
        }

        //이미 min보다 같거나 크면 끝
        if(cnt >= min) return;
        
        if(oneCnt == 0) {
        	if(min > cnt) min = cnt;
        	return;
        }


        for(int i = startY;i<10;i++){
            for(int j = 0;j<10;j++){
                if(map[i][j] == 1){
                    //큰 사각형 부터 돌리기
                    if(i+4 < 10 && j+4 < 10 && check(5, i, j)){
                        fillZero(5, i,j);
                        recur(one, two, three, four, five+1, i, cnt+1, oneCnt-25);
                        fillOne(5, i,j);
                    }
                    if(i+3 < 10 && j+3 < 10 && check(4, i, j)){
                        fillZero(4, i,j);
                        recur(one, two, three, four+1, five, i, cnt+1, oneCnt-16);
                        fillOne(4, i,j);
                    }
                    if(i+2 < 10 && j+2 < 10 && check(3, i, j)){
                        fillZero(3, i,j);
                        recur(one, two, three+1, four, five, i, cnt+1, oneCnt-9);
                        fillOne(3, i,j);
                    }
                    if(i+1 < 10 && j+1 < 10 && check(2, i, j)){
                        fillZero(2, i,j);
                        recur(one, two+1, three, four, five, i, cnt+1, oneCnt-4);
                        fillOne(2, i,j);
                    }

                    map[i][j] = 0;
                    recur(one+1, two, three, four, five, i, cnt+1, oneCnt-1);
                    map[i][j] = 1;
                    
                    return;
                }
            }
        }

   

    }

    static void print(){
        System.out.println("==============================");
        for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }

    //해당 정사각형을 사용할 수 있는지 체크
    static boolean check(int n, int y, int x){
        for(int i = y;i<y+n;i++){
            for(int j = x;j<x+n;j++){
                if(map[i][j] != 1) return false;
            }
        }
        return true;
    }

    static void fillOne(int n, int y, int x){
        for(int i = y;i<y+n;i++){
            for(int j = x;j<x+n;j++){
                map[i][j] = 1;
            }
        }
    }

    static void fillZero(int n, int y, int x){
        for(int i = y;i<y+n;i++){
            for(int j = x;j<x+n;j++){
                map[i][j] = 0;
            }
        }
    }
}