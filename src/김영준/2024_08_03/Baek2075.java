
import java.util.*;
import java.io.*;

public class Baek2075 {
	static List<Integer> numbers;
	static int N;
	public static void main(String[] args) throws IOException {
		init();
		print();
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		numbers = new ArrayList<>();

		//번호 입력
		for(int i = 0 ; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			for(int j = 0; j<N; j++) {
				int number = Integer.parseInt(st.nextToken());
				numbers.add(number);
			}
		}

	}
	private static void print() {

		//내림차순 정렬
		numbers.sort((o1,o2)->{
			return o2 - o1;
		});

		//N번째 출력
		System.out.println(numbers.get(N-1));
	}

}
