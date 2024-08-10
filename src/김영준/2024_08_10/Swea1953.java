import java.util.*;
import java.io.*;

class Swea1953 {

	static BufferedReader br;
	static int T;
	static int N, M, R, C, L;
	static int[][] graph;

	//상 우 하 좌
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};

	static boolean[][] tunnelType = {
			{false, false, false, false},
			{true, true, true, true},
			{true, false, true, false},
			{false, true, false, true},
			{true, true, false, false},
			{false, true, true, false},
			{false, false, true, true},
			{true,false,false,true}
	};

	public static void main(String[] args) throws IOException {
		init();
		start();
	}

	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
	}

	private static void start()  throws IOException{
		for (int testcase = 1; testcase <= T; testcase++) {
			initGameInfo();
			int result = play();

			System.out.println("#" + testcase + " " + result);
		}
	}

	private static void initGameInfo() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		graph = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}

	private static int play() {
		Queue<Theft> q = new LinkedList<>();
		//1초에 맨홀에 들어감
		q.add(new Theft(R, C, 1));
		boolean[][] visited = new boolean[N][M];
		visited[R][C] = true;

		while (!q.isEmpty()) {
			Theft now = q.poll();

			if (now.moveCount == L) {
				continue;
			}

			for (int direction = 0; direction < 4; direction++) {
				int nx = now.x + dx[direction];
				int ny = now.y + dy[direction];

				boolean canMove = canMove(now.x, now.y, nx, ny, direction, visited);

				if(canMove) {
					visited[nx][ny] = true;
					q.add(new Theft(nx,ny,now.moveCount+1));
				}

			}
		}

		int result = 0;
		for(int i = 0 ; i<N; i++) {
			for(int j = 0 ; j<M; j++) {
				if(visited[i][j]) result++;
			}
		}

		return result;

	}

	private static boolean canMove(int x, int y,int nx, int ny, int direction, boolean[][] visited) {
		if (nx < 0 || ny < 0 || nx >= N || ny >= M) {
			return false;
		}
		if (visited[nx][ny]) {
			return false;
		}

		int nowType = graph[x][y];
		int nextType = graph[nx][ny];

		if(nextType == 0 ) return false;

		return tunnelType[nowType][direction] && tunnelType[nextType][(direction + 2) % 4];
	}


}

class Theft extends Location {

	int moveCount;

	Theft(int x, int y, int moveCount) {
		super(x, y);
		this.moveCount = moveCount;
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