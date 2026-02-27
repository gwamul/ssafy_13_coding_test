package week2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b1765 {
    static int[] parent;
    static int N, M;
    
    // b의 부모를 a로 설정한다.
    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) parent[b] = a;
    }
    static int find(int a) {
        if (parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }
    static int enemy(int x) {
        return x + N;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        
        // [0,N-1] 본인, [N,2*N-1] 나의 적
        parent = new int[2*N];
        
        // 초기화
        for(int i=0;i<2*N;i++){
            parent[i] = i;
        }
        
        for(int i=0;i<M;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String type = st.nextToken();
            
            //0-based로 변환
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            
            // enemy는 가상의 인덱스다. 실제 조상은 모두 실존이어야 하므로 union 순서 주의
            if(type.equals("E")) {
                int enemyA = find(enemy(a));
                int enemyB = find(enemy(b));
            	// a의 적과 b는 같은 편
                union(b,enemyA);
                // b의 적과 a는 같은 편
                union(a,enemyB);

            }
            else{
                union(a, b);
            }
        }
        int count = 0;
        boolean[] seen = new boolean[N];
        for(int i=0;i<N;i++){
            int r = find(i);

            if(!seen[r]) {
                seen[r] = true;
                count++;
            }
        }
        System.out.println(count);

    }
}
