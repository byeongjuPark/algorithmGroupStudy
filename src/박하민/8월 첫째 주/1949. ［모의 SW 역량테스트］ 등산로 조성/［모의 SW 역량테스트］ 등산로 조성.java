import java.util.*;
import java.io.*;

class Point {
    int x;
    int y;
    int d;
    Point(int x, int y, int d) {
        this.x =x;
        this.y = y;
        this.d = d;
    }
}


class Solution
{   
    static int[] dx = {0,0,-1,1};
    static int[] dy = {1,-1,0,0};
    static StringBuilder sb;
    static int longestWay;
    static Queue<Point> queue;
    static ArrayList<Point> startingPoints;

    //dfs용 최대 길이
    static int longestWayForDFS;

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
        sb = new StringBuilder();
        

		for(int test_case = 1; test_case <= T; test_case++){  
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][N];
            startingPoints = new ArrayList<>();

            //가장 높은 봉우리 표시위한 변수
            int tmpMax= 0;
            for (int y = 0; y<N; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x= 0; x<N; x++) {
                    int e =Integer.parseInt(st.nextToken());
                    map[y][x] = e;

                    if(e > tmpMax) {
                        tmpMax = e;
                    }
                    
                }
            }

            //가장 높은 봉우리들을 스타팅 포인트로 리스트에 저장
            for (int y = 0; y<N; y++) {
                for (int x= 0; x<N; x++) {
                    if(map[y][x] == tmpMax) {
                        startingPoints.add(new Point(x, y, 0));
                    }
                }
            }

            //가장긴 등산로 구하기
            longestWay = 0;
            for(int i = 0; i<N; i++) {
                for(int j = 0; j<N; j++) {
                    makeWay(i, j, K, map);
                }
            }
            
            
            sb.append("#"+test_case+" "+longestWay + "\n");
		}
        System.out.println(sb.toString());
	}

    /* 등산로 제작 함수
     * 등산로를 제작하기위해 K만큼 깎을 수 있다.
     * 이때에 한 점 x,y에서 높이를 변경하는 것은 상하좌우에서 갈 수 있도록 -1만큼 깎는 것을 의미한다.
     * 따라서 nx,ny와 비교하여 높이를 변경하거나 변경하지 않고 최단거리를 구한다.
     */
    static void makeWay(int x, int y,int k, int[][]map) {
        for(int i = 0; i<4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            if(nx<0 || ny<0|| nx>=map.length|| ny>=map.length) {
                continue;
            }
            int prev = map[y][x];

            //비교대상과 같다면
            if(map[ny][nx] == map[y][x]) {
                //K는 최소 1이므로 그냥 1깎기
                map[y][x] -=1;
                int result = forDFS(map);
                
                //longest 갱신
                if(longestWay < result) {
                    longestWay = result;
                }
               
            //비교대상보다 본인이 더 큰경우
            }else if (map[ny][nx] < map[y][x]) {
                //먼저 그대로 실행
                int result = forDFS(map);

                //longest 갱신
                if(longestWay < result) {
                    longestWay = result;
                }

                //롤백
                map[y][x] = prev;

                //본인이 더 크고, 본인에서 k를 뺐을때 옆보다 작아진다면 
                if(map[y][x] -k < map[ny][nx]) {

                    //비교대상에서 올수 있게만 해주면 되기때문에 -1
                    map[y][x] = map[ny][nx]-1;
                    int result2 = forDFS(map);

                    //longest 갱신
                    if(longestWay < result2) {
                        longestWay = result2;
                    }
                }
                
            //비교대상보다 본인이 더 작은경우
            }else if (map[ny][nx] > map[y][x]) {
                //그대로
                int result = forDFS(map);

                //longest 갱신
                if(longestWay < result) {
                    longestWay = result;
                }
            }

            //롤백
            map[y][x] = prev;
        }
    }


    //최대길이 등산로 반환
    //사실 최대길이는 BFS로 구하면 안됨, BFS는 최단거리를 보장하지만 최장거리는 알 수 없음??
    //다만, 이 문제의 경우 뒤로 돌아갈 수 없기 때문에 가능하다.

    //라고 생각했는데 dfs도 구현해보니 그냥 비슷함, 그냥 이문제는 visited없는 문제인듯 하다
    static int BFS(int[][]map) {
        int N = map.length;
        int looong = 0;

        for (Point start : startingPoints) {
            queue = new LinkedList<>();
                queue.add(start);
                // boolean[][] visited = new boolean[N][N];
                
                while(!(queue.isEmpty())) {
                    Point p = queue.poll();
                    int x = p.x;
                    int y = p.y;
                    int d = p.d;

                    if(looong < d+1) {
                        looong = d+1;
                    }

                    for(int k = 0; k<4; k++) {
                        int nx = x + dx[k];
                        int ny = y + dy[k];

                        if(nx<0 || ny<0|| nx>=N|| ny>=N) {
                            continue;
                        }

                        if((map[ny][nx] < map[y][x]) ) {
                            // visited[ny][nx] = true;
                            queue.add(new Point(nx, ny, d+1));
                        }
                        

                    }
                }
            
        }
        return looong;
    }

    static int forDFS(int[][]map) {
        int N = map.length;
        longestWayForDFS = 0;
        for (Point start : startingPoints) {
            DFS(start.x, start.y, N, 1, map);
        }
        return longestWayForDFS;
    }
    static void DFS(int x, int y, int N, int d, int[][]map) {
        
        for(int i = 0; i<4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx<0 || ny<0|| nx>=N|| ny>=N) {
                continue;
            }
            if((map[ny][nx] < map[y][x]) ) {
                DFS(nx, ny, N, d+1, map);
            } 
        }

        if(longestWayForDFS < d) {
            longestWayForDFS = d;
        }
    }
}
    



/*

문제 조건 잘못 이해함
무조건 가장 높은 봉우리부터 시작

 */