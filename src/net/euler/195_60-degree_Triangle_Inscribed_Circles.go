package main

import (
	"fmt"
	"math"
)

/*

余弦定理
c^2 = a^2 + b^2 - 2ab*cosA

cosA = 1/2, sinA = √3/2

c^2 = a^2 - ab + b^2

艾森斯坦整数 参数化
a = m^2-n^2
b = 2mn - n^2
c = m^2 -mn + n^2

m>n>0
gcd(m,n)=1
m != n (mod 3)
m <= 2n

S = 1/2 * ab * sinA = √3/4 * ab
  = √3/4 * (m-n)(m+n)*n(2m-n)

s = (a+b+c)/2 一半的三角形周长
  = (m^2-n^2 + 2mn - n^2 + m^2-mn+n^2)/2
  = (2m^2+mn-n^2)/2
  = (2m-n)(m+n)/2



r = S/s = √3/2 * n * (m-n)

r<=N
√3/2 * n * (m-n) <= N
n*(m-n) <= 2N/√3
找m的最大值，取m-n=1的时候，m最大，n<=2N/√3
--> m <= 2N/√3 + 1
*/

func main() {
	// N := 1053779
	N := 10000
	limit := float64(N*2) * math.Sqrt(3.0)
	M := int(limit) + 1
	cnt := 0
	for m := 1; m <= M; m++ {
		for n := m - 1; n > m/2; n-- {
			if gcd(m, n) != 1 {
				continue
			}
			r := float64(n*(m-n)) * math.Sqrt(3.0) / 2.0
			if r/3.0 > float64(N) {
				break
			}
			if (m+n)%3 == 0 {
				r /= 3.0
			}
			/*
				a := m*m - n*n
				b := 2*m*n - n*n
				c := m*m - m*n + n*n
				fmt.Printf("a=%d, b=%d, c=%d, m=%d, n=%d\n", a, b, c, m, n)
			*/
			k := int(float64(N) / r)
			cnt += k
		}
	}
	fmt.Printf("total=%d\n", cnt)
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
