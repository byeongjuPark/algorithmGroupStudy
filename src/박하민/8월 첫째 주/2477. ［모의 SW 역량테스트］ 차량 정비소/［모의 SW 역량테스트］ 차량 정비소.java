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

//recption과 repair용 카운터
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
    
    //시간을 흐르게 하는 메서드
    int isTimeUp() {
        if(customerNum != -1) {
            //사람이 있으면 시간+1
            runningTime+=1;

            //정해진 시간이 끝나면
            if(time <= runningTime) {
                //시간, 사람 변수 리셋
                runningTime =0;
                int tmp = customerNum;
                customerNum = -1;
                //사람 번호 리턴
                return tmp;
            }
            //정해진 시간이 끝나지 않았다면 -1 리턴
            else {
                return -1;
            }
        }

        //사람이 들어있지 않으면 return -2
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

            //모든사람이 빠져나갔는지 확인하는 flag
            boolean closed = false;

            /*  1. 손님 도착 큐의 peek()과 시간이 맞으면 poll()하여 receptionQueue에 등록
             *  2. receptionBox가 비어있다면 receptionQueue에서 손님을 뽑아 등록
             *  3. receptionBox의 시간이 흐르고 시간이 다 되었다면 손님을 repairQueue에 등록
             *     이때 다음 시간이 흐를때 맞추기위해 receptionBox에서 손님을 빼냄과 동시에 
             *     receptionQueue에서 손님을 뽑아 등록
             *  4. 손님이 targetReception을 지났다면 targetList에 저장해둠
             *  5. repairBox가 비어있다면 repairQueue에서 손님을 뽑아 등록
             *  6. repairBox의 시간이 흐르고 시간이 다 되었다면 손님을 빼내 targetList와 비교하고 target인지 확인
             *     이때 다음 시간이 흐를때 맞추기위해 repairBox에서 손님을 빼냄과 동시에 
             *     repairQueue에서 손님을 뽑아 등록
             */
            while(true) {

                // 1
                if(customerIncomeTime.peek()!=null) {
                    while(customerIncomeTime.peek()==time) {
                        customerIncomeTime.poll();
                        receptionQueue.add(customerKey++);
                        if(customerIncomeTime.isEmpty()) {
                            break;
                        }
                    }
                }

                
                
                for(int i = 0; i<receptionBoxList.size(); i++) {

                    //시간 흐름
                    int tmp = receptionBoxList.get(i).isTimeUp();
                    
                    //박스에서 손님이 꺼내졌는가?
                    if (tmp != -1 && tmp != -2) {
                        //다음 큐에 등록
                        repairQueue.add(tmp);
                        //박스에 손님 다시 넣기
                        if(!receptionQueue.isEmpty()) {
                        receptionBoxList.get(i).setCustomerNum(receptionQueue.poll());
                        }

                        //타겟인지 확인
                        if(i+1 == targetReception) {
                            targets.add(new Target(tmp));
                        }
                    }
                    //박스가 원래 빈칸이었다면
                    if(tmp == -2 && !receptionQueue.isEmpty()) {
                        //박스에 손님 넣기
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