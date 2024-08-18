package SWEA;
import java.io.*;
import java.util.*;
public class Solution_SWEA_1953 {
	/*
	 * 1 : 상하좌우
	 * 2 : 상하
	 * 3 : 좌우
	 * 4 : 상우
	 * 5 : 하우
	 * 6 : 하좌
	 * 7 : 상좌
	 */
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int T, N, M, R, C, L;
	static int[][] map = new int[50][50];
	static boolean[][] visit;
	static int[] dx = {0,-1,1,0,0};
	static int[] dy = {0,0,0,-1,1};
	static Queue<int[]> q = new LinkedList<>();	//가능한 위치
	static int[][] tunnelConnect = {	//각 터널 번호 마다 연결 가능한 방향 설정
		// 1: 상, 2:하, 3:좌, 4:우
		{},		//0번 터널은 없음
		{1,2,3,4},
		{1,2},
		{3,4},
		{1,4},
		{2,4},
		{2,3},
		{1,3}
	};
	
	
	public static void main(String[] args) throws Exception {
		T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {			
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	//지하 터널 세로(행)
			M = Integer.parseInt(st.nextToken());	//지하 터널 세로(행)
			R = Integer.parseInt(st.nextToken());	//맨홀 뚜껑 위치(행)
			C = Integer.parseInt(st.nextToken());	//맨홀 뚜껑 위치(열)
			L = Integer.parseInt(st.nextToken());	//탈출 후 진행된 시간
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			visit = new boolean[N][M];	//방문한 곳 재탐색 하지 않기 위한 배열
			q.clear();
			q.offer(new int[] {R,C,1});	//처음 위치와 시간
			visit[R][C] = true;
			System.out.println("#" + tc+ " " + bfs());
		}
		

	}

	private static int bfs() {
		int cnt = 1;	//처음 위치에 대한 개수 카운트 1
		
		while(!q.isEmpty()) {	//중간에 계속 추가 되니 for문에 q.size()를 이용하는건 안됨. 그냥 bfs 한번으로 종료(더 깊어지는 과정 없이)
			int[] position = q.poll();	//가능한 이동 범위 하나
			int x = position[0];
			int y = position[1];
			int time = position[2];
			int tunnelType = map[x][y];	//현재 위치의 터널 번호
			
			if(time == L) continue;		//L시간 이후는 탐색 불필요.
			
			for (int i : tunnelConnect[tunnelType]) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= M || visit[nx][ny]) continue;	//범위를 벗어나거나 방문한 적 있는 경우
				
				int nextTunnel = map[nx][ny];
				
				if(isConnect(i, nextTunnel)) {
					visit[nx][ny] = true;
					q.offer(new int[] {nx,ny,time+1});
					cnt++;	//q에 삽입 되었으니 개수 + 1
				}
			}
			
		}
		
		return cnt;

	}
	
	//현재 위치의 터널에서 가고자 하는 방향이 다음 터널과 연결 되는지 확인
	private static boolean isConnect(int direction, int nextTunnel) {
		if(direction == 1) return Arrays.asList(1,2,5,6).contains(nextTunnel);
		if(direction == 2) return Arrays.asList(1,2,4,7).contains(nextTunnel);
		if(direction == 3) return Arrays.asList(1,3,4,5).contains(nextTunnel);
		if(direction == 4) return Arrays.asList(1,3,6,7).contains(nextTunnel);

		return false;
		
		
	}

}
