N = int(input())

#맨밑을 두고 나머지를 다른 빈 칸에 옮긴다
#맨 밑을 빈 곳에 옮긴다
#옮겨 놨던 맨 밑 제외를 다시 맨 밑이 옮겨간 곳을 옮긴다
#다 옮기면 종료

#n-1개의 원반이 처음 위치(시작점)에서 한 막대기를 거쳐 다른 막대기로 간다.
#n번째 원반이 마지막 위치로 간다
#n-1개의 원반을 빈 막대기를 거쳐 n번째 원반이 있는 막대기로 간다
#n이 1이면 시작점에서 목표 막대기로 가서 종료



arr=[]
#star '위치'에서 aux를 '거쳐' end로 '가자'
#hanoi(n개원반을, 위치에서, 거쳐서, 가자)
def hanoi(N, star,aux,end) :
  if N==1 :
    arr.append([star,end])
    return
  
  #먼저 star위치에서 end를 거쳐 
  hanoi(N-1,star,end,aux)
  arr.append([star,end])
  hanoi(N-1,aux,star,end)

hanoi(N,1,2,3)
print(len(arr))
for i in range(len(arr)):
  print(arr[i][0], arr[i][1])