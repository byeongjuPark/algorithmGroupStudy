import java.util.*;

class Processor {
	
	int y;
	int x;
	
	public Processor(int y, int x) {
		this.y = y;
		this.x = x;
	}
}

public class swea1767 {
	
	static int n;
	static int minLen;
	static int maxCnt;
	
	static void findMinLen(ArrayList<Processor> processors, int[][] map, int len, int idx, int cnt) {
		
		// 탈출 조건
		if(idx == processors.size()) {
			if(cnt > maxCnt) {
				maxCnt = cnt;
				minLen = len;
			} else if(cnt == maxCnt) {
				minLen = Math.min(minLen, len);
			}
			
			return;
		}
		
		// 백트래킹
		if(processors.size() - idx + cnt < maxCnt) {
			return;
		}
		
		// DFS 진행
		Processor cur = processors.get(idx);
		// 각 방향에 대해 진행
		for(int d = 1; d <= 4; d++) {
			int[] direction = linkToPower(cur, map, d);
			if(direction[0] != -1) {
				if(direction[1] != 0) {
					int[][] newMap = updateMap(cur, map, direction[0], direction[1]);
					findMinLen(processors, newMap, len + direction[1], idx + 1, cnt + 1);  
				} else {
					findMinLen(processors, map, len, idx + 1, cnt + 1);
				}
			} else {
				findMinLen(processors, map, len, idx + 1, cnt);
			}
		}
		
		findMinLen(processors, map, len, idx + 1, cnt);
	}

	// 가능한 가장 짧은 연결 찾기 {방향, 길이}
	static int[] linkToPower(Processor processor, int[][] map, int direction) {
		
		int y = processor.y;
		int x = processor.x;
		int len = 0;
		
		// 상
		if(direction == 1) {
			if(y == 0) {
				len = 0;
			} else {
				for(int i = y-1; i >= 0; i--) {
					if(map[i][x] == 0) {
						len++;
					} else {
						direction = -1;
						break;
					}
				}
			}
		}
		// 하
		else if(direction == 2) {
			if(y == n-1) {
				len = 0;
			} else {
				for(int i = y+1; i < n; i++) {
					if(map[i][x] == 0) {
						len++;
					} else {
						direction = -1;
						break;
					}
				}
			}
		} 
		// 좌
		else if(direction == 3) {
			if(x == 0) {
				len = 0;
			} else {
				for(int i = x-1; i >= 0; i--) {
					if(map[y][i] == 0) {
						len++;
					} else {
						direction = -1;
						break;
					}
				}
			}
		// 우
		} else {
			if(x == n-1) {
				len = 0;
			} else {
				for(int i = x+1; i < n; i++) {
					if(map[y][i] == 0) {
						len++;	
					} else {
						direction = -1;
						break;
					}
				}
			}
		}
		
		return new int[] {direction, len};
	}
	
	static int[][] updateMap(Processor cur, int[][] map, int direction, int len) {
		
		int y = cur.y;
		int x = cur.x;
		
		int[][] newMap = new int[n][n];
		for(int i = 0; i < map.length; i++) {
			System.arraycopy(map[i], 0, newMap[i], 0, newMap[i].length);
		}
		
		if(direction == 1) {
			for(int j = 0; j < len; j++)  {
				newMap[y-(j+1)][x] = 2;
			}
		}
		else if(direction == 2) {
			for(int j = 0; j < len; j++)  {
				newMap[y+(j+1)][x] = 2;
			}
		}
		else if(direction == 3) {
			for(int j = 0; j < len; j++)  {
				newMap[y][x-(j+1)] = 2;
			}
		}
		else {
			for(int j = 0; j < len; j++)  {
				newMap[y][x+(j+1)] = 2;
			}
		}
		
		return newMap;
	}
	

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		int T = sc.nextInt();
		for(int i = 0; i < T; i++) {
			
			n = sc.nextInt();
			int[][] map = new int[n][n];
			ArrayList<Processor> processors = new ArrayList<>();
			
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < n; k++) {
					map[j][k] = sc.nextInt();
					if(map[j][k] == 1) {
						processors.add(new Processor(j, k));
					}
				}
			}
			
			minLen = Integer.MAX_VALUE;
			maxCnt = -1;
			findMinLen(processors, map, 0, 0, 0);
			System.out.println("#" + (i+1) + " " + minLen);
		}
	}

}
