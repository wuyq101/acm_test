package main

import (
	"fmt"
	"strconv"
	"strings"
)

/*
-- 二分法
根号a,求100位小数
设x = a^(1/2), (x*10^100)^2 = a*10^200, 整数部分就是a^(1/2)的100位小数
*/
func main() {
	a := "3"
	a += strings.Repeat("0", 200)
	result := sqrt(a)
	fmt.Printf("sqrt(%s) = %s, len=%d\n", a, result, len(result))
	fmt.Printf("%d\n", digitSum(result))
	total := 0
	for i := 1; i <= 100; i++ {
		if isSquare(i) {
			continue
		}
		a = strconv.Itoa(i)
		a += strings.Repeat("0", 220)
		result = sqrt(a)
		fmt.Printf("sqrt(%d) = %s, len=%d\n", i, result, len(result))
		sum := digitSum(result)
		fmt.Printf("%d\n", sum)
		total += sum
	}
	fmt.Printf("total = %d\n", total)
}

func isSquare(n int) bool {
	for i := 1; i*i <= n; i++ {
		if i*i == n {
			return true
		}
	}
	return false
}

func digitSum(a string) int {
	a = a[0:100]
	sum := 0
	for _, c := range a {
		sum += int(c - '0')
	}
	return sum
}

func sqrt(n string) string {
	left := "1"
	right := n
	mid := left
	for Cmp(left, right) < 0 {
		mid = Div2(Add(left, right))
		//fmt.Printf("left = %s, right = %s, mid = %s\n", left, right, mid)
		prod := Mul(md, mid)
		if Cmp(prod, n) > 0 {
			right = mid
		} else {
			left = Add(mid, "1")
		}
		if Add(left, "1") == right {
			break
		}

	}
	return mid
}

func Div2(a string) string {
	a = Mul(a, "5")
	last := a[len(a)-1]
	a = a[:len(a)-1]
	if last >= '5' {
		a = Add(a, "1")
	}
	return a
}

func Cmp(a, b string) int {
	if len(a) > len(b) {
		return 1
	}
	if len(a) < len(b) {
		return -1
	}
	for i := 0; i < len(a); i++ {
		if a[i] > b[i] {
			return 1
		}
		if a[i] < b[i] {
			return -1
		}
	}
	return 0
}

func Mul(a, b string) string {
	a = reverse(a)
	b = reverse(b)
	n := make([]int, len(a)+len(b))
	for i := 0; i < len(a); i++ {
		for j := 0; j < len(b); j++ {
			n[i+j] += int(a[i]-'0') * int(b[j]-'0')
		}
	}
	carry := 0
	for i := 0; i < len(n); i++ {
		n[i] += carry
		carry = n[i] / 10
		n[i] = n[i] % 10
	}
	buf := ""
	for i := 0; i < len(n); i++ {
		buf = strconv.Itoa(n[i]) + buf
	}
	for strings.HasPrefix(buf, "0") {
		buf = buf[1:]
	}
	return buf
}

func Add(a, b string) string {
	// 整数部分相加
	size := max(len(a), len(b))
	ints := make([]int, size+1)
	a = reverse(a)
	b = reverse(b)
	carry := 0
	for i := 0; i < len(ints); i++ {
		ints[i] += carry
		if i < len(a) {
			ints[i] += int(a[i] - '0')
		}
		if i < len(b) {
			ints[i] += int(b[i] - '0')
		}
		carry = ints[i] / 10
		ints[i] = ints[i] % 10
	}
	buf := ""
	for i := 0; i < len(ints); i++ {
		buf = strconv.Itoa(ints[i]) + buf
	}
	for strings.HasPrefix(buf, "0") {
		buf = buf[1:]
	}
	return buf
}

func reverse(a string) string {
	buf := []byte(a)
	i, j := 0, len(buf)-1
	for i < j {
		buf[i], buf[j] = buf[j], buf[i]
		i++
		j--
	}
	return string(buf)
}
