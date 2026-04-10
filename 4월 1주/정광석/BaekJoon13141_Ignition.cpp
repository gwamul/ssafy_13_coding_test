#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
using namespace std;
/*
노드에 언제 도착하는 지를 알고, 그 노드 최단 도착 시간
a 시간에 노드에 불이 도착하면, 
그 노드에 연결된 모든 불이 붙는다.
모든 노드는  

5 8
1 2 4
1 2 6
1 5 6
2 4 4
4 5 4
3 4 6
3 4 6
3 3 4


5 10
1 2 1
2 3 1
3 4 1
4 5 1
1 3 10
2 4 10
3 5 10
1 4 7
2 5 7
1 5 9
*/

struct Edge{
    int from;
    int to;
    int weight;
};
int n,m ;
vector<vector<int>> adj;
vector<Edge> edges;

int main(){

    cin >> n >> m;
    adj.assign(n+1, vector<int>(n+1, 99999));
    

    for(int i=0; i<m; i++){
        int a,b,w;
        cin >> a >> b  >> w;
        adj[a][b] = min(adj[a][b] , w);
        adj[b][a] = min(adj[b][a] , w);
        edges.push_back({a,b,w});
    }
    for(int i=1; i<=n ;i++) adj[i][i] = 0;

    for(int k=1; k<=n; k++){

        for(int i=1; i<=n; i++){
            for(int j=1; j<=n; j++){
                
                adj[i][j] = min(adj[i][j] , adj[i][k] + adj[k][j]);
            }
        }
    }

    for(int i=1; i<=n; i++){
        for(int j=1 ;j<=n; j++){
            cout << adj[i][j] << " ";
        }
        cout << endl;
    }
    double tot_max_time = 99999;
    for(int start = 1; start<= n; start++){
        double max_time = -1;

        for(const auto& e : edges){
            int from = e.from;
            int to = e.to;
            int weight =  e.weight  ;

            int from_fire = adj[start][from];
            int to_fire = adj[start][to];
            double time = (double)min(from_fire, to_fire);
            if( weight >=  abs(from_fire - to_fire)){
                // 엣지 길이가 시간 차보다 길다
                // 두방향에서 탈거다.
                time += (double)(weight - abs(from_fire - to_fire)) / 2;
                time += abs(from_fire - to_fire);

            }else{
                // 엣지 길이가 시간 차보다 짧다
                // 한방향에서 타면 끝난다.
                time += weight;
            }
            
            max_time = max(max_time, time);
        //     cout << "start: " << start << " from : " << from << " to : " << to << endl;
          //  cout << "max time: " << max_time << "  time: " << time << endl;
        }
        tot_max_time = min(tot_max_time, max_time);
        
        
    }
    
    printf("%0.1f", tot_max_time);

    return 0;
}