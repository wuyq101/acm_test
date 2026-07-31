package main

import "fmt"

/*
http://projecteuler.net/problem=154

(x+y+z)^n展开系数中是10^12次的系数个数
n = 200000

系数 C(n,i)*C(n,j)*C(n,k), i+j+k=n
C(n,i) = n!/(i!*(n-i)!)

因为10 = 2*5，因子2的个数远远超过5，所以只要统计5的因子个数

k!中5的个数可以统计

假设次数分别是i,j,k
系数的计算，从N个(x+y+z)挑选i个贡献x，剩下的(N-i)个(x+y+z)挑选j个贡献y，余下的全部贡献z

系数 ai = C(N,i)*C(N-i,j)*1
  = N!/i!*(N-i)! * (N-i)!/j!*(N-i-j)! * 1
  = N!/i!*j!*(N-i-j)!
  = N!  / i!*j!*k!


N = 20000
cnt = 191388450

*/

func main() {
	N := 200000
	P2 := make([]int, N+1)
	P5 := make([]int, N+1)
	for i := 1; i <= N; i++ {
		P2[i] = P2[i-1] + p(i, 2)
		P5[i] = P5[i-1] + p(i, 5)
	}
	cnt := 0
	for i := 0; i <= N/3; i++ {
		for j := i; j <= (N-i)/2; j++ {
			k := N - i - j
			v := P5[N] - P5[i] - P5[j] - P5[k]
			if v >= 12 {
				v2 := P2[N] - P2[i] - P2[j] - P2[k]
				if v2 >= 12 {
					if i == j || j == k {
						cnt += 3
					} else {
						cnt += 6
					}
				}
			}

		}
	}
	fmt.Printf("cnt = %d\n", cnt)
}

func p(n, k int) int {
	if n%k == 0 {
		return 1 + p(n/k, k)
	}
	return 0
}
