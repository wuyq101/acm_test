package main

import (
	"fmt"
	"math/big"
	"strconv"
)

/*
http://projecteuler.net/problem=284

在14进制下，一个数的平方，后面几位和本身是一样的。
假设是n位数
x^2 = x (mod 14^n)

x^2 - x = 0 (mod 14^n)
x*(x-1) = 0 (mod 14^n)

x和x-1是互质的。
14^n = 2^n * 7^n, 所以x*(x-1) = 0 (mod 2^n*7^n)
只有4种不同的组合

1:
x = 0 (mod 2^n)
x = 0 (mod 7^n)
---> x=0
2:
x-1 = 0 (mod 2^n)
x-1 = 0 (mod 7^n)
---> x=1
3:
x = 0 (mod 2^n)
x-1 = 0 (mod 7^n)
---> 待求解,记为序列X
4:
x-1 = 0 (mod 2^n)
x = 0 (mod 7^n)
---> 待求解,记为序列Y

第一组和第二组解，x=0,x=1, 只有在n=1, 一位数的时候才有效。其他n>1的时候，都不成立, 因为不能有前导'0'。

第三组解和第四组解的关系分析。
对于n位数X来说，
Xn = 0 (mod 2^n)  并且 Xn=1 (mod 7^n)
同样对于n位数Y来说,
Yn = 1 (mod 2^n)  并且 Yn=0 (mod 7^n)

另外Xn和Yn,都是方程 x^2 - x = 0 (mod 14^n)的解，
所以 Xn,Yn < 14^n

Xn+Yn = 0+1 = 1 (mod 2^n)
Xn+Yn = 1+0 = 1 (mod y^n)
2^n 和 7^n是互质的，-->
Xn+Yn = 1 (mod 14^n)

现在假设 Xn+Yn = m*14^n + 1, 我们要确定这个m是多少？
从Xn和Yn各自的范围来看，(Xn,Yn 不等于0和1）
1 < Xn < 14^n
1 < Yn < 14^n
-->
2 < Xn+Yn < 2*14^n
将Xn+Yn = m*14^n +1 代入得到
2 < m*14^n + 1 < 2*14^n
分析可得，m只能取1，
m!=0, 否则 2 < 1
m!=2, 否则 2*14^n + 2 < 2*14^n, (n>1), 更大的m，更不可能
所以m只能等于1. 也就是：
Xn+Yn = 14^n + 1.

14^n + 1, 这个数字在14进制下，形如： 100000000(...)00001,两头是1，中间都是0，一共是n+1位,中间有n-1个0
所以每个Xn刚好严格对应一个Yn，也就是说，我们解同余方程的时候，只要求解序列X，就能得到序列Y

那如何求解序列X呢，
先分析n=1的时候：
x = 0 (mod 2)
x-1 = 0 (mod 7)
---> x=8, (2到13种逐个查找，得到x=8,此时对应的y=14^1+1-x=7)

假设Xk是n=k的时候，得到的解。
Xk = 0 (mod 2^k)
Xk = 1 (mod 7^k)
同时
Xk^2 = Xk (mod 14^k)

我们通过Hensel's Lemma, 推导Xk+1, 当n=k+1时候的解
Xk+1^2 = Xk+1 (mod 14^(k+1))
由于Xk^2 = Xk (mod 14^k), 就是后面的k位是不变的，那k+1位只能是Xk在最高位前增加一个d（0<d<14)
Xk+1 = d*14^k + Xk

(d*14^k + Xk)^2 = (d*14^k + Xk) (mod 14^(k+1))

d^2*14^(2k) + 2*d*14^k*Xk + Xk^2 = d*14^k + Xk (mod 14^k*14)

k>1, 所以 2k > k+1, d^2*14^(2k) = 0 (mod 14^k*14), 所以左边第一项是0，可以舍去。

移项得

Xk^2-Xk + d*14^k*(2Xk-1) = 0 (mod 14^k*14)
由于Xk是n=k的解，所以Xk^2-Xk = 0 (mod 14^k) ---> Xk^2-Xk = C*14^k , C是整数

代入可得 C*14^k + d*14^k*(2Xk-1) = 0 (mod 14^k*14)
注意到每一项都包含14^k, 约去得

C + d*(2Xk-1) = 0 (mod 14), 同时， C = (Xk^2-Xk)/14^k
d的范围是 1到13， 代入可以求解，求出d也就求出了Xk+1
d*(2Xk-1) = 0 (mod 14), Xk是14进制数，最低位是8，所以d*(2*8-1) = d (mod 14)
所以 C+d = 0 (mod 14)
d = (14 - C%14) % 14

d = (14 - (Xk^2-Xk)/14^k) % 14,

Xk+1 = d*14^k + Xk, 至此从Xk到Xk+1的递推式就出来了

--------
*/
func main() {
	Convolution()
	return

	X := big.NewInt(8)
	p := big.NewInt(1)
	pX := int64(8)
	n14 := big.NewInt(14)
	sum := int64(7 + 8 + 1)
	for n := 2; n <= 10000; n++ {
		d := D(X, n-1)
		//  构造下一层的X
		p.Mul(p, n14) // p = 14^(n-1)
		dp := new(big.Int).Mul(big.NewInt(d), p)
		X.Add(X, dp)

		//  累加数位之和
		pX += d
		if d > 0 {
			sum += pX
		}
		if d < 13 {
			pY := int64(13*n+2) - pX
			sum += pY
		}
	}
	fmt.Printf("sum=%s\n", strconv.FormatInt(sum, 14))
}

func D(X *big.Int, n int) int64 {
	n14 := big.NewInt(14)
	a := big.NewInt(0)
	a.Mul(X, X)
	a.Sub(a, X)
	b := big.NewInt(14)
	b.Exp(b, big.NewInt(int64(n)), nil)
	a.Div(a, b)
	a.Mod(a, n14)
	c := a.Int64()
	return (14 - c) % 14
}

/*
直接计算第k+1位，假设前k位都是已知的
*/
func Convolution() {
	X := make([]int, 10000)
	X[0] = 8
	carry := 8 * 8 / 14
	sum := 7 + 8 + 1
	pX := 8
	pY := 7
	for k := 1; k < 10000; k++ {
		ks := sc(X, k)
		// 第k+1位的计算逻辑
		// 第k+1位置上的对应总和（卷积) = carry(进位）+ ∑x[s]*s[k-s] ,(s=1..k-1) + 2*x[0]*d
		// 计算第k位的数字d，d有14种可能
		// (carry+ks)%14 = v, v+2*d=d (mod 14)
		// v+d = 0 (mod 14)
		// d = -v
		v := (carry + ks) % 14
		d := (14 - v) % 14
		X[k] = d
		pX += d
		yk := 13 - d
		pY += yk
		carry = (carry + ks + 16*d) / 14
		if d > 0 {
			sum += pX
		}
		if yk > 0 {
			sum += pY
		}
		/*
			for d := 0; d <= 13; d++ {
				// 假设第k+1位是d
				test_total := carry + ks + 2*X[0]*d
				if test_total%14 == d {
					X[k] = d
					pX += d
					yk := 13 - d
					pY += yk
					carry = test_total / 14
					if d > 0 {
						sum += pX
					}
					if yk > 0 {
						sum += pY
					}
					break
				}
			}
		*/
	}
	fmt.Printf("sum=%s\n", strconv.FormatInt(int64(sum), 14))
}

func sc(X []int, k int) int {
	s := 0
	for i := 1; i < k; i++ {
		s += X[i] * X[k-i]
	}
	return s
}
