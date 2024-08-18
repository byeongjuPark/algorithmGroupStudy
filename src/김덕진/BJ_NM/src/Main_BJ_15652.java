import java.io.*;
import java.util.*;

public class Main_BJ_15652 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	static int N,M;
	static int[] arr;
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[M];
		perm(0, 1);
		System.out.println(sb);
	}

	private static void perm(int idx, int compare) {
		if (idx == M) {
			for (int i = 0; i < M; i++) {
				sb.append(arr[i]+" ");
			}
			sb.append("\n");
			return;
		}
		
		for (int i = 1; i <= N; i++) {
			if(compare <= i) {
				arr[idx] = i;
				perm(idx+1, i);				
			}
		}
		
	}

}
