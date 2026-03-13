import java.io.*;
import java.util.*;

/**
 * 
 * 전략 2.
 * 피는 일자 기준으로 정렬
 * 
 * int s = 3/1
 * int e = 먼저 오는 것을 체크해서
 * 
 * 피는 일자가 3/1일 보다 같거나 작은 것을 까지만 체크하면 됨
 * End일자의 최대값을 구함.
 * 
 * 이거 반복?
 * 
 * 일단 3/1일보다 피는 일자가 더 큰 꽃은 
 * -> 처음 발견시 s = e로 업데이트 후 e의 최대값을 또 찾음
 * 업데이트 된 end보다 같거나 작으면 됨. (해당 꽃의 피는 일짜가)
 * 
 * 
 * 이 전략으로 간다. 
 * 날짜는 int로 치환 : 1월 1일 기준으로 1로 치환
 * 
 */

public class Main{
	
	static int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
	static int n;
	static List<Flower> list;
	
	static class Flower implements Comparable<Flower>{
		int start, end;
		
		public Flower(int start, int end) {
			this.start =start;
			this.end = end;
		}

		@Override
		public int compareTo(Flower o) {
			return Integer.compare(start, o.start);
		}
	}
	
	static int dayToint(int month, int day) {
		if(month == 1) return day;
		
		int result = 0;
		for(int i = 0;i<month-1;i++) {
			result += days[i];
		}
		result += day;
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		n = Integer.parseInt(br.readLine().trim());
		list = new ArrayList<>();
		for(int i = 0;i<n;i++) {
			 st = new StringTokenizer(br.readLine(), " ");
			 int m1 = Integer.parseInt(st.nextToken());
			 int d1 = Integer.parseInt(st.nextToken());
			 int m2 = Integer.parseInt(st.nextToken());
			 int d2 = Integer.parseInt(st.nextToken());
			 list.add(new Flower(dayToint(m1, d1),dayToint(m2, d2)-1));
		}

		Collections.sort(list);
		
		//e는 꽃이 지기 하루전
		int ans = 0;
		int s = dayToint(3,1);
		int end11_30 = dayToint(11,30);
		int min = list.get(0).start; //꽃이 피는 처음 날짜 저장 -> 만약 3/1일보다 크다면 0출력
		if(min > s) {
			System.out.println(0);
			return;
		}
		int e = s;
		for(Flower f : list) {
//			System.out.println("s : "+s+", e : "+e+", f.start : "+f.start+", f.end : "+f.end);
			if(f.start <= s && f.end >= s) {
				e = Math.max(e , f.end);
			}
			else if(f.start > e+1) {
				System.out.println(0);
				return;
			}
			else if(f.start > s && f.end > e) {
				//s,e업데이트
				s = e+1;
				e = f.end;
				ans++; //이전 s부터 e까지 꽃 하나 피움
			}
			if(e >= end11_30) { //11월 30일까지 다 채움
				System.out.println(ans+1);
				return;
			}
		}
		
		System.out.println(0);
		
	}
	

}