import java.io.*;
import java.util.*;

/**
 * 전략
 * 
 * 1. 소형 기관차가 끌수 있는 수만큼 모든 구간합을 구해놓기 -> sliding window
 * 2. i번째 구간까지 봤고 j개의 기차를 사용했을 때의 승객의 최대 수
 * 
 * 점화식
 * dp[i][j] = dp[i-subTrainCnt][j-1] + subTrain[i] , 
 */

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int[] train = new int[n];
//		int[] preSum = new int[n];
		for(int i = 0;i<n;i++) {
			train[i] = Integer.parseInt(st.nextToken());
			
//			if(i == 0) preSum[i] = train[i];
//			else preSum[i] = preSum[i-1] + train[i];
		}
		
		int subTrainCnt = Integer.parseInt(br.readLine());
		
		List<Integer> subTrainList = new ArrayList<>();
		
		//1. 구간 합 구해놓기
		int start = 0;
		int end = subTrainCnt-1;
		int sum = 0;
		for(int i = 0;i<n;i++) {
			if(end == n) break;
			
			if(i == 0) {
				for(int j = start;j<=end;j++) {
					sum += train[j];
				}
				subTrainList.add(sum);
				start++;
				end++;
			}
			else {
				sum -= train[start-1];
				sum += train[end];
				subTrainList.add(sum);
				start++;
				end++;
			}
		}
		
		//2. dp로 dp[j][i] = i번째 구간까지 봤을 때, j대수인 경우 승객의 최대값
		int[][] dp = new int[3][subTrainList.size()];
		
		//점화식
		for(int j = 0;j<3;j++) {
			for(int i = 0;i<subTrainList.size();i++) {
				//1번째 소형 기차 초기화
				if(j-1 < 0) {
					if(i == 0) dp[j][i] = subTrainList.get(i);
					else dp[j][i] = Math.max(dp[j][i-1], subTrainList.get(i));
				}
				else{
					if(subTrainCnt*j <= i) dp[j][i] = Math.max(dp[j-1][i-subTrainCnt] + subTrainList.get(i), dp[j][i-1]);
				}
				
			}
		}
		
		System.out.println(dp[2][subTrainList.size()-1]);
		
	}

}
