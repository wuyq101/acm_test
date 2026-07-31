package main

import "fmt"

/*


a^2 = a (mod n), 对每个n求最大的a
1<=n<=10^7

a*(a-1) = 0 (mod n)
说明 n|a*(a-1), 而gcd(a,a-1)=1
假设n = p1^a1 * p2^a2 * ... * pk^ak
对于n的任一质因子，pi, pi|a*(a-1), pi|a 或者  pi|a-1
所以a是pi的倍数，或者pi的倍数+1.

解法：针对n的最大的质因子，然后从大到小，逐个检查一下kp+1和kp，是否符合条件，第一个找到符合条件的即是答案。



*/

func main() {
	N := int64(10000000)
	// largest prime factor
	LPF := make([]int64, N+1)
	for i := int64(1); i <= N; i++ {
		LPF[i] = i
	}
	for i := int64(2); i <= N; i++ {
		if LPF[i] != i {
			continue
		}
		// i是质数
		for j := i + i; j <= N; j += i {
			LPF[j] = i
		}
	}
	sum := int64(0)
	for i := int64(2); i <= N; i++ {
		sum += M(i, LPF[i])
	}
	fmt.Printf("Sum=%d\n", sum)
}

func M(n int64, p int64) int64 {
	if p == n {
		return 1
	}
	x := n - n%p
	for x >= p {
		// x+1
		if x+1 < n && (x+1)*(x+1)%n == x+1 {
			return x + 1
		}
		// x
		if x*x%n == x {
			return x
		}
		x -= p
	}
	return 1
}
