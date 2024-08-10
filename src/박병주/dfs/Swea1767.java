package dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Swea1767 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int T;
    static int result, maxCore;
    static int[][] dir = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {
        init();
        start();
    }

    static void init() throws IOException {
        T = Integer.parseInt(br.readLine());
    }

    static void start() throws IOException {
        for (int t = 1; t <= T; t++) {
            int n = Integer.parseInt(br.readLine());
            int map[][] = new int[n][n];

            List<Process> processes = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    int temp = Integer.parseInt(st.nextToken());
                    map[i][j] = temp;
                    // 프로세스의 위치 개수 체크
                    if (temp == 1) {
                        // 프로세스 위치 저장
                        if (i == 0 || j == 0 || i == n - 1 || j == n - 1) {
                        } else {
                            processes.add(new Process(i, j));

                        }
                    }
                }
            }

            // dfs(프로세스위치, 프로세스 시작 위치, 초기 맵, 누적합 값)
            result = Integer.MAX_VALUE;
            maxCore = 0;
            // dfs(processes, 0, map, 0);
            dfs2(processes, 0, map, 0, 0, 0);
            System.out.println("#" + t + " " + result + ", cor : " + maxCore);

        }
    }

    static void dfs2(List<Process> processes, int n, int[][] map, int sum, int cores, int depth) {
        // 모든 프로세스를 체크 시 탈출
        if (n == processes.size()) {

            if (maxCore < cores) {
                maxCore = cores;
                result = sum;
            } else if (maxCore == cores) {
                result = Math.min(result, sum);
            }

            // if (sum == 12 && cores == 5) {
            //     System.out.println("------------");
            //     for (int a = 0; a < map.length; a++) {
            //         for (int b = 0; b < map.length; b++) {
            //             System.out.print(map[a][b] + " ");
            //         }
            //         System.out.println();
            //     }
            //     System.out.printf("탈출 결과 : %d, core : %d \n", sum, cores);
            //     System.out.println(processes.size() - depth + cores);
            // }
            return;
        }

        int row = processes.get(n).x;
        int col = processes.get(n).y;
        for (int i = 0; i < 4; i++) {

            int nr = row;
            int nc = col;
            int tempSum = sum;
            int tempCore = cores;
            boolean canDraw = true;
            // 지도 2차원 배열 깊은 복사
            int[][] temp = new int[map.length][map.length];
            for (int a = 0; a < temp.length; a++) {
                for (int b = 0; b < temp.length; b++) {
                    temp[a][b] = map[a][b];
                }
            }
            while (nr >= 0 && nc >= 0 && nr < map.length && nc < map.length) {
                nr = nr + dir[i][0];
                nc = nc + dir[i][1];

                if (nr >= 0 && nc >= 0 && nr < map.length && nc < map.length) {
                    if (temp[nr][nc] == 2 || temp[nr][nc] == 1) {

                        canDraw = false;

                    }
                    ;

                    if (temp[nr][nc] == 0) {
                        temp[nr][nc] = 2;
                        tempSum++;

                    }
                }
            }
            // 남은 코어와 현재 코어의 합이 max코어보다 작으면 x
            if(processes.size() - depth + cores < maxCore) return;
            if (canDraw) {
                dfs2(processes, n + 1, temp, tempSum, tempCore + 1, depth+1);
            } else {
                dfs2(processes, n + 1, map, sum, tempCore, depth+1);
            }
        }
    }

    /*
     * 
     * 1
     * 7
     * 0 0 1 0 0 0 0
     * 0 0 1 0 0 0 0
     * 0 0 0 0 0 1 0
     * 0 0 0 0 0 0 0
     * 1 1 0 1 0 0 0
     * 0 1 0 0 0 0 0
     * 0 0 0 0 0 0 0
     * 
     */
}

class Process {
    int x, y;

    Process(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
