package main

import "fmt"

/*
https://projecteuler.net/problem=131

n^3 + n^2*p = m^3
n^2(n+p) = m^3
设d = gcd(n, n+p), 则d也能整除两数之差(n+p)-n = p, 又因为p是质数，所以d = 1 or p
情况1: d = 1
当d=1时，n和(n+p)互质，则n^2和n+p互质，两个互质的乘积是立方数，所以n^2和n+p也必须是立方数
设n^2 = a^3, n+p = b^3, 所以 p=b^3-a^3 = (b-a)(b^2+ab+a^2)
p只能分解为 1*p, 所以 b-a = 1, b^2+ab+a^2 = p
带入b = a+1, p = (a+1)^2 + a(a+1) + a^2 = a^2 + 2a + 1 + a^2 + a + a^2 = 3a^2 + 3a + 1
此时，对应的n = a^3

情况2: d = p
分析同情况1，但是无解
*/
func main() {
	cnt := 0
	a := 0
	for {
		a++
		p := 3*a*a + 3*a + 1
		if p > 1000000 {
			break
		}
		if isPrime(p) {
			fmt.Printf("%d %d\n", a, p)
			cnt++
		}
	}
	fmt.Printf("cnt = %d\n", cnt)
}

func isPrime(n int) bool {
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
