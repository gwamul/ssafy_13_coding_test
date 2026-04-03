import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main_b2295_세수의합 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        for(int i=0;i<n;i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(arr);
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<n;i++) {
            for(int j=i;j<n;j++) {
                set.add(arr[j]+arr[i]);
            }
        }
        for(int i=n-1;i>=0;i--) {
            for(int j=0;j<=i;j++) {
                if(set.contains(arr[i]-arr[j])) {
                    System.out.println(arr[i]);
                    return;
                }
            }
        }
    }
}
