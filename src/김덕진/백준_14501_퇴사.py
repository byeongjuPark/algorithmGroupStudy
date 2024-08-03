N = int(input())

arr = [list(map(int, input().split())) for _ in range(N)]

profit = [0]*(N+1)
for i in range(N-1, -1, -1) :
  if i + arr[i][0] <= N :
    profit[i] = max(profit[i+1], arr[i][1] + profit[arr[i][0]+i])
  else :
    profit[i] = profit[i+1]

print(profit)
print(profit[0])