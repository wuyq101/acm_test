package main

import "fmt"

/*
(𝑝−1)^𝑛 +(𝑝+1)^𝑛  mod p^2
多项式展开
p^n + C(n,1)*p^(n-1)*(-1)^1 + C(n,2)*p^(n-2)*(-1)^2 + ... +C(n,n-2)*p^2*(-1)^(n-2) + C(n,n-1)*p*(-1)^(n-1) + (-1)^n
前面的项都超过p^2，所以可以忽略
只考虑最后两项：n*p*(-1)^(n-1) + (-1)^n
(p+1)^n同样的分析： n*p + 1
当n是偶数时候： 和 = -n*p + 1 + n*p + 1 = 2, 余数恒等于2.
当n是奇数时候： 和 = n*p - 1  n*p + 1 = 2*n*p
2*n*p >= 10^10
n*p >= 5*10^9
*/
func main() {
	M := 1000000
	v := make([]bool, M)
	v[0] = false
	v[1] = false
	for i := 2; i < M; i++ {
		v[i] = true
	}
	for i := 2; i < M; i++ {
		if v[i] {
			for j := 2; i*j < M; j++ {
				v[i*j] = false
			}
		}
	}
	primes := make([]int, 0)
	for i := 2; i < M; i++ {
		if v[i] {
			primes = append(primes, i)
		}
	}
	fmt.Printf("%d\n", len(primes))
	for i, p := range primes {
		n := i + 1
		if n%2 == 0 {
			continue
		}
		r := n * p
		if r >= 5000000000 {
			fmt.Printf("n=%d p=%d r=%d\n", n, p, r)
			break
		}
	}
}
