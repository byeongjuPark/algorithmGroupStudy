import java.io.*;
import java.util.*;

public class Solution_SWEA_1949 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int T, N, K;
	static int[][] map;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,1,-1};
	static int maxLenght;
	
	public static void main(String[] args) throws Exception {
		T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	
			K = Integer.parseInt(st.nextToken());	//최대 공사 가능 깊이
			map = new int[N][N];
			visited = new boolean[N][N];
			int top = Integer.MIN_VALUE;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					top = Math.max(top, map[i][j]);
				}
			}
			
			maxLenght=Integer.MIN_VALUE;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if ( map[i][j] == top ) {
						//dfs 수행
						visited[i][j] = true;
						dfs(i, j, 1, false);
						visited[i][j] = false;
					}
				}
			}
			System.out.println("#"+tc+" "+maxLenght);
		}

	}
	
	//분기마다 공사를 진행 한 경우 / 하지 않은 경우 모두 탐색
	private static void dfs(int x, int y, int lenght, boolean check) {
		maxLenght = Math.max(maxLenght, lenght);
		
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(nx < 0 || ny < 0 || nx >= N || ny >= N || visited[nx][ny]) continue;	//범위를 벗어낫으므로 or 방문 했던 곳이므로 다음 인덱스
			
			if(map[x][y] > map[nx][ny]) {
				visited[nx][ny] = true;
				dfs(nx, ny, lenght+1, check);
				visited[nx][ny] = false;
			}
			
			else if(!check && map[x][y] > map[nx][ny] - K) {
				int tmp = map[nx][ny]; 
				map[nx][ny] = map[x][y] - 1;
				visited[nx][ny] = true;
				dfs(nx, ny, lenght+1, true);
				map[nx][ny] = tmp;
				visited[nx][ny] = false;
			}

		}
		
	}

}
