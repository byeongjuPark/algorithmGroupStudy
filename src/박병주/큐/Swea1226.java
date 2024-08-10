import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
 
public class Swea1226 {
    final static int MAP_SIZE = 16;
    static int[][] map = new int[MAP_SIZE][MAP_SIZE];
    static int r,c;
    static int[][] delta = {{-1,0}, {0, 1}, {1, 0}, {0, -1}};
    
    static class Node {
        int r;
        int c;
 
        Node(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         
        StringBuffer sb = new StringBuffer();
        for (int t = 1; t <= 10; t++) {
            boolean[][] visited = new boolean[MAP_SIZE][MAP_SIZE];
            br.readLine(); // testcase 날리기
            for (int i = 0; i < 16; i++) {
                String[] strArr = br.readLine().split("");
 
                for (int j = 0; j < 16; j++) {
                    map[i][j] = Integer.parseInt(strArr[j]);
                }
            }
 
            Queue<Node> queue = new LinkedList<>();
            // 시작점 넣기
            queue.offer(findStartPoint());
 
            visited[queue.peek().r][queue.peek().c] = true;
 
            boolean checkBreak = false;
            while(!queue.isEmpty()){
                //사방 탐색
                if(checkBreak){
                    break;
                }
                boolean isMove = false;
                for(int k = 0; k < 4; k++){
                    int nr = r + delta[k][0];
                    int nc = c + delta[k][1];
                    //문제 ) 첫 시작점을 노드에 넣게 되면 peek로 검사했을때 걸린다.
                    //해결 방안 -> visited 체크
                    if(nr >= 0 && nc >= 0 && nr < MAP_SIZE && nc < MAP_SIZE && map[nr][nc] != 1 && !visited[nr][nc]){
                        //&& nNode.r != queue.peek().r && nNode.c !=queue.peek().c // 첫 노드에 걸림
                         
                        // 0이면 먹고 이동
                        if(map[nr][nc] == 0){
                            queue.offer(new Node(nr, nc));
                            visited[nr][nc] = true;
                            r = nr;
                            c = nc;
                            isMove = true;
                            break;
                        }
                        if(map[nr][nc] == 3){
                            // 도달 가능한 미로
                            sb.append("#").append(t).append(" 1\n");
                            checkBreak = true;
                            break;
                        }
                    }
                }
                // 사방 탐색 후 이동 없으면
                if(!isMove){
                    // 현재 위치를 1로 덮음
                    map[r][c] = 1;
                    // 하나 뱉고, 뱉은 노드의 위치로 이동
                    Node node = queue.poll();
                    r = node.r;
                    c = node.c;
                }
            }
            if(!checkBreak){
                // 도달 불가능한 미로
                sb.append("#").append(t).append(" 0\n");
            }
 
 
        }
        System.out.println(sb);
     
    }
 
    static Node findStartPoint() {
        Node result = null;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (map[i][j] == 2) {
                    result = new Node(i, j);
                    r = i;
                    c = j;
                    break;
                }
            }
        }
        return result;
    }
}