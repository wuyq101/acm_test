package main

import (
	"fmt"
	"strconv"
	"strings"
)

/**
 * @see https://projecteuler.net/problem=66
 * https://en.wikipedia.org/wiki/Pell%27s_equation
 */
func main() {
	// x^2 - D * y^2 = 1
	// (x/y)^2 - D = (1/y)^2
	// (x/y)^2 - (1/y)^2 = D
	// 当y很大 x/y = sqrt(D)
	// 所以 x/y是sqrt(D)会逐渐收敛
	// 用连分数的方式来表示D，逐个试，就可以找到最小的X和Y

	maxX := "0"
	maxD := 0

	for D := 1; D <= 1000; D++ {
		if isSquare(D) {
			continue
		}
		x := findX(D)
		if Cmp(x, maxX) >= 0 {
			maxX = x
			maxD = D
		}
	}
	fmt.Printf("maxX=%s, maxD=%d\n", maxX, maxD)
}

func findX(D int) string {
	pm = make(map[int]string)
	qm = make(map[int]string)
	// 根号D的连分数表达式
	f := Sqrt(D)
	//	fmt.Printf("sqrt(%d) = %s\n", D, f)
	// pn = pn-1 * an + pn-2
	// qn = qn-1 * an + qn-2
	// fn = pn/qn
	// D = 901
	// f = sqrt(901) = [30;(60)]
	f = f[1 : len(f)-1] // 去掉[]
	f = strings.ReplaceAll(f, "(", "")
	f = strings.ReplaceAll(f, ")", "")
	f = strings.ReplaceAll(f, ";", ",")
	list := strings.Split(f, ",")
	//	fmt.Printf("list==%v\n", list)
	ds := strconv.Itoa(D)
	n := 1
	for {
		x, y := getXY(list, n)
		//	fmt.Printf("%d --> %s/%s\n", n, x, y)
		// x^x - Dy^y == 1
		left := Mul(x, x)
		right := Add(Mul(ds, Mul(y, y)), "1")
		if left == right {
			//	fmt.Printf("find an answer %s %s\n", x, y)
			fmt.Printf("%d-->  %s*%s - %d*%s*%s = 1\n", D, x, x, D, y, y)
			return x
		}
		n++
	}
	return f
}

var pm map[int]string
var qm map[int]string

func getXY(list []string, n int) (string, string) {
	pv, ok := pm[n]
	qv, ok := qm[n]
	if ok {
		return pv, qv
	}
	if n == 0 {
		return list[0], "1"
	}
	if n == 1 {
		// a0 + 1/a1
		// a0*a1+1, a1
		return Add(Mul(list[0], list[1]), "1"), list[1]
	}
	//	fmt.Printf("getXY %d\n", n)
	pp, qq := getXY(list, n-2)
	p, q := getXY(list, n-1)

	k := (n - 1) % (len(list) - 1)
	k = k + 1
	an := list[k]
	pn := Add(Mul(p, an), pp)
	qn := Add(Mul(q, an), qq)
	pm[n] = pn
	qm[n] = qn
	return pn, qn
}

func isSquare(n int) bool {
	v := IntSqrt(n)
	return v*v == n
}
