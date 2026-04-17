#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

vector<int> perfumes;
int dp[3005];
int remove(int idx){
    int n = perfumes.size();
    if(idx >= n) return -1;
    int origin = perfumes[idx];
    if(origin == -1) return -1;
    perfumes[idx] = -1;
    return origin;
}

// dp 활용
int blending(int k){
    fill(dp,dp+k+1,1e9);
    dp[0] = 0;
    for (int perfume : perfumes) {
        if(perfume == -1) continue;
        for (int i = perfume; i <= k; i++) {
            if (dp[i - perfume] != 1e9) {
                dp[i] = min(dp[i], dp[i - perfume] + 1);
            }
        }
    }
    if (dp[k] != 1e9) return dp[k];
    return -1;
}


// 투포인터
long long composition(int k) {
    vector<int> tmp;
    for (int p : perfumes) {
        if (p != -1) tmp.push_back(p);
    }
    sort(tmp.begin(), tmp.end());
    int n = tmp.size();
    long long ret = 0;

    // 1. 탑 노드(i)를 하나씩 고정
    for (int i = 0; i < n; i++) {
        
        int right = n - 1; 

        for (int left = 0; left < n; left++) {
            
            //합이 k 이상인 동안 right를 왼쪽으로 이동하며 경계선을 찾음
            while (right >= 0 && tmp[i] + tmp[left] + tmp[right] >= k) {
                right--;
            }
            
            ret += (n - 1 - right);
        }
    }
    return ret;
}

int main() {
    int q;
    cin >> q;
    while(q-- > 0){
        int type;
        cin >> type;
        if(type == 1){
            int n;
            cin >> n;
            for(int i=0;i<n;i++){
                int s;
                cin >> s;
                perfumes.push_back(s);
            }
        }
        else if(type == 2){
            int v;
            cin >> v;
            perfumes.push_back(v);
        }
        else if(type == 3){
            int idx;
            cin >> idx;
            cout << remove(idx-1) << '\n';
        }
        else if(type == 4){
            int k;
            cin >> k;
            cout << blending(k) << '\n';
        }
        else{
            int k;
            cin >> k;
            cout << composition(k) << '\n';
        }
    }
    return 0;
}
