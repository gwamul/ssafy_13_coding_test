import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int n;
	static final int MOD = 1_000_000_007;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++)
			arr[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(arr);
		
		long ans = 0;
		
		long[] pow = new long[n];
		pow[0] = 1;

		for(int i = 1; i < n; i++) {
		    pow[i] = (pow[i-1] * 2) % MOD;
		}
		
		for(int i=0;i<n;i++) {
			// 최대가 되는 경우의 수 - 최소가 되는 경우의 수
			ans += arr[i] * (pow[i] - pow[n-1-i]);
			ans %= MOD;
		}
		
		System.out.println(ans);

	}

}
