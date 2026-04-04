#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

// double linked list
struct Node {
	int id;           // 물건의 고유 번호
	int weight;       // 물건의 무게
	int belt_num;
	Node* prev;       // 이전 물건
	Node* next;       // 다음 물건
	Node(int i, int w, int b) : id(i), weight(w), belt_num(b), prev(nullptr), next(nullptr) {}
};

// 벨트 관리를 위한 배열 (벨트 개수 최대 10개)
Node* head[11];
Node* tail[11];
bool broken[11];  

// 빠른 탐색을 위한 해시 맵
unordered_map<int, Node*> node_map;

void change_belt_num(int a, int b) {
	Node* cur = head[a];
	while (cur != nullptr) {
		cur->belt_num = b;
		cur = cur->next;
	}
}

int main() {
	ios_base::sync_with_stdio(0);
	cin.tie(nullptr);
	cout.tie(nullptr);

	int q;
	cin >> q;
	int n, m;
	while (q-- > 0) {
		int cmd;
		cin >> cmd;
		// init
		if (cmd == 100) {
			cin >> n >> m;

			vector<int> ids(n);
			vector<int> weights(n);
			for (int i = 0; i < n; i++) cin >> ids[i];
			for (int i = 0; i < n; i++) cin >> weights[i];

			int size_per_belt = n / m;

			for (int i = 0; i < n; i++) {
				int belt_num = (i / size_per_belt) + 1;

				Node* newNode = new Node(ids[i], weights[i],belt_num);

				node_map[ids[i]] = newNode;

				if (head[belt_num] == nullptr) {
					head[belt_num] = newNode;
					tail[belt_num] = newNode;
				}
				else {
					tail[belt_num]->next = newNode; 
					newNode->prev = tail[belt_num]; 
					tail[belt_num] = newNode;       
				}
			}
		}
		// 물건 하차
		else if (cmd == 200) {
			int limit;
			cin >> limit;
			long long acc = 0; 

			for (int i = 1; i <= m; i++) {

				// 2. 벨트가 고장났거나, 벨트가 비어있으면(nullptr) 무시하고 넘어감
				if (broken[i] || head[i] == nullptr) {
					continue;
				}

				Node* cur = head[i]; 

				// 3-1. 무게 제한 이하인 경우 (하차)
				if (cur->weight <= limit) {
					acc += cur->weight;

					node_map.erase(cur->id);

					head[i] = cur->next;

					if (head[i] != nullptr) {
						head[i]->prev = nullptr;
					}
					else {
						tail[i] = nullptr;      
					}

					delete cur; 
				}

				// 3-2. 무게 제한 초과인 경우 (맨 뒤로 보내기)
				else {
					// 벨트에 물건이 2개 이상일 때만 뒤로 보냅니다. (1개뿐이면 보낼 필요 없음)
					if (head[i] != tail[i]) {
						// 기존 Head 떼어내기
						head[i] = cur->next;
						head[i]->prev = nullptr;

						// 떼어낸 노드를 기존 Tail 뒤에 붙이기 
						tail[i]->next = cur;
						cur->prev = tail[i];
						cur->next = nullptr; 

						tail[i] = cur;
					}
				}
			}
			cout << acc << "\n"; 
		}
		// 물건 제거
		else if (cmd == 300) {
			int id;
			cin >> id;

			// 1. 물건이 존재하는지 확인
			if (node_map.find(id) == node_map.end()) {
				cout << "-1\n";
				continue;
			}

			Node* cur = node_map[id];

			// 2. 이 노드가 어느 벨트의 Head나 Tail인지 확인하여 업데이트
			int b = cur->belt_num;
			if (head[b] == cur) head[b] = cur->next;
			if (tail[b] == cur) tail[b] = cur->prev;

			// 3. 앞뒤 노드 연결 (이중 연결 리스트의 핵심)
			if (cur->prev != nullptr) {
				cur->prev->next = cur->next;
			}
			if (cur->next != nullptr) {
				cur->next->prev = cur->prev;
			}

			node_map.erase(id);
			delete cur;

			cout << id << "\n";
		}
		// 물건 확인
		else if (cmd == 400) {
			int f_id;
			cin >> f_id;

			// 1. 물건이 존재하는지 확인
			if (node_map.find(f_id) == node_map.end()) {
				cout << "-1\n";
				continue;
			}

			Node* cur = node_map[f_id];
			int b_num = cur->belt_num; 

			cout << b_num << '\n';

			// 2. 찾아낸 물건이 이미 맨 앞(Head)이라면 옮길 필요 없음
			if (head[b_num] == cur) {
				continue;
			}

			// 3. 해당 상자부터 끝까지 뭉텅이로 잘라서 맨 앞으로 가져오기 (포인터 십자수)
			Node* prev_node = cur->prev;

			// 꼬리와 머리 이어주기 (원형으로 일단 연결)
			tail[b_num]->next = head[b_num];
			head[b_num]->prev = tail[b_num];

			// 새로운 Head와 Tail 지정 및 끊어주기
			head[b_num] = cur;
			cur->prev = nullptr; 

			tail[b_num] = prev_node;
			tail[b_num]->next = nullptr; 
		}
		// 벨트 고장
		else {
			int broken_idx;
			cin >> broken_idx;
			if (broken[broken_idx]) {
				cout << "-1\n";
				continue;
			}
			broken[broken_idx] = true;

			// 포인터를 옮길 필요 없이 벨트만 고장내고 종료
			if (head[broken_idx] == nullptr) {
				cout << broken_idx << '\n';
				continue;
			}

			for (int i = 1; i < m; i++) {
				int idx = (broken_idx - 1 + i) % m + 1;
				if (!broken[idx]) {
					change_belt_num(broken_idx, idx);
					// 예외 처리:타겟 벨트가 텅 비어있는 경우
					if (head[idx] == nullptr) {
						head[idx] = head[broken_idx];
						tail[idx] = tail[broken_idx];
					}
					else {
						tail[idx]->next = head[broken_idx];
						head[broken_idx]->prev = tail[idx];
						tail[idx] = tail[broken_idx]; // Tail을 옮겨온 덩어리의 끝으로 갱신
					}

					head[broken_idx] = nullptr;
					tail[broken_idx] = nullptr;

					cout << broken_idx << '\n';
					break;
				}
			}
		}
	}
	return 0;
}

