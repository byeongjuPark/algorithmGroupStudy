package BJ_NM;
import java.io.*;
import java.util.*;

public class Main_BJ_15665 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	static int N,M;
	static int[] narr;
	static int[] arr;
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		narr = new int[N];
		for (int i = 0; i < N; i++) {
			narr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(narr);
		
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
		
		int prev = -1;
		for (int i = 0; i < N; i++) {
			if(prev != narr[i]) {
				arr[idx] = narr[i];
				perm(idx+1);
				prev = narr[i];				
			}
		}
		
	}

}
