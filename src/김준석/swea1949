import java.util.*;

public class Solution {

	static int[][] map;
    static boolean[][] visited;
    static int n, k, maxLen;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static int[][] delta = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        
    	Scanner sc = new Scanner(System.in);
        
        int T = sc.nextInt();
        for(int t = 0; t < T; t++) {
        	
        	n = sc.nextInt();
            k = sc.nextInt();
            map = new int[n][n];
            visited = new boolean[n][n];

            int maxHeight = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    map[i][j] = sc.nextInt();
                    maxHeight = Math.max(maxHeight, map[i][j]);
                }
            }
            
            maxLen = 0;
            
            // 모든 좌표를 탐색하며 정상에서 출발
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == maxHeight) {
                    	// 각 정상에 대해 dfs
                        dfs(i, j, 1, false);
                    }
                }
            }

            System.out.println("#" + (t+1) + " " + maxLen);
        }
    }

    static void dfs(int x, int y, int length, boolean flag) {
    	
        maxLen = Math.max(maxLen, length);
        visited[x][y] = true;

        for (int i = 0; i < 4; i++) {
            int nx = x + delta[i][0];
            int ny = y + delta[i][1];
            
            // 맵을 벗어나거나 방문한 좌표가 아니면
            if (nx >= 0 && ny >= 0 && nx < n && ny < n && !visited[nx][ny]) {
                if (map[nx][ny] < map[x][y]) {
                    dfs(nx, ny, length + 1, flag);
                } 
                // 공사 시도
                else if (!flag) {
                    // 공사를 하지 않은 상태라면, 1부터 k까지의 높이 감소 시도
                    for (int depth = 1; depth <= k; depth++) {
                        if (map[nx][ny] - depth < map[x][y]) {
                            int originalHeight = map[nx][ny];
                            map[nx][ny] -= depth;  // 높이 낮추기
                            // 낮춘 높이로 연장이 가능하면 추가
                            dfs(nx, ny, length + 1, true);
                            map[nx][ny] = originalHeight;  // 원래 높이로 복구
                        }
                    }
                }
            }
        }
        // 방문 초기화
        visited[x][y] = false; 
    }
}
