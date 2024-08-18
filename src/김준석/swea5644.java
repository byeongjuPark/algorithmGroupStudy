package swea5644;

import java.io.*;
import java.util.*;

class Charger {
	
	int num; // 충전기 식별 번호
	int y, x; // 충전기의 가운데 좌표
	int c; // 충전 범위
	int p; // 충전기의 처리량
	boolean[][] range; // 충전기의 범위 자표
	int userCnt; // 해당 충전기의 사용자 수
	static int[] dy = {-1, 1, 0, 0};
	static int[] dx = {0, 0, -1, 1};
	
	void inputRange(int y, int x, int cnt) {
	    //  현재 cnt가 충전 범위를 넘었으면 종료
	    if (cnt > this.c) {
	        return;
	    }
	    
	    // 해당 좌표가 유효한 범위 내에 있는지 확인
	    if (y >= 1 && x >= 1 && y <= 10 && x <= 10) {
	        range[y][x] = true; // 좌표 갱신
	    }
	    
	    // 상하좌우로 확장하여 다음 좌표 갱신
	    for (int i = 0; i < 4; i++) {
	        int ny = y + dy[i];
	        int nx = x + dx[i];
	        // 재귀 호출
	        inputRange(ny, nx, cnt + 1);
	    }
	}

	public Charger(int num, int y, int x, int c, int p) {
		this.num = num;
		this.y = y;
		this.x = x;
		this.c = c;
		this.p = p;
		this.userCnt = 0;
		this.range = new boolean[11][11];
		// 충전기 범위에 해당하는 좌표 입력
		inputRange(y, x, 0);
	}
}

public class Solution {
	
	static int m; // 총 이동 시간
	static int a; // 충전기의 개수 
	static int[] A; // A의 좌표
	static int[] B; // B의 좌표
	static int[] moveA; // A의 이동 정보
	static int[] moveB; // B의 이동 정보
	static ArrayList<Charger> chargers; // 충전기들의 좌표
	static final int NONE = 0, UP = 1, RIGHT = 2, DOWN = 3, LEFT = 4;
	static final int[][] delta = {{0, 0}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	static int max; // 충전량의 최대값
	
	static void findMax() {
		
		// 초기 상태
		max += charge();
		
		// 이후 m초 동안
		for(int time = 0; time < m; time++) {
			A[0] += delta[moveA[time]][0];
			A[1] += delta[moveA[time]][1];
			B[0] += delta[moveB[time]][0];
			B[1] += delta[moveB[time]][1];
			max += charge();
		}
	}
	
	static int charge() {
	    int maxCharge = 0;

	    ArrayList<Charger> chargerA = new ArrayList<>(); // A가 이용 가능한 충전기
	    ArrayList<Charger> chargerB = new ArrayList<>(); // B가 이용 가능한 충전기

	    // A와 B의 좌표에 충전기가 있는지 확인
	    for (int i = 0; i < a; i++) {
	        Charger charger = chargers.get(i);
	        if (charger.range[A[0]][A[1]]) {
	            chargerA.add(charger);
	        }
	        if (charger.range[B[0]][B[1]]) {
	            chargerB.add(charger);
	        }
	    }

	    // A와 B 모두 이용 가능한 충전기가 없을 경우
	    if (chargerA.isEmpty() && chargerB.isEmpty()) {
	        return 0;
	    }

	    // 가능한 최대 충전량을 계산
	    for (Charger aCharger : chargerA) {
	        for (Charger bCharger : chargerB) {
	            int chargeA = aCharger.p;
	            int chargeB = bCharger.p;

	            if (aCharger.num == bCharger.num) {
	                // A와 B가 동일한 충전기를 사용하면, 반씩 나눠가진다
	                maxCharge = Math.max(maxCharge, chargeA);
	            } else {
	                // 서로 다른 충전기를 사용하면 각자의 충전량을 더한다
	                maxCharge = Math.max(maxCharge, chargeA + chargeB);
	            }
	        }
	    }

	    // A 또는 B만 가능한 경우 처리
	    if (chargerA.isEmpty()) {
	        for (Charger bCharger : chargerB) {
	            maxCharge = Math.max(maxCharge, bCharger.p);
	        }
	    } else if (chargerB.isEmpty()) {
	        for (Charger aCharger : chargerA) {
	            maxCharge = Math.max(maxCharge, aCharger.p);
	        }
	    }

	    return maxCharge;
	}


	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			m = Integer.parseInt(st.nextToken());
			a = Integer.parseInt(st.nextToken());
			A = new int[] {1, 1};  // (y, x)로 저장
			B = new int[] {10, 10};  // (y, x)로 저장
			
			// A의 이동 정보 입력
			st = new StringTokenizer(br.readLine());
			moveA = new int[m];
			for(int i = 0; i < m; i++) {
				moveA[i] = Integer.parseInt(st.nextToken());
			}
			
			// B의 이동 정보 입력
			st = new StringTokenizer(br.readLine());
			moveB = new int[m];
			for(int i = 0; i < m; i++) {
				moveB[i] = Integer.parseInt(st.nextToken());
			}
			
			// 충전기 정보 입력
			chargers = new ArrayList<>();
			for(int i = 0; i < a; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());
				chargers.add(new Charger(i, y, x, c, p));
			}
			
			// 최대 충전량 구하기
			max = 0; 
			findMax();
			
			System.out.println("#" + tc + " " + max);
		}
	}
}
