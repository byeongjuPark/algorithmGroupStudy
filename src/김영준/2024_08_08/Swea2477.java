import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {

	static BufferedReader br;
	static int T;
	static int N, M, K, A, B;

	static int[] receiptCosts;
	static int[] repairCosts;

	static Customer[] receiptCounter;
	static Customer[] repairCounter;

	static Queue<Customer> customers;
	static PriorityQueue<Customer> repairWaiting;

	static List<Customer> results;

	public static void main(String[] args) throws IOException {
		init();
		start();
	}

	private static void init() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
	}

	private static void start() throws IOException {

		for (int testcase = 1; testcase <= T; testcase++) {

			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());

			receiptCosts = new int[N + 1];
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				receiptCosts[i] = Integer.parseInt(st.nextToken());
			}

			repairCosts = new int[M + 1];
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= M; i++) {
				repairCosts[i] = Integer.parseInt(st.nextToken());
			}

			customers = new LinkedList<>();
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= K; i++) {
				customers.add(new Customer(i, Integer.parseInt(st.nextToken())));
			}

			repairWaiting = new PriorityQueue<>((o1, o2) -> {
				if (o1.endTime == o2.endTime) {
					return o1.receiptNumber - o2.receiptNumber;
				}
				return o1.endTime - o2.endTime;
			});

			results = new ArrayList<>();
			receiptCounter = new Customer[N + 1];
			repairCounter = new Customer[M + 1];

			play();
			print(testcase);
		}
	}

	private static void play() {
		int t = 0;

		//접수 창구 와 리페어 창구가 전부 비어있어도 지금 접수 웨이팅과 리페어 웨이팅이 존재하면 끝내면 안된다.
		while (!customers.isEmpty() || !repairWaiting.isEmpty() || !allRepairComplete() || !allReceiptComplete()) {

			while (!customers.isEmpty() && customers.peek().arriveTime <= t) {
				int isEmptyReceiptIndex = isEmptyReceipt();
				if (isEmptyReceiptIndex != -1) {
					Customer customer = customers.poll();
					customer.receiptNumber = isEmptyReceiptIndex;
					receiptCounter[isEmptyReceiptIndex] = customer;
					continue;
				}
				break;
			}

			while (!repairWaiting.isEmpty() && isEmptyRepair() != -1) {
				int index = isEmptyRepair();
				Customer customer = repairWaiting.poll();
				customer.repairNumber = index;
				repairCounter[index] = customer;
			}

			t++;
			useTimeReceipt(t);
			useTimeRepair();
		}
	}

	private static void print(int testcase) {
		int result = 0;
		for (Customer customer : results) {
			if (customer.receiptNumber == A && customer.repairNumber == B) {
				result += customer.number;
			}
		}

		if (result == 0) {
			System.out.println("#" + testcase + " " + -1);
		} else {
			System.out.println("#" + testcase + " " + result);
		}

	}

	private static void useTimeReceipt(int t) {
		for (int i = 1; i <= N; i++) {
			if (receiptCounter[i] != null) {
				receiptCounter[i].waitingTime++;
				if (receiptCounter[i].waitingTime == receiptCosts[i]) {
					receiptCounter[i].waitingTime = 0;
					receiptCounter[i].endTime = t;
					repairWaiting.add(receiptCounter[i]);
					receiptCounter[i] = null;
				}
			}
		}
	}

	private static void useTimeRepair() {
		for (int i = 1; i <= M; i++) {
			if (repairCounter[i] != null) {
				repairCounter[i].waitingTime++;
				if (repairCounter[i].waitingTime == repairCosts[i]) {
					Customer customer = repairCounter[i];
					results.add(customer);
					repairCounter[i] = null;
				}
			}
		}
	}

	private static int isEmptyReceipt() {
		for (int i = 1; i <= N; i++) {
			if (receiptCounter[i] == null) {
				return i;
			}
		}
		return -1;
	}

	private static int isEmptyRepair() {
		for (int i = 1; i <= M; i++) {
			if (repairCounter[i] == null) {
				return i;
			}
		}
		return -1;
	}

	private static boolean allRepairComplete() {
		for (int i = 1; i <= M; i++) {
			if (repairCounter[i] != null) {
				return false;
			}
		}
		return true;
	}

	private static boolean allReceiptComplete() {
		for (int i = 1; i <= N; i++) {
			if (receiptCounter[i] != null) {
				return false;
			}
		}
		return true;
	}
}

class Customer {

	int number;
	int arriveTime;
	int waitingTime;
	int endTime;

	int receiptNumber;
	int repairNumber;

	Customer(int number, int arriveTime) {
		this.number = number;
		this.arriveTime = arriveTime;
		this.waitingTime = 0;
	}
}
