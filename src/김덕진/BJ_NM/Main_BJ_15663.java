package BJ_NM;

import java.io.*;
import java.util.*;

public class Main_BJ_15663 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, M;

    static int[] narr;
    static int[] arr;
    static boolean[] check;

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        narr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            narr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(narr); // 숫자들을 정렬해서 중복 방지에 사용

        check = new boolean[N];
        arr = new int[M];
        perm(0);
        System.out.println(sb);
    }

    private static void perm(int idx) {
        if (idx == M) {
            for (int i = 0; i < M; i++) {
                sb.append(arr[i]+" ");
            }
            sb.append("\n");
            return;
        }

        int prev = -1; // 중복 값을 건너뛰기 위한 변수
        for (int i = 0; i < N; i++) {
            if (!check[i] && narr[i] != prev) { // 중복 값 처리
                check[i] = true;
                arr[idx] = narr[i];
                perm(idx + 1);
                check[i] = false;
                prev = narr[i]; // 중복 방지
            }
        }
    }
}
