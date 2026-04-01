#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int n,m;
int board[50][50];
bool isRemoved[50][50];
int pointer[50];

bool remove() {
    bool marked[50][50] = {false}; // 이번 턴에 지워질 위치 표시
    bool hasChanged = false;

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            // 공간상의 j번째 위치에 있는 실제 인덱스 계산
            int cur_idx = (pointer[i] + j) % m;
            if (isRemoved[i][cur_idx]) continue;

            int cur_val = board[i][cur_idx];

            // 1. 좌우 체크 (같은 원판 내)
            int side_idx = (pointer[i] + (j + 1)) % m;
            if (!isRemoved[i][side_idx] && board[i][side_idx] == cur_val) {
                marked[i][cur_idx] = marked[i][side_idx] = true;
                hasChanged = true;
            }

            // 2. 상하 체크 (다른 원판 간)
            if (i == n - 1) continue;
            int down_idx = (pointer[i + 1] + j) % m; // '공간상 j번째'로 정렬됨
            if (!isRemoved[i + 1][down_idx] && board[i + 1][down_idx] == cur_val) {
                marked[i][cur_idx] = marked[i + 1][down_idx] = true;
                hasChanged = true;
            }
            
        }
    }

    // 마킹된 곳 실제 삭제 처리 및 isRemoved 갱신
    if (hasChanged) {
        for(int i=0; i<n; i++)
            for(int j=0; j<m; j++)
                if(marked[i][j]) isRemoved[i][j] = true;
    }

    return hasChanged;
}

void norm(){
    int total = 0;
    int cnt = 0;
    int perCnt[50] = {0,};
    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            if(!isRemoved[i][j]){
                cnt++;
                perCnt[i]++;
                total += board[i][j];
            }
        }
    }
    if(cnt == 0) return;

    int avg = total/cnt;
    for(int i=0;i<n;i++){
        if(perCnt[i] == 0) continue;
        for(int j=0;j<m;j++){
            if(!isRemoved[i][j]){
                if(board[i][j] < avg){
                    board[i][j]++;
                }
                else if(board[i][j] > avg){
                    board[i][j]--;
                }
            }
        }
    }
}


int main() {
    ios_base::sync_with_stdio(false); 
	cin.tie(nullptr);
    int q = 0;
    cin >> n >> m >> q;
    int dx[] = {-1, 1};

    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            cin >> board[i][j];
        }
    }
    
    for(int i=0; i < q; i++){
        int x = 0, d = 0, k=0;
        cin >> x >> d >> k;
        for(int i = x-1; i< n; i+=x){
            pointer[i] = (pointer[i] + m + k*dx[d]) % m;
        }
        bool res = remove();
        if(!res)
            norm();
    }
    int answer = 0;
    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            if(!isRemoved[i][j])
                answer += board[i][j];
        }
    }
    cout << answer;
    return 0;
}
