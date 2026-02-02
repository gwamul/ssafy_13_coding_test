package saffy_algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class BaekJoon3425_Main {
	static final long MAX_VAL = 1_000_000_000L;
	// 전역 스택 제거 -> 각 연산마다 새로운 지역 변수 스택 사용 권장
	// 하지만 구조상 파라미터로 넘기거나 매번 생성하는 방식 사용

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		while (true) {
			List<String> commands = new ArrayList<>();

			// 1. 명령어 입력 받기
			while (true) {
				String line = br.readLine();

				// 입력이 끝났거나(null) QUIT이면 종료
				if (line == null || line.equals("QUIT")) {
					System.out.print(sb);
					return; 
				}

				// 엔터(빈 줄) 처리: 명령어가 아니므로 루프 종료하고 숫자 입력으로 넘어감
				// 주의: 문제 형식상 기계와 기계 사이 빈 줄이 있음
				if (line.isEmpty()) { 
					continue; 
				}

				if (line.equals("END")) {
					break;
				}

				commands.add(line);
			}

			// 2. 숫자 N 입력 받기
			String numLine = br.readLine();
			if(numLine == null) break; 
			int n = Integer.parseInt(numLine);

			// 3. N번만큼 기계 돌리기
			for (int i = 0; i < n; i++) {
				int startValue = Integer.parseInt(br.readLine());
				sb.append(executeMachine(commands, startValue)).append("\n");
			}
			sb.append("\n"); // 기계 블록 끝날 때마다 빈 줄
		}
	}

	public static String executeMachine(List<String> commands, int inputVal) {
		Deque<Long> stack = new ArrayDeque<>(); // 오버플로우 방지를 위해 Long 스택 사용
		stack.push((long) inputVal);


		for (String cmd : commands) {
			if (cmd.startsWith("NUM")) {
				long val = Long.parseLong(cmd.split(" ")[1]);
				stack.push(val);
			} else if (cmd.equals("POP")) {
				if (stack.isEmpty()) return "ERROR";
				stack.pop();
			} else if (cmd.equals("INV")) {
				if (stack.isEmpty()) return "ERROR";
				stack.push(-stack.pop());
			} else if (cmd.equals("DUP")) {
				if (stack.isEmpty()) return "ERROR";
				stack.push(stack.peek());
			} else if (cmd.equals("SWP")) {
				if (stack.size() < 2) return "ERROR";
				long a = stack.pop();
				long b = stack.pop();
				stack.push(a);
				stack.push(b);
			} else if (cmd.equals("ADD")) {
				if (stack.size() < 2) return "ERROR";
				long a = stack.pop();
				long b = stack.pop();
				long res = b + a;
				if (Math.abs(res) > MAX_VAL) return "ERROR"; 
				stack.push(res);
			} else if (cmd.equals("SUB")) {
				if (stack.size() < 2) return "ERROR";
				long a = stack.pop();
				long b = stack.pop();
				long res = b - a;
				if (Math.abs(res) > MAX_VAL) return "ERROR";
				stack.push(res);
			} else if (cmd.equals("MUL")) {
				if (stack.size() < 2) return "ERROR";
				long a = stack.pop();
				long b = stack.pop();
				long res = b * a;
				if (Math.abs(res) > MAX_VAL) return "ERROR";
				stack.push(res);
			} else if (cmd.equals("DIV")) {
				if (stack.size() < 2) return "ERROR";
				long a = stack.pop();
				long b = stack.pop();
				if (a == 0) return "ERROR";

				long res = Math.abs(b) / Math.abs(a);
				if ((a < 0) ^ (b < 0)) res = -res;


				if (Math.abs(res) > MAX_VAL) return "ERROR"; 
				stack.push(res);
			} else if (cmd.equals("MOD")) {
				if (stack.size() < 2) return "ERROR";
				long a = stack.pop();
				long b = stack.pop();
				if (a == 0) return "ERROR";

				long res = Math.abs(b) % Math.abs(a);
				if (b < 0) res = -res;

				if (Math.abs(res) > MAX_VAL) return "ERROR";
				stack.push(res);
			}
		}


		if (stack.size() != 1) return "ERROR";
		return String.valueOf(stack.pop());
	}
}