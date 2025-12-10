package main

import (
	"fmt"
	"sort"
	"strings"
)

func main() {
	sum := 0
	for i := 1; i < 100; i++ {
		cnt = 0
		m = map[string]bool{}
		dfs(i, nil)
		fmt.Printf("i=%d, cnt=%d\n", i, cnt)
		sum += cnt
	}
	fmt.Printf("sum = %d\n", sum)
}

func hash(path []string) string {
	// 最后一个保持不变
	last := path[len(path)-1]
	path = path[:len(path)-1]
	sort.Strings(path)
	return strings.Join(path, "") + last
}

var m = map[string]bool{}

var cnt = 0

var prefix = []string{"S", "D", "T"}

func dfs(n int, pre []string) {
	if n == 0 {
		last := pre[len(pre)-1]
		if !strings.HasPrefix(last, "D") {
			return
		}
		key := hash(pre)
		_, ok := m[key]
		if !ok {
			m[key] = true
			cnt++
			//			fmt.Printf("path %v, key=%s\n", pre, key)
		}
		return
	}
	if len(pre) == 3 {
		return
	}
	// 1-20 S D T
	// 25 S D
	for i := 1; i <= 25; i++ {
		if i >= 21 && i <= 24 {
			continue
		}
		for j := 1; j <= 3; j++ {
			if i == 25 && j == 3 {
				continue
			}
			if n >= i*j {
				dart := fmt.Sprintf("%s%d", prefix[j-1], i)
				pre = append(pre, dart)
				dfs(n-i*j, pre)
				pre = pre[:len(pre)-1]
			}
		}
	}
}
