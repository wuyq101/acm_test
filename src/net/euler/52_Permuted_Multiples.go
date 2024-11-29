package main

import (
	"fmt"
	"sort"
	"strconv"
)

/**
 * https://projecteuler.net/problem=52
 */
func main() {
	i := 1
	for {
		a := normal(i)
		b := normal(i * 2)
		c := normal(i * 3)
		d := normal(i * 4)
		e := normal(i * 5)
		f := normal(i * 6)
		if a == b && b == c && c == d && d == e && e == f {
			fmt.Println(i)
			break
		}
		i++
	}
}

func normal(n int) string {
	s := strconv.Itoa(n)
	buf := []byte(s)
	sort.Slice(buf, func(i, j int) bool {
		return buf[i] < buf[j]
	})
	return string(buf)
}
