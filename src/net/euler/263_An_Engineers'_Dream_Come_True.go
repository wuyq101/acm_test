package main

import (
	"fmt"
	"math"
)

/*
http://projecteuler.net/problem=263




判断一个数是否为Practical Number。
N = p1^a1 * p2^a2 * ... * pk^ak
p1必须等于2
1是N的约数，那么1，可以由N的约数组成，
假设N是奇数，下一个最小的约数是3，那么2就无法有N的约数组成，因此p1必须等于2.

第二个条件：
pi <= 1+∑p1^ai*p2^a2*..pi-1^(ai-1)
假设我们已经处理了前i-1个因子。记录这部分的乘积是p1^a1*p2^a2*..pi-1^(ai-1) = D
假设D已经是一个Practical Number, 那么从1到D的每个数字，我们都可以用D的约数构造。
同时超出D的部分，我们可以无缝构造一直到∑p1^ai*p2^a2*..pi-1^(ai-1)
举例子D = 4, 约数是1，2，4
此时∑ = 1+2+4=7， 用D的约数，可以无缝构造出1到7。
现在要引入下一个因子pi,那么可用的约数就会多出一批：pi,pi*d,pi^2*d.... d是D的约数
当前能凑出得的最大的数是∑p1^ai*p2^a2*..pi-1^(ai-1), 那么需要的下一个数自然就是 1+∑p1^ai*p2^a2*..pi-1^(ai-1)
位了凑出这个数，那么下一个因子pi就必须<= 1+∑



n-9,n-3,n+3,n+9
四个连续的质数p,p+6,p+12,p+18, 对5取模。
p
p+6=p+1(mod 5)
p+12=p+2(mod 5)
p+18=p+3(mod 5)
p,p+1,p+2,p+3都不能是0(否则被5整除），因此这4个余数只能是1--4，所以p=1(mod 5)
那么n=p+9 = 0 (mod 5)



如何快速求N的所有约数之和
N = p1^a1 * p2^a2 * ... * pk^ak
d是N的一个约数
假设d=p1^b1*p2^b2*...pk^bk,
b1<=a1,b2<=a2...bk<=ak. d本质上是从p1,p2...pk中选一些数出来的乘积。
本质上是在做一个组合。
对于p1因子来说，它能贡献的一共有a1+1中
(1+p1+p1^2+...+p1^a1)
同理，对p2因子来说，一共可以贡献a2+1中
(1+p2+p2^2+...+p2^a2)
将这些多项式相乘，就可以得到所有的约数，相加之后，就是所有约数的和。
举例：12 = 2*2*3
2: 1+2+4 = 7
3: 1+3 = 4
7*4=28 = 1+2+3+4+6+12



*/

func main() {
	N := int(12e8)
	primes := bitsetSieve(N)
	fmt.Printf("len(primes) = %d\n", len(primes))
	cnt := 0
	sum := 0
	for i := 0; i+3 < len(primes); i++ {
		p1, p2, p3, p4 := primes[i], primes[i+1], primes[i+2], primes[i+3]
		if p2 == p1+6 && p3 == p2+6 && p4 == p3+6 {
			n := p1 + 9
			// n-9是奇数，所以n必须是偶数, 同时n必须是5的倍数
			if n%10 != 0 {
				continue
			}
			flag := true
			for j := -2; j <= 2; j++ {
				k := n + j*4
				if !isPractical(k, primes) {
					flag = false
					break
				}
			}
			if flag {
				fmt.Printf("%d %d %d %d, n=%d\n", p1, p2, p3, p4, n)
				sum += n
				cnt++
				if cnt == 4 {
					break
				}
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

func isPractical(n int, primes []int) bool {
	sum := 1
	for _, p := range primes {
		if p*p > n {
			break
		}
		if n%p != 0 {
			continue
		}
		if p > 1+sum {
			return false
		}
		k := p
		s := 1
		for n%p == 0 {
			s += k
			k *= p
			n /= p
		}
		// 1+p+p^2+p^3+...
		sum *= s
	}
	if n > 1 {
		// 还有剩余的质数
		if n > 1+sum {
			return false
		}
	}
	return true
}

func eulerSieve(n int) []int {
	primes := make([]int, 0)
	composite := make([]bool, n+1)
	for i := 2; i <= n; i++ {
		if !composite[i] {
			primes = append(primes, i)
		}
		for _, p := range primes {
			if i*p > n {
				break
			}
			composite[i*p] = true
			if i%p == 0 {
				break
			}
		}
	}
	return primes
}

func bitsetSieve(n int) []int {
	// 只存奇数，索引 i 代表数字 2*i + 3
	half := (n - 1) / 2
	sieve := make([]uint64, half/64+1)

	// 预估 12 亿以内的素数个数约 6100 万，预先分配容量避免动态扩容
	cnt := int(float64(n) / math.Log(float64(n)))
	primes := make([]int, 0, cnt)
	primes = append(primes, 2)

	// 一个uint64有64位，每个位代表一个数字
	for i := 0; i < half; i++ {
		// 检查第 i 位是否为 0
		if (sieve[i>>6] & (1 << (i & 63))) == 0 {
			p := 2*i + 3
			primes = append(primes, p)

			// 标记 p 的倍数，从 p^2 开始
			if p*p <= n {
				// p^2 对应的索引是 (p^2 - 3) / 2
				for j := (p*p - 3) / 2; j < half; j += p {
					// 将第 j 位置为 1
					sieve[j>>6] |= (1 << (j & 63))
				}
			}
		}
	}
	return primes
}
