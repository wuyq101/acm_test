package main

import (
	"fmt"
	"strconv"
	"strings"
)

func Add(a, b string) string {
	va := revert([]byte(a))
	vb := revert([]byte(b))
	buf := make([]byte, 0, len(a)+len(b))
	i, j := 0, 0
	carry := 0
	for i < len(a) || j < len(b) {
		sum := carry
		if i < len(a) {
			sum += int(va[i] - '0')
			i++
		}
		if j < len(b) {
			sum += int(vb[j] - '0')
			j++
		}
		buf = append(buf, byte(sum%10+'0'))
		carry = sum / 10
	}
	if carry > 0 {
		buf = append(buf, byte(carry+'0'))
	}
	return string(revert(buf))
}

func Sub(a, b string) string {
	if Cmp(a, b) < 0 {
		fmt.Printf("warning: %s < %s\n", a, b)
		panic(a)
		return "-" + Sub(b, a)
	}
	va := revert([]byte(a))
	vb := revert([]byte(b))
	buf := make([]byte, 0, len(a)+len(b))
	i, j := 0, 0
	carry := 0
	for i < len(va) || j < len(vb) {
		sum := carry
		if i < len(va) {
			sum += int(va[i] - '0')
			i++
		}
		if j < len(vb) {
			sum -= int(vb[j] - '0')
			j++
		}
		if sum < 0 {
			sum += 10
			carry = -1
		} else {
			carry = 0
		}
		buf = append(buf, byte(sum+'0'))
	}
	s := string(revert(buf))
	for len(s) > 1 && s[0] == '0' {
		s = s[1:]
	}
	return s
}

func Cmp(a, b string) int {
	if len(a) > len(b) {
		return 1
	}
	if len(a) < len(b) {
		return -1
	}
	va := []byte(a)
	vb := []byte(b)
	for i := 0; i < len(a); i++ {
		if va[i] > vb[i] {
			return 1
		}
		if va[i] < vb[i] {
			return -1
		}
	}
	return 0
}

func Mul(a, b string) string {
	va := revert([]byte(a))
	vb := revert([]byte(b))
	buf := make([]int, len(a)+len(b))
	for i := 0; i < len(va); i++ {
		for j := 0; j < len(vb); j++ {
			sum := int(va[i]-'0') * int(vb[j]-'0')
			buf[i+j] += sum
		}
	}
	vc := make([]byte, len(buf))
	carry := 0
	for i := 0; i < len(buf); i++ {
		sum := buf[i] + carry
		vc[i] = byte(sum%10 + '0')
		carry = sum / 10
	}
	s := string(revert(vc))
	for len(s) > 0 && s[0] == '0' {
		s = s[1:]
	}
	return s
}

// 整除
func DIV(a, b string) string {
	if b == "1" {
		return a
	}

	if Cmp(a, b) < 0 {
		return "0"
	}
	//	fmt.Printf("divide %s, %s\n", a, b)

	// 取a的前b位，一直减去b
	size := len(b)
	pa := a[0:size]
	pl := a[size:]
	if Cmp(pa, b) < 0 {
		pa = a[0 : size+1]
		pl = a[size+1:]
	}
	cnt := 0
	for Cmp(pa, b) >= 0 {
		//		fmt.Printf("pa, b = %s, %s\n", pa, b)
		pa = Sub(pa, b)
		cnt += 1
	}

	//	fmt.Printf("pa=%s, pl=%s\n", pa, pl)
	a = pa + pl
	for len(a) > 1 && a[0] == '0' {
		a = a[1:]
	}
	result := strconv.Itoa(cnt) + strings.Repeat("0", len(pl))
	return Add(result, DIV(a, b))
}

// 取模
func MOD(a, b string) string {
	//	fmt.Printf("MOD(%s, %s)\n", a, b)
	if b == "1" {
		return "0"
	}
	if Cmp(a, b) < 0 {
		return a
	}
	d := DIV(a, b)
	return Sub(a, Mul(b, d))
}

func Pow(a string, n int) string {
	if n == 0 {
		return "1"
	}
	if n%2 == 0 {
		s := Pow(a, n/2)
		return Mul(s, s)
	}
	return Mul(a, Pow(a, n-1))
}

func revert(buf []byte) []byte {
	i, j := 0, len(buf)-1
	for i < j {
		buf[i], buf[j] = buf[j], buf[i]
		i++
		j--
	}
	return buf
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func GCD(a, b string) string {
	//	fmt.Printf("GCD(%s, %s)\n", a, b)
	if b == "0" {
		return a
	}
	return GCD(b, MOD(a, b))
}

func DigitSum(s string) int {
	sum := 0
	for _, c := range s {
		sum += int(c - '0')
	}
	return sum
}
