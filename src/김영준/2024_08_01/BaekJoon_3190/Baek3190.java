import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

class Baek3190 {

	static int N, K, L;

	static int result = 0;
	static int[][] graph;

	static List<Direction> directions;


	//우 하 좌 상
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};


	public static void main(String[] args) throws IOException {
		init();
		play();
		System.out.println(result);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N,K 입력
		N = Integer.parseInt(br.readLine());

		K = Integer.parseInt(br.readLine());

		graph = new int[N][N];
		directions = new ArrayList<>();

		// 사과 정보 입력
		for (int i = 0; i < K; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			// 그래프 1행 1열 시작 -> -1
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;

			//1이면 사과 존재
			graph[x][y] = 1;
		}

		//L 정보 입력
		L = Integer.parseInt(br.readLine());

		for (int i = 0; i < L; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			int time = Integer.parseInt(st.nextToken());
			String direction = st.nextToken();

			directions.add(new Direction(time, direction));
		}

		br.close();
	}

	private static void play() {

		// 초기상태 0,0 우
		Snake snake = new Snake(new Location(0, 0), 0, 0);
		int time = 0;
		int index = 0;
		int x = snake.head.x;
		int y = snake.head.y;

		while(true) {
			time++;
			int nx = x + dx[snake.direction];
			int ny = y + dy[snake.direction];


			if(!canMove(nx,ny,snake)) {
//				System.out.println("움직이지 못합니다"+nx+" "+ny);
				result = snake.moveCount +1;
				return;
			}

			if(isApple(nx,ny)) {
				snake.add(new Location(x,y));
				graph[nx][ny] = 0;
			}else {
				snake.moveBody(new Location(x,y));
			}

			snake.head.x = nx;
			snake.head.y = ny;
			snake.moveCount++;

			x = snake.head.x;
			y = snake.head.y;

			//스네이크 방향 회전
			if(index<directions.size() && time == directions.get(index).time) {
				if(directions.get(index).direction.equals("D")) {
					snake.direction = (snake.direction + 1) % 4;
				} else {
					snake.direction = (snake.direction + 3) % 4;
				}
				index++;
			}
		}



	}

	private static boolean isApple(int nx, int ny) {
		if (graph[nx][ny] == 1) {
			return true;
		}

		return false;
	}

	private static boolean canMove(int nx, int ny, Snake snake) {

		// 벽과 부딪히는지
		if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
			return false;
		}

		//자신의 몸과 부딪히는지
		for (Location body : snake.bodys) {
			if (body.x == nx && body.y == ny) {
				return false;
			}
		}

		return true;
	}
}

class Snake {

	Location head;

	int direction;

	int moveCount;

	Deque<Location> bodys;

	Snake(Location head, int direction, int moveCount) {
		this.head = head;
		this.direction = direction;
		this.moveCount = moveCount;
		bodys = new ArrayDeque<>();
	}

	public void add(Location body) {
		bodys.addFirst(body);
	}

	public void moveBody(Location head) {
		int beforeX = head.x;
		int beforeY = head.y;
		int count = bodys.size();

		for (int i = 0; i < count; i++) {
			Location body = bodys.poll();

			int currentX = body.x;
			int currentY = body.y;

			body.x = beforeX;
			body.y = beforeY;

			beforeX = currentX;
			beforeY = currentY;

			bodys.add(body);
		}
	}
}

class Direction {

	int time;
	String direction;

	Direction(int time, String direction) {
		this.time = time;
		this.direction = direction;
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