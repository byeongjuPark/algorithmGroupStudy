import java.util.*;
import java.io.*;

class Swea1952{
	static int T;
	static BufferedReader br;

	static int[] costs;
	static int[] plans;
	static int[][] realCosts;
	static int[] dp;

	public static void main(String[] args) throws IOException {
		init();
		play();
	}
	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());

		// 0 : 1일, 1: 달, 2: 3달, 3: 1년
		costs = new int[4];
		plans = new int[12];
	}

	private static void play() throws IOException{

		for(int testcase = 1; testcase<= T; testcase++) {
			initPlans();
			int result = calculate();

			System.out.println("#"+testcase+" "+result);
		}
	}

	private static void initPlans() throws IOException{
		StringTokenizer st = new StringTokenizer(br.readLine());

		//이용권 가격 입력
		for(int i = 0 ; i<4; i++) {
			costs[i] = Integer.parseInt(st.nextToken());
 		}

		//이용 계획 정보 입력
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 12; i++) {
			plans[i] = Integer.parseInt(st.nextToken());
		}
	}

	private static int calculate() {
		//월 이용권 가격
		realCosts = new int[12][4];

		//dp
		dp = new int[13];

		for(int i = 0 ; i<12; i++) {
			realCosts[i][0] = costs[0] * plans[i];
			realCosts[i][1] = costs[1];
			realCosts[i][2] = costs[2];
			realCosts[i][3] = costs[3];
		}


		for(int i = 1 ; i<=12; i++) {
			//1월부터 12월까지 최소한의 금액으로 이용하는거 업데이트
			dp[i] = dp[i-1] + Math.min(realCosts[i-1][0],realCosts[i-1][1]);
			//3월이상부터는 3월 이용권 사용가능
			if(i>=3) {
				dp[i] = Math.min(dp[i],dp[i-3]+realCosts[i-1][2]);
			}
		}
		return Math.min(dp[12],realCosts[11][3]);
	}
}