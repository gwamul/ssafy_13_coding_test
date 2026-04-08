#include <iostream>
#include <bits/stdc++.h>

using namespace std;

class Node{
public:
    unordered_map<char, Node*> children;
    bool isLeaf;
    Node(){
        isLeaf = false;
    }
};

Node* root;

void insert(const string& word){
    Node* cur = root;
    for(char c : word){
        
        if(!cur -> children.count(c)){
            //없으면
            cur -> children[c] = new Node();
        }
        cur = cur -> children[c];
    }
    cur -> isLeaf = true;
}
bool search(const string& s){
    Node* cur = root;
    for(int i = 0; i<s.size(); i++){
        if(!cur -> children.count(s[i])) return false;
        cur = cur -> children[s[i]];
        if(i < s.size()-1 && cur -> isLeaf) return true;
    }
    return false;
}


int main(){
    int t;
    cin >> t;
    for(int tc = 0; tc < t; tc++){
        int n;
        cin >> n;
        root = new Node();
        vector<string> phone;
        for(int i=0; i<n; i++){
            string num;
            cin >> num;
            phone.push_back(num);
            insert(num);
            
        }
        bool flag = true;
        for(int i=0; i<n; i++){
            if(search(phone[i])){
                flag = false;
                break;
            }
        }
        if(flag) cout << "YES" << endl;
        else cout << "NO" << endl;
    }




}