import java.io.*;
import java.util.*;

public class Solution_SWEA_3234 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int T,N;
	static int[] m = new int[9];
	static boolean check[];
	static int cnt;

	public static void main(String[] args) throws Exception {
		T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());	//무게 추 개수
//			m = new int[N];		//무게추 배열
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				m[i] = Integer.parseInt(st.nextToken()); 
			}
						
			//2개 짜리 순열(range는 계속 가져가고, 방문체크)
			check = new boolean[N];	//방문 여부 체크 배열
			
			
			cnt=0;
			scaling(0, 0, 0, N, m, check);	//무게를 달아줄 저울 인덱스, 저울에 달려 있는 왼쪽 무게값, 오른쪽 무게값.
			System.out.println("#" + tc + " " +cnt);
			
		}
		
	}

	private static void scaling(int idx, int left, int right, int N, int[] m, boolean[] check) {
		if(idx >= N) {	//저울이 완성되어서 넘어 온 경우 카운트 및 종료
			cnt++;
			return;
		}
		
		for (int i = 0; i < N; i++) {	//값 선정을 위한 for loop
			if(check[i]) continue;
			check[i] = true;
			int weight = m[i]; //선정된 무게 추의 무게
			
			scaling(idx + 1, left+weight, right, N, m, check);
			if(right+weight <= left) {	//조건 충족
				scaling(idx + 1, left, right+weight, N, m, check);
			}			
			check[i] = false;			
		}
		
	}
	
}
