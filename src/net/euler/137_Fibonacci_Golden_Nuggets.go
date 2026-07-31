package main

import (
	"fmt"
	"sort"
)

/*
https://projecteuler.net/problem=137

1: AF(x) = xF1 + x^2F2 + x^3F3 + ... + x^kFk + ...
2: xAF(x) = x^2F1 + x^3F2 + x^4F3 + ... + x^kFk-1 + x^(k+1)Fk + ...
1式 - 2式 = (1-x)AF(x) = x + 0 + x^3F1 + x^4F2 + ... = x + x^2AF(x)

--> AF(x) = x/(1-x-x^2)
因为x必须是一个正有理数，假设x = c/d, (c,d互质), AF(x) = n

nx^2 + (n+1)x - n = 0
由于x是有理数，所以判别式delta必须是一个平方数，这样x才是一个正有理数
delta = (n+1)^2 + 4n^2 = 5n^2 + 2n + 1
设这个delta为m^2,

5n^2 + 2n + 1 = m^2
5(n^2+ n * 2/5) + 1 = m^2
5(n^2 + 2n/5 + 1/25) - 1/5 + 1  = m^2
5(n+1/5)^2 + 4/5 = m^2
5*(n+1/5)^2 - m^2 = -4/5
25*(n+1/5)^2 - 5m^2 = -4

(5n+1)^2 - 5m^2 = -4

整理为广义佩尔方程的形式
设u = 5n+1, v = m, 则

u^2 - 5v^2 = -4

标准佩尔方程

x^2 - Dy^2 = 1。 广义佩尔方程x^2 -Dy^2 = N (N为整数)
这里D = 5, N = -4

标准佩尔方程 x^2 - 5y^2 = 1, 的基本解
(x0,y0) = (9,4) 9*9 - 5*4*4 = 1
基本单位 α = 9 + 4√5
基本单位的正整数次幂构成了标准佩尔方程的所有解

广义佩尔方程u^2 - 5v^2 = -4的基本解
通过枚举可得(u,v) = (1,1) (4,2) (11,5) 三组基本解
广义佩尔方程的所有解可以由这两个解 * 基本单位的整数次幂得到
假设(u0,v0)是u^2 - 5v^2 = -4的一个解
那么

		(u0 + v0√5) * ( 9 + 4√5)^k 也是一个解
		设(u,v)已知, 求下一组解（找到递推公式)
		(u' + v'√5)  = (u + v√5) * ( 9 + 4√5) = 9u + 4u√5 + 9v√5 + 20v
			        = (9u+20v) + (4u + 9v)√5
	        u' = 9u+20v
		v' = 4u+9v

找到递推公式后，可以求得所有符合条件的解
每个解的u = 5n+1, 对应一个n
n = (u-1)/5, u = 1 mod 5
*/
func main() {
	list := make([]Unit, 0)
	list = append(list, Unit{u: 1, v: 1})
	list = append(list, Unit{u: 4, v: 2})
	list = append(list, Unit{u: 11, v: 5})
	k := 0
	for {
		head := list[0]
		list = list[1:]
		u, v := head.u, head.v
		if u > 5 && u%5 == 1 {
			k++
			n := (u - 1) / 5
			fmt.Printf("find %d %d\n", k, n)
		}

		a := 9*u + 20*v
		b := 4*u + 9*v
		list = append(list, Unit{u: a, v: b})
		fmt.Printf("next = (%d %d)\n", a, b)
		sort.Slice(list, func(i, j int) bool {
			return list[i].u < list[j].u
		})
		if k == 15 {
			break
		}

	}
}

type Unit struct {
	u int64
	v int64
}
