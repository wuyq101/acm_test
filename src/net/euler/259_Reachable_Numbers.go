package main

import (
	"fmt"
	"strconv"
)

/*

1,2,3,4,5,6,7,8,9
随意组合可以组装成几个数字
相当于有8个隔板，每个隔板有0-1两种状态，一共有2^8种组合


针对某一种组合，有多少个数字可以达到？
比如
一个数字，a, 那就是a

如果两个数字
a,b:   a+b, a-b, a*b, a/b?? 如果整除的话。

三个数字，
a,b,c： 可以将相邻两个数字，通过计算得到的结果，然后进行递归





*/

func main() {
	N := 1 << 8
	for i := 0; i < N; i++ {
		list := split(i)
		fmt.Printf("%8b %v\n", i, list)
		fs := make([]Frac, 0)
		for _, n := range list {
			fs = append(fs, Frac{int64(n), 1})
		}
		dfs(fs)
	}
	fmt.Printf("sum=%d\n", sum)
	fmt.Printf("len=%d cnt=%d\n", len(dfsKeys), len(visit))
}

func split(mask int) []int {
	str := "123456789"
	idx := 0
	result := make([]int, 0)

	for mask > 0 {
		idx++
		if mask&1 == 1 {
			s := str[0:idx]
			n, _ := strconv.Atoi(s)
			result = append(result, n)
			str = str[idx:]
			idx = 0
		}
		mask >>= 1
	}
	if len(str) > 0 {
		n, _ := strconv.Atoi(str)
		result = append(result, n)
	}
	return result
}

var visit = make(map[int64]bool)
var sum int64
var dfsKeys = make(map[string]bool)

func dfs(nums []Frac) {
	//fmt.Printf("dfs nums = %v\n", nums)
	if len(nums) == 1 {
		a, b := nums[0].n, nums[0].d
		//	fmt.Printf("%d/%d\n", a, b)
		if a%b == 0 {
			a = a / b
		} else {
			a = 0
		}
		if a > 0 && !visit[a] {
			visit[a] = true
			//	fmt.Printf("find %d\n", a)
			sum += a
		}
		return
	}
	key := fmt.Sprintf("%v", nums)
	if _, ok := dfsKeys[key]; ok {
		return
	}
	// 找两个相邻的数，进行四则运算
	for i := 0; i < len(nums)-1; i++ {
		f1 := nums[i]
		f2 := nums[i+1]
		pre := make([]Frac, 0)
		// 前面i个
		for j := 0; j < i; j++ {
			pre = append(pre, nums[j])
		}
		// 后面的
		post := make([]Frac, 0)
		for j := i + 2; j < len(nums); j++ {
			post = append(post, nums[j])
		}
		// +
		f := Add(f1, f2)
		list := make([]Frac, 0)
		list = append(list, pre...)
		list = append(list, f)
		list = append(list, post...)
		dfs(list)

		// -
		f = Sub(f1, f2)
		list = make([]Frac, 0)
		list = append(list, pre...)
		list = append(list, f)
		list = append(list, post...)
		dfs(list)

		// *
		f = Mul(f1, f2)
		list = make([]Frac, 0)
		list = append(list, pre...)
		list = append(list, f)
		list = append(list, post...)
		dfs(list)

		// /
		if !isZero(f2) {
			f = Div(f1, f2)
			list = make([]Frac, 0)
			list = append(list, pre...)
			list = append(list, f)
			list = append(list, post...)
			dfs(list)
		}
	}
	dfsKeys[key] = true
}

type Frac struct {
	n int64
	d int64
}

func (f Frac) String() string {
	return fmt.Sprintf("%d/%d", f.n, f.d)
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

// a/b + c/d =
func Add(f1, f2 Frac) Frac {
	a, b := f1.n, f1.d
	c, d := f2.n, f2.d
	num := a*d + b*c
	den := b * d
	g := gcd(num, den)
	return Frac{num / g, den / g}
}

// a/b - c/d
func Sub(f1, f2 Frac) Frac {
	return Add(f1, Frac{-f2.n, f2.d})
}

// a/b * c/d
func Mul(f1, f2 Frac) Frac {
	a, b := f1.n, f1.d
	c, d := f2.n, f2.d
	num := a * c
	den := b * d
	g := gcd(num, den)
	return Frac{num / g, den / g}
}

func Inv(f Frac) Frac {
	return Frac{f.d, f.n}
}

func Div(f1, f2 Frac) Frac {
	return Mul(f1, Inv(f2))
}

func isZero(f Frac) bool {
	return f.n == 0
}
