import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_b5052_전화번호목록 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		while(T-- > 0) {
			int n = Integer.parseInt(br.readLine());
			String[] phones = new String[n];
			for(int i=0;i<n;i++) phones[i] = br.readLine().trim();
			Arrays.sort(phones);
			boolean flag = true;
			for(int i=0;i<phones.length-1;i++) {
				if(phones[i].length() > phones[i+1].length()) continue;
				if(phones[i+1].substring(0, phones[i].length()).equals(phones[i])) {
					sb.append("NO\n");
					flag = false;
					break;
				}
			}
			if(flag) sb.append("YES\n");
		}
		System.out.print(sb);
	}
}
