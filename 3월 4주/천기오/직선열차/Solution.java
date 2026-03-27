package week8.직선열차;

import java.util.Scanner;

class Solution {
	private final static int MAX_K = 200;
	private final static int CMD_INIT = 100;
	private final static int CMD_ADD = 200;
	private final static int CMD_REMOVE = 300;
	private final static int CMD_CALC = 400;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) {
		int q = sc.nextInt();

		int n, k;
		String strTmp;
		int[] mIdArr = new int[MAX_K];
		int[] sIdArr = new int[MAX_K];
		int[] eIdArr = new int[MAX_K];
		int[] mIntervalArr = new int[MAX_K];
		int mId, sId, eId, mInterval;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			cmd = sc.nextInt();
			strTmp = sc.next();
			switch (cmd) {
				case CMD_INIT:
					okay = true;
					strTmp = sc.next();
					n = sc.nextInt();
					strTmp = sc.next();
					k = sc.nextInt();
					for (int j = 0; j < k; ++j) {
						strTmp = sc.next();
						mIdArr[j] = sc.nextInt();
						strTmp = sc.next();
						sIdArr[j] = sc.nextInt();
						strTmp = sc.next();
						eIdArr[j] = sc.nextInt();
						strTmp = sc.next();
						mIntervalArr[j] = sc.nextInt();
					}
					usersolution.init(n, k, mIdArr, sIdArr, eIdArr, mIntervalArr);
					break;
				case CMD_ADD:
					strTmp = sc.next();
					mId = sc.nextInt();
					strTmp = sc.next();
					sId = sc.nextInt();
					strTmp = sc.next();
					eId = sc.nextInt();
					strTmp = sc.next();
					mInterval = sc.nextInt();
					usersolution.add(mId, sId, eId, mInterval);
					break;
				case CMD_REMOVE:
					strTmp = sc.next();
					mId = sc.nextInt();
					usersolution.remove(mId);
					break;
				case CMD_CALC:
					strTmp = sc.next();
					sId = sc.nextInt();
					strTmp = sc.next();
					eId = sc.nextInt();
					strTmp = sc.next();
					ans = sc.nextInt();
					ret = usersolution.calculate(sId, eId);
					if (ret != ans)
						okay = false;
					break;
				default:
					okay = false;
					break;
			}
		}
		return okay;
	}

	public static void main(String[] args) throws Exception {
		int TC, MARK;

		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);

		TC = sc.nextInt();
		MARK = sc.nextInt();

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		sc.close();
	}
}