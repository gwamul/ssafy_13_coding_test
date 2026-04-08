#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

struct Box {
    int id, weight, belt_num;
    int prev, next;
};

Box pool[100005];
int head[11], tail[11];
bool broken[11];
unordered_map<int, int> id_to_idx;
int N, M;

// 두 노드를 안전하게 연결
void connect(int p, int n) {
    if (p != -1) pool[p].next = n;
    if (n != -1) pool[n].prev = p;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int Q; cin >> Q;
    while (Q--) {
        int cmd; cin >> cmd;

        if (cmd == 100) {
            cin >> N >> M;
            vector<int> ids(N), weights(N);
            for (int i = 0; i < N; i++) cin >> ids[i];
            for (int i = 0; i < N; i++) cin >> weights[i];

            id_to_idx.clear();
            int per_belt = N / M;
            for (int b = 1; b <= M; b++) {
                broken[b] = false;
                head[b] = (b - 1) * per_belt;
                tail[b] = b * per_belt - 1;

                for (int i = 0; i < per_belt; i++) {
                    int cur = (b - 1) * per_belt + i;
                    pool[cur].id = ids[cur];
                    pool[cur].weight = weights[cur];
                    pool[cur].belt_num = b;
                    id_to_idx[ids[cur]] = cur;

                    pool[cur].prev = (i == 0) ? -1 : cur - 1;
                    pool[cur].next = (i == per_belt - 1) ? -1 : cur + 1;
                }
            }
        }
        else if (cmd == 200) {
            int w_max; cin >> w_max;
            long long sum_w = 0;
            for (int b = 1; b <= M; b++) {
                if (broken[b] || head[b] == -1) continue;

                int cur = head[b];
                if (pool[cur].weight <= w_max) {
                    sum_w += pool[cur].weight;
                    int nxt = pool[cur].next;
                    id_to_idx.erase(pool[cur].id); // ID 맵에서 제거
                    if (nxt != -1) {
                        pool[nxt].prev = -1;
                        head[b] = nxt;
                    } else head[b] = tail[b] = -1;
                } else {
                    // 무게 초과 시 맨 뒤로 이동 (물건이 하나면 변화 없음)
                    if (pool[cur].next != -1) {
                        int nxt = pool[cur].next;
                        int old_tail = tail[b];
                        
                        head[b] = nxt;
                        pool[nxt].prev = -1;
                        
                        connect(old_tail, cur);
                        pool[cur].next = -1;
                        tail[b] = cur;
                    }
                }
            }
            cout << sum_w << "\n";
        }
        else if (cmd == 300) {
            int r_id; cin >> r_id;
            if (id_to_idx.find(r_id) == id_to_idx.end()) {
                cout << "-1\n";
            } else {
                int cur = id_to_idx[r_id];
                int b = pool[cur].belt_num;
                int prv = pool[cur].prev;
                int nxt = pool[cur].next;

                if (prv != -1 && nxt != -1) connect(prv, nxt);
                else if (prv != -1) { pool[prv].next = -1; tail[b] = prv; }
                else if (nxt != -1) { pool[nxt].prev = -1; head[b] = nxt; }
                else { head[b] = tail[b] = -1; }

                id_to_idx.erase(r_id);
                cout << r_id << "\n";
            }
        }
        else if (cmd == 400) {
            int f_id; cin >> f_id;
            if (id_to_idx.find(f_id) == id_to_idx.end()) {
                cout << "-1\n";
            } else {
                int cur = id_to_idx[f_id];
                int b = pool[cur].belt_num;
                cout << b << "\n";
                // 해당 상자가 이미 맨 앞이 아닐 때만 옮기기
                if (head[b] != cur) {
                    int old_head = head[b];
                    int old_tail = tail[b];
                    int new_tail = pool[cur].prev;

                    pool[new_tail].next = -1; // 리스트 끊기
                    pool[cur].prev = -1;
                    
                    connect(old_tail, old_head); // 기존 꼬리와 기존 머리 연결
                    head[b] = cur;
                    tail[b] = new_tail;
                }
            }
        }
        else if (cmd == 500) {
            int b_num; cin >> b_num;
            if (broken[b_num]) cout << "-1\n";
            else {
                broken[b_num] = true;
                if (head[b_num] != -1) {
                    int target = -1;
                    // 문제 조건: m번 다음엔 다시 1번 벨트 확인
                    for (int i = 1; i < M; i++) {
                        int next_b = (b_num + i - 1) % M + 1;
                        if (!broken[next_b]) { target = next_b; break; }
                    }
                    
                    // 상자들의 소속 벨트 번호 갱신
                    int tmp = head[b_num];
                    while (tmp != -1) {
                        pool[tmp].belt_num = target;
                        tmp = pool[tmp].next;
                    }

                    if (head[target] == -1) {
                        head[target] = head[b_num];
                        tail[target] = tail[b_num];
                    } else {
                        connect(tail[target], head[b_num]);
                        tail[target] = tail[b_num];
                    }
                    head[b_num] = tail[b_num] = -1;
                }
                cout << b_num << "\n";
            }
        }
    }
    return 0;
}