package main

import (
	"fmt"
	"strconv"
)

/*
假设一个数n，满足条件，它的长度是L，最低位是d

n = 10*A + d, A是除最低位以外的数字，d是最低位
A = (n-d)/10

旋转之后得到R = d*10^(L-1) + A = d*10^(L-1) + (n-d)/10
另外 R = k * n

k*n = d*10^(L-1) + (n-d)/10
两边同乘以10
10k*n = d*10^L + n-d
(10k-1)*n = d*(10^L-1)

n = d*(10^L-1) / (10k-1) , {d | 0<d<=9, L是长度从2到100，0<k<=9)

考察分数Q = d/(10k-1), 写出十进制的循环分数形式

Q = 0.(n), n是长度L的循环节
10^L*Q = n.(n)...
相减得到 (10^L-1)*Q = n

n = d*(10^L-1) / (10k-1)


所以本质上n就是分数 d/(10k-1) 的循环节, 然后要求是长度是L，不能以0开头


*/

func main() {
	sum := 0
	for k := 1; k <= 9; k++ {
		for d := 1; d <= 9; d++ {
			a := d
			b := 10*k - 1
			period := frac(a, b)
			if period[0] == '0' {
				continue
			}
			fmt.Printf("%d/%d = 0.(%s), L=%d\n", a, b, period, len(period))
			s := period
			for len(s) <= 100 {
				if len(s) >= 2 {
					//取最低的5位
					sub := s
					if len(sub) >= 5 {
						sub = sub[len(sub)-5:]
					}
					v, _ := strconv.Atoi(sub)
					sum = (sum + v) % 100000
					//	fmt.Printf("sub=%s, sum=%d\n", sub, sum)
				}
				s += period
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

// 求分数 a/b的循环节
func frac(a, b int) string {
	if a == b && a == 9 {
		return "9"
	}
	g := gcd(a, b)
	a /= g
	b /= g
	m := make(map[int]int)
	m[a] = 0
	r := a
	str := ""
	for {
		r *= 10
		c := r / b
		str += string('0' + c)
		r %= b
		_, ok := m[r]
		if ok {
			break
		}
		m[r] = c
	}
	return str
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
