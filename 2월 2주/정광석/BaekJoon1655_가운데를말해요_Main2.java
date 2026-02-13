package saffy_algo.BaekJoon.골드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BaekJoon1655_가운데를말해요_Main2 {
	static int n;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] count = new int[20001];
        
        int first = Integer.parseInt(br.readLine());
        count[first + 10000]++;
        int median = first + 10000;
        int leftTotal = 0;  
        int total = 1;   
        sb.append(first).append("\n");
/*
 * 카운팅 소트는 데이터 값 직접 비교 안함
 * 각 숫자 몇번 등장했나 개수를 센다.
 * O(N)
 * 
 * 데이터의 범위가 작을 때 써야 한다. - 데이터가 가질 수 있는 최대값 까지를 담을 수 있는 배열이 필요함
 * 공간 복잡도 O(K)
 * 시간 복잡도 O(N+K)
 * 
 * 
 * 
 * 
 */
        for (int i = 1; i < N; i++) {
            int num = Integer.parseInt(br.readLine()) + 10000;
            count[num]++;
            total++;

            if (num < median) {
                leftTotal++;
            }

            
            int targetLeft = (total - 1) / 2;

            if (leftTotal > targetLeft) {
                while (leftTotal > targetLeft) {
                    median--;
                    leftTotal -= count[median];
                }
            } else if (leftTotal + count[median] <= targetLeft) {
            	
                while (leftTotal + count[median] <= targetLeft) {
                    leftTotal += count[median];
                    median++;
                }
            }
            
            sb.append(median - 10000).append("\n");
        }
        System.out.print(sb);
	}
}
