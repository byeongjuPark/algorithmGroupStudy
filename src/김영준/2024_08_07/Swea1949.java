import java.util.*;
import java.io.*;

class Swea1949 {
	static int T;
	static int N;
	static int K;
	static int maxHeightLoad;
	static int result;
	static BufferedReader br;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		init();
		play();
	}

	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
	}

	private static void play() throws IOException {

		// 각 테스트 케이스
		for (int testcase = 1; testcase <= T; testcase++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			// N,K 정보 입력
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// 가장 높은 봉우리 길이
			maxHeightLoad = Integer.MIN_VALUE;

			// 가장 높은 봉우리 위치 기억
			List<Location> loads = new ArrayList<>();
			int[][] graph = new int[N][N];
			result = Integer.MIN_VALUE;

			// 그래프 정보 초기화
			initGraph(graph);
			// 가장 높은 봉우리 길이 위치 정보 찾기
			findStartLoad(graph, loads);

			// 각 위치에서 시작
			for (Location load : loads) {
				int[][] tmp = new int[N][N];
				copyGraph(graph, tmp);
				boolean[][] visited = new boolean[N][N];
				visited[load.x][load.y] = true;
				dfs(tmp, 1, load, false, visited);

			}

			System.out.println("#" + testcase + " " + result);
		}
	}

	private static void initGraph(int[][] graph) throws IOException {
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
				maxHeightLoad = Math.max(maxHeightLoad, graph[i][j]);
			}
		}
	}

	private static void findStartLoad(int[][] graph, List<Location> loads) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (graph[i][j] == maxHeightLoad) {
					loads.add(new Location(i, j));
				}
			}
		}
	}

	private static void dfs(int[][] graph, int buildLoad, Location now, boolean cut, boolean[][] visited) {

		result = Math.max(buildLoad, result);

		// 4방향으로 탐색
		for (int i = 0; i < 4; i++) {
			int nx = now.x + dx[i];
			int ny = now.y + dy[i];

			// 만약 그래프 범위가 벗어난다면 피하기
			if (nx < 0 || ny < 0 || ny >= N || nx >= N)
				continue;

			// 다음 위치의 높이가 지금의 높이보다 작으면 높이를 깎아야함
			if (graph[nx][ny] >= graph[now.x][now.y]) {

				// 만약 전에 꺾은적이 있다면 깎이 불가능
				if (!cut) {

					// 또한 전에 가본적이라면 갈필요가 없음 또한 전으로 체크하지 않으면 전으로 돌아갈수도있음
					if (!visited[nx][ny]) {
						// 길이가 1부터 K까지 전부 깍는 경우
						for (int cutCount = 1; cutCount <= K; cutCount++) {
							// 길이를 깎아보았는데 다음으로 건너갈 수 있는경우
							if (graph[nx][ny] - cutCount < graph[now.x][now.y]) {

								graph[nx][ny] -= cutCount;
								visited[nx][ny] = true;

								dfs(graph, buildLoad + 1, new Location(nx, ny), true, visited);

								graph[nx][ny] += cutCount;
								visited[nx][ny] = false;

							}
						}
					}
				}
			}
			else { // 다음 위치가 지금의 높이보다 낮다면 그냥 갈 수 있음
				if (!visited[nx][ny]) {
					visited[nx][ny] = true;
					dfs(graph, buildLoad + 1, new Location(nx, ny), cut, visited);
					visited[nx][ny] = false;
				}
			}

		}
	}

	private static void copyGraph(int[][] graph, int[][] tmp) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tmp[i][j] = graph[i][j];
			}
		}
	}

}

class Location {
	int x;
	int y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
}