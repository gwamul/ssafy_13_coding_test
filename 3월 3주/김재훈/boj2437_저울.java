import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 
 * chat gpt 힌트
 * 
 * 현재까지 1 ~ X까지 모두 만들 수 있다고 하자.

이때 다음 추의 무게가 X+1 이하라면,
그 추를 이용해서 만들 수 있는 범위가 끊기지 않고 더 뒤까지 확장돼.

반대로 다음 추가 X+1보다 크면,
바로 X+1은 절대 만들 수 없게 된다.
 * 
 * 
 */
public class Main {
	
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int n = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(br.readLine(), " ");
		
		for(int i = 0;i<n;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr);
		
		if(arr[0] > 1) {
			System.out.println(1);
			return;
		}
		
		int x = arr[0];
		for(int i = 1;i<n;i++) {
			if(arr[i] > x+1) {
				System.out.println(x+1);
				return;
			}
			
			x += arr[i];
		}
		
		System.out.println(x+1);
		
	}
}
