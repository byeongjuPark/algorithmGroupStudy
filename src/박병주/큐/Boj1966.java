package 큐;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Boj1966
 */
public class Boj1966 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int test_case = 0; test_case < T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            StringTokenizer st2 = new StringTokenizer(br.readLine(), " ");
            Queue<Integer[]> queue = new LinkedList<>();
            int result = 0;
            int count = 0;
            for (int i = 0; i < n; i++) {
                int temp = Integer.parseInt(st2.nextToken());

                queue.offer(new Integer[] { temp, i });
            }

            while (!queue.isEmpty()) {

                boolean hasPri = false;
                Integer[] temp = queue.poll();

                // 큰 거 있는지 검사
                for (Integer[] a : queue) {
                    if (a[0] > temp[0]) {
                        // 내부에 큰게 있으면
                        hasPri = true;
                    }
                }
                // 큰게 있으면
                if (hasPri) {

                    queue.offer(temp);
                } else {
                    count++;
                    if (temp[1] == m) {
                        result = count;
                        sb.append(result).append("\n");
                        break;
                    }
                }

            }
        }

        System.out.println(sb.toString());
    }
}
