import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int appleCnt = Integer.parseInt(br.readLine());
        HashMap<Integer, Character> changess = new HashMap<>();
        
        int [][] map = new int[N][N];

        for(int i = 0; i<appleCnt; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int tmpy = Integer.parseInt(st.nextToken()) -1; 
            int tmpx = Integer.parseInt(st.nextToken()) -1;
            map[tmpy][tmpx] = 2;
        }

        int changeCnt = Integer.parseInt(br.readLine());

        for(int i =0; i<changeCnt; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            char c = st.nextToken().charAt(0);
            changess.put(t, c);
        }

        int headX = 0;
        int headY = 0;
        int tailX = 0;
        int tailY = 0;

        boolean appleFlag = false;
        
        int[]dx = {0,1,0,-1};//아래 오 위 왼
        int[]dy = {1,0,-1,0};

        int direction = 1;
        
        Queue<Integer> directionQueue = new LinkedList<>();

        int time = 0;
        map[0][0] = 1;
        directionQueue.add(1);
        
        while(true) {
            //머리 돌리기
            if(changess.getOrDefault(time, '0') == 'L') {
                direction += 1;
                if(direction == 4) {
                    direction = 0;
                }
            }else if(changess.getOrDefault(time, '0') == 'D'){
                direction -= 1;
                if(direction == -1) {
                    direction = 3;
                }
            }
            
            time += 1;

            //머리 이동
            int nx = headX + dx[direction];
            int ny = headY + dy[direction];

            //맵 나감
            if(nx <0|| ny<0|| nx>=N|| ny>=N) {
                break;
            }
            //몸 부딫
            if(map[ny][nx] == 1) {
                break;
            }
            //사과먹기
            if(map[ny][nx] == 2) {
                appleFlag = true;
                map[ny][nx] = 1;
                headX = nx;
                headY = ny;
                directionQueue.add(direction);
            //머리 늘리기
            } else {
                map[ny][nx] = 1;
                headX = nx;
                headY = ny;
                directionQueue.add(direction);
            }
            
            // 꼬리 줄이기
            if(!appleFlag) {
                int tailDirection = directionQueue.poll();     
                map[tailY][tailX] = 0;
                tailX += dx[tailDirection];
                tailY += dy[tailDirection];
            }

            appleFlag = false;

        }

        System.out.println(time);
    }  
}