package saffy_algo.BaekJoon.골드.골드4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon1253_좋다_Main {
	static int nums[];
	static int n;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		n = Integer.parseInt(br.readLine()); //2000
		nums = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {//최대 10억  두개 합쳐도 20억
			nums[i] = Integer.parseInt(st.nextToken());
		}
		int cnt = 0;
		Arrays.sort(nums);
		for(int i=0; i<n; i++) {
			// 모든 수에 대해서 bs
			int target_num = nums[i];
			
			int left = 0;
			int right = n-1;
			while(left<right) {
				int sum = nums[left] + nums[right];
				//System.out.println(nums[left] + " " + nums[right] + " " + sum + " " + target_num );
				if(sum <= target_num) {
					//sum이 작음
					if(sum == target_num) {
						if(left == i) {
	                        left++;
	                    } else if(right == i) {
	                        right--;
	                    } else {
	                        // 둘 다 본인이 아닌 다른 두 수의 합으로 이루어진 경우
	                        cnt++;
	                        break;
	                    }
					}
					else left ++;
				}else {
					right --;
				}
						
			}
			//System.out.println(i + "번째 후 cnt: " + cnt);
			
			
			
		}

		System.out.println(cnt);
		
		
	}
}
