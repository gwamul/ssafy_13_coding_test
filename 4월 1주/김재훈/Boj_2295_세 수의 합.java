import java.io.*;
import java.util.*;

public class Main{
	
	static int n;
	static Set<Long> set;
	static List<Long> list;
	public static void main(String[] args) throws Exception {
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		set = new HashSet<>();
		for(int i = 0;i<n;i++) {
			int num = Integer.parseInt(br.readLine());
			list.add((long)num);
		}
		
		Collections.sort(list);
		
		for(int i = 0;i<list.size();i++) {
			for(int j = i;j<list.size();j++) {
				long add = list.get(i) + list.get(j);
				set.add(add);
			}
		}
		
		for(int i = list.size()-1;i>=0;i--) {
			for(int j = i;j>=0;j--) {
				long gap = list.get(i) - list.get(j);
				if(set.contains(gap)) {
					System.out.println(list.get(i));
					return;
				}
			}
		}
		
		
		return;
	}
}