package main

import (
	"fmt"
	"math"
	"os"
	"strings"
)

func main() {
	buf, err := os.ReadFile("words.txt")
	if err != nil {
		panic(err)
	}
	words := strings.Split(string(buf), ",")
	for i, word := range words {
		if len(word) == 0 {
			continue
		}
		word = strings.ReplaceAll(word, "\"", "")
		for j := i + 1; j < len(words); j++ {
			wj := strings.ReplaceAll(words[j], "\"", "")
			if findByLetter(word, wj) {
				fmt.Printf("%s <---> %s\n", word, wj)
				findMaxSquare(word, wj)
			}
		}
	}
}

func findByLetter(a, b string) bool {
	cnt := map[rune]int{}
	for _, c := range a {
		cnt[c]++
	}
	for _, c := range b {
		cnt[c]--
	}
	for _, v := range cnt {
		if v != 0 {
			return false
		}
	}
	return true
}

func findMaxSquare(a, b string) int {
	size := len(a)
	fmt.Printf("a=%s, b=%s size=%d\n", a, b, size)
	min := int(math.Pow10(size - 1))
	max := int(math.Pow10(size)) - 1
	for i := min; i <= max; i++ {
		if !isSquare(i) {
			continue
		}
		// i--->a
		// THROW <---> WORTH
		// 92416
		m := mapping(a, i)
		if len(m) == 0 {
			continue
		}
		//		fmt.Printf("mapping %s %d, %v\n", a, i, m)
		// map--- [t:9, h:2, r:4, o:1, w:6]
		// j--->b
		// j : 61492
		j := gen(b, m)
		if isSquare(j) {
			fmt.Printf("find a pair %s %s %d %d\n", a, b, i, j)
		}

	}
	return 0
}

func gen(s string, m map[byte]int) int {
	v := 0
	for _, c := range s {
		b := byte(c)
		v = v*10 + m[b]
	}
	// 检查是否0开头
	b := byte(s[0])
	if m[b] == 0 {
		return 3 // 返回一个非平方数
	}
	return v
}

func mapping(s string, n int) map[byte]int {
	// 字母映射到数字
	m := make(map[byte]int)
	for i := len(s) - 1; i >= 0; i-- {
		b := s[i]
		v := n % 10
		n /= 10
		pre, ok := m[b]
		if ok && pre != v {
			return make(map[byte]int)
		}
		m[b] = v
	}
	// 数字映射到字母
	k := make(map[int]byte)
	for b, v := range m {
		k[v] = b
	}
	if len(k) != len(m) {
		return make(map[byte]int)
	}
	return m
}

func isSquare(n int) bool {
	a := int(math.Sqrt(float64(n)))
	return a*a == n
}
