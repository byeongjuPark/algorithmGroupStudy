{
 "cells": [
  {
   "cell_type": "code",
   "execution_count": 11,
   "id": "cd6b9c39",
   "metadata": {},
   "outputs": [
    {
     "data": {
      "text/plain": [
       "15"
      ]
     },
     "execution_count": 11,
     "metadata": {},
     "output_type": "execute_result"
    }
   ],
   "source": [
    "r, c , t = map(int, input().split())\n",
    "#r 행 / c 열 / t 시간\n",
    "\n",
    "r_c = []  #방 격자 리스트\n",
    "\n",
    "for _ in range(r) :\n",
    "    r_c.append(list(map(int, input().split())))\n",
    "\n",
    "for i in range(r) :\n",
    "    if r_c[i][0] == -1 :\n",
    "        air = i\n",
    "        break\n",
    "    \n",
    "    \n",
    "def time_c(r_c) :\n",
    "    r_copy = [arr[:] for arr in r_c] #확산 후 리스트\n",
    "    #확산 과정\n",
    "    for i in range(r) :\n",
    "        for j in range(c) :\n",
    "            if r_c[i][j] > 0 :\n",
    "                spread_count = 0\n",
    "                if i - 1 > -1 and r_c[i-1][j] != -1: #위\n",
    "                    spread_count += 1\n",
    "                    r_copy[i-1][j] += r_c[i][j] // 5\n",
    "                if i + 1 < r : #아래\n",
    "                    spread_count += 1\n",
    "                    r_copy[i+1][j] += r_c[i][j] // 5\n",
    "                if j+1 < c : #오른쪽\n",
    "                    spread_count += 1\n",
    "                    r_copy[i][j+1] += r_c[i][j] // 5\n",
    "                if j-1 > -1 and r_c[i][j-1] != -1 : #왼쪽\n",
    "                    spread_count += 1\n",
    "                    r_copy[i][j-1] += r_c[i][j] // 5\n",
    "                r_copy[i][j] -= (r_c[i][j] // 5) * spread_count #제자리에 남아있는 값 정산\n",
    "                \n",
    "   #공청기 가동\n",
    "    r_cc = [arr [:] for arr in r_copy]\n",
    "    \n",
    "    for i in range(air+1) :  #윗칸 반시계방향\n",
    "        if i == 0 :\n",
    "            r_c_air = r_copy[0][1:]\n",
    "            r_cc[0] = r_c_air + [r_copy[1][-1]]\n",
    "        elif i == air :\n",
    "            r_c_air = r_copy[i][1:c-1]\n",
    "            r_cc[i] = [-1] + [0] + r_c_air\n",
    "        else :\n",
    "            r_cc[i][0] = r_copy[i-1][0]\n",
    "            r_cc[i][-1] = r_copy[i+1][-1]\n",
    "\n",
    "    for i in range(r-1,air,-1) : #아래칸 시계방향\n",
    "        if i == air+1 :\n",
    "            r_c_air = r_copy[i][1:-1]\n",
    "            r_cc[i] = [-1] + [0] + r_c_air\n",
    "        elif i == r-1 :\n",
    "            r_c_air = r_copy[i][1:]\n",
    "            r_cc[i] = r_c_air + [r_copy[i-1][-1]]\n",
    "        else :\n",
    "            r_cc[i][0] = r_copy[i+1][0]\n",
    "            r_cc[i][-1] = r_copy[i-1][-1]\n",
    "                \n",
    "    return r_cc\n",
    "answer = 0\n",
    "for _ in range(t) :\n",
    "    r_c = time_c(r_c)\n",
    "for i in range(r) :\n",
    "    answer += sum(r_c[i])\n",
    "print(answer + 2)"
   ]
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3 (ipykernel)",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.9.7"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 5
}
