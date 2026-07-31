package main

import (
	"fmt"
	"strconv"
	"strings"
)

/*

https://projecteuler.net/problem=175
f(n), n是奇数2k+1，必须使用2^0 = 1, f(2k+1) = f(k)
n是偶数2k，可以使用2个1，也可以不用1，f(2k) = f(k) + f(k-1)

定义g(n) = f(n)/f(n-1) = p/q

当n的末尾增加0（二进制下） n --->2n

g(n) = p/q
g(2n) = f(2n)/f(2n-1) = f(n)+f(n-1) / f(n-1) = 1 + f(n)/f(n-1) = 1 + g(n) = 1 + p/q = (p+q) / q

也就是说，如果在一个数的末尾加上一个0，分子就会增加一个q，分数值增加1，此时 分子>分母


当n的末尾增加1（二进制下） n --->2n+1
g(n) = p/q
g(2n+1) = f(2n+1)/f(2n) = f(n)/[f(n)+f(n-1)] = 1/{1 + 1/g(n)}
两边都取倒数
1/g(2n+1) = 1 + 1/g(n) = 1 + q/p = (p+q) / p

g(2n+1) = p/(p+q)

也就是说，如果在一个数的末尾加上一个1，分母就会增加一个p，分母>分子

计算过程：
从目标分数p/q出发：

如果p>q, 那么说明这一步是通过尾部增加0得到的，假设 p = k*q + r, 说明之前的分数是 r/q, 然后n是尾部增加了k个0

如果p<q, 那么说明这一步是通过尾部增加1得到的，假设 q = k*p + r, 说明之前的分数是 p/r, 然后n是尾部增加了k个1


*/

func main() {
	s := sbe(13, 17, "")
	fmt.Println(s)
	fmt.Println("----")
	s = sbe(13717421, 109739369, "")
	fmt.Println(s)
}

func sbe(p, q int, s string) string {
	fmt.Printf("p=%d, q=%d, s=%s\n", p, q, s)
	if p == 1 && q == 1 {
		// 看之前的字符串，最前面是代表0，还是代表1，如果是0，则直接写1，如果是1，则+1到之前的数字上
		strs := strings.Split(s, ",")
		if strings.HasSuffix(strs[0], "|1") {
			a := strings.Split(strs[0], "|")[0]
			k, _ := strconv.Atoi(a)
			k++
			strs[0] = fmt.Sprintf("%d|1", k)
			s = strings.Join(strs, ",")
		} else {
			s = fmt.Sprintf("1|1,%s", s)
		}
		s = strings.ReplaceAll(s, "|1", "")
		s = strings.ReplaceAll(s, "|0", "")
		if strings.HasSuffix(s, ",") {
			s = s[:len(s)-1]
		}
		return s
	}
	if p > q {
		// 这一步是增加0得到的
		k := p / q
		p := p % q
		if p == 0 {
			// 不能直接整整到0
			k--
			p = 1
			q = 1
		}
		s = fmt.Sprintf("%d|0,%s", k, s)
		return sbe(p, q, s)
	}
	// p<q
	// 这一步是增加1得到的
	k := q / p
	q = q % p
	if q == 0 {
		// 不能直接整整到0
		k--
		p = 1
		q = 1
	}
	s = fmt.Sprintf("%d|1,%s", k, s)
	return sbe(p, q, s)
}
