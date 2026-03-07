import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * type은 비트로
 * TCM
 * 111
 *
 * T : 1
 * C : 2
 * M : 4
 */

public class Main {

    static class Group{
        int type;
        int[] leader; //리더 위치
        List<int[]> members = new ArrayList<>();
    }

    static int n;
    static int t;
    static int[][] food;
    static int[][] holly;

    static List<Group> groups;

    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] nt = br.readLine().trim().split(" ");
        n = Integer.parseInt(nt[0]);
        t = Integer.parseInt(nt[1]);
        food = new int[n][n];
        holly = new int[n][n];
        for(int i = 0;i<n;i++) {
            char[] chs = br.readLine().trim().toCharArray();
            for(int j = 0;j<n;j++) {
                food[i][j] = 1 << "TCM".indexOf(chs[j]);
            }
        }

        for(int i = 0;i<n;i++) {
            String[] line = br.readLine().trim().split(" ");
            for(int j = 0;j<n;j++) {
                holly[i][j] = Integer.parseInt(line[j]);
            }
        }
        while(t-- > 0){
            groups = new ArrayList<>();
            morning();
            evening();
            afternoon();

            printHolly();
        }
    }


    private static void printHolly() {
        int[] hollySum = new int[8];
        for(int i = 0;i<n;i++) {
            for(int j = 0;j<n;j++) {
                hollySum[food[i][j]] += holly[i][j];
            }
        }
        System.out.println(hollySum[7] +" "+ hollySum[3] +" "+ hollySum[5] +" "+ hollySum[6] +" "+ hollySum[4] +" "+ hollySum[2] +" "+ hollySum[1]);
    }

    private static void afternoon() {
        //신앙심 전파

        //전파 순서 정렬
        int[] groupSortStandered_size = {0,1,1,2,1,2,2,3};
        groups.sort((o1, o2) -> {
            //1. 단일, 이중, 삼중 기준으로
            int result = Integer.compare(groupSortStandered_size[o1.type], groupSortStandered_size[o2.type]);
            if (result != 0) return result;

            //2. 대표자의 신앙심 기준
            result = Integer.compare(holly[o1.leader[0]][o1.leader[1]], holly[o2.leader[0]][o2.leader[1]]);
            if (result != 0) return result*(-1);

            //3. 대표자 행번호 기준
            result = Integer.compare(o1.leader[0], o2.leader[0]);
            if (result != 0) return result;

            //4. 대표자의 열 번호 기준
            return Integer.compare(o1.leader[1], o2.leader[1]);
        });

        boolean[][] cannotSpread = new boolean[n][n];
        //차례대로 전파
        for(Group g:groups){
            if(cannotSpread[g.leader[0]][g.leader[1]]) continue;
            int dir_ind = holly[g.leader[0]][g.leader[1]] % 4;
            int x = holly[g.leader[0]][g.leader[1]]-1;
            holly[g.leader[0]][g.leader[1]] = 1;

            spread(x, g.leader[0], g.leader[1], g.type, dir_ind, cannotSpread);
        }
    }

    private static void spread(int x, int posy, int posx, int type, int dir_ind, boolean[][] cannotSpread) {
        int ny = posy + dir[dir_ind][0];
        int nx = posx + dir[dir_ind][1];

        if(ny < 0 || ny >= n || nx < 0 || nx >= n) return;
        if(x <= 0 ) return;

        if(food[ny][nx] == type){
            spread(x, ny, nx, type, dir_ind, cannotSpread);
        }
        else if(x > holly[ny][nx]) {
            int cost = ++holly[ny][nx];
            food[ny][nx] = type;
            cannotSpread[ny][nx] = true;
            spread(x-cost, ny, nx, type, dir_ind, cannotSpread);
        }
        else{
            food[ny][nx] |= type;
            holly[ny][nx] += x;
            x = 0;
            cannotSpread[ny][nx] = true;
        }
    }

    private static void evening() {
        //1. bfs로 인접한 신봉자들끼리 그룹 짓기
        boolean[][] visited = new boolean[n][n];

        for(int i = 0;i<n;i++) {
            for(int j = 0;j<n;j++) {
                if(!visited[i][j]) {
                    //같은 그룹 내에서 리더 뽑고
                    //groups에 그룹 추가
                    bfs(visited, i, j);
                }
            }
        }

        //2. 대표자에게 신앙심 1씩 넘기기
        for(Group g:groups){
            //대표자에게 그룹 크기만큼 신앙심 더하고
            holly[g.leader[0]][g.leader[1]] += g.members.size();
            //전체 1씩 감소
            for(int[] member : g.members) {
                holly[member[0]][member[1]]--;
            }
        }
    }

    private static void bfs(boolean[][] visited, int y, int x) {

        Group g = new Group();

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {y, x});
        visited[y][x] = true;
        int type = food[y][x];
        g.type = type;
        g.members.add(new int[] {y, x});

        int[] leader = new int[]{y, x};

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();

            for(int i = 0;i<4;i++){
                int ny = cur[0] + dir[i][0];
                int nx = cur[1] + dir[i][1];

                if(ny < 0 || ny >= n || nx < 0 || nx >= n) continue;
                if(visited[ny][nx]) continue;

                if(type == food[ny][nx]){
                    visited[ny][nx] = true;
                    queue.add(new int[] {ny, nx});
                    g.members.add(new int[] {ny, nx});

                    //대표자 선정
                    leader = chooseLeader(leader, new int[]{ny, nx});
                }

            }
        }
        g.leader = leader;
        groups.add(g);
    }

    static int[] chooseLeader(int[] curLeader, int[] canLeader){
        //1. 신앙심 기준
        if(holly[curLeader[0]][curLeader[1]] < holly[canLeader[0]][canLeader[1]]) {
            return canLeader;
        }
        else if(holly[curLeader[0]][curLeader[1]] == holly[canLeader[0]][canLeader[1]]) {
            //2. 행 기준
            if(curLeader[0] > canLeader[0]) {
                return canLeader;
            }
            else if(curLeader[0] == canLeader[0]) {
                //3. 열 기준
                if(curLeader[1] > canLeader[1]) {
                    return canLeader;
                }
            }
        }
        //전부 아니라면 원래 리더
        return curLeader;
    }

    private static void morning() {
        for(int i = 0;i<n;i++) {
            for(int j = 0;j<n;j++) {
                holly[i][j]++;
            }
        }
    }

}