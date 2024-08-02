import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Baek14501{
	static int N;
	static int[] times;

	static int[] values;

	public static void main(String[] args) throws IOException{
		init();
		play();
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		values = new int[N];
		times = new int[N];

		for(int i = 0 ; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int time = Integer.parseInt(st.nextToken());
			int value = Integer.parseInt(st.nextToken());

			times[i] = time;
			values[i] = value;
		}
	}

	private static void play() {
		int[] dp = new int[N+1];

		//I일차 기준 시간을 소요해서 만들수 있는 최대 가치와 기존의 최대 가치를 비교
		for(int i = 0 ; i<N; i++) {
			if(i+times[i]<=N) {
				dp[i+times[i]] = Math.max(dp[i+times[i]],dp[i]+values[i]);
			}

			//그 다음날은 그 날을 만약 건너뛰면 그 일차는 전날의 가치와 다음날의 최대가치로 비교해야한다.
			dp[i+1] = Math.max(dp[i+1],dp[i]);
		}

		System.out.println(dp[N]);


	}

}