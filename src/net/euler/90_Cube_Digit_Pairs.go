package main

import (
	"fmt"
	"sort"
	"strings"
)

func main() {

	// 两位的平方数: 01 04 09 16 25 36 49 64 81
	// 十位：0 1 2 3 4 6 8
	// 个位：1 4 5 6 9
	// 第一组 选6个数字
	// 第二组 选6个数字
	// 0 和 1，4，9要分开，这样可以组成 01 04 09
	// 2 和 5 也分开，可以组成25
	// 10^6 * 10^6 空间 10^12, 判断每一种，是否可以构成全部的平方数。
	nums := make([]int, 0)
	for i := 0; i < 1000000; i++ {
		if hasDifferentDigits(i) {
			nums = append(nums, i)
		}
	}
	m := make(map[string]bool)
	result := make([]string, 0)
	for i := 0; i < len(nums); i++ {
		s := format(nums[i])
		if !m[s] {
			m[s] = true
			result = append(result, s)
		}
	}
	for i := 0; i < len(result); i++ {
		fmt.Printf("%s\n", result[i])
	}
	fmt.Printf("%d done\n", len(result))
	total := 0
	for i := 0; i < len(result); i++ {
		for j := i; j < len(result); j++ {
			if isValid(result[i], result[j]) {
				total++
				fmt.Printf("%s %s\n", result[i], result[j])
			}
		}
	}
	fmt.Printf("total = %d\n", total)
}

func isValid(a, b string) bool {
	for i := 1; i < 10; i++ {
		d := i * i
		s := fmt.Sprintf("%02d", d)
		s1 := s[0:1]
		s2 := s[1:2]
		if strings.Contains(a, s1) && strings.Contains(b, s2) {
			continue
		}
		if strings.Contains(b, s1) && strings.Contains(a, s2) {
			continue
		}
		// 6 和 9特殊处理
		if s1 == "6" {
			// 64
			if strings.Contains(a, s2) && (strings.Contains(b, "6") || strings.Contains(b, "9")) {
				continue
			}
			if strings.Contains(b, s2) && (strings.Contains(a, "6") || strings.Contains(a, "9")) {
				continue
			}
		}
		if s2 == "6" || s2 == "9" {
			// 09 16 36 49
			if strings.Contains(a, s1) && (strings.Contains(b, "6") || strings.Contains(b, "9")) {
				continue
			}
			if strings.Contains(b, s1) && (strings.Contains(a, "6") || strings.Contains(a, "9")) {
				continue
			}
		}
		return false
	}
	return true
}

func format(n int) string {
	s := fmt.Sprintf("%06d", n)
	buf := []byte(s)
	sort.Slice(buf, func(i, j int) bool {
		return buf[i] < buf[j]
	})
	return string(buf)
}

func hasDifferentDigits(n int) bool {
	// 123456
	flag := make([]bool, 10)
	for i := 0; i < 6; i++ {
		j := n % 10
		n /= 10
		if flag[j] {
			return false
		}
		flag[j] = true
	}
	return true
}
