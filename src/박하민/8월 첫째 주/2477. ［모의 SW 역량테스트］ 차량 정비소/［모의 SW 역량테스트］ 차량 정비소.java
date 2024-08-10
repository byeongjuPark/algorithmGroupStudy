import java.util.*;
import java.io.*;

class Target {
    int targetRepair;
    int customerKey;
    boolean isCount = false;
    Target(int customerKey) {
        this.customerKey = customerKey;
    }
}

class Box {
    int time;
    int runningTime;
    int customerNum;

    Box(int time) {
        this.time = time;
        runningTime = 0;
        customerNum = -1;
    }

    void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }
    
    int isTimeUp() {
        //사람이 있으면 -1 or 사람 num
        if(customerNum != -1) {
            runningTime+=1;
            if(time <= runningTime) {
                runningTime =0;
                int tmp = customerNum;
                customerNum = -1;
                return tmp;
            }
            else {
                return -1;
            }
        }
        //사람이 없으면 return -2
        return -2;
    }
    
}

public class Solution {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int tc = 0; tc<T; tc++) {

            int receptionNum=0;
            int repairNum=0;
            int customerNum=0;
            int targetReception=0;
            int targetRepair=0;
            int result = 0;
            StringTokenizer st = new StringTokenizer(br.readLine());
            receptionNum = Integer.parseInt(st.nextToken());
            repairNum = Integer.parseInt(st.nextToken());
            customerNum = Integer.parseInt(st.nextToken());
            targetReception = Integer.parseInt(st.nextToken());
            targetRepair = Integer.parseInt(st.nextToken());
            
            ArrayList<Box> receptionBoxList = new ArrayList<>();
            ArrayList<Box> repairBoxList = new ArrayList<>();
    
            Queue<Integer> repairQueue = new ArrayDeque<>();
            Queue<Integer> receptionQueue = new ArrayDeque<>();
            Queue<Integer>customerIncomeTime = new ArrayDeque<>();
    
            ArrayList<Target> targets = new ArrayList<>();
    
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<receptionNum; i++) {
                receptionBoxList.add(new Box(Integer.parseInt(st.nextToken())));
            }
            
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<repairNum; i++) {
                repairBoxList.add(new Box(Integer.parseInt(st.nextToken())));
            }
    
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<customerNum; i++) {
                customerIncomeTime.add(Integer.parseInt(st.nextToken())); 
            }
            
            int customerKey = 1;
            int time = 0;
            boolean closed = false;

            while(true) {
                
                //손님도착 리스트확인하여 a큐에 등록
                if(customerIncomeTime.peek()!=null) {
                    while(customerIncomeTime.peek()==time) {
                        customerIncomeTime.poll();
                        receptionQueue.add(customerKey++);
                        if(customerIncomeTime.isEmpty()) {
                            break;
                        }
                    }
                }

                //receptionbox 시간업하여 return이 -2(빈칸)인경우 리셉션 등록
    
                for(int i = 0; i<receptionBoxList.size(); i++) {
    
                    int tmp = receptionBoxList.get(i).isTimeUp();
    
                    
                    if (tmp != -1 && tmp != -2) {
                        repairQueue.add(tmp);
                        if(!receptionQueue.isEmpty()) {
                        receptionBoxList.get(i).setCustomerNum(receptionQueue.poll());
                        }

                        if(i+1 == targetReception) {
                            targets.add(new Target(tmp));
                        }
                    }
                    if(tmp == -2 && !receptionQueue.isEmpty()) {
                        receptionBoxList.get(i).setCustomerNum(receptionQueue.poll()); 
                    }
                    
                }
    
    
                for(int i = 0; i<repairBoxList.size(); i++) {
    
                    int tmp = repairBoxList.get(i).isTimeUp();
    
                    
                    if (tmp != -1 && tmp != -2) {
                        //집에감
                        if(i+1 == targetRepair) {
                            for(int j = 0; j<targets.size() ; j++) {
                                if(!targets.get(j).isCount && targets.get(j).customerKey == tmp) {
                                    result += tmp;
                                    targets.get(j).isCount = true;
                                }
                            }
                        }
                        if(!repairQueue.isEmpty()) {
                            repairBoxList.get(i).setCustomerNum(repairQueue.poll());
                        }

                    }  
                    if(tmp == -2 && !repairQueue.isEmpty()) {
                        repairBoxList.get(i).setCustomerNum(repairQueue.poll()); 
                    } 
                }
    
                time +=1;

                if(customerIncomeTime.isEmpty() && receptionQueue.isEmpty()) {
                    boolean closedFlag1 = true;
                    for(Box b : receptionBoxList) {
                        if(b.customerNum != -1) {
                            closedFlag1 = false;
                            break;
                        }
                    }

                    if(closedFlag1 && repairQueue.isEmpty()) {
                        boolean closedFlag2 = true;
                        for(Box b : repairBoxList) {
                            if(b.customerNum != -1) {
                                closedFlag2 = false;
                                break;
                            }
                        }
                        if(closedFlag2) {
                            closed = true;
                        }
                    }
                }

                if(closed) {
                    break;
                }
            }

            if(result == 0) {
                sb.append("#" + (tc+1) + " " + -1 + "\n");
            }else {
                sb.append("#" + (tc+1) + " " + result + "\n");
            }

        }
        System.out.println(sb.toString());
    }
}