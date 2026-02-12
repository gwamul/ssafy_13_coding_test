import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> right = new PriorityQueue<>();
        int mid = Integer.parseInt(br.readLine().trim());
        n--;
        int size = 1;
        System.out.println(mid);
        while (n-- > 0) {
            int num =  Integer.parseInt(br.readLine().trim());

            //새로운 숫자 삽입
            if(num < mid){
                left.offer(num);
            }
            else if(num > mid){
                right.offer(num);
            }
            else{
                if(size % 2 == 0) left.offer(num);
                else right.offer(num);
            }
            size++;

            //만약 mid가 교체되어야 한다면?
            //right의 크기는 left보다 같거나 1더 커야 한다.
            if(right.size() < left.size()){
                right.offer(mid);
                mid = left.poll();
            }
            else if(right.size() - left.size() > 1){
                left.offer(mid);
                mid = right.poll();
            }
            System.out.println(mid);
        }
    }

}