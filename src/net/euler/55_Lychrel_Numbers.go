package main

import (
	"fmt"
	"strconv"
)

/**
 * https://projecteuler.net/problem=55
 */
func main() {
	cnt := 0
	for i := 1; i < 10000; i++ {
		if isLychrel(i) {
			cnt++
		}
	}
	fmt.Printf("%d\n", cnt)
}

func isLychrel(n int) bool {
	s := strconv.Itoa(n)
	for i := 0; i < 50; i++ {
		s = add(s, string(revert([]byte(s))))
		if isPalindrome(s) {
			fmt.Printf("%d-->%d\n", n, i+1)
			return false
		}
	}
	return true
}

func add(a, b string) string {
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

func revert(buf []byte) []byte {
	i, j := 0, len(buf)-1
	for i < j {
		buf[i], buf[j] = buf[j], buf[i]
		i++
		j--
	}
	return buf
}

func isPalindrome(s string) bool {
	b := []byte(s)
	i, j := 0, len(b)-1
	for i < j {
		if b[i] != b[j] {
			return false
		}
		i++
		j--
	}
	return true
}
