package main

import "fmt"

var total int64

func main() {
	for d := 0; d <= 9; d++ {
		flag = false
		f(d)
	}
	fmt.Printf("total = %d\n", total)
}

var flag bool

func f(d int) {
	k := 9
	a := make([]bool, 10)
	for {
		flag = false
		findK(a, d, 0, k)
		if flag {
			fmt.Printf("d=%d, k=%d\n", d, k)
			break
		}
		k--
	}
}

func findK(a []bool, d, i, k int) {
	if k == 0 {
		// 找到一种模版，开始处理这个模版
		//	fmt.Printf("%v\n", a)
		checkPrime(a, d)
		return
	}
	if i >= len(a) {
		return
	}
	// 使用i位
	if !a[i] {
		a[i] = true
		findK(a, d, i+1, k-1)
		a[i] = false
	}
	// 不使用i位
	findK(a, d, i+1, k)
}

func checkPrime(a []bool, d int) bool {
	n := make([]int, 10)
	for i := 0; i < 10; i++ {
		if a[i] {
			n[i] = d
		}
	}
	// 剩余10-k个位置，0-9， 非d的数字代替, 一共9^(10-k)种
	dfs(a, d, n, 0)
	return true
}

// a 模版，d模版中的数字, i，当前枚举的位置， k，当前位枚举的数字
func dfs(a []bool, d int, n []int, i int) {
	if i == 10 {
		if n[0] == 0 {
			return
		}
		if n[9]%2 == 0 {
			return
		}
		// 枚举到最后一位，判断是否是素数
		v := int64(0)
		for j := 0; j < 10; j++ {
			v = v*10 + int64(n[j])
		}
		p := isPrime(v)
		if p {
			total += v
			flag = true
			fmt.Printf("%d=%v\n", v, p)
		}
		return
	}
	if a[i] {
		// 第i位必须是d
		dfs(a, d, n, i+1)
		return
	}
	for k := 0; k <= 9; k++ {
		if k == d {
			continue
		}
		n[i] = k
		dfs(a, d, n, i+1)
		n[i] = 0
	}

}

func isPrime(n int64) bool {
	for i := int64(2); i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
