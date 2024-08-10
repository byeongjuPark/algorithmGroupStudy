import java.util.*;
import java.io.*;

public class Solution {
    static int fee;
    static int dayFee;
    static int monthFee;
    static int threeMonthFee;
    static int yearFee;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int tc = 0; tc<T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            dayFee = Integer.parseInt(st.nextToken());
            monthFee = Integer.parseInt(st.nextToken());
            threeMonthFee = Integer.parseInt(st.nextToken());
            yearFee = Integer.parseInt(st.nextToken());

            int[] plan = new int[13];
            
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i<13; i++) {
                plan[i] = Integer.parseInt(st.nextToken());
            }

            fee = Integer.MAX_VALUE;
            planCal(plan, 0, 1);

            sb.append("#" + (tc+1) + " " + fee + "\n");
        }
        System.out.println(sb.toString());
    }



    static void planCal(int[]plan, int sum, int index) {
        //기저조건
        if(index == 13) {
            if(fee > sum) {
                fee = sum;
            }
            return;
        }

        //연권
        if(index == 1) {
            planCal(plan, yearFee, 13);
        }

        //3개월
        if(index >= 11) {
            planCal(plan, sum + threeMonthFee, 13);
        }else {
            planCal(plan, sum + threeMonthFee, index+3);
        }

        //월권
        planCal(plan, sum + monthFee, index+1);

        //일권
        planCal(plan, sum + dayFee * plan[index], index+1);
    }
}