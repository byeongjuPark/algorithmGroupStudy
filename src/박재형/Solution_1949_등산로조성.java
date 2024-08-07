package codigtest;

import java.util.*;
import java.io.*;

public class Solution_1949_등산로조성 {

	static int[][] board;
	static int top;
	static ArrayList<int[]> list;
	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };
	static boolean[][] visited;
	static int k;
	static int max_length;
	static int n;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int t = Integer.parseInt(st.nextToken());
		for (int T = 1; T < t+1; T++) {
			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			board = new int[n][n];
			visited = new boolean[n][n];
			list = new ArrayList<>();
			top = 0;
			max_length = 0;
			boolean flag = false;
			
			//제일 높은 높이 top 에 저장
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
					if (board[i][j] > top) {
						top = board[i][j];
					}
				}
			}
			//높이가 top인 봉우리의 위치를 list에 add
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] == top) {
						list.add(new int[] { i, j });
					}
				}
			}
			
			//list의 사이즈 만큼 dfs 반복
			for (int i = 0; i < list.size(); i++) {
				int[] num = list.get(i);
				DFS(num, 1, flag);
			}
			System.out.println("#" + T + " " + max_length);

		}
	}

	public static void DFS(int[] num, int length, boolean flag) {
		visited[num[0]][num[1]] = true;
		//length 가 max_length보다 크면 업데이트
		if (length > max_length) {
			max_length = length;
		}
		
		//4방향으로 하산
		for (int i = 0; i < 4; i++) {
			int x = num[0];
			int y = num[1];
			int xx = x + dx[i];
			int yy = y + dy[i];
			//범위 안에 있는지 확인
			if (xx >= 0 && yy >= 0 && xx < n && yy < n) {
				//방문하지 않았고
				if (!visited[xx][yy]) {
					//그전 봉우리보다 갈 봉우리의 높이가 낮아야함
					if (board[x][y] > board[xx][yy]) {
						DFS(new int[] { xx, yy }, length+1, flag);
						//만약 한번도 지형을 깎지 않았다면 flag 를 true로 바꾸고 깎음
					} else if (!flag) {
						//갈 봉우리 - k 깊이가 이전 봉우리보다 낮으면
						if (board[x][y] > board[xx][yy] - k) {
							//갈 봉우리의 높이를 저장했다가
							int before = board[xx][yy];
							//이전 봉우리보다 1만 낮게 지형을 깎고
							board[xx][yy] = board[x][y] - 1;
							//dfs를 true로 진행했다가
							DFS(new int[] { xx, yy }, length+1, true);
							//dfs에서 나오면 다시 봉우리를 원상복구해줌
							board[xx][yy] = before;
						}
					}
				}
			}
		}
		visited[num[0]][num[1]] = false;
	}

	
}
