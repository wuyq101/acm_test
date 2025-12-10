package main

import "fmt"

func main() {
	n := 5
	r := red(n) - 1
	fmt.Printf("n=%d, r=%d\n", n, r)
	g := green(n) - 1
	fmt.Printf("n=%d, g=%d\n", n, g)
	b := blue(n) - 1
	fmt.Printf("n=%d, b=%d\n", n, b)
	fmt.Printf("sum=%d\n", f(50))
}

func f(n int) int {
	R = map[int]int{}
	G = map[int]int{}
	B = map[int]int{}
	return red(n) + green(n) + blue(n) - 3
}

var R = map[int]int{}

func red(n int) int {
	v, ok := R[n]
	if ok {
		return v
	}
	if n == 0 {
		return 1
	}
	// 左边第一个，设置为灰色
	sum := red(n - 1)
	if n >= 2 {
		// 左边两个，设置为红色
		sum += red(n - 2)
	}
	R[n] = sum
	return sum
}

var G = map[int]int{}

func green(n int) int {
	v, ok := G[n]
	if ok {
		return v
	}
	if n == 0 {
		return 1
	}
	// 左边第一个，设置为灰色
	sum := green(n - 1)
	if n >= 3 {
		// 左边两个，设置为绿色
		sum += green(n - 3)
	}
	G[n] = sum
	return sum
}

var B = map[int]int{}

func blue(n int) int {
	v, ok := B[n]
	if ok {
		return v
	}
	if n == 0 {
		return 1
	}
	// 左边第一个，设置为灰色
	sum := blue(n - 1)
	if n >= 4 {
		// 左边两个，设置为蓝色
		sum += blue(n - 4)
	}
	B[n] = sum
	return sum
}
