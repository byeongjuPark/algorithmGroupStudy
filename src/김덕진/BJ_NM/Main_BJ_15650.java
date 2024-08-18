package BJ_NM;

import java.io.*;
import java.util.*;

public class Main_BJ_15650 {
	static int N;
	static int M;
	static int[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[M];
		
		comb(0,0);
	}
	

	private static void comb(int idx, int cnt) {
		if(cnt == M) {
			for (int i = 0; i < M; i++) {
				System.out.printf("%d ", arr[i]);
			}
			System.out.println();
			return;
		}

		for (int i = idx; i < N; i++) {
			arr[cnt] = i+1;
			comb(i+1, cnt+1);
		}
	}
	
	
	
	

}
