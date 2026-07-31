package main

import (
	"fmt"
	"math/bits"
)

/*
欧拉准则
欧拉准则（Euler's Criterion） 是数论中判断一个数在模素数意义下“到底有没有平方根”的终极定理

x^2 = a (mod p), p是素数,
gcd(a,p)=1. 根据费马小定理
a^(p-1) = 1 (mod p)

a^(p-1)-1 = 0 (mod p)
p是大于2的质数，肯定是奇数，所以p-1是偶数，根据平方差公式
设k = (p-1)/2

(a^((p-1)/2)+1)*(a^((p-1)/2)-1) = 0 (mod p)
如果两个数相乘能被p整除，那必然是其中至少有一个能被p整除
所以必然有以下两种情况之一发生：
a^((p-1)/2)=1 (mod p)
a^((p-1)/2)=-1 (mod p)

所以我们只要对a做快速幂运算，就可以知道a是属于1(有平方根，二次剩余)还是-1(无平方根，非二次剩余)阵营。

假设存在根x，a = x^2
(x^2)^((p-1)/2) = x^(p-1) = 1 (mod p), 所以算出来是1的，一定是二次剩余。

Tonelli-Shanks 算法
已知 x^2 = a (mod p), 有解，求出x。

用欧拉准则判断了a是否为二次剩余，然后用Tonelli-Shanks算法计算出x

已知：
a^((p-1)/2) = 1 (mod p)   (1)

第一步，拆分2因子：
p是奇质数，p-1是偶数，把p-1中的所有2因子都提取出来
p-1 = Q*2^S, Q是奇数。

将p-1=Q*2^S 代入(1):

a^((Q*2^S)/2) = 1 (mod p)

a^(Q*2^(S-1)) = 1 (mod p)

第二步，猜测与调整
我们要找一个x，使得x^2 = a (mod p)
既然Q是一个奇数， 那么(Q+1)/2是整数。算法先根据Q，盲猜一个初值R = a^((Q+1)/2)

那这个R，是不是方程的根呢，代入

R^2 = a^(Q+1) = a^Q*a (mod p)
如果我们希望R是方程的根，那么R^2 = a^Q*a = a (mod p), 所以最好a^Q=1(mod p), 就满足情况。

我们将a^Q定义为t，误差因子。

R^2 = a^Q*a = t*a (mod p)

所以目标就是调整R，使得t=1, 这样我们就找到方程的根

第三步，修正因子
先随便找一个，在模p下，没有平方根的整数z，非二次剩余，根据欧拉准则

z^((p-1)/2) = -1 (mod p)
z^(Q*2^(S-1)) = -1 (mod p)
定义修正因子c = z^Q (mod p)

c有一个特性，通过不断平方，平方S-1次，最终会变成 z^(Q*2^(S-1)) = -1 (mod p)

第四步, 修正R

当前的参数 R, t, c

R^2 = t*a (mod p)
t = a^Q
c = z^Q

通过给t不断的平方，最多平方S-1次，最终会变成 a^(Q*2^(S-1)) = 1 (mod p)

假设，我们给t连续平方了M次，它第一次变成1，即t^(2^M) = 1 (mod p)
那么M-1次的时候，一定是-1，只有(-1)*(-1)=1.
所以t^(2^(M-1)) = -1 (mod p)

假设我们有一个b^2,经过M-1次平方之后，也是-1，即 (b^2)^(2^(M-1)) = -1 (mod p)
那我们用这个b^2，M-1次方之后的-1，乘以 t，M-1次平方之后的-1，就可以得到1.

根据c的性质，它不断平方之后会变成-1. 我们来计算b.
c = z^Q,

c^(2^(S-1)) = -1 (mod p)

(b^2)^(2^(M-1)) = c^(2^(S-1))

b^(2^M) = c^(2^(S-1))
假设b=c^k

c^(k*2^M) = c^(2^(S-1))
--> k = 2^(S-M-1)

所以另 b = c^(2^(S-M-1)), 这样b^2经过M-1次平方之后，就变成-1.

更新
t' = t*b^2
R' = R*b
c' = b^2
*/
func TonelliShanks(a, p int64) int64 {
	// 欧拉准则
	if !EulerCriterion(a, p) {
		// 解的范围在[0,p-1], 这里返回p表示无解
		return p
	}
	// 分解p-1 = Q*2^S
	Q := p - 1
	S := 0
	for Q%2 == 0 {
		S++
		Q /= 2
	}
	R := pow(a, (Q+1)/2, p)
	t := pow(a, Q, p)
	// 查找z,非二次剩余
	z := int64(2)
	for i := int64(2); i <= p-1; i++ {
		if !EulerCriterion(i, p) {
			z = i
			break
		}
	}
	c := pow(z, Q, p)
	M := S
	for t != 1 {
		k := t
		m := 0
		for i := 0; i < M; i++ {
			if k == 1 {
				m = i
				break
			}
			k = mulMod(k, k, p)
		}
		b := pow(c, int64(1<<(M-m-1)), p)
		b2 := pow(b, 2, p)
		t = mulMod(t, b2, p) // t = t*b^2
		R = mulMod(R, b, p)
		c = b2
		M = m
	}
	return R
}

func EulerCriterion(a, p int64) bool {
	return pow(a, (p-1)/2, p) == 1
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

func main() {
	a := int64(23)
	p := int64(41)
	x := TonelliShanks(a, p)
	fmt.Printf("x=%d\n", x)
}
