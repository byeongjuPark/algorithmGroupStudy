import java.io.*;
import java.util.*;

class Printing {
    int index;
    int importance;

    Printing(int index, int importance) {
        this.index = index;
        this.importance = importance;
    }
}

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int test = 0; test<tc; test++) {
            Queue<Printing> printer = new LinkedList<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int order = 1;

            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<N; i++) {
                printer.add(new Printing(i,Integer.parseInt(st.nextToken())));
            }

            while(!(printer.isEmpty())) {
                Printing now = printer.poll();
                boolean canPrint = true;

                if (printer.size()>0) {
                    for(Printing p : printer) {
                        if(now.importance < p.importance) {
                            canPrint = false;
                            break;
                        }
                    }
                }

                if(canPrint) {
                    if(now.index == M) {
                        sb.append(""+order +"\n");
                        break;
                    }
                    order++;
                }
                else {
                    
                    printer.add(now);
                }
            }

        }
        System.out.println(sb.toString());
    }
}