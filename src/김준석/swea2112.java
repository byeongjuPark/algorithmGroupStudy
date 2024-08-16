package swea2112;

import java.util.*;

public class Solution {
	
	static int[][] film;
	static int depth; // 필름의 두께(셀의 개수)
	static int width; // 필름의 가로 크기(바의 개수)
	static int k; // 테스트 합격 기준(각 위치에 k 개의 연속된 특성이 있는지)
	static final int A = 0, B = 1; // 특성 A는 0, B는 1
	static int min; // 테스트를 통과하는 약품 투입 횟수의 최솟값
	static boolean[] visited;
	
	// 필름이 테스트를 통과하는 최소 약물 투입 횟수를 뱉 
	static void findMin(int y, int cnt) {
		
		// 탈출 조건
		if(testFilm()) {
			min = Math.min(min, cnt);
			return;
		}
		
		// 백트래킹 : 이미 최소보다 투입 횟수가 많아지면
		if(cnt > min) return;
		
		// 필름의 끝에 도달한 경우 return
		if(y == depth) return;
		
		// 테스트를 통과하지 못함
		if(!visited[y]) {
			visited[y] = true;
			// 약품을 투입하지 않음
			findMin(y+1, cnt);
			// 약품 투입 전 현재 필름 상태 저장
			int[] originalLayer = new int[width];
			for(int x = 0; x < width; x++) originalLayer[x] = film[y][x];
			// A 약품 투입 
			for(int x = 0 ; x < width; x++) film[y][x] = A;
			findMin(y+1, cnt+1);
			// B 약품 투입
			for(int x = 0 ; x < width; x++) film[y][x] = B;
			findMin(y+1, cnt+1);
			// 초기화
			for(int x = 0 ; x < width; x++) film[y][x] = originalLayer[x];
			visited[y] = false;
		}
	}
	
	// 현재 상태의 필름이 테스트를 통과하는지 체크
	static boolean testFilm() {
		
		for(int x = 0; x < width; x++) {
			if(!testPosition(x)) {
				return false;
			}
		}
		
		return true;
	}
	
	// 현재 상태의 필름에서 해당 위치가 테스트를 통과하는지 체크
	static boolean testPosition(int x) {
		
		if(k == 1) return true;
		
		int prevType = A; 
		int seqCnt = 0; // 연속된 특성 카운트
		
		// 해당 위치에 대하여
		for(int y = 0; y < depth; y++) {
			int curType = film[y][x]; 
			// 이전 셀과 같은 특성
			if(curType == prevType) {
				seqCnt++;
				// 연속 3개 특성 달성
				if(seqCnt >= k) {
					return true;
				}
			}
			// 다른 특성
			if(curType != prevType) {
				seqCnt = 1;
				prevType = curType;
			}
		}
		
		// 여기까지 왔으면 연속 3개 특성이 없음
		return false;
	}
	
//	// 약품을 투입한 이후로 필름 갱신
//	static int[][] updateFilm(int[][] film, int type, int target) {
//		
//		// 필름 복사
//		int[][] newFilm = new int[depth][width];
//		for(int y = 0; y < depth; y++) {
//			for(int x = 0; x < width; x++) {
//				newFilm[y][x] = film[y][x];
//			}
//		}
//		
//		// 갱신
//		for(int x = 0; x < width; x++) {
//			newFilm[target][x] = type;
//		}
//		
//		return newFilm;
//	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			depth = sc.nextInt();
			width = sc.nextInt();
			k = sc.nextInt();
			
			film = new int[depth][width];
			for(int y = 0; y < depth; y++) {
				for(int x = 0; x < width; x++) {
					film[y][x] = sc.nextInt();
				}
			}
			
			min = Integer.MAX_VALUE;
			visited = new boolean[depth];
			findMin(0, 0);
			System.out.println("#" + tc + " " + min); 
		}
	}

}
