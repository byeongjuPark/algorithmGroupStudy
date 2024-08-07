import java.util.*;
import java.io.*;

class Core {
    int x;
    int y;
    Core(int x, int y) {
        this.x =x;
        this.y = y;
    }
}
// class Result {
//     int cores;
//     int sum;
//     Result(int cores, int sum) {
//         this.cores = cores;
//         this.sum = sum;
//     }
// }

class Solution
{   
    static int cores;
    static int summ;
    // static ArrayList<Result> resultList;
    static int[] dx = {0,0,-1,1};
    static int[] dy = {1,-1,0,0};
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

		for(int test_case = 1; test_case <= T; test_case++){
            // 정답을 만들기 위해 값을 갱신하는 것으로 해결
            cores = -1;
            summ = Integer.MAX_VALUE;

            int N = Integer.parseInt(br.readLine().trim());
            int[][] map = new int[N][N];
            
            // 각 코어의 위치를 저장하는 리스트
            ArrayList<Core> coreList = new ArrayList<>();

            // map에 코어의 위치를 1로 표시
            for (int y = 0; y<N; y++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int x= 0; x<N; x++) {
                    int tmp = Integer.parseInt(st.nextToken());
                    if(tmp == 1) {
                        // 모서리에 있는 코어는 재귀를 위한 코어리스트에 넣지 않음
                        if(!(x == 0 || y == 0 || x == N -1 || y == N -1)) { 
                            coreList.add(new Core(x, y));
                        }
                        map[y][x] = 1;
                    }
                }
            }

            // 재귀 함수
            coreCheck(0, map, coreList, 0,0);

            // 시간 초과로 제거한 sort
            // resultList  = new ArrayList<>();
            // Collections.sort(resultList,(o1, o2) -> {
            //     int cores1 = ((Result)o1).cores; 
            //     int cores2 = ((Result)o2).cores; 
            //     int sum1 = ((Result)o1).sum; 
            //     int sum2 = ((Result)o2).sum;
            //     if(cores1 != cores2) {
            //         return Integer.compare(cores2, cores1);
            //     }else {
            //         return Integer.compare(sum1,sum2);
            //     }
            // } );
            
            //결과를 한번에 출력하기위해 StringBuilder사용
            sb.append("#"+test_case+" "+summ + "\n");
		}
        System.out.println(sb.toString());
	}

    //메인 재귀함수
    static void coreCheck(int depth, int[][]visited, ArrayList<Core> coreList, int sum, int connected) {
        
        // 기저 사례 (Base Case)
        // 탈출조건으로 depth는 코어 갯수를 나타내며 모든 코어에 대해 확인 했는지 나타낸다
        if (depth == coreList.size()) {
            // 연결된 코어수가 가장 많으며, 연결 선이 가장 적은 경우 결과값을 갱신한다
            if (connected > cores || (connected == cores && sum < summ)) {
                cores = connected;
                summ = sum;
            }
            return;
        }

        // 코어리스트에서 x,y값을 가져온다
        int x = coreList.get(depth).x;
        int y = coreList.get(depth).y;

        // 시간 초과로 제거한 재귀
        // 각 모서리에 존제하는 코어는 애초에 연결되어 있어 재귀에 사용하지 않아도 되며
        // 전선을 연결하지 않으므로 최종 답에 포함되지 않는다.
        // if(x == 0 || y == 0 || x == visited.length -1 || y == visited.length -1) {
        //     coreCheck(depth + 1, visited, coreList, sum, connected+1);
        // }

        // 재귀 사례 (Recursive Case)
        // 쪼갠 작은 사건은 하나의 코어에 대해 연결을 확인하며 연결 여부에 따라 다음 재귀로 connected+1을 결정한다
        boolean isCoreConnect = false;
        for(int i = 0; i<4; i++) {      
            isCoreConnect = false;
            int tmpSum = 0;
            // 코어의 선이 연결되지 않았을때 맵을 다시 원상복구하기위한 리스트
            ArrayList<Core> rollback = new ArrayList<>();
            int ny = y;
            int nx = x;
            while(true) {
                // 네 방향을 확인하며 코어로부터 맵의 끝까지 전선인 2를 맵에 추가한다
                ny = ny + dy[i];
                nx = nx + dx[i];
                // 정상적으로 전선이 끝까지 연결되었을 경우 flag인 isCoreConnect = true
                if(nx <0 || ny <0 || nx>=visited.length || ny>=visited.length) {
                    isCoreConnect = true;
                    break;
                }
                // 전선 연결중 실패할 경우 flag인 isCoreConnect = false
                if(visited[ny][nx] != 0) {
                    isCoreConnect = false;
                    break;
                }
                
                // 전선을 맵에 추가하고, 전선 개수를 기록하며, 원상복구하기위한 리스트를 만든다
                visited[ny][nx] = 2;
                tmpSum++;
                rollback.add(new Core(nx, ny));

            }
            
            // 전선이 끝까지 정상 연결된 경우 연결된코어 갯수인 connected에 +1하고 다음 코어로 넘어간다
            if(isCoreConnect) {
                coreCheck(depth + 1, visited, coreList, sum + tmpSum, connected+1);       
            }
            // 다음 재귀를 위해 맵을 원상복구한다
            for(Core c : rollback) {
                visited[c.y][c.x] = 0;
            }
        }
        
        // 코어가 연결되지 않아 connected에 +1하지 않고 다음 코어로 넘어간다
        coreCheck(depth + 1, visited, coreList, sum, connected);
    }
}