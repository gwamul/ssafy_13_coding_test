package saffy_algo.BaekJoon.골드.골드3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BaekJoon2457_공주님의정원_Main {
    
    static int[] month = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static class Flower implements Comparable<Flower> {
        int s, e;
        
        public Flower(int sm, int sd, int em, int ed) {
            this.s = dateToDay(sm, sd);
            this.e = dateToDay(em, ed);
        }

        private int dateToDay(int m, int d) {
            int total = 0;
            for (int i = 1; i < m; i++) {
                total += month[i];
            }
            return total + d;
        }

        @Override
        public int compareTo(Flower o) {
            if (this.s != o.s) return this.s - o.s;  
            return o.e - this.e; 
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Flower[] board = new Flower[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            board[i] = new Flower(
                Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())
            );
        }
        
        Arrays.sort(board);

        int startLimit = 60;
        int endLimit = 335;
        
        int cnt = 0;
        int target = startLimit;
        int i = 0;
        int maxEnd = 0;
        
        while (target < endLimit) {
            boolean found = false;
            
            while (i < n && board[i].s <= target) {
                if (board[i].e > maxEnd) {
                    maxEnd = board[i].e;
                    found = true;
                }
                i++;
            }
            
            if (found) {
                target = maxEnd;
                cnt++;
            } else {
                break;
            }
        }

        if (target >= endLimit) System.out.println(cnt);
        else System.out.println(0);
    }
}