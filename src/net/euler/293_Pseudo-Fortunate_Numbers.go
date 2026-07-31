package main

import (
	"fmt"
	"math/bits"
)

func main() {
	t := 1
	primes := genPrimes(200)
	list := make([]int, 0)
	sum := int64(0)
	m := make(map[int]bool)
	for _, p := range primes {
		t *= p
		if t > 1e9 {
			break
		}
		fmt.Printf("t=%d,p=%d\n", t, p)
		list = append(list, p)
		admissible := genAdmissible(list)
		fmt.Printf("len(admissible)=%d\n", len(admissible))
		for _, a := range admissible {
			np := nextPrime(a + 2)
			v := np - a
			if m[v] {
				continue
			}
			m[v] = true
			sum += int64(v)
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

func nextPrime(n int) int {
	v := int64(n + 1)
	for !millerRabinTest(v) {
		v++
	}
	return int(v)
}

var N = int64(1e9)

func genAdmissible(list []int) []int {
	m := make(map[int64]bool)
	first := int64(1)
	for _, p := range list {
		first *= int64(p)
	}
	q := []int64{first}
	m[first] = true
	for len(q) > 0 {
		a := q[0]
		q = q[1:]
		for _, p := range list {
			v := int64(a) * int64(p)
			if v > N {
				break
			}
			if !m[v] {
				m[v] = true
				q = append(q, v)
			}
		}
	}
	result := make([]int, 0, len(m))
	for k := range m {
		result = append(result, int(k))
	}
	return result
}

func genPrimes(n int) []int {
	composite := make([]bool, n+1)
	for i := 2; i <= n; i++ {
		if composite[i] {
			continue
		}
		for j := i + i; j <= n; j += i {
			composite[j] = true
		}
	}
	primes := make([]int, 0, n/2)
	for i := 2; i <= n; i++ {
		if !composite[i] {
			primes = append(primes, i)
		}
	}
	return primes
}

/*
Miller-Rabin Prime Test
根据费马小定理，对于一个质数n

a^(n-1) = 1 (mod n)

那么a^(n-1) != 1 (mod n), n就一定不是质数

n-1是偶数， n-1 = 2^s*d, 将n-1拆分成2^s和d

x0 = a^(d) (mod n), 如果x0 = 1 or n-1, 则n通过了基数a的测试，暂时认为n是质数

x1 = x0*x0 = a^(2d) (mod n)

x2 = x1*x1 = a^(4d) (mod n)

x3 = x2*x2 = a^(8d) (mod n)
...
xs = xs-1*xs-1 = a^(2^s*d) (mod n) ---> xs = 1 (mod n)

另外，如果n是质数， x^2 = 1 (mod n), 只有x=1 or -1(即n-1) 这两个根

如果n是质数， x0,x1,x2,...xs-1,xs 只有两种可能
形式一：1,1,1,...,1
形式二: a,b,c,...,d,e,n-1,1,1,...,1

所以通过对x不断平方，循环s-1次，总是能遇到n-1的。如果没有遇到n-1,或者直接先遇到1（说明x^2 mod n有其他非平凡平方根，那么n就是合数）

*/

var base = []int64{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}

func millerRabinTest(n int64) bool {
	if n < 2 {
		return false
	}
	if n == 2 || n == 3 {
		return true
	}
	if n&1 == 0 {
		return false
	}
	// n-1 = 2^s * d
	s, d := 0, n-1
	for d&1 == 0 {
		s++
		d >>= 1
	}
	fn := func(a int64) bool {
		x := pow(a, d, n)
		if x == 1 || x == n-1 {
			return true
		}
		for range s - 1 {
			x = mulMod(x, x, n)
			if x == n-1 {
				return true
			}
			if x == 1 {
				return false
			}
		}
		return false
	}
	for _, a := range base {
		if a == n {
			return true
		}
		if a > n {
			break
		}
		if !fn(a) {
			return false
		}
	}
	return true
}

func pow(a, d, n int64) int64 {
	res := int64(1)
	for d > 0 {
		if d&1 == 1 {
			res = mulMod(res, a, n)
		}
		a = mulMod(a, a, n)
		d >>= 1
	}
	return res
}

func mulMod(a, d, n int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(d))
	return int64(bits.Rem64(hi, lo, uint64(n)))
}
