package kjh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BackJoon3425 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		List<String> order = new ArrayList<>();
		boolean isOver = false;
		while(true) {
			String str;
			while(!(str=br.readLine().trim()).equals("END")) {
				if(str.isBlank()) continue;
				if(str.equals("QUIT")) {
					isOver = true;
					break;
				}
				order.add(str);
			}
			
			if(isOver) break;
			
			int n = Integer.parseInt(br.readLine().trim());
			for(int i = 0 ;i<n;i++) {
				int num = Integer.parseInt(br.readLine().trim());
				
				//각 숫자마다 명령어 실행
				Stack<Integer> stack = new Stack<>();
				stack.add(num);
				
				boolean isError = false;
				for(String s : order) {
					if(!runOrder(stack, s)) {
						isError = true;
						break;
					}
				}
				if(isError)System.out.println("ERROR");
				else if(stack.size() != 1)System.out.println("ERROR");
				else System.out.println(stack.pop());
			}
			
			System.out.println();
			order = new ArrayList<>();
		}
	}

	private static boolean runOrder(Stack<Integer> stack, String s) {
		if(s.substring(0, 3).equals("NUM")) {
			int x = Integer.parseInt(s.substring(4));
			stack.add(x);
		}
		else if(s.equals("POP")) {
			if(stack.isEmpty()) return false;
			stack.pop();
		}
		else if(s.equals("INV")) {
			if(stack.isEmpty()) return false;
			int num = stack.pop();
			stack.add(num*(-1));
		}
		else if(s.equals("DUP")) {
			if(stack.isEmpty()) return false;
			int num = stack.peek();
			stack.add(num);
		}
		else if(s.equals("SWP")) {
			if(stack.size() < 2) {
				return false;
			}
			int first = stack.pop();
			int second= stack.pop();
			stack.add(first);
			stack.add(second);
		}
		else if(s.equals("ADD")) {
			if(stack.size() < 2) {
				return false;
			}
			int first = stack.pop();
			int second= stack.pop();
			if(first + second > 1000000000 || first + second < -1000000000) return false;
			stack.add(first+second);
		}
		else if(s.equals("SUB")) {
			if(stack.size() < 2) {
				return false;
			}
			int first = stack.pop();
			int second= stack.pop();
			if(second-first > 1000000000 || second-first < -1000000000) return false;
			stack.add(second-first);
		}
		else if(s.equals("MUL")) {
			if(stack.size() < 2) {
				return false;
			}
			long first = stack.pop();
			long second= stack.pop();
			if((long)first*(long)second > 1000000000) {
				return false;
			}
			stack.add((int)first*(int)second);
		}
		else if(s.equals("DIV")) {
			if(stack.size() < 2) {
				return false;
			}
			int first = stack.pop();
			if(first == 0) return false;
			int second= stack.pop();
			int m = 1;
			if((long)first*(long)second < 0) m = -1;
			
			stack.add((Math.abs(second)/Math.abs(first))*m);
		}
		else if(s.equals("MOD")) {
			if(stack.size() < 2) {
				return false;
			}
			int first = stack.pop();
			if(first == 0) return false;
			int second= stack.pop();
			int m = 1;
			if(second < 0) m = -1;
			
			stack.add((Math.abs(second)%Math.abs(first))*m);
		}
		
		return true;
	}
	
	
}
