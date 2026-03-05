import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 일단 완탐은 시간 때문에 안됨.
 *
 * 그리디 도전
 * 전략 gi중에 가장 큰 곳에 넣으면 된다.
 *
 * 근데 가장 큰 비어있는 곳을 for문을 돌리면 시간이 좀 애매
 *
 * ----
 * 중간에 틀린 이유
 * 1. 빈 공간에 도크를 넣었어도 해당 도크 양 옆으로 만약 이미 있는게 있다면 연결해줘야 한다.
 *
 * 2. 큰 곳에서 부터 find로 찾아서 작은 곳에 넣었어도 그 작은곳 보다 1작은 곳에 이미 채워
 * 져 있는지 확인해서 있다면 연결해줘야 한다.
 *
 * ==> 연결 상황을 잘 업데이트 해줘야 함
 *
 */

public class Main {

    static int[] parent;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int G = Integer.parseInt(br.readLine().trim());
        int P = Integer.parseInt(br.readLine().trim());

        parent = new int[G+1];
        Arrays.fill(parent, -1);
        boolean flag = false;
        int result = 0;
        while(P-- > 0){
            int dock =  Integer.parseInt(br.readLine().trim());

            if(flag) continue;
            //가장 큰 곳이 비어있다면 넣고
            if(parent[dock] == -1){
                parent[dock] = dock;
                result++;

                //만약 작은 도크가 먼저 채워진 상황일 수도 있으니 연결
                if(parent[dock-1] != -1){
                    union(dock, dock-1);
                }
                //큰 곳이 먼저 채워져 있어도 연결
                if(dock+1 < G+1 && parent[dock+1] != -1){
                    union(dock+1, dock);
                }
            }
            else{ //아니면 find로 연속된것 중에서 가장 작은거 다음에 넣기
                int root = find(dock);
                if(root == 1){
                    //현재 1번 도크까지 전부 채워져있다는 뜻
                    flag = true;
                    continue;
                }
                parent[root-1] = root-1;
                union(root-1, root);
                result++;

                //채웠는데 더 작은 연속된 게 이미 채워져 있으면 또 연결
                if(parent[root-2] != -1){
                    union(root-2, root-1);
                }
            }
            //System.out.println(Arrays.toString(parent));
        }

        System.out.println(result);
    }

    //루트 찾기
    static int find(int x){
        if(parent[x] == x){
            return x;
        };
        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b){
        int rootA = find(a);
        int rootB = find(b);
        if(rootA == rootB) return;

        if(rootA < rootB){
            parent[rootB] = rootA;
        }
        else{
            parent[rootA] = rootB;
        }
    }

}




