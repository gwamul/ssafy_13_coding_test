package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_b9527_1의개수세기 {
	static long[] acc;

	static long calc(long x) {
		if (x == 0)
			return 0;

		int k = 63 - Long.numberOfLeadingZeros(x); // 최상위 비트
		long msb = 1L << k;

		long below = (k == 0) ? 0 : acc[k - 1];
		// 0~(2^k-1) 구간 합 + 2^k ~ x까지의 합 + msb개수(x-msb+1)
		return (x - msb + 1) + below + calc(x - msb);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		long a = Long.parseLong(st.nextToken());
		long b = Long.parseLong(st.nextToken());
		acc = new long[55];
		acc[0] = 1;
		// acc[n] = acc[n-1] * 2 + 2^n; -> 0~2^n-1까지의 합
		for (int i = 1; i < 55; i++) {
			acc[i] = (acc[i - 1] << 1) + (1L << i);
		}
		long answer = calc(b) - calc(a - 1);
		System.out.println(answer);
	}
}
