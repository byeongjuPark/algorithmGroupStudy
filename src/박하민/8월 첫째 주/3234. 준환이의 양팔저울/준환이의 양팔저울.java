import java.util.*;
import java.io.*;


public class Solution {
    static int count;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int tc = 0; tc<T; tc++) {
            int chuSum= 0;
            count = 0;
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] chu = new int[N];
            boolean[] visited = new boolean[N];

            for(int i = 0; i<N; i++) {
                int tmp = Integer.parseInt(st.nextToken());
                chu[i] = tmp;
                chuSum += tmp;
            }

            DFS(visited, 0, 0, chu, N, chuSum);
            sb.append("#" + (tc+1) + " " + count + "\n");

        }
        System.out.println(sb.toString());
    }

    static void DFS(boolean[] visited, int left, int right, int[] chu, int N, int chuSum) {
        if(left + right == chuSum) {
            count++;
            return;
        }

        for(int i = 0; i<N; i++) {
            if(visited[i] == false) {

                visited[i] = true;
                DFS(visited, left + chu[i], right, chu, N, chuSum);
                visited[i] = false;

                if(right + chu[i] <=left) {
                    visited[i] = true;
                    DFS(visited, left, right + chu[i], chu, N, chuSum);
                    visited[i] = false;
                }
            }
        }
    }
}