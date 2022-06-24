package main

import "fmt"

/**
A string is good if there are no repeated characters.

Given a string s​​​​​, return the number of good substrings of length three in s​​​​​​.

Note that if there are multiple occurrences of the same substring, every occurrence should be counted.

A substring is a contiguous sequence of characters in a string.
*/
func main() {
	strs := []string{"xyzzaz", "aababcabc", "npdrlvffzefb"}
	for _, s := range strs {
		fmt.Printf("%s--->%d\n", s, countGoodSubstrings(s))
	}
}

// countGoodSubStrings
// 暴力解法，逐个匹配查找
// 稍微改进, 部分模式，可以快速跳过，减少次数
func countGoodSubstrings(s string) int {
	buf := []byte(s)
	lastIdx := len(buf) - 3
	cnt := 0
	i := 0
	for i <= lastIdx {
		a := buf[i]
		b := buf[i+1]
		c := buf[i+2]
		if c == b {
			i = i + 2
			continue
		}
		if a == c {
			i++
			continue
		}
		if a == b {
			i++
			continue
		}
		cnt++
		i++
	}
	return cnt
}
