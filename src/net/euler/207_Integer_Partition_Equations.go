package main

import "fmt"

/*
<pre>
4^t = 2^t  + k
2^(2t) - 2^t = k
2^t * (2^t - 1) = k

设x=2^t
x*(x-1) = k

也就是说k能拆分乘两个连续的整数乘积，并且x是2的幂

P(m) = t是整数的能拆分的情况/能拆分的情况, 1<=k<=m, 并且k符合上面的条件
m = 6是

k = 1, 不能拆分
k = 2 = 2*1, 2^t=2, t=1
k = 3, 不能拆分
k = 4, 不能拆分
k = 5, 不能拆分
k = 6 = 3*2, 2^t = 3, t=log(2,3)
k = 7, X
k = 8, X
k = 9, X
k = 10, X
k = 11, X
k = 12 = 4*3, 2^t = 4, t=2
k = 13, X
k = 14, X
k = 15, X
k = 16, X
k = 17, X
k = 18...


2^t*(2^t-1)
t=1, k=2*1
     3*2
t=2, k=4*3=12
     5*4
     6*5
     7*6
t=3, k=8*7=56
     9*8
     10*8
     ...
     15*14
t=4, k=16*15=240


t = i时， 整数d的个数就是i个，总的个数是2^i - 1

M = 2^i*(2^i-1)
P(M) = i/(2^i - 1)
随着i的增大，P值不断下降，题目要求P < 1/12345




</pre>
*/

func main() {
	i := 1
	for {
		a := i
		b := pow(2, i) - 1
		f := cmp(a, b, 1, 12345)
		if f == -1 {
			fmt.Printf("i=%d,a=%d,b=%d\n", i, a, b)
			break
		}
		i++
	}
	k := 0
	a := i
	b := pow(2, i) - 1
	for {
		k++
		f := cmp(a-1, b-k, 1, 12345)
		if f >= 0 {
			fmt.Printf("k=%d,a=%d,b=%d\n", k, a-1, b-k)
			break
		}
	}
	k--
	M := pow(2, i) - k
	M = M * (M - 1)
	fmt.Printf("M=%d\n", M)

}

// a/b - c/d
func cmp(a, b, c, d int) int {
	n := a*d - b*c
	if n == 0 {
		return 0
	}
	if n > 0 {
		return 1
	}
	return -1
}

func pow(n, e int) int {
	result := 1
	for e > 0 {
		if e&1 == 1 {
			result *= n
		}
		n *= n
		e >>= 1
	}
	return result
}
