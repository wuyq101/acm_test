package main

import "fmt"

/*
 欧拉生成函数 和 五边形数
 1+x+x^2+x^3+... = 1/(1-x)
 证明 设 S = 1+x+x^2+x^3+...
        x*S = x+x^2+x^3+... = 1 - S
	(1-x)S = 1
	S = 1/(1-x)

对于数字1，我们可以使用
1 + x + x^2 + x^3 + ... = 1/(1-x)

对于数字2，我们可以使用
1+x^2+x^4+x^6+... = 1/(1-x^2)

对于数字k，我们可以使用
1+x^k+x^2k+x^3k+... = 1/(1-x^k)
P(x) = ∏ (1/(1-x^k)) = 1/(1-x) * 1/(1-x^2) * 1/(1-x^3) + ...

展开这个无限乘积之后，x^n的系数就是整数n的拆分个数p(n):

P(x) = ∑ p(n) * x^n

P(x)的倒数Q(x) =  ∏ (1-x^k) = (1-x) * (1-x^2) * (1-x^3) + ... = 1-x-x^2+x^5+x^7-x^12-x^15+...

P(x)*Q(x) =  1
(∑ p(n) * x^n) * ( ∏ (1-x^k) ) = 1
展开之后x^n的系数为0， 所以
p(n)−p(n−1)−p(n−2)+p(n−5)+p(n−7)−p(n−12)−p(n−15)+⋯=0

p(n)=p(n−1)+p(n−2)−p(n−5)−p(n−7)+p(n−12)+p(n−15)−⋯
1,2,5,7,12,15... 五边形数

(-1)^k *[k*(3k-1)/2 + k*(3k+1)/2]



*/

func main() {
	i := 1
	for {
		cnt := p(i)
		fmt.Printf("i=%d, cnt=%d\n", i, cnt)
		if cnt%1000000 == 0 {
			fmt.Printf("find %d %d\n", i, cnt)
			return
		}
		i++
	}
}

var m map[int]int = make(map[int]int)

func p(n int) int {
	//fmt.Printf("call p(%d)\n", n)

	if n == 0 || n == 1 {
		return 1
	}
	v, ok := m[n]
	if ok {
		return v
	}
	sum := 0
	k := 1
	for {
		m := k * (3*k - 1) / 2
		if m > n {
			break
		}
		//	fmt.Printf("n=%d, k=%d, m=%d, sign=%d\n", n, k, m, sign)

		sum += sign(k) * p(n-m)

		m = k * (3*k + 1) / 2
		if m > n {
			break
		}
		//	fmt.Printf("n=%d, k=%d, m=%d, sign=%d\n", n, k, m, sign)
		sum += sign(k) * p(n-m)

		k++

	}

	if sum > 1000000 {
		sum %= 1000000
	}
	for sum <= 0 {
		sum += 1000000
	}

	m[n] = sum
	return sum
}

func sign(k int) int {
	if k%2 == 1 {
		return 1
	}
	return -1
}
