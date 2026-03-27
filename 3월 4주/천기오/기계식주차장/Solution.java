package week8.기계식주차장;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution
{
    private static final int CMD_INIT           = 100;
    private static final int CMD_ENTER          = 200;
    private static final int CMD_PULL_OUT       = 300;
    private static final int CMD_SEARCH         = 400;

    private static UserSolution usersolution = new UserSolution();

    public static class RESULT_E
    {
        int success;
        String locname;

        RESULT_E()
        {
            success = -1;
        }
    }

    public static class RESULT_S
    {
        int cnt;
        String[] carlist = new String[5];

        RESULT_S()
        {
            cnt = -1;
        }
    }

	private static boolean run(BufferedReader br) throws Exception
	{
	    int Q, N, M, L;
	    int mTime;
	
	    // 나노초 단위로 누적하기 위해 long 타입 사용
	    long tInit = 0, tEnter = 0, tPullOut = 0, tSearch = 0; 
	    
	    String mCarNo;
	    String mStr;
	
	    int ret = -1, ans;
	
	    RESULT_E res_e;
	    RESULT_S res_s;
	
	    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
	    Q = Integer.parseInt(st.nextToken());
	
	    boolean okay = false;
	
	    for (int q = 0; q < Q; ++q)
	    {
	        st = new StringTokenizer(br.readLine(), " ");
	        int cmd = Integer.parseInt(st.nextToken());
	
	        // 측정용 변수
	        long startTime, endTime;
	
	        switch(cmd)
	        {
	        case CMD_INIT:
	            N = Integer.parseInt(st.nextToken());
	            M = Integer.parseInt(st.nextToken());
	            L = Integer.parseInt(st.nextToken());
	            
	            // 순수 함수 호출 부분만 측정
	            startTime = System.nanoTime();
	            usersolution.init(N, M, L);
	            endTime = System.nanoTime();
	            tInit += (endTime - startTime);
	            
	            okay = true;
	            break;
	            
	        case CMD_ENTER:
	            mTime = Integer.parseInt(st.nextToken());
	            mCarNo = st.nextToken();
	            
	            startTime = System.nanoTime();
	            res_e = usersolution.enter(mTime, mCarNo);
	            endTime = System.nanoTime();
	            tEnter += (endTime - startTime);
	            
	            ans = Integer.parseInt(st.nextToken());
	            if (res_e.success != ans)
	                okay = false;
	            if (ans == 1)
	            {
	                mStr = st.nextToken();
	                if (!mStr.equals(res_e.locname)) {
	                    System.out.println("ans : " + mStr + " / res : " + res_e.locname);
	                    okay = false;
	                }
	            }
	            break;
	            
	        case CMD_PULL_OUT:
	            mTime = Integer.parseInt(st.nextToken());
	            mCarNo = st.nextToken();
	            
	            startTime = System.nanoTime();
	            ret = usersolution.pullout(mTime, mCarNo);
	            endTime = System.nanoTime();
	            tPullOut += (endTime - startTime);
	            
	            ans = Integer.parseInt(st.nextToken());
	            if (ret != ans)
	                okay = false;
	            break;
	            
	        case CMD_SEARCH:
	            mTime = Integer.parseInt(st.nextToken());
	            mStr = st.nextToken();
	            
	            startTime = System.nanoTime();
	            res_s = usersolution.search(mTime, mStr);
	            endTime = System.nanoTime();
	            tSearch += (endTime - startTime);
	            
	            ans = Integer.parseInt(st.nextToken());
	            if (res_s.cnt != ans)
	                okay = false;
	            for (int i = 0; i < ans; ++i)
	            {
	                mCarNo = st.nextToken() + mStr;
	                if (!mCarNo.equals(res_s.carlist[i]))
	                    okay = false;
	            }
	            break;
	            
	        default:
	            okay = false;
	            break;
	        }
	    }
	    
//	    // 나노초(ns)를 1,000,000으로 나누어 밀리초(ms) 단위로 보기 좋게 출력
//	    System.out.printf("[소요 시간] INIT: %.3f ms | ENTER: %.3f ms | PULLOUT: %.3f ms | SEARCH: %.3f ms\n", 
//	            tInit / 1_000_000.0, tEnter / 1_000_000.0, tPullOut / 1_000_000.0, tSearch / 1_000_000.0);
	            
	    return okay;
	}

    public static void main(String[] args) throws Exception
    {
        //System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        long startTime, endTime;
        int TC = Integer.parseInt(st.nextToken());
        int MARK = Integer.parseInt(st.nextToken());
        startTime = System.nanoTime();
        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(br) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }
        endTime = System.nanoTime();
	    System.out.printf("[소요 시간]%.3f ms\n", 
        (endTime - startTime) / 1_000_000.0);
        br.close();
    }
}