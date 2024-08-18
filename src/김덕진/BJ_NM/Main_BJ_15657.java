package BJ_NM;
import java.util.*;
import java.io.*;
public class Main_BJ_15657 {
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
		
		for (int i = 0; i < N; i++) {
			if(compare <= i) {
				arr[idx] = narr[i];
				comb(idx+1, i);				
			}
		}
		
	}
	
	

}
