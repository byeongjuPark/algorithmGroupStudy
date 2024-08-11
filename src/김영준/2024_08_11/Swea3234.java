import java.util.*;
import java.io.*;

class Swea3234{

	static int T;
	static int N;
	static List<Integer> weights;
	static BufferedReader br;

	static List<List<Integer>> pers;
	static int result;

	public static void main(String[] args) throws IOException {
		init();
		play();
	}
	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
	}

	private static void play() throws IOException{
		for (int testcase = 1; testcase <= T; testcase++) {
			N = Integer.parseInt(br.readLine());
			weights = new ArrayList<>();
			pers = new ArrayList<>();

			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				weights.add(Integer.parseInt(st.nextToken()));
			}


			result = 0;
			permutation(new ArrayList<>(),new boolean[N]);
			for (List<Integer> per : pers) {
				dfs(0, 0, 0, per);
			}

			System.out.println("#" + testcase + " " + result);

		}
	}

	private static void permutation(List<Integer> per,boolean[] visited) {

		if (per.size() == N) {
			pers.add(new ArrayList<>(per));
		}
		for (int i = 0; i < weights.size(); i++) {
			if (!visited[i]) {
				visited[i] = true;
				per.add(weights.get(i));
				permutation(per,visited);
				per.remove(per.size() - 1);
				visited[i] = false;
			}
		}
	}

	private static void dfs(int left, int right,int index,List<Integer> per) {
		if (left < right) {
			return;
		}

		if (index == per.size()) {
			result++;
			return;
		}

		dfs(left + per.get(index), right, index + 1, per);
		dfs(left, right+per.get(index), index + 1, per);
	}

}