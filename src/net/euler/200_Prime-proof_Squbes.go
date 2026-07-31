package main

import (
	"fmt"
	"math/bits"
	"sort"
	"strconv"
	"strings"
)

func main() {
	N := 1000000
	primes := make([]int64, 0)
	for n := 2; n < N; n++ {
		if millerRabinTest(int64(n)) {
			primes = append(primes, int64(n))
		}
	}
	fmt.Printf("len=%d\n", len(primes))
	// p^2*q^3
	squbes := make([]int64, 0)
	limit := int64(1e12)
	for _, p := range primes {
		// p*p
		p2 := p * p
		// p*p*8>limit
		if p2 > limit/8 {
			break
		}
		q3Limit := limit / p2
		for _, q := range primes {
			if p == q {
				continue
			}
			q3 := q * q * q
			if q3 > q3Limit {
				break
			}
			squbes = append(squbes, p2*q3)
		}
	}
	sort.Slice(squbes, func(i, j int) bool {
		return squbes[i] < squbes[j]
	})
	fmt.Printf("first 100 squbes: %v\n", squbes[:100])
	list := make([]int64, 0)
	for _, v := range squbes {
		if has200(v) {
			list = append(list, v)
		}
	}
	squbes = list
	fmt.Printf("-----------\n")
	fmt.Printf("first 100 --200-- squbes: %v\n", squbes[:100])
	cnt := 0
	for _, v := range squbes {
		if isPrimeProof(v) {
			cnt++
			fmt.Printf("%d %d\n", cnt, v)
			if cnt == 200 {
				break
			}
		}
	}
}

func has200(n int64) bool {
	s := strconv.FormatInt(n, 10)
	return strings.Index(s, "200") >= 0
}

func isPrimeProof(n int64) bool {
	s := strconv.FormatInt(n, 10)
	for i := 0; i < len(s); i++ {
		c := rune(s[i])
		for ch := '0'; ch <= '9'; ch++ {
			if (i == 0 && ch == '0') || c == ch {
				continue
			}
			str := s[:i] + string(ch) + s[i+1:]
			v, _ := strconv.ParseInt(str, 10, 64)
			if millerRabinTest(v) {
				return false
			}
		}
	}
	return true
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
