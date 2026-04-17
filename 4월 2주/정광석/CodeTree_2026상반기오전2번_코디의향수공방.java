package saffy_algo.CodeTree;

import java.io.*;
import java.util.*;

public class CodeTree_2026상반기오전2번_코디의향수공방 {
    static int[] scent;
    static boolean[] alive;
    static int nextIdx;
    static int[] sortedUnique;
    static int[] sortedAll;
    static int uniqueSize;
    static int allSize;
    static boolean dirty = true;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int Q = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int __cmd__ = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int MAX = N + Q + 5;
        scent = new int[MAX];
        alive = new boolean[MAX];
        sortedUnique = new int[MAX];
        sortedAll = new int[MAX];
        nextIdx = N + 1;

        for (int i = 1; i <= N; i++) {
            scent[i] = Integer.parseInt(st.nextToken());
            alive[i] = true;
        }

        for (int q = 0; q < Q - 1; q++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            int val = Integer.parseInt(st.nextToken());

            if (type == 2) {
                scent[nextIdx] = val;
                alive[nextIdx] = true;
                nextIdx++;
                dirty = true;

            } else if (type == 3) {
                if (val >= nextIdx || !alive[val]) {
                    sb.append(-1).append('\n');
                } else {
                    sb.append(scent[val]).append('\n');
                    alive[val] = false;
                    dirty = true;
                }

            } else if (type == 4) {
                if (dirty) rebuild();
                sb.append(blending(val)).append('\n');

            } else if (type == 5) {
                if (dirty) rebuild();
                sb.append(perfume(val)).append('\n');
            }
        }

        System.out.print(sb);
    }

    static void rebuild() {
        allSize = 0;
        for (int i = 1; i < nextIdx; i++) {
            if (alive[i]) {
                sortedAll[allSize++] = scent[i];
            }
        }
        Arrays.sort(sortedAll, 0, allSize);

        // 고유값 추출 (정렬된 배열에서 중복 제거)
        uniqueSize = 0;
        for (int i = 0; i < allSize; i++) {
            if (i == 0 || sortedAll[i] != sortedAll[i - 1]) {
                sortedUnique[uniqueSize++] = sortedAll[i];
            }
        }

        dirty = false;
    }

    static int blending(int K) {
        if (uniqueSize == 0) return -1;
        int[] dp = new int[K + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= K; i++) {
            for (int j = 0; j < uniqueSize; j++) {
                int s = sortedUnique[j];
                if (s > i) break;
                if (dp[i - s] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - s] + 1);
                }
            }
        }
        return dp[K] == Integer.MAX_VALUE ? -1 : dp[K];
    }

    static long perfume(int K) {
        if (allSize == 0) return 0;

        long total = (long) allSize * allSize * allSize;
        long less = 0;

        for (int i = 0; i < allSize; i++) {
            for (int j = 0; j < allSize; j++) {
                int need = K - sortedAll[i] - sortedAll[j];
                int lo = 0, hi = allSize;
                while (lo < hi) {
                    int mid = (lo + hi) / 2;
                    if (sortedAll[mid] < need) lo = mid + 1;
                    else hi = mid;
                }
                less += lo;
            }
        }

        return total - less;
    }
}