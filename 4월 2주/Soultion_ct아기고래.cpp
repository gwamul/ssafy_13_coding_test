#include <bits/stdc++.h>

using namespace std;

int board[51][51];
int n, r, c, k;
int x, y, dir;

// 0:상, 1:우, 2:하, 3:좌 (시계방향 순서)
int dx[] = {-1, 0, 1, 0};
int dy[] = {0, 1, 0, -1};

// 방향(1~4)을 위 인덱스(0~3)로 변환
int trans[] = {0, 0, 2, 3, 1}; 

// order: 0(직진), 1(좌), 2(우), 3(180도)
int getNextDir(int d, int order) {
    if (order == 0) return d;               // 직진
    if (order == 1) return (d + 3) % 4;     // 좌회전
    if (order == 2) return (d + 1) % 4;     // 우회전
    return (d + 2) % 4;                     // 180도 회전
}

bool outOfIdx(int a, int b){
    return a < 0 || a >= n || b < 0 || b >= n;
}

int getDist(int x, int y, int a, int b){
    return abs(x-a) + abs(y-b);
}

bool visited[51][51];

struct node {
    int x;
    int y;
    int dist;
    int dir;
};

// bfs로 다음 위치 갱신
node nextPos(){
    queue<node> q;
    q.push({x, y, 0, dir}); // 현재 위치, 거리 0, 현재 방향
    
    bool visited2[51][51] = {false, };
    visited2[x][y] = true;
    
    node ret = {100, 100, 10000, 0};
    
    // 2단계 이동 우선순위: 좌(3), 하(2), 우(1), 상(0)
    int moveOrder[] = {3, 2, 1, 0};

    while(!q.empty()){
        node cur = q.front();
        q.pop();

        // [목적지 도달] 가장 가까운 미방문 바다 발견
        if (!visited[cur.x][cur.y] && board[cur.x][cur.y] == 0) {
            // BFS 특성상 가장 먼저 도착한 cur.dist가 무조건 최솟값입니다.
            // 거리가 같다면 문제 조건(행/열 번호)에 따라 ret를 갱신합니다.
            if (cur.dist < ret.dist) {
                ret = cur;
            } else if (cur.dist == ret.dist) {
                if (cur.x < ret.x || (cur.x == ret.x && cur.y < ret.y)) {
                    ret = cur;
                }
            }
            // 같은 거리(dist) 내의 다른 후보들도 확인해야 하므로 
            // 바로 return하지 않고 continue 합니다.
            continue; 
        }

        // 목적지를 이미 찾았는데 현재 탐색하는 거리가 그보다 멀어지면 더 볼 필요 없습니다.
        if (cur.dist >= ret.dist) continue;

        for(int i = 0; i < 4; i++){
            int nd = moveOrder[i]; // 우선순위 적용
            int nx = cur.x + dx[nd];
            int ny = cur.y + dy[nd];
            
            if(outOfIdx(nx, ny) || board[nx][ny] == 1 || visited2[nx][ny]) continue;
            
            visited2[nx][ny] = true;
            q.push({nx, ny, cur.dist + 1, nd}); 
        }
    }
    return ret;
}

int main() {

    ios_base::sync_with_stdio(0);
    cin.tie(0);

    cin >> n >> r >> c >> dir;
    dir = trans[dir];
    k = n*n;

    x = r - 1;
    y = c - 1;

    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin >> board[i][j];
            if(board[i][j] == 1) k--;
        }
    }
    k--;
    visited[x][y] = true;
    cout << x+1 << " " << y+1 << '\n';

    while(k-- > 0){
        pair<int,int> ans = {0,0};
        bool canMove = false;
        for(int i = 0; i < 4; i++){
            int nd = getNextDir(dir,i);
            int nx = x + dx[nd];
            int ny = y + dy[nd];
            if(outOfIdx(nx,ny) || visited[nx][ny] || board[nx][ny] == 1) continue;
            canMove = true;
            visited[nx][ny] = true;
            x = nx;
            y = ny;
            dir = nd;
            break;
        }
        if(!canMove){
            auto next = nextPos();
            x = next.x;
            y = next.y;
            visited[x][y] = true;
            dir = next.dir;
        }

        cout << x+1 << " " << y+1 << '\n';
    }
    return 0;
}
