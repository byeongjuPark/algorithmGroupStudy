import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Solution
 */

class Node {
    int row, col;

    Node(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class Swea1949 {

    static int T;
    static int N, K;
    static int count;
    static int[][] delta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {
        start();
    }

    static void start() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][N];
            int[][] road = new int[N][N];
            count = 0;

            int topHeight = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] > topHeight) {
                        topHeight = map[i][j];
                    }
                }
            }

            // 시작 가능 지점
            ArrayList<Node> startList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == topHeight) {
                        startList.add(new Node(i, j));
                    }
                }
            }

            // 큰 반복문 (시작 지점만큼 반복)
            for (Node node : startList) {
                dfs(node, 1, map, road, K);
            }
            //dfs(startList.get(2), 1, map, road, K);


            sb.append("#").append(t).append(" ").append(count).append("\n");
        }
        System.out.println(sb);
    }
    
    static void dfs(Node node, int cnt, int[][] map, int[][] road, int paramK) {

        if (count < cnt) {
            count = cnt;
            // if (cnt == 5) {
            //     System.out.printf("row : %d, col : %d \n", node.row, node.col);
            //     printing(road);
            //     printing(map);
            //     System.out.println();
            // }
        }

        // 현재 방문
        int row = node.row;
        int col = node.col;

        int[][] tempMap = new int[N][N];
        int[][] tempRoad = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tempMap[i][j] = map[i][j];
                tempRoad[i][j] = road[i][j];
            }
        }
        // 방문 처리
        tempRoad[row][col] = 1;

        // 사방 탐색
        for (int d = 0; d < 4; d++) {
            int nr = row + delta[d][0];
            int nc = col + delta[d][1];
            if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
                // 1. 현재보다 낮은 곳
                // 1-1. 높을 경우 K만큼 깎을 수 있다면
                // > 필요한 만큼만 깎아야함.
                // 2. 길이 아닌 곳
                // if(nr == 2 && nc == 3) {
                //     printing(tempRoad);
                //     printing(tempMap);
                //     System.out.println();
                // }
                if (tempMap[nr][nc] - paramK < tempMap[row][col] && tempRoad[nr][nc] != 1) {
                    if (tempMap[nr][nc] < tempMap[row][col]) {
                        // 깎을 필요가 없다면
                        // System.out.println("row :" + nr + " col : " + nc);
                        // printing(tempRoad);
                        // printing(tempMap);
                        dfs(new Node(nr, nc), cnt + 1, tempMap, tempRoad, paramK);
                    } else {
                        // 깎아야 한다면
                        // for(int i = 1; i <= K; i++){
                        //     tempMap[nr][nc] -= i;
                        //     if (tempMap[nr][nc] < tempMap[row][col]) {
                        //         dfs(new Node(nr, nc), cnt + 1, tempMap, tempRoad, 0);
                        //     }
                        // }
                        int diff = tempMap[nr][nc] - tempMap[row][col];
                        int down = (diff + 1);
                        tempMap[nr][nc] -= down;

                        // System.out.println("row :" + nr + " col : " + nc);
                        // printing(tempRoad);
                        // printing(tempMap);
                        dfs(new Node(nr, nc), cnt + 1, tempMap, tempRoad, 0);
                    }
                } 
                // else {
                //     // 만들 길이 없으면
                //     return;
                // }
            }
        }
    }
    static void printing(int[][] arr){
        System.out.println("print---");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
1
5 1
9 3 2 3 2
6 3 1 7 5
3 4 8 9 9
2 3 7 7 7
7 6 5 5 8

1
4 4
8 3 9 5
4 6 8 5
8 1 5 1
4 9 5 5
 * 
 */