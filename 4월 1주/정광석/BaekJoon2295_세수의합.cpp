#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
using namespace std;

int main() {
    int n;
    cin >> n;
    
    vector<int> v(n);
    for (int i = 0; i < n; i++) cin >> v[i];
    
    sort(v.begin(), v.end());
    
    set<int> s(v.begin(), v.end()); 
    set<int> pairSum;                
    
    for (int i = 0; i < n; i++)
        for (int j = i; j < n; j++)
            pairSum.insert(v[i] + v[j]);  
    
    // 큰 d부터 탐색
    for (int k = n-1; k >= 0; k--) {
        for (int i = 0; i < n; i++) {
            int need = v[k] - v[i];      
            if (need >= 0 && pairSum.count(need)) {
                cout << v[k];
                return 0;
            }
        }
    }
    return 0;
}