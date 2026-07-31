package main

import "fmt"

/*

https://projecteuler.net/problem=157

1/a + 1/b = p/10^n

观察一:
1/a + 1/b = p/10^n, p*10^k / 10^(n+k) , k>0, n+k<=9. 因此找到一个解之后，只要增加k，还可以找到9-n个解

观察二:
1/a + 1/b = p/10^n
假设gcd(a,b) = d

1/d*a1 + 1/d*b1 = p/10^n, 两边都乘以d

1/a1 + 1/b1 = p*d/10^n gcd(a1,b1)=1

由此可见， 任意一组 gcd(a1,b1)=1,对应的解，都可以通过扩大相同倍数得到一个新的解,  其中这个倍数d必须是p的因子

观察三：

1/a + 1/b = (a+b)/ab = p/10^n, 假设gcd(a,b)=1,

p = 10^n*(a+b)/(ab)   由于gcd(a,b)=1, a+b 肯定不是 ab的倍数
p是正整数，因此 10^n*(a+b) = 0 (mod ab)
a+b != 0 (mod ab)
--> 10^n = 0 (mod ab)
--> 2^n * 5^n = 0 (mod ab)
所以a,b必须是 2^n * 5^n 的倍数, n>=1,n<=9






*/

func main() {
	cnt := 0
	abmap := make(map[[2]int]bool)
	for i := 0; i <= 9; i++ {
		for j := 0; j <= 9; j++ {
			a, b := 1, pow(2, i)*pow(5, j)
			abmap[[2]int{a, b}] = true
			a, b = pow(2, i), pow(5, j)
			if a > b {
				a, b = b, a
			}
			abmap[[2]int{a, b}] = true
			a, b = pow(2, i), pow(5, j)
		}
	}
	for pair := range abmap {
		a, b := pair[0], pair[1]
		for n := 1; n <= 9; n++ {
			// 1/a + 1/b = p/10^n
			// p*d = 10^n*(a+b)/(ab)
			if pow(10, n)*(a+b)%(a*b) != 0 {
				continue
			}
			M := pow(10, n) * (a + b) / (a * b)
			cnt += countFactors(M)
		}
	}
	fmt.Printf("total = %d\n", cnt)
}

func pow(n, i int) int {
	if i == 0 {
		return 1
	}
	if i%2 == 0 {
		v := pow(n, i/2)
		return v * v
	}
	return n * pow(n, i-1)
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func countFactors(n int) int {
	if n == 1 {
		return 1
	}
	p := 0
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			p = i
			break
		}
	}
	if p == 0 {
		// n is prime
		return 2
	}
	// n = p^k * q
	k := 1
	q := n / p
	for q%p == 0 {
		k++
		q /= p
	}
	return (k + 1) * countFactors(q)
}

// 求n的所有因子
func factors(n int) []int {
	if n == 1 {
		return []int{1}
	}
	p := 0
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			p = i
			break
		}
	}
	if p == 0 {
		// n是素数
		return []int{1, n}
	}
	// n = p^k * q
	k := 1
	q := n / p
	for q%p == 0 {
		k++
		q /= p
	}
	list := factors(q)
	result := make([]int, 0)
	// 针对这里求得的每一个因子，都可以乘以p^0,p^1,...,p^k, 得到一个新的因子
	m := make(map[int]bool)
	for _, v := range list {
		for i := 0; i <= k; i++ {
			if !m[v] {
				m[v] = true
				result = append(result, v)
			}
			v *= p
		}
	}
	return result
}
