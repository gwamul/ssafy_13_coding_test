import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[][] dir = {{0,1,2},{1,2,0},{0,2,1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] abc = br.readLine().trim().split(" ");
        int[] sort = Arrays.stream(abc).mapToInt(Integer::parseInt).toArray();//sorted().toArray();

        Set<Integer> set = new HashSet<>();

        if(sort[0] == sort[1] && sort[1] == sort[2]){
            System.out.println(1);
            return;
        }

        Queue<int[]>  queue = new ArrayDeque<>();
        queue.add(new int[]{sort[0], sort[1], sort[2]});

        int answer = 0;
        while(!queue.isEmpty() && answer == 0){
            int[] cur =  queue.poll();

            for(int i = 0; i < 3; i++){
                int small = Math.min(cur[dir[i][0]], cur[dir[i][1]]);
                int big = Math.max(cur[dir[i][0]], cur[dir[i][1]]);

                big -= small;
                small *= 2;

                if(big == small && small == cur[dir[i][2]]){
                    answer = 1;
                    break;
                }

                int key = 500*500*(big-1) + 500*(small-1)+ cur[dir[i][2]]-1;
                if(!set.contains(key)){
                    set.add(key);
                    queue.add(new int[]{big, small, cur[dir[i][2]]});
                }

            }
        }

        System.out.println(answer);
    }

}
