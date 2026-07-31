package main

import (
	"fmt"
	"sort"
)

/*
圆
(x-N/2)^2 + (y-N/2)^2 = (N/2)^2
两边乘以4
(2x-N)^2 + (2y-N)^2 = N^2
令u=2x-N, v=2y-N
u^2 + v^2 = N^2

经过分析，u,v和N同奇偶性, 一对u，v都对应着一个x,y
问题变为求所有u，v的整数解的个数。

A^2+B^2 = N^2
引入复数，做因数分解

N^2 = (A+Bi)(A-Bi)

为了拼出A+Bi的所有可能性，对N分解

N = 2^k * p1^e1 * p2^e2 * ... * q1^f1 * q2^f2 * ...

其中pi = 1 (mod 4)
qi = 3 (mod 4)

N^2 = 2^(2k) * π(pi^2ei) * π(qi^2fi)

其中qi在高斯整数中不可再分。
2 = -i(1+i)^2, 分配的时候，本质上不会产生新的组合，所以视为1.
pi, 每个pi都可以视作一对共轭复数z，z'的乘积。
pi^2ei = z^(2ei) * z'^(2ei)

对于每一个pi^2ei，一共z^(2ei), z'^(2ei),要保证最后出来的复数*它的共轭值是pi^2ei, 必须从z和z'中挑选2ei个。
这样可以有2ei+1中挑法：
z,0个; z', 2ei个
z,1个； z', 2ei-1个
...

z,ei个;z', 0个

所以每个pi对于的选择个数是 2ei+1, 再结合四个单位元(1,i,-1,-i),就是4(2ei+1)个。

最后的结果f(N) = 4π(2ei+1)

举例：105=3*5*7
最高次ei=1,
其中5 = 1(mod 4)

f(105) = 4*(2+1) = 12

10000 = 100*100 = 4*4*25*25 = 2^4*5^4
f(10000) = 4*(2*4+1) = 36

现在要求 所有 f(N) = 420 = 4*π(2ei+1)
所以 π(2ei+1) = 420/4 = 105

N <= 10^11
105的分解情况：
3*5*7 --> p1^1*p2^2*p3^3
3*35  --> p1^1*p2^17
5*21 --> p1^2*p2^10
7*15 -->p1^3*p2^7
105 --> p1^52

求 p=1 (mod 4) 这类型的质数的最大可能值是多少？
5,13,17,29,37,41,53,61,73,...

5^3*13^2*p1 <= N, p1 <= 4733727

q=3(mod 4), 这类型的质数的最大可能值是多少？
M * p <= N, M<= 278454
*/
func main() {
	N = int64(1e11)
	pMax := int(N / 5 / 5 / 5 / 13 / 13)
	primes := genPrimes(pMax)
	fmt.Printf("len=%d, largest=%d\n", len(primes), primes[len(primes)-1])
	pType := make([]int, 0)
	for _, p := range primes {
		if p%4 == 1 {
			pType = append(pType, p)
		}
	}
	fmt.Printf("len=%d, largest=%d, first 10: %v\n", len(pType), pType[len(pType)-1], pType[:10])
	p1, p2, p3 := pType[0], pType[1], pType[2]
	Mmax := N / int64(p1*p1*p1) / int64(p2*p2) / int64(p3)
	fmt.Printf("M max = %d\n", Mmax)
	qType := []int{2}
	for _, q := range primes {
		if q%4 == 3 && q <= int(Mmax) {
			qType = append(qType, q)
		}
	}
	fmt.Printf("len=%d, largest=%d\n", len(qType), qType[len(qType)-1])
	m, preMList := genM(int(Mmax), qType)
	fmt.Printf("len=%d, largest=%d, first 10: %v\n", len(m), m[len(m)-1], preMList[:10])

	sum = int64(0)
	//3*5*7 --> p1^1*p2^2*p3^3
	for i := 0; i < len(pType); i++ {
		p1 := int64(pType[i])
		for j := 0; j < len(pType); j++ {
			if j == i {
				continue
			}
			p2 := int64(pType[j])
			if p1*p2*p2 > N {
				break
			}
			for k := 0; k < len(pType); k++ {
				if k == i || k == j {
					continue
				}
				p3 := int64(pType[k])
				n := p1 * p2 * p2 * p3 * p3 * p3
				if n > N {
					break
				}
				sum += processM(n, m, preMList)
			}
		}
	}
	//7*15 -->p1^3*p2^7
	for i := 0; i < len(pType); i++ {
		p1 := int64(pType[i])
		if p1*p1*p1 > N {
			break
		}
		for j := 0; j < len(pType); j++ {
			if j == i {
				continue
			}
			p2 := int64(pType[j])
			n := p1 * p1 * p1 * p2 * p2 * p2 * p2 * p2 * p2 * p2
			if n > N {
				break
			}
			sum += processM(n, m, preMList)
		}
	}
	//5*21 --> p1^2*p2^10
	for i := 0; i < len(pType); i++ {
		p1 := int64(pType[i])
		if p1*p1 > N {
			break
		}
		for j := 0; j < len(pType); j++ {
			if j == i {
				continue
			}
			p2 := int64(pType[j])
			n := p1 * p1 * p2 * p2 * p2 * p2 * p2 * p2 * p2 * p2 * p2 * p2
			if n > N {
				break
			}
			sum += processM(n, m, preMList)
		}
	}

	fmt.Printf("sum=%d\n", sum)
}

func processM(n int64, m []int, pre []int64) int64 {
	l, h := 0, len(m)-1
	L := 0
	for l <= h {
		mid := (l + h) / 2
		v := n * int64(m[mid])
		if v <= N {
			L = mid
			l = mid + 1
		} else {
			h = mid - 1
		}
	}
	return n * pre[L]
}

var sum int64
var N int64

func genM(Mmax int, q []int) ([]int, []int64) {
	m := make(map[int]bool)
	m[1] = true
	list := []int{1}
	for len(list) > 0 {
		v := list[0]
		list = list[1:]
		for _, p := range q {
			k := v * p
			if k > Mmax {
				break
			}
			if !m[k] {
				m[k] = true
				list = append(list, k)
			}
		}
	}
	for k := range m {
		list = append(list, k)
	}
	sort.Ints(list)
	pre := make([]int64, len(list))
	pre[0] = int64(list[0])
	for i := 1; i < len(list); i++ {
		pre[i] = pre[i-1] + int64(list[i])
	}

	return list, pre
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
