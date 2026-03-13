import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main{
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		long n = Integer.parseInt(br.readLine().trim());
		int k = Integer.parseInt(br.readLine().trim());
		
		if(n == 1) {
			System.out.println(1);
			return;
		}
		
		long start = 1;
		long end = n*n;
		long mid = (start + end)/2;
		long ans = 0;
		while(start <= end) {
			long cnt = findCnt(n, mid);
			if(cnt >= k) {
				end = mid-1;
				ans = mid;
			}
			else if(cnt < k) {
				start = mid+1;
			}
			mid = (start + end)/2;
		}
		
		System.out.println(ans);
	}
	static long findCnt(long n, long x) {
		long cnt = 0;
		for(int i = 1;i<=n;i++) {
			if(x >= i*n) cnt +=n;
			else {
				cnt += x/i;
			}
		}
		return cnt;
	}
}