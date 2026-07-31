package main

import "fmt"

/*

OAL三种字母构成
长度为n的字符串，不能连续3个A，L最多只允许出现1次



dp(n,0), n表示长度，0表示l出现的次数

第一个使用L  后面出现的总次数是 dp(n-1,1)
第一个使用O， 后面出现的总次数是 dp(n-1,0)
第一个使用A，
          AL dp(n-2,1)
	  AA
	    AAL dp(n-3,1)
	    AAO dp(n-3,0)
	  AO dp(n-2,0)
AAA 不允许出现

dp(n,1)
第一个使用L  错误，
第一个使用A
          AA
	    AAO dp(n-3,1)
	  AO dp(n-2,1)
第一个使用O dp(n-1,1)


*/

func main() {
	n := 30
	cnt := dp(n, 0)
	fmt.Printf("cnt=%d\n", cnt)
}

// 长度为n，只有AO两个字母构成，不能有连续3个A
func f(n int) int64 {
	v, ok := cf[n]
	if ok {
		return v
	}
	if n == 1 {
		// A O
		return 2
	}
	if n == 2 {
		// AA
		// AO
		// OA
		// OO
		return 4
	}
	if n == 3 {
		// 8-1 , AAA (x)
		return 7
	}
	// 第一个使用O
	a := f(n - 1)
	// 第一个使用A
	// AA
	// AAA (x)  AAO
	// AO
	b := f(n - 3)
	c := f(n - 2)
	v = a + b + c
	cf[n] = v
	return v
}

var cf = map[int]int64{}
var cd = map[int]int64{}

func dp(n, l int) int64 {
	if l == 0 {
		v, ok := cd[n]
		if ok {
			return v
		}
	}
	if n == 1 {
		if l == 1 {
			// A, O
			return f(1)
		}
		// A O L
		return 1 + f(1)
	}
	if n == 2 {
		if l == 1 {
			// 只有AO
			return f(2)
		}
		// 第一个不使用L 2*dp(1,0)
		// AO
		// AL
		// AA
		// OA
		// OL
		// OO
		// 第一个使用L f(1)
		// LA
		// LL (x)
		// LO
		return f(1) + 2*dp(1, 0)
	}
	if n == 3 {
		// 第一个使用L f(2)
		// 第一个使用O dp(2, 0)
		// 第一个使用A , 除了AAA不行，其他都可以
		// A x x dp(2, 0) - 1
		return f(2) + 2*dp(2, 0) - 1
	}
	if l == 0 {
		// 第一个使用L
		a := f(n - 1)
		// 第一个使用O
		b := dp(n-1, 0)
		// 第一个使用A，分三种情况讨论
		// AL
		c := f(n - 2)
		// AO
		d := dp(n-2, 0)
		// AA,  细分为AAL AAO
		e := f(n - 3)
		f := dp(n-3, 0)
		v := a + b + c + d + e + f
		cd[n] = v
		return v
	}
	return f(n)
}
