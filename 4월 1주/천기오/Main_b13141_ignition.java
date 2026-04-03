import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main_b13141_ignition {
    static int N,M;
    static int[][] dist;
    static List<int[]> edges;

    static final int INF = 200000000; 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dist = new int[N][N];
        edges = new ArrayList<>();
        for(int i=0;i<N;i++){
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken());

            dist[a][b] = Math.min(c,dist[a][b]);
            dist[b][a] = Math.min(c,dist[b][a]);
            edges.add(new int[] {a,b,c});
        }
        for (int k = 0; k < N; k++) {          // 거쳐 가는 정점
            for (int i = 0; i < N; i++) {      // 출발 정점
                for (int j = 0; j < N; j++) {  // 도착 정점
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        double answer = Double.MAX_VALUE;

        // 1. 모든 정점 한 번씩
        for (int S = 0; S < N; S++) {
            double localMax = 0.0; // S에서 불을 붙였을 때, 가장 늦게 타는 간선의 시간

            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int l = edge[2];

                // S에서 u, v까지 도달하는 시간과 간선 길이 l을 이용해 타는 시간을 계산
                double burnTime = (double)(dist[S][u] + dist[S][v] + l) / 2.0;

                // 최댓값 갱신
                localMax = Math.max(localMax, burnTime);
            }

            // 최솟값 갱신
            answer = Math.min(answer, localMax);
        }

        // 문제 조건에 맞게 소수점 첫째 자리까지 출력
        System.out.printf("%.1f\n", answer);
    }
}
