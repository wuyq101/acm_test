package main

import "fmt"

/*
http://projecteuler.net/problem=153

n = 5

Gaussian integer divisors
1
1 + 2i
1 - 2i
2 + i
2 - i
5

假设n = m * p, 其中p是素数，所有p的divisor都是n的divisor

p的divisor：
1
p

a + bi
a - bi
if a^2 + b^2 = p

n 1---10^8

如果遍历 a b
a 从1----> 10^4
b 从0----> 10^4
a^2 + b^2 = n n=[1,10^8]

每一堆共轭复数 对最后的结果和贡献 2a

1 + 0*i  贡献1， N/1 次， 最后是1*N/1

1 + 1*i
1 - 1*i  贡献2， |1+i|^2 = 2, N/2, 2*N/2

1 + 2*i
1 - 2*i 贡献2， |1+2i|^2 = 5, N/5, 2*N/5
*/
func main() {
	N := int64(1e8)
	M := int64(1e4)
	sum := int64(0)
	for a := int64(1); a <= M; a++ {
		for b := int64(1); b <= M; b++ {
			if gcd(a, b) != 1 {
				// 之前已经算过了
				continue
			}
			if a*a+b*b > N {
				break
			}
			// 计算 k*(a+bi), k*(a-bi) 类型， k=1,2,3...
			K := N / (a*a + b*b)
			sum += Real(K) * a
		}
	}
	sum *= 2
	sum += Real(N)
	fmt.Printf("sum=%d\n", sum)
	fmt.Printf("size=%d\n", len(m))
}

var m = make(map[int64]int64)

// ∑k*(n/k), k从1到n
func Real(n int64) int64 {
	v, ok := m[n]
	if ok {
		return v
	}
	l, r, q := int64(1), int64(0), int64(0)
	sum := int64(0)
	for l <= n {
		// 从l开始一直到r，N/k 都等于q
		q = n / l
		r = n / q
		// 对和的影响 = q * (l + l+1 + l+2 + ... + r)
		sum += q * (r - l + 1) * (l + r) / 2
		l = r + 1
	}
	m[n] = sum
	return sum
}

func gcd(a, b int64) int64 {
	for b != 0 {
		a, b = b, a%b
	}
	return a
}
