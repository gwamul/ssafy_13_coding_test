import java.io.*;
import java.util.*;

/**
 * 
 * 누적합, 이분탐색
 * 
 */

public class Main{

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int pizzaSize = Integer.parseInt(br.readLine().trim());
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		
		int[] marr = new int[m];
		int[] narr = new int[n];
		
		for(int i = 0;i<m;i++) {
			marr[i] = Integer.parseInt(br.readLine().trim());
		}
		for(int i = 0;i<n;i++) {
			narr[i] = Integer.parseInt(br.readLine().trim());
		}
		
		//1. 각m, n의 누적합을 구하기 -> 각 위치마다
		int[][] prefixSumM = new int[m][m];
		int[][] prefixSumN = new int[n][n];
		
		//mmap에는 필요한 피자 사이즈를 넣고 해당 사이즈가 있다면 몇개를 만족시킬 수 있는지 value로 넣는다. 
		Map<Integer, Integer> mmap = new HashMap<>();
		Map<Integer, Integer> nmap = new HashMap<>();
	
		for(int i = 0;i<m;i++) {
			//i를 기준으로 누적합 시작
			for(int j = 0;j<m;j++) {
				int ind = i+j;
				if(ind >= m) ind -= m;
				
				if(j == 0) prefixSumM[i][j] = marr[ind];
				else prefixSumM[i][j] = marr[ind] + prefixSumM[i][j-1];
				
				mmap.put(prefixSumM[i][j], mmap.getOrDefault(prefixSumM[i][j], 0)+1);
			}
		}
		mmap.put(prefixSumM[m-1][m-1], 1);
		for(int i = 0;i<n;i++) {
			//i를 기준으로 누적합 시작
			for(int j = 0;j<n;j++) {
				int ind = i+j;
				if(ind >= n) ind -= n;
				
				if(j == 0) prefixSumN[i][j] = narr[ind];
				else prefixSumN[i][j] = narr[ind] + prefixSumN[i][j-1];
				
				nmap.put(prefixSumN[i][j], nmap.getOrDefault(prefixSumN[i][j], 0)+1);
			}
		}
		nmap.put(prefixSumN[n-1][n-1], 1);
		//List에 넣어서 정렬
		List<int[]> mlist = new ArrayList<>();
		List<int[]> nlist = new ArrayList<>();
		
		Iterator<Integer> iter = mmap.keySet().iterator();
		while(iter.hasNext()) {
			int key = iter.next();
			mlist.add(new int[] {key, mmap.get(key)});
		}
		
		iter = nmap.keySet().iterator();
		while(iter.hasNext()) {
			int key = iter.next();
			nlist.add(new int[] {key, nmap.get(key)});
		}
		
		
		//정렬
		Collections.sort(mlist, (o1,o2) -> o1[0]-o2[0]);
		Collections.sort(nlist, (o1,o2) -> o1[0]-o2[0]);
		
		int ans = 0;
		//이분탐색으로 pizzasize에 맞는 거 찾기
		for(int[] cur : mlist) {
			if(pizzaSize == cur[0]) {
				ans+= cur[1];
				continue;
			}
			else if(pizzaSize < cur[0]) continue;
			
			int s = 0;
			int e = nlist.size()-1;
			int mid = (s+e)/2;
			
			int size = pizzaSize - cur[0];
			while(s <= e) {
				if(nlist.get(mid)[0] > size) {
					e = mid-1;
				}
				else if(nlist.get(mid)[0] < size) {
					s = mid+1;
				}
				else {
					ans += cur[1] * nlist.get(mid)[1];
					break;
				}
				
				mid = (s+e)/2;
			}
		}
		
		for(int[] cur : nlist) {
			if(pizzaSize == cur[0]) {
				ans+= cur[1];
			}
		}
		
		System.out.println(ans);
	}
	

}