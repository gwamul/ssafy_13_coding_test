import java.io.*;
import java.util.*;

public class Main {
	static int q;
	static int n;
	static int m;
	static Map<Integer, Node> beltNodes = new HashMap<>();//belt번호랑 시작Node
	static Map<Integer, Node> nodes = new HashMap<>(); //id와 node
	static Map<Integer, Node> lastNode = new HashMap<>();//belt이 마지막 노드
	
	static Set<Integer> removed = new HashSet<>();
	static Map<Integer, Set<Integer>> existId = new HashMap<>();
	
	static List<Integer> initId = new ArrayList<>();
	static List<Integer> initW = new ArrayList<>();
	
	static Set<Integer> brokenBelt = new HashSet<>();
	static class Node{
		int id;
		int w;
		Node prev = null, next = null;
		
		Node(int id, int w){
			this.id = id;
			this.w = w;
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		q = Integer.parseInt(br.readLine());
		for(int i = 0;i<q;i++) {
			st =  new StringTokenizer(br.readLine(), " ");
			
			int order = Integer.parseInt(st.nextToken());
			switch(order) {
			case 100:
				init(st);
				break;
			case 200:
				int w_max = Integer.parseInt(st.nextToken());
				boxDown(w_max);
				break;
			case 300:
				int r_id = Integer.parseInt(st.nextToken());
				boxRemove(r_id);
				break;
			case 400:
				int f_id = Integer.parseInt(st.nextToken());
				boxSearch(f_id);
				break;
			case 500:
				int b_num = Integer.parseInt(st.nextToken());
				beltChange(b_num);
				break;
			}
		}
	}

	private static void beltChange(int b_num) {
		//이미 고장남
		if(brokenBelt.contains(b_num)) {
			System.out.println(-1);
			return;
		}
		
		//만약 고장날 벨트에 아무것도 없다면
		if(existId.get(b_num).isEmpty()) {
			brokenBelt.add(b_num);
			System.out.println(b_num);
			return ;
		}
		
		for(int i = b_num+1;i<b_num+1+m;i++) {
			int beltInd = (i - 1) % m + 1;
			if(brokenBelt.contains(beltInd)) continue;
			
			Node brokenBeltFirst = beltNodes.get(b_num);
			Node brokenBeltLast = lastNode.get(b_num);
			//만약 새로 옮겨갈 벨트에 상자가 없다면
			if(existId.get(beltInd).isEmpty()) {
				beltNodes.put(beltInd, brokenBeltFirst);
				lastNode.put(beltInd, brokenBeltLast);
			}
			else {
				Node changeBeltLast = lastNode.get(beltInd);
				changeBeltLast.next = brokenBeltFirst;
				brokenBeltFirst.prev = changeBeltLast;
				lastNode.put(beltInd, brokenBeltLast);
			}
			
			existId.get(beltInd).addAll(existId.get(b_num));
			existId.get(b_num).clear();
			beltNodes.put(b_num, null);
			lastNode.put(b_num, null);
			break;
		}
		
		
		brokenBelt.add(b_num);
		System.out.println(b_num);
	}

	private static void boxSearch(int f_id) {
		if(removed.contains(f_id)) {
			System.out.println(-1);
			return;
		}
		
		for(int i = 1;i<=m;i++) {
			if(existId.get(i).isEmpty() || brokenBelt.contains(i)) continue;
			if(existId.get(i).contains(f_id)) {
				
				Node target = nodes.get(f_id);
				//이 벨트에 하나뿐이라면
				if(existId.get(i).size() == 1) {
					//암것도 안함..
				}
				//이게 처음거라면
				else if(beltNodes.get(i).id == f_id) {
					//아무것도 안함
				}
				//이게 마지막이라면
				else if(lastNode.get(i).id == f_id) {
					//해당 벨트의 마지막 노드를 앞에것으로 교체
					lastNode.put(i, target.prev);
					target.prev.next = null;
					
					Node first = beltNodes.get(i);
					first.prev = target;
					target.next = first;
					
					beltNodes.put(i, target);
					target.prev = null;
				}
				//중간이라면
				else {
					Node first = beltNodes.get(i);
					Node last = lastNode.get(i);
					Node oldPrev = target.prev;

					oldPrev.next = null;   // target 앞에서 끊기
					target.prev = null;

					last.next = first;
					first.prev = last;

					beltNodes.put(i, target);
					lastNode.put(i, oldPrev);
					
					
				}
				System.out.println(i);
				return;
			}
		}
		//그냥 이런 아이디 없음
		System.out.println(-1);
	}

	private static void boxRemove(int r_id) {
		if(removed.contains(r_id)) {
			System.out.println(-1);
			return;
		}
		
		for(int i = 1;i<=m;i++) {
			if(existId.get(i).isEmpty() || brokenBelt.contains(i)) continue;
			if(existId.get(i).contains(r_id)) {
				//해당 벨트에서 삭제
				Node target = nodes.get(r_id);
				Node prev = target.prev;
				Node next = target.next;
				
				//이 벨트에 하나뿐이라면
				if(existId.get(i).size() == 1) {
					beltNodes.put(i, null);
					lastNode.put(i, null);
				}
				//이게 마지막이라면
				else if(lastNode.get(i).id == r_id) {
					//해당 벨트의 마지막 노드를 앞에것으로 교체
					lastNode.put(i, target.prev);
					target.prev.next = null;
				}
				//이게 처음거라면
				else if(beltNodes.get(i).id == r_id) {
					//다음걸로 바꿔주기
					Node newHead = target.next;
					beltNodes.put(i, newHead);
					newHead.prev = null;
					target.next = null;
				}
				else {
					//3개 이상인데 양 끝도 아닌 경우
					//벨트에서 지우기
					prev.next = next;
					next.prev = prev;
				}
				
				removed.add(r_id);
				existId.get(i).remove(r_id);
				System.out.println(r_id);
				return;
			}
		}
		//그냥 이런 아이디 없음
		System.out.println(-1);
	}

	private static void boxDown(int w_max) {
		int sum = 0;
		for(int i = 1;i<=m;i++) {
			
			if(existId.get(i).isEmpty() || brokenBelt.contains(i)) continue; //해당 벨트 비어있음
			Node root = beltNodes.get(i);
			if(existId.get(i).size() == 1) {
				if(root.w <= w_max) {
					beltNodes.put(i, null);
					lastNode.put(i, null);
					removed.add(root.id);
					existId.get(i).remove(root.id);
					sum += root.w;
				}
				else {
					//아무것도 안함
				}
			}
			else {//2개 이상인경우
				if(root.w <= w_max) {
					beltNodes.put(i, root.next);
					root.next.prev = null;
					removed.add(root.id);
					existId.get(i).remove(root.id);
					sum += root.w;
				}
				else {
					beltNodes.put(i, root.next);
					root.next.prev = null;
					Node last = lastNode.get(i);
					last.next = root;
					root.prev = last;
					root.next = null;
					lastNode.put(i, root);
				}
			}
			
		}
		System.out.println(sum);
	}

	private static void init(StringTokenizer st) {
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		for(int i = 0;i<n;i++) {
			initId.add(Integer.parseInt(st.nextToken()));
		}
		for(int i = 0;i<n;i++) {
			initW.add(Integer.parseInt(st.nextToken()));
		}
		
		//nodes 초기화
		int ind = 0;
		for(int i = 0 ;i<m;i++) {
			Set<Integer> cur = existId.getOrDefault(i+1, new HashSet<>());
			Node root = new Node(initId.get(ind), initW.get(ind++));
			nodes.put(root.id, root);
			cur.add(root.id);
			Node prev = root;
			for(int j = 1;j< (n/m);j++) {
				Node next = new Node(initId.get(ind), initW.get(ind++));
				nodes.put(next.id, next);
				cur.add(next.id);
				prev.next = next;
				next.prev = prev;
				prev = next;
			}
			beltNodes.put(i+1, root);
			lastNode.put(i+1, prev);
			existId.put(i+1, cur);
		}
	}

}
