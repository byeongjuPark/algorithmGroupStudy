import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	// 동, 남, 서, 북
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int[][] board = new int[n + 1][n + 1];
		st = new StringTokenizer(br.readLine());
		int k = Integer.parseInt(st.nextToken());
		int a_row, a_col, time, row = 1, col = 1, dir = 0, timer = 0;
		String turn;
		// 사과는 1, 몸은 -1, 빈칸은 0
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			a_row = Integer.parseInt(st.nextToken());
			a_col = Integer.parseInt(st.nextToken());
			board[a_row][a_col] = 1;
		}
		st = new StringTokenizer(br.readLine());
		int l = Integer.parseInt(st.nextToken());
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < l; i++) {
			st = new StringTokenizer(br.readLine());
			time = Integer.parseInt(st.nextToken());
			turn = st.nextToken();
			map.put(time, turn);
		}

		Deque<int[]> deque = new ArrayDeque<>();
		deque.offer(new int[] { 1, 1 });
		board[1][1] = -1;

		while (row > 0 && row < n + 1 && col > 0 && col < n + 1) {
			timer++;
			row += dx[dir];
			col += dy[dir];
			if (row < 1 || row > n || col < 1 || col > n) {
				break;
			}
			// 본인 몸이랑 부딫히면 종료
			if (board[row][col] == -1) {
				break;
			}

			// 사과를 안먹으면 꼬리 덱에서 빼고 보드에서 0으로 바꿈
			if (board[row][col] != 1) {
				int[] tail = deque.poll();
				board[tail[0]][tail[1]] = 0;
			}
			// 덱에 머리추가 보드에 -1로 바꿈
			deque.offer(new int[] { row, col });
			board[row][col] = -1;

			if (map.containsKey(timer)) {
				if (map.get(timer).charAt(0)=='D') {
					dir = (dir + 1) % 4;
				} else if (map.get(timer).charAt(0)=='L') {
					dir = (dir - 1 + 4) % 4;
				}
			}
		}
		System.out.println(timer);
	}

}
