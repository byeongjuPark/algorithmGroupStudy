package BJ_NM;

import java.io.*;
import java.util.*;

public class Main_BJ_15666 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static StringBuilder sbtmp = new StringBuilder();
	
	static int N,M;
	static int[] narr;
	static int[] arr;
	static List<String> before = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		narr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			narr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(narr);
		
		arr = new int[M];
		comb(0, 0);
		System.out.println(sb);
	}

	private static void comb(int idx, int compare) {
		if(idx == M) {
			for (int i = 0; i < M; i++) {
				sb.append(arr[i] + " ");
			}
			sb.append("\n");
			return;
		}
		
		int prev = -1;
		for (int i = 0; i < N; i++) {
			if (prev != narr[i]) {
				arr[idx] = narr[i];
				comb(idx+1, i);
				prev = narr[i];
			}
		}
		
	}


}
