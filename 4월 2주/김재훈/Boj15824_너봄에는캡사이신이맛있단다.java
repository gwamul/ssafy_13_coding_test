import java.io.*;
import java.util.*;

/**
 * 전략
 * 
 * 정렬 후에 최소와 최대만 있다면 그 사이에 뭐가 되었든 고통지수는 동일하다.
 * 
 * 즉, 1 ? ? ? 7 이라면 고통지수 6이 2의 3승만큼 있는 것
 * 
 * 이걸 전체를 나란히 적으면
 * 
 * 1 3 4 5 7
 *   3 4 5 7
 *     4 5 7
 *       5 7
 *       
 * 이렇게 생각하면 for문을 2번 돌면 된다. 
 * 최대값의 합 구하기
 * 최소값의 합 구하기
 * 
 * 최대값의 합은 예를 들어서 7이라고 해보면
 * 위 예시에서 위부터 
 * 2^n-2, 2^n-3, 2^n-4, 2^n-5 번 더해진다. 
 * 즉, 2^n-1 -1 번 더해지는 것과 동일
 * 
 * 생각해보면 최소값이 빼지는 것 또한 동일하다. 
 */

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		long MOD = 1_000_000_007L;
		long[] pow2 = new long[n];
		pow2[0] = 1;
		for (int i = 1; i < n; i++) {
		    pow2[i] = (pow2[i - 1] * 2) % MOD;
		}
		long[] nums = new long[n];
		for(int i = 0;i<n;i++) {
			int num = Integer.parseInt(st.nextToken());
			nums[i] = num;
		}
		
		Arrays.sort(nums);
		
		long sum = 0;
		//최대값인 경우 더하기
		for(int i = 1;i<n;i++) {
//			sum += nums[i] * ((1 << (i))-1);
//			sum %= 1_000_000_007;
			sum = (sum + nums[i] * (pow2[i] - 1 + MOD) % MOD) % MOD;
		}
		
		//최소값인 경우 더하기
		long minSum = 0;
		for(int i = 0;i<n-1;i++) {
//			minSum += nums[i] * ((1 << (n-1-i))-1);
//			minSum %= 1_000_000_007;
			minSum = (minSum + nums[i] * (pow2[n-1-i] -1 +MOD) % MOD) % MOD;
		}
		
		long answer = ((sum + MOD) - minSum) % MOD;
		
		System.out.println(answer);
	}

}
