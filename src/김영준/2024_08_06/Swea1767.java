import java.io.*;
import java.util.*;


class Swea1767{
	static int T;
	static BufferedReader br;
	static List<Location> coils;
	static int N;
	//상 하 좌 우
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int maxCoil;
	static int minCount;
	public static void main(String[]args) throws IOException {
		init();
		play();

	}

	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());


	}

	private static void play()throws IOException {
		for(int  testCase= 1 ; testCase <= T; testCase++) {
			N = Integer.parseInt(br.readLine());

			int[][] graph = new int[N][N];
			coils = new ArrayList<>();

			maxCoil = Integer.MIN_VALUE;
			minCount = Integer.MAX_VALUE;

			initGraph(graph,N);
			calculate(0,0,graph,0);
//            System.out.println(coils.size());
			System.out.println("#"+testCase+" "+minCount);
		}
	}

	private static void calculate(int index,int maxCoilCount,int[][] graph,int count) {

		if(maxCoilCount>maxCoil) {
			maxCoil = maxCoilCount;
			minCount = count;
		}

		if(maxCoilCount == maxCoil) {
			minCount = Math.min(minCount, count);
		}


		if(index == coils.size()) {
			return;
		}


		for(int i = index; i<coils.size(); i++) {

			Location now = coils.get(i);
//    		System.out.println("현재 인덱스"+index);

			for(int k = 0; k<4; k++) {

				int[][] tmpGraph = new int[N][N];
				int nx = now.x;
				int ny = now.y;
				int moveCount = 0;
				copyGraph(graph,tmpGraph);

				while(true) {
					nx = dx[k] + nx;
					ny = dy[k] + ny;

					if(nx<0 || ny<0 || nx>=N || ny>=N) {
//    					System.out.println("다음인덱스"+(i+1));
						calculate(i+1,maxCoilCount+1,tmpGraph,count+moveCount);
						break;
					}

					if(graph[nx][ny] == 1) {
//    					System.out.println("방향 불가"+k);
						break;
					}
					moveCount++;
					tmpGraph[nx][ny] = 1;
				}


			}

//    		System.out.println();

		}






	}



	private static void copyGraph(int[][] graph, int[][] tmp) {

		for(int i = 0 ; i<N; i++) {
			for(int j = 0; j<N;j ++) {
				tmp[i][j] = graph[i][j];
			}
		}

	}

	private static void initGraph(int[][] graph,int N) throws IOException {

		for(int i = 0;i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
				if(i != 0 && j != 0 && i!=N-1 && j !=N-1) {
					if(graph[i][j]==1) {
						coils.add(new Location(i,j));
					}
				}
			}
		}
	}


}

class Result{
	int maxCoil;
	int count;

	Result(int maxCoil,int count){
		this.maxCoil = maxCoil;
		this.count = count;
	}
}

class Location{
	int x;
	int y;

	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
}