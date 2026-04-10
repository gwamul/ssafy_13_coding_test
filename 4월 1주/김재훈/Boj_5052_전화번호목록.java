import java.io.*;
import java.util.*;


public class Main{
	
	static class Node{
		HashMap<Character, Node> child;
		boolean endOfWord;
		
		public Node() {
			this.child = new HashMap<>();
			this.endOfWord = false;
		}
	}
	
	static class Trie{
		Node root;
		
		public Trie() {
			this.root = new Node();
		}
		
		public void insert(String str) {
			
			Node node = this.root;
			boolean isNew = false;
			for(int i = 0;i<str.length();i++) {
				char c = str.charAt(i);
				Node ch = node.child.putIfAbsent(c, new Node());
				if(ch == null) isNew = true;
				node = node.child.get(c);
				if(node.endOfWord) {
					isNew = false;
					break;
				}
			}
			
			node.endOfWord = true;
			if(!isNew) isYes = false;
		}
		boolean search(String str) {
			Node node =  this.root;
			
			for(int i = 0;i<str.length();i++) {
				char c = str.charAt(i);
				
				if(node.child.containsKey(c)) {
					node = node.child.get(c);
				}
				else return false;
			}
			
			return node.endOfWord;
		}
		public boolean delete(String str) {
			boolean result = delete(this.root, str, 0);
			return result;
		}

		private boolean delete(Node node, String str, int idx) {
			char c = str.charAt(idx);
			
			if(!node.child.containsKey(c)) {
				return false;
			}
			
			Node cur = node.child.get(c);
			idx++;
			if(idx == str.length()) {
				if(!cur.endOfWord) {
					return false;
				}
				
				cur.endOfWord = false;
				
				if(cur.child.isEmpty()) {
					node.child.remove(c);
				}
			}
			else {
				if(!this.delete(cur, str, idx)) {
					return false;
				}
				
				if(!cur.endOfWord && cur.child.isEmpty()) {
					node.child.remove(c);
				}
			}
			return true;
		}
	}

	static boolean isYes = true;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int t = Integer.parseInt(br.readLine());
		for(int test = 1; test <= t; test++) {
			int n = Integer.parseInt(br.readLine());
			
			Trie trie = new Trie();
			isYes = true;
			for(int i = 0;i<n;i++) {
				//char[] chArr = br.readLine().toCharArray();
				String str = br.readLine();
				trie.insert(str);
				//1. 만약 나는 끝났는데 이미 있다면 
				//2. 만약 내가 넣는데 endOfWord가 true를 지나쳤다면
			}
			
			if(isYes) System.out.println("YES");
			else System.out.println("NO");
		}

	}
	

}
