package saffy_algo.BaekJoon.골드.골드2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
/*
 * 
 * 2의 1000승은 안된다
 * 
 * 정렬하면 nlog n
 * 
 * 뭔가 pq가 효과적인거 같은데
 * pq에 다 때려 넣고
 * pq에서 하나씩 뽑는다.
 * 뽑을 때 마다 visited에 저장하고
 * 
 * 1 1 2 3 6 7 30
 * 
 * 1 -> 1
 * 1 1 -> 1 , 2
 * 1 1 2 -> 1, 2, 3, 4 = 이전거에 2더한것들
 * 1,1,2,3 -> 1,2,3,4 + 4,5,6,7\
 * 
 * 1,1,2,3,6 -> 1,2,3,4,5,6,7 + 7,8,9,10,11,12,13
 * 
 * 1,1,2,3,6,7 -> 1,2,3,4,5,6,7,8,9,10,11,12,13, + 8,9,10,11,12,13,14,15,16,17,18,19,20
 * 
 * 1,1,2,3,6,7,30 -> 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20 + 31, 32,
 * 
 * 
 * 2,3,5,7
 * 애초에 1에서 끝
 * 2 -> 2
 * 2,3 -> 2,3,5, 
 * 
 * 1,2,3,5,7
 * 1 -> 1
 * 1, 2 -> 1,2,3
 * 1,2,3 -> 1,2,3, + 4,5,6
 * 
 * 
 */
public class BaekJoon2437_저울_Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		int n = Integer.parseInt(br.readLine());

		int[] nums = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(nums);
		
		if(nums[0] > 1) {
			System.out.println(1);
		}else {
			int sum = 1;
			for(int i=0; i<n; i++) {
				if(sum < nums[i]) break;
				sum += nums[i];
			}
			System.out.println(sum);
		}
		
		
	}
}
