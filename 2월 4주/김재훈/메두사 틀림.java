import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 최단 경로로 이동
 * -> 일단, time을 적어서 저장
 * -> 공원에서 반대로 메두사 집까지 경로를 찾는데 상,하,좌,우의 우선순위를 반대로 적용해서 구하기
 *
 *
 * 전사는 같은 곳 위치 가능
 * -> 위치를 기준으로 돌로 변해야 한다.
 * -> 4방향을 전부 보고 어딜 볼지 결정 + 보는건 4방향이지만 전사들의 곂침은 8방향
 *
 * + 전사가 한번 같은 위치에 겹치면 이후부터는 같이 행동한다.
 * -> 즉, 한 위치의 전사 개수만 유지해도 ok
 * => 단, 전사들 위치 업데이트할 때, 순서에 주의
 * -> 잘못하면 이미 있는곳에 옮겼는데 그 다음 옮길때 더해진걸 옮길 수 도 있다.
 * 즉, wmap[i][j]를 옮기기 전에 wmap[i][j-1]이 wmap[i][j]로 옮겨졌는데
 * wmap[i][j]가 증가한 채로 옮겨지는 것 주의
 *
 * 전략
 * 1. 일단 메두사에서 공원까지의 경로를 미리 구하기
 * -> 만약 못가면 -1출력 후 끝
 *
 * 고민
 * 1. 전사들을 맵에 표시 하는가 마는가
 * -> 표시하면 메두사가 겹치는지 확인 가능
 * -> 아니라면 300번 돌아야함.
 * 근데 짜피 맵에서 옮기는 거 생각하면 동일하긴 함.
 *
 * 매두사가 보는 로직
 * 1. 8방향
 * 탐색 법
 * 메두사 기준으로 완전히 상하좌우 축이라면 그 축만 보고
 * 나머지는 대각선으로 탐색
 * 즉, 상하좌우 축 옆에서 바깥쪽으로 대각선으로 탐색하면 된다.
 *
 * 대각선의 경우 2개만 보면 된다. (각 방향마다)
 * 하 방향이라고 하면
 * 각 열을 기준으로 탐색해서 찾으면 종료하는 방식
 *
 *
 * 그럼 시간복잡도는?
 * 전 방향 탐색에 2500번
 * 메두사가 2500번 움직인다고 치면
 *
 * 총 600만 정도
 *
 * 전사들 움직임 정도야 300 * 8 이니 무시 가능
 */

public class Main {
    //상, 하, 좌, 우
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    //하, 상, 우, 좌
    static int[][] dir_re = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    //좌우상하
    static int[][] dir_lrud = {{0, -1}, {0, 1},{-1, 0}, {1, 0}};

    //좌상 상우 | 좌하 하우 | 상좌 좌하 | 하우 우상
    static int[][][] dir_diag = {{{-1, -1}, {-1, 1}}, {{1, -1}, {1, 1}}, {{-1, -1}, {1, -1}}, {{-1, 1}, {1, 1}}};

    static int[][] dir_up_diag = {{-1, -1}, {-1, 1}};

    static int n;
    static int m;
    static int sy;
    static int sx;
    static int ey;
    static int ex;

    static List<int[]> worriers;
    static Set<Integer> worriersSet;
    static int[][] map;
    static int[][] wmap;
    static int[][] view;

    static int[] dirStoneWorrierCnt;
    static List<int[]>[] tempStoneWorrierList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nm = br.readLine().trim().split(" ");
        n = Integer.parseInt(nm[0]);
        m = Integer.parseInt(nm[1]);

        String[] se = br.readLine().trim().split(" ");
        sy = Integer.parseInt(se[0]);
        sx = Integer.parseInt(se[1]);
        ey = Integer.parseInt(se[2]);
        ex = Integer.parseInt(se[3]);

        map = new int[n][n];
        wmap = new int[n][n];
        view = new int[n][n];
        //worriers = new ArrayList<>();

        String[] lineWorrier =  br.readLine().trim().split(" ");
        for(int i = 0;i<m;i++){
            int y = Integer.parseInt(lineWorrier[i*2]);
            int x = Integer.parseInt(lineWorrier[i*2+1]);
            //worriers.add(new int[] {y,x});
            wmap[y][x]++;
        }

        for(int i = 0;i<n;i++){
            String[] line =  br.readLine().trim().split(" ");
            for(int j = 0;j<n;j++){
                map[i][j] = Integer.parseInt(line[j]);
            }
        }

        //1. 메두사와 공원 사이의 최단 경로
        //일단 최단거리를 시간으로 저장
        int[][] time = medu_park_bfs();
        if(time[ey][ex] == 0){
            System.out.println(-1);
            return;
        }
//        for(int i = 0;i<n;i++){
//            for(int j = 0;j<n;j++){
//                System.out.print(time[i][j]+" ");
//            }
//            System.out.println();
//        }
        //시간을 기준으로 상,하,좌,우 우선순위 적용해서 메두사의 이동 경로 구하기
        List<int[]> route = findRoute(time);

        //2.메두사 이동 + 전사 이돋
        int noView = -1;
        //4방향(각 방향당 3방향(대각2개, 정1)) 탐색해서 맵에 메두사가 보이는 곳 표시
        //보이는 곳은 비트연산으로 1111 -> 상하좌우 로 표시
        //이렇게 나눠놔야 전사가 움직일 곳을 찾기 편함
        //안보이는 곳은 noview로 채우기 -> 한 cycle마다 1씩 감소됨(그래야 새로 맵에 채울때 편함)
        for(int i = 1;i<route.size()-1;i++){
            int[] meduPos = route.get(i);
            //메두사가 전사의 위치에 가면 전사를 죽임
            wmap[meduPos[0]][meduPos[1]] = 0;
            searchDirAndMoveWorrier(meduPos, noView);
            noView--;
            for(int h = 0;h<n;h++){
                for(int j = 0;j<n;j++){
                    view[h][j] = 0;
                }
            }
        }
        System.out.println(0);
    }

    static void printView(){
        for(int i = 0;i<n;i++){
            for(int j = 0;j<n;j++){
                System.out.printf("%3d",view[i][j]);
            }
            System.out.println();
        }
    }

    private static void printWmap() {
        for(int i = 0;i<n;i++){
            for(int j = 0;j<n;j++){
                System.out.printf("%3d",wmap[i][j]);
            }
            System.out.println();
        }
    }

    private static void searchDirAndMoveWorrier(int[] meduPos, int noView) {
//        System.out.println("처음 들어왔을 때, veiw는 0으로 초기화 되어 야 한다. : , noView : "+noView);
//        printView();
        //이 worriers는 움직일 수 있는 전사들만
        worriers = new ArrayList<>(); //{전사 y,전사 x,전사 수}
        worriersSet = new HashSet<>(); //350진법으로 하자.
        dirStoneWorrierCnt = new int[]{0,0,0,0};
        tempStoneWorrierList = new List[4];
        for(int i = 0;i<4;i++){
            tempStoneWorrierList[i] = new ArrayList<>();
        }
        //1. 매두사 시야 탐색 -> view 채우기
        for(int i = 0;i<4;i++){
            //0001 은 상, 0010은 하, 0100 은 좌, 1000은 우
            int viewNum = 1 << i;

            //대각 1
            int o1y = meduPos[0] + dir_diag[i][0][0];
            int o1x = meduPos[1] + dir_diag[i][0][1];
            if(!(o1y < 0 || o1y >= n || o1x < 0 || o1x >= n)){
                view[o1y][o1x] |= viewNum;
                fillViewFromMedusa(new int[]{o1y, o1x}, viewNum, noView, i, 0);
            }
            //대각 2
            o1y = meduPos[0] + dir_diag[i][1][0];
            o1x = meduPos[1] + dir_diag[i][1][1];
            if(!(o1y < 0 || o1y >= n || o1x < 0 || o1x >= n)){
                view[o1y][o1x] |= viewNum;
                fillViewFromMedusa(new int[]{o1y, o1x}, viewNum, noView, i, 1);
            }
            //대각 아닌 열 혹은 행
            fillViewFromMedusaRC(meduPos, viewNum, noView, i);
        }
//        System.out.println("현재 메두사 위치 : "+Arrays.toString(meduPos));
//        System.out.println("전사 위치 : =====================");
//        printWmap();
//        System.out.println("view : ======================");
//        printView();


//        System.out.println("못움직이는 전사들");
//        System.out.println(Arrays.toString(dirStoneWorrierCnt));
//        System.out.println("못 움직이는 전사들 상세");
//        for(int i = 0;i<4;i++){
//            System.out.println(i+"인덱스 못움직이는 전사 : ");
//            for(int[] pos : tempStoneWorrierList[i]){
//                System.out.println(Arrays.toString(pos));
//            }
//        }

//        System.out.println("움직일 수 있는 전사들");
//        for(int[] worrier : worriers){
//            System.out.println(Arrays.toString(worrier));
//        }
        //2. 각 방향에서 전사의 최대를 구해야 한다.
        int max = 0;
        int direction = 0;
        for(int i = 0;i<4;i++){
            if(dirStoneWorrierCnt[i] > max){
                max = dirStoneWorrierCnt[i];
                direction = i;
            }
        }

        //돌이 아니었던 전사들을 worriers에 추가
//        System.out.println("메두사가 바라보고 있는 방향 : " + direction +", 비트는 : "+ (1 << direction));
        for(int i = 0;i<4;i++){
            if(direction == i) continue;

            for(int[] notStoneWorrier : tempStoneWorrierList[i]){
                int ny = notStoneWorrier[0];
                int nx = notStoneWorrier[1];

                //만약 이 전사의 view 위치가 direction에서 보이는 곳이라면
                //즉, 1 << direction 이랑 view[위치]랑 & 연산을 했는데 0이 아니라면 stone인것
//                System.out.println("이상한 부분; 일단 ny : "+ny+", nx : "+nx+", "+(1 << direction) +", view 값  : "+view[ny][nx]+", 둘이 &하면 : "+((1 << direction) & view[ny][nx]));
                if(((1 << direction) & view[ny][nx]) == 0){
                    //포함이 안되어야 안전
                    int key = (ny+1)*100 + nx+1;
                    //중복 안되게 처리하면서 추가
                    if(!worriersSet.contains(key)){
                        worriersSet.add(key);
                        worriers.add(new int[]{ny,nx, notStoneWorrier[2]});
                    }
                }


            }
        }
//        System.out.println("현재 움직일 수 있는 전사들 목록 : ");
//        for(int[] worrier : worriers){
//            System.out.println(Arrays.toString(worrier));
//        }

        int fightWorrierCnt = 0;
        int moveCnt = 0;
        List<int[]> nextWorriersPos = new ArrayList<>();
        List<int[]> lastWorriersPos = new ArrayList<>();
//        System.out.println("이동 전 전사들 위치 :");
//        printWmap();
        if(max > 0){
            if(worriers.isEmpty()) System.out.println("0 "+max+" 0");
            else{
                //3. 돌이 아닌 전사가 움직임
                //첫 이동
                for(int[] worrier : worriers){
                    //업데이트할 때, 중복 안되게 조심하고
                    int cannotMove = 1 << direction;
                    //view의 값에 &를 했을 때, 0이라면 이동 가능하다는 뜻
                    int diff = Math.abs(meduPos[0] - worrier[0]) +  Math.abs(meduPos[1] - worrier[1]);
                    boolean move = false;
                    for(int i = 0;i<4;i++){
                        int ny = worrier[0] + dir[i][0];
                        int nx = worrier[1] + dir[i][1];
                        if(ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                        if(view[ny][nx] < 0 || (view[ny][nx] & cannotMove) == 0){
                            int nextDiff = Math.abs(meduPos[0] - ny) +  Math.abs(meduPos[1] - nx);
                            if(diff > nextDiff){ //더 가깝게 이동 했다면
                                wmap[worrier[0]][worrier[1]] = 0; //현재는 지우고
                                move = true;
                                moveCnt += worrier[2];
                                if(nextDiff == 0){
                                    fightWorrierCnt += worrier[2];
                                    break;
                                }
                                nextWorriersPos.add(new int[]{ny,nx,worrier[2]});
                                break;
                            }
                        }
                    }
                    //안움직였다면 일단 지우고 나중에 다시 추가해주기
                    if(!move){
                        wmap[worrier[0]][worrier[1]] = 0; //현재는 지우고
                        nextWorriersPos.add(worrier);
                    }
                }
//                System.out.println("두번째로 움직일 수 있는 전사들 목록 : ");
//                for(int[] worrier : nextWorriersPos){
//                    System.out.println(Arrays.toString(worrier));
//                }

                //두번째 이동
                for(int[] worrier : nextWorriersPos){
                    //업데이트할 때, 중복 안되게 조심하고
                    int cannotMove = 1 << direction;
                    //view의 값에 &를 했을 때, 0이라면 이동 가능하다는 뜻
                    int diff = Math.abs(meduPos[0] - worrier[0]) +  Math.abs(meduPos[1] - worrier[1]);
                    boolean move = false;
                    for(int i = 0;i<4;i++){
                        int ny = worrier[0] + dir_lrud[i][0];
                        int nx = worrier[1] + dir_lrud[i][1];
                        if(ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                        if(view[ny][nx] == -1 || (view[ny][nx] & cannotMove) == 0){
                            int nextDiff = Math.abs(meduPos[0] - ny) +  Math.abs(meduPos[1] - nx);
                            if(diff > nextDiff){ //더 가깝게 이동 했다면
                                move = true;
                                moveCnt += worrier[2];
                                if(nextDiff == 0){
                                    fightWorrierCnt+= worrier[2];
                                    break;
                                }
                                lastWorriersPos.add(new int[]{ny,nx,worrier[2]});
                                break;
                            }
                        }
                    }
                    //안움직였다면 추가해주기
                    if(!move){
                        lastWorriersPos.add(worrier);
                    }
                }

                //두번의 이동 후 전사들 위치 업데이트
                for(int[] worrier : lastWorriersPos){
                    wmap[worrier[0]][worrier[1]] += worrier[2];
                }
//                System.out.println("이동 후 전사들 위치 :");
//                printWmap();
                //출력
                System.out.println(moveCnt+" "+max+" "+fightWorrierCnt);
            }
        }
        else{//전사가 없다.
            System.out.println("0 0 0");
        }
    }



    //대각 말고 상하좌우 채우기
    static void fillViewFromMedusaRC(int[] meduPos, int viewNum, int noView, int direction){
        int ny = meduPos[0]+ dir[direction][0];
        int nx = meduPos[1]+ dir[direction][1];
        //System.out.println("이거 실행되지? : " +direction+" 채울 위치 : "+ny+", "+nx+", viewNum : "+viewNum);
        while(true){
            if(ny < 0 || ny >= n || nx < 0 || nx >= n) break;
            //전사 발견했으면 전사 뒤로 noView로 채우고 해당 선 채우기 종료
            if(wmap[ny][nx] > 0){
                tempStoneWorrierList[direction].add(new int[]{ny,nx,wmap[ny][nx]});
                dirStoneWorrierCnt[direction] += wmap[ny][nx];
                view[ny][nx] |= viewNum;
                while(true){
                    ny += dir[direction][0];
                    nx += dir[direction][1];
                    if(ny < 0 || ny >= n || nx < 0 || nx >= n) break;
                    if(wmap[ny][nx] > 0){
                        int key = (ny+1)*100 + nx+1;
                        if(!worriersSet.contains(key)){
                            worriersSet.add(key);
                            worriers.add(new int[]{ny,nx,wmap[ny][nx]});
                        }
                    }
                    view[ny][nx] = noView;
                }
                break;
            }
            view[ny][nx] |= viewNum;
            //System.out.println("이거 됌?");
            ny += dir[direction][0];
            nx += dir[direction][1];
        }
    }
    //실제 메두사의 위치에서 대각으로 한번 움직인 후
    static void fillViewFromMedusa(int[] meduPos, int viewNum, int noView, int direction, int sideDir){
        int ny = meduPos[0];//+ dir_diag[direction][sideDir][0];
        int nx = meduPos[1];//+ dir_diag[direction][sideDir][1];

        while(true){
            if(ny < 0 || ny >= n || nx < 0 || nx >= n) break;

            if(view[ny][nx] == noView) break; //이미 시야에서 벗어난 부분
            //전사 발견했으면 전사 뒤로 noView로 채우고 해당 선 채우기 종료
            if(wmap[ny][nx] > 0){
                view[ny][nx] |= viewNum;
                dirStoneWorrierCnt[direction] += wmap[ny][nx];
                tempStoneWorrierList[direction].add(new int[]{ny,nx,wmap[ny][nx]});
                fillViewBehindWorrier(new int[]{ny, nx}, noView, direction, sideDir);
                break;
            }
            view[ny][nx] |= viewNum;
            ny += dir_diag[direction][sideDir][0];
            nx += dir_diag[direction][sideDir][1];
        }
        int nextY = meduPos[0]+dir[direction][0];
        int nextX = meduPos[1]+dir[direction][1];
        if(nextY < 0 || nextY >= n || nextX < 0 || nextX >= n) return;
        //만약 다음의 첫 시작이 이미 noView라면 더이상 볼 필요는 없다.
        if(view[nextY][nextX] == noView) return;

        view[nextY][nextX] |= viewNum; //다음 그 다음부터 시작이어서 채우고 가야함.
        fillViewFromMedusa(new int[]{nextY, nextX},viewNum,  noView, direction, sideDir);
    }

    /**
     * 메두사의 시야에 걸린 전사 뒤를 noView로 채우기
     *
     */
    static void fillViewBehindWorrier(int[] worrierPos, int noView, int direction, int sideDir){
        int ny = worrierPos[0]+ dir_diag[direction][sideDir][0];
        int nx = worrierPos[1]+ dir_diag[direction][sideDir][1];

        while(true){
            if(ny < 0 || ny >= n || nx < 0 || nx >= n) break;
            if(wmap[ny][nx] > 0){
                int key = (ny+1)*100 + nx+1;
                if(!worriersSet.contains(key)){
                    worriersSet.add(key);
                    worriers.add(new int[]{ny,nx,wmap[ny][nx]});
                }
            }
            view[ny][nx] = noView;
            ny += dir_diag[direction][sideDir][0];
            nx += dir_diag[direction][sideDir][1];
        }
        int nextY = worrierPos[0]+dir[direction][0];
        int nextX = worrierPos[1]+dir[direction][1];
        if(nextY < 0 || nextY >= n || nextX < 0 || nextX >= n) return;
        view[nextY][nextX] = noView; //다음 그 다음부터 시작이어서 채우고 가야함.
        //만약 전사 위치라면 추가해줘야한다.
        if(wmap[nextY][nextX] > 0) {
            int key = (nextY + 1) * 100 + nextX + 1;
            if (!worriersSet.contains(key)) {
                worriersSet.add(key);
                worriers.add(new int[]{nextY, nextX, wmap[nextY][nextX]});
            }
        }

        fillViewBehindWorrier(new int[]{nextY, nextX}, noView, direction, sideDir);
    }

    private static List<int[]> findRoute(int[][] time) {
        int distance = time[ey][ex];
        int[][] route = new int[distance][2];
        //공원 부터 반대로 출발
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{ey, ex});
        route[time[ey][ex]-1] = new int[]{ey, ex};

        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            //메두사 기준으로 상,하,좌,우니깐
            //공원 기준으로는 하,상,우,좌
            for(int i = 0;i<4;i++){
                int y = cur[0] + dir_re[i][0];
                int x = cur[1] + dir_re[i][1];

                if(y < 0 || y >= n || x < 0 || x >= n) continue;
                //시간상 바로 이전 최우선 경로 찾았다면
                if(time[y][x] == time[cur[0]][cur[1]]-1){
                    if(time[y][x] == 0) break;
                    route[time[y][x]-1] = new int[]{y, x};
                    queue.add(new int[]{y, x});
                    break; //우선순위 기준이므로 다른거 볼 필요 없음.
                }
            }
        }

        List<int[]> res = new ArrayList<>();
        for(int i = 0;i<distance;i++){
            res.add(route[i]);
        }
        return res;
    }

    private static int[][] medu_park_bfs() {
        //일단 메두사에서 공원까지는 시간만 저장하면 된다.
        int[][] time = new int[n][n]; //visited 역할도

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sy,sx});
        time[sy][sx] = 1; //1부터 시작하고 0인것을 찾자

        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            for(int i = 0;i<4;i++){
                int y = cur[0] + dir[i][0];
                int x = cur[1] + dir[i][1];

                if(y < 0 || y >= n || x < 0 || x >= n || map[y][x] != 0)continue;
                if(time[y][x] != 0)continue; //이미 갔던곳은 무조건 더 시간이 적게 걸린다. 같거나

                time[y][x] = time[cur[0]][cur[1]] + 1;
                queue.offer(new int[]{y,x});
            }
        }

        return time;
    }


}
