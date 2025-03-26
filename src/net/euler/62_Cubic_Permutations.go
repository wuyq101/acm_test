package main

import (
	"fmt"
	"sort"
	"strconv"
)

/**
 * https://projecteuler.net/problem=62
 */
func main() {

	m := make(map[string]int)
	cubes := make([]int, 0)
	for i := 0; i < 10000; i++ {
		cubes = append(cubes, i*i*i)
		m[hash(cubes[i])]++
	}

	list := make([]string, 0)
	for k, v := range m {
		if v == 5 {
			list = append(list, k)
		}
	}
	fmt.Printf("max=%v\n", list)
	for _, v := range cubes {
		if m[hash(v)] == 5 {
			fmt.Printf("%d\n", v)
		}
	}
}

func hash(n int) string {
	s := strconv.Itoa(n)
	b := []byte(s)
	sort.Slice(b, func(i, j int) bool {
		return b[i] < b[j]
	})
	return string(b)
}
