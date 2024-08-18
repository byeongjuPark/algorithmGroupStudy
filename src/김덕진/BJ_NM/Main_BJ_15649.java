package BJ_NM;

import java.io.*;
import java.util.*;

public class Main_BJ_15649 {
	static int N, M;
	static boolean check[];
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static int[] arr;
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		check = new boolean[N];
		arr = new int[M];
		perm(0);
		System.out.println(sb);
		
	}

	private static void perm(int cnt) {
		if(cnt == M) {
			for (int i : arr) {
				sb.append(i).append(" ");
			}
			sb.append("\n");
			return;
		}
		
		
		for (int i = 0; i < N; i++) {
			if(!check[i]) {
				check[i] = true;
				arr[cnt] = i+1;
				perm(cnt+1);
				check[i] = false;
			}
			
		}
		
	}

}
