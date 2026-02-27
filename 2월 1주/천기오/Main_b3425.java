package week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_b3425 {
	static int[] stack;
	static int top;
	static final int MAX = 1_000_000_000;
	static final int ERROR = -1_000_000_001;
	static void num(int x) {
		stack[++top] = x;
		return;
	}
	static int pop() {
		if(top == -1) return ERROR;
		return stack[top--];
	}
	static boolean dup() {
		if(top == -1) return false;
		stack[top+1] = stack[top++];
		return true;
	}
	static boolean inv() {
		if(top == -1) return false;
		stack[top] = -stack[top];
		return true;
	}
	static boolean swp() {
		int fir = pop();
		int sec = pop();
		if(fir == ERROR || sec == ERROR) return false;
		num(fir);
		num(sec);
		return true;
	}
	static boolean add() {
		int fir = pop();
		int sec = pop();
		if(fir == ERROR || sec == ERROR) return false;
		int sum = fir + sec;
		if(Math.abs(sum) > MAX) return false;
		num(sum);
		return true;
	}
	static boolean sub() {
		int fir = pop();
		int sec = pop();
		if(fir == ERROR || sec == ERROR) return false;
		int sum = sec - fir;
		if(Math.abs(sum) > MAX) return false;
		num(sum);
		return true;
	}
	static boolean mul() {
		int fir = pop();
		int sec = pop();
		if(fir == ERROR || sec == ERROR) return false;
		long sum = (long) fir * sec;
		if(Math.abs(sum) > MAX) return false;
		num((int)sum);
		return true;
	}
	static boolean div() {
		int fir = pop();
		int sec = pop();
		if(fir == ERROR || sec == ERROR) return false;
		if(fir == 0) return false;
	    int res = Math.abs(sec) / Math.abs(fir);
	    if(fir > 0 && sec < 0 || fir < 0 && sec > 0) res = -res;
		num(res);
		return true;
	}
	static boolean mod() {
		int fir = pop();
		int sec = pop();
		if(fir == ERROR || sec == ERROR) return false;
		if(fir == 0) return false;
	    // sec의 부호를 따라야 함
	    int res = Math.abs(sec) % Math.abs(fir);
	    if(sec < 0) res = -res;
		num(res);
		return true;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		stack = new int[10_001];
		top = -1;
		StringBuilder sb = new StringBuilder();
		//QUIT일 때까지 계속 실행
		while(true) {
			List<String> commands = new ArrayList<>();
			// 1. 프로그램 입력 받기
			String input;
			StringTokenizer st = new StringTokenizer(br.readLine());
			List<Integer> operands = new ArrayList<>();
			while(true) {
				input = st.nextToken();
				commands.add(input);
				if(input.equals("END")) break;
				if(input.equals("QUIT")) {
					System.out.print(sb);
					System.exit(0);
				}
				if(input.equals("NUM")) {
					operands.add(Integer.parseInt(st.nextToken()));
				}
				st = new StringTokenizer(br.readLine());
			}
			int n = Integer.parseInt(br.readLine());
			int[] init = new int[n];
			for(int i=0;i<n;i++) {
				init[i] = Integer.parseInt(br.readLine());
			}
			br.readLine(); //빈 줄 제거
			
			// 2. 프로그램 실행
			for(int i=0;i<n;i++) {
				top = -1;
				num(init[i]);
				int idx = 0;
				boolean isNormal = true;
				for(String command : commands) {
					boolean isError = false;
					switch(command) {
						case "NUM":
							int x = operands.get(idx++);
							num(x);
							break;
						case "POP":
							if(pop() == ERROR) isError = true;
							break;
						case "INV":
							if(!inv()) isError = true;
							break;
						case "DUP":
							if(!dup()) isError = true;
							break;
						case "SWP":
							if(!swp()) isError = true;
							break;
						case "ADD":
							if(!add()) isError = true;
							break;
						case "SUB":
							if(!sub()) isError = true;
							break;
						case "MUL":
							if(!mul()) isError = true;
							break;
						case "DIV":
							if(!div()) isError = true;
							break;
						case "MOD":
							if(!mod()) isError = true;
							break;
					}
					if(isError) {
						isNormal = false;
						break;
					}
				}
				if(top == 0 && isNormal) {
					sb.append(stack[top]).append('\n');
				}
				else {
					sb.append("ERROR\n");
				}
			}
			sb.append('\n');
		}
	}
}
