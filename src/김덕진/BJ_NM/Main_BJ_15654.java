package BJ_NM;
import java.io.*;
import java.util.*;

public class Main_BJ_15654 {
	static BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();
	
	static int N,M;
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
		Arrays.sort(narr);
		
		check = new boolean[N];
		arr = new int[M];
		combi(0);
		System.out.println(sb);
		
	}

	private static void combi(int idx) {
		if(idx == M) {
			for (int i = 0; i < M; i++) {
				sb.append(arr[i] + " ");
			}
			sb.append("\n");
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if(!check[i]) {
				check[i] = true;
				arr[idx] = narr[i];
				combi(idx+1);
				check[i] = false;
			}
		}
		
	}

}
