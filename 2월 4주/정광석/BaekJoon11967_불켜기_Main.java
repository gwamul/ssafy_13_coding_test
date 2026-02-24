
public class BaekJoon11967_불켜기_Main {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {

			sb.append("#").append(tc).append(" ");
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
