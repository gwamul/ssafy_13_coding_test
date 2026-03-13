import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 */

public class Main{
	static int n;
	static int c;
	static List<Integer> bag;
	static long[] prefixSum;
	static long count;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] nc = br.readLine().trim().split(" ");
		n = Integer.parseInt(nc[0]);
		c = Integer.parseInt(nc[1]);
		count = 0;
		bag = new ArrayList<>();
		prefixSum = new long[n];
		String[] line = br.readLine().trim().split(" ");
		for(int i = 0;i<n;i++) {
			bag.add(Integer.parseInt(line[i]));
		}
		bag.sort((o1,o2) -> o2-o1);
		//부분 집합
		//넣는다 안넣는다. 
		
		//가지치기 : 누적합으로 더 이상 볼 필요 없는 것?
		//누적합 : 뒤부터
		prefixSum[n-1] = bag.get(n-1);
		for(int i = n-2;i>=0;i--) {
			prefixSum[i] = prefixSum[i+1]+bag.get(i);
		}
		
		recur(0, 0);
		
		System.out.println(count);
	}
	private static void recur(int cnt, long sum) {
		
		//c보다 sum이 많으면 안됨.
		//마지막에 더 커질수도 있기 때문에 먼저 검사
		if(sum > c) {
			return;
		}
		//끝까지 다 돎
		if(cnt >= n) {
			count++;
			return;
		}
		//합이 c를 넘어감 =-> 더 못담음
		if(sum == c) {
			count++;
			return;
		}
		
		//남은 걸 다 넣어도 c보다 같거나 작음 => 즉, 다 넣을 수 있으니 수학적으로 계산 가능
		if(prefixSum[cnt] + sum <= c) {
			//남은 개수가 n-cnt개 
			// 2^(n-cnt) 개수만큼 더해주면 됨
			count += Math.pow(2, n-cnt);
			return;
		}
		
		//선택
		recur(cnt+1, sum+bag.get(cnt));
		//선택 안함
		recur(cnt+1, sum);
		
		
	}

}