import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 이진수의 각 자리마다 패턴이 있다.
 *
 * b까지의 1의 개수에서 a-1까지의 1의 개수를 빼기
 *
 * 1. 2진수로 몇 자리인지 구하기
 *
 *  2.
 *  예를 들어서 3번째 자리수라고 해보자.
 *  세로로 보면
 *  000
 *  001
 *  010
 *  011
 *  100
 *  101
 *  110
 *  111
 *
 *  이렇게 8개의 길이의 패턴이 나타난다.
 *  그전은 4개, 처음은 0101반복
 *
 *  즉, 각 자리마다 1의 개수는 n/패턴 길이에 패턴길이/2 를 곱한것
 *  그리고 나머지가 절반 보다 크다면 나머지 - 패턴길이/2를 추가로 더해주면 1의 개수
 *
 *  참고로 0부터 시작하는게 패턴이 예뻐서 n+1을 기준으로 구하게됨.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] ab = br.readLine().split(" ");
        long a = Long.parseLong(ab[0]);
        long b = Long.parseLong(ab[1]);

        String sb = Long.toBinaryString(b);
        String sa = Long.toBinaryString(a-1);

        long big = getOneCnt(b, sb);
        long small = getOneCnt(a-1, sa);

        System.out.println(big-small);
    }

    private static long getOneCnt(long n, String s) {
        n++;
        int len = s.length();
        long cnt = 0;
        for(int i=1;i<=len;i++){
            long pattern = (long)Math.pow(2, i);
            long half = pattern/2;
            long quo = n/pattern;
            long rem = n%pattern;
            cnt += quo*half;
            if(rem > half){
                cnt += rem - half;
            }
        }
        return cnt;
    }

}




