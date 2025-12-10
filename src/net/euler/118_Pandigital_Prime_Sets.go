package main

import (
	"fmt"
	"strconv"
)

/*
https://projecteuler.net/problem=118
1-9的所有排列，然后中间分割成几个数字，要求所有数字都是素数，求所有可能
针对1个排列，
集合元素1个，一种
元素2个，8种
元素3个
.....
元素9个，1种
*/
func main() {
	//	S("254789631", nil)
	//	return

	P("", "123456789")
	fmt.Printf("cnt=%d\n", cnt)
}

var N = 9
var cnt = 0

// 1-9的所有排列
func P(a, b string) {
	if len(a) == N {
		//	fmt.Printf("----------" + a + "\n")
		last := a[len(a)-1]
		v, _ := strconv.Atoi(string(last))
		if v%2 == 0 && v != 2 {
			return
		}
		S(a, make([]string, 0))
		return
	}
	// 从b当中取一个，加入到a当中
	for i := 0; i < len(b); i++ {
		ch := b[i]
		c := b[:i] + b[i+1:]
		P(a+string(ch), c)
	}
}

// 其中的一个排列，拆分成几个数字
func S(a string, list []string) {
	if len(a) == 0 {
		// 找到一个set
		//	if check(list) {
		cnt++
		fmt.Printf("%v\n", list)
		//	}
	}
	// 剪枝
	/*
		if len(list) > 0 {
			if !check(list) {
				return
			}
		}
	*/
	for i := 1; i <= len(a); i++ {
		pre := a[0:i]
		post := a[i:]
		// 强制有序
		last := 0
		if len(list) > 0 {
			last, _ = strconv.Atoi(list[len(list)-1])
		}
		cur, _ := strconv.Atoi(pre)
		if cur > last && isPrime(cur) {
			list = append(list, pre)
			S(post, list)
			list = list[:len(list)-1]
		}
	}
}

// 检查list是否为素数
func check(list []string) bool {
	for _, s := range list {
		n, _ := strconv.Atoi(s)
		if !isPrime(n) {
			return false
		}
	}
	return true
}

var primes = map[int]bool{}

func isPrime(n int) bool {
	b, ok := primes[n]
	if ok {
		return b
	}
	if n == 1 {
		primes[n] = false
		return false
	}
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			primes[n] = false
			return false
		}
	}
	primes[n] = true
	return true
}
