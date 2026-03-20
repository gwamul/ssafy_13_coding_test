import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main2 {
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int n = Integer.parseInt(st.nextToken());
		List<Integer> list = new ArrayList<>();
//		int[] arr = new int[n];
		Map<Integer, Integer> numCnt = new HashMap<>();
		
		int zeroCnt = 0;
		st = new StringTokenizer(br.readLine(), " ");
		for(int i = 0;i<n;i++) {
			int num = Integer.parseInt(st.nextToken());
			if(num != 0) {
				list.add(num);
				numCnt.put(num, numCnt.getOrDefault(num, 0)+1);
			}
			else {
				zeroCnt++;
			}
			
		}
		
		if(n <= 2) {
			System.out.println(0);
			return ;
		}
		
		int result = 0;
		//0을 제외하고 먼저 계산
		boolean canMakeZero = false;
		for(int i = 0;i<list.size()-1;i++) {
			for(int j = i+1;j<list.size();j++) {
				int num = list.get(i) + list.get(j);
				if(num == 0) canMakeZero = true;
				result += numCnt.getOrDefault(num, 0);
				numCnt.put(num, 0);
			}
		}
		
//		System.out.println(result);
		
		//0을 만들수 있다면 0도 좋다
		if(canMakeZero) {
			result += zeroCnt;
		}
		//다른 수끼리 0을 못만드니 자기끼리 만들어야 함.
		else {
			if(zeroCnt >= 3) result += zeroCnt;
			//0이 2개면 0끼리 0을 못만듬
		}
		
		//0이랑 현재수라면
		if(zeroCnt > 0) {
			for(int i = 0;i<list.size();i++) {
				//여기서는 아직 좋지 않은 수만 보면 됨.
				int cnt = numCnt.getOrDefault(list.get(i), 0);
				if(cnt > 0) {
					//자기 혼자만 있으므로 못만듬
					if(cnt == 1) {
						//넘어감
					}
					//자기 이외에 더 있음. 즉, 자기이외에 동일한 수가 있으므로 자신과 다른 수도 만들 수 있음.
					else {
						result += cnt;
						numCnt.put(list.get(i), 0);
					}
				}
			}
		}
		
		
		System.out.println(result);
		
		
	}
}
