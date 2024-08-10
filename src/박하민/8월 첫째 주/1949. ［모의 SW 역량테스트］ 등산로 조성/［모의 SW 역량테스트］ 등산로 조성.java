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

            for (int y = 0; y<N; y++) {
                for (int x= 0; x<N; x++) {
                    if(map[y][x] == tmpMax) {
                        startingPoints.add(new Point(x, y, 0));
                    }
                }
            }


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


    static void makeWay(int x, int y,int k, int[][]map) {
        for(int i = 0; i<4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            
            if(nx<0 || ny<0|| nx>=map.length|| ny>=map.length) {
                continue;
            }
            int prev = map[y][x];

            // 작게 하는경우 K가 충분한지 확인 else->continue
            if(map[ny][nx] == map[y][x]) {
                //xy 작게
                map[y][x] -=1;
                int result = BFS(map);
                if(longestWay < result) {
                    longestWay = result;
                }
                //longest 갱신
                
                //본인이 더 큰경우
            }else if (map[ny][nx] < map[y][x]) {
                //그대로
                int result = BFS(map);
                if(longestWay < result) {
                    longestWay = result;
                }
                //xy 작게
                map[y][x] = prev;
                //본인이 더 크고, 본인에서 k를 뺐을때 옆보다 작아진다면 
                if(map[y][x] -k < map[ny][nx]) {
                    map[y][x] = map[ny][nx]-1;
                    int result2 = BFS(map);
                    if(longestWay < result2) {
                        longestWay = result2;
                    }
                }
                

            }else if (map[ny][nx] > map[y][x]) {
                //그대로
                int result = BFS(map);
                if(longestWay < result) {
                    longestWay = result;
                }
            }
            //롤백

            map[y][x] = prev;
        }
    }


    //최대길이 등산로 반환
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
}
    
/*

문제 조건 잘못 이해함
무조건 가장 높은 봉우리부터 시작

 */