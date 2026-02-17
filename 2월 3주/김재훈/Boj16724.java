import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
완탐으로 한다면

일단, 한 지점에서 시작해서 명령을 따라가며 지나는 곳을 표시
표시한 곳을 다시 갔다면 해당 장소가 SAFE ZONE => 현재 표시된 곳에 회원이 있다면
 반드시 safe zone으로 들어옴

 num으로 표시한다고 치고 num++를 해서 빈 공간부터 num으로 채우는 것 다시 시작
 다시 동일 num으로 들어오면 SAFE zone

 또한 표시 안된 곳에서 시작했는데 표시 된 곳으로 합류한다면 그냥 continue;
 => num을 SAFE ZONE 크기로 하면 안됨. num은 그냥 체크용

 시간 복잡도 : 아마 2칸에서 왔다갔다 하는게 최악인거 같은데
 n*n에 *2 => 몇 백만 정도니 충분히 가능

##실수한 포인트
num으로 표시하기 때문에 다른 싸이클에서 동일한 num이 되지 않도록 먼저 num을 증가시켜주는것
-> 이것을 어디서 증가시켜주는지에 따라서 반례가 나올 수 있다.

이전에 틀린 상황
: SAFE ZONE이 결정난 시점에 num을 증가

반례 : num으로 채우고 이동중에 이미 표시된 곳을 만나서 종료(num증가 안됨)
-> 다음 싸이클에 map에 표시된 num이 지금 싸이클이랑 같아서 SAFE Zone이 아님에도 safe zone으로 됨

풀이시간 : 30분
 */

public class Main {

    static int n;
    static int m;
    static int[][] map;
    static int num = 0;
    static String[][] orders;
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] mn = br.readLine().trim().split(" ");

        n = Integer.parseInt(mn[0]);
        m = Integer.parseInt(mn[1]);
        map = new int[n][m];
        orders = new String[n][m];
        //초기화
        for(int i = 0;i<n;i++){
            String row = br.readLine().trim();
            for(int j = 0;j<m;j++){
                orders[i][j] = row.charAt(j)+"";
            }
        }

        //처음부터 돌면서 완탐
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                //명령어 따라가면서
                follow(i,j);
            }
        }

        System.out.println(answer);
    }

    static void follow(int i, int j){
        //이미 표시 한거면 끝
        if(map[i][j] != 0) return;

        map[i][j] = ++num;
        while(true){
            //명령어에 따라서 위치 조정하고
            if(orders[i][j].equals("D")) i++;
            else if(orders[i][j].equals("L")) j--;
            else if(orders[i][j].equals("R")) j++;
            else if(orders[i][j].equals("U")) i--;

            //처음온 곳이면
            if(map[i][j] == 0) map[i][j] = num;
            //"이번에"표시한 곳으로 되돌아왔다면 SAFE ZONE
            else if(map[i][j] == num){
                answer++;
                return;
            }
            //만약 새로운 곳이 이미 표시된 곳이면 끝
            else return;
        }
    }
}




