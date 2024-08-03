package 시뮬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Boj14503
 */
public class Boj14503 {

    static int n, m;
    static int r, c;
    static int dir;
    static int[][] table;
    static StringTokenizer st;
    // 0인 경우 북쪽, 1인 경우 동쪽, 2인 경우 남쪽, 3인 경우 서쪽
    // 북0 -> 서1 -> 남2 -> 동3 -> 북1
    //
    static int[][] delta = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
    static int result = 0;

    public static void main(String[] args) throws IOException {
        init();
        start();
        System.out.println(result);
        // for (int i = 0; i < n; i++) {
        // for (int j = 0; j < m; j++) {
        // System.out.print(table[i][j] + " ");
        // }
        // System.out.println();
        // }
    }

    static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        dir = Integer.parseInt(st.nextToken());
        table = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                table[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    static void start() {
        boolean flag = true;
        while (flag) {
            // 현재 칸이 0이면 청소
            if (table[r][c] == 0) {
                table[r][c] = 2;
                result++;

            }
            // 4방 탐색으로 청소되지 않은 칸 찾기
            boolean isTrash = false;
            for (int i = 0; i < 4; i++) {
                int nr = r + delta[i][0];
                int nc = c + delta[i][1];
                if (nr >= 0 && nc >= 0 && nr < n && nc < m) {
                    if (table[nr][nc] == 0) {
                        // --
                        r = nr;
                        c = nc;
                        // --
                        isTrash = true;
                        break;
                    }
                }
            }
            // 탐색 후 쓰레기가 여부?
            if (isTrash) {

                // 청소 되지 않은 빈 칸이 있는 경우
                dir = (dir + 1) % 4; // 90도 회전
                int nr = r + delta[dir][0];
                int nc = c + delta[dir][1];

                if (nr >= 0 && nc >= 0 && nr < n && nc < m) {
                    if (table[nr][nc] == 0) {
                        // 바라보는 방향이 청소되지 않은 빈칸이면 한칸 앞으로
                        r = nr;
                        c = nc;
                    }
                }
            } else {

                // 후진 가능 시 1칸 후진
                int nrb = r + (delta[dir][0] * -1);
                int ncb = c + (delta[dir][1] * -1);
                if (nrb >= 0 && ncb >= 0 && nrb < n && ncb < m && table[nrb][ncb] != 1) {
                    // 1칸 후진
                    r = nrb;
                    c = ncb;
                } else {
                    // 작동 종료
                    break;
                }

            }
        }
    }
}