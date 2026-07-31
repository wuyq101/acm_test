package main

import "fmt"

func main() {
	f(7, "")
}

func f(n int, pre string) {
	if len(pre) == 2*n {
		fmt.Printf("%s\n", pre)
		return
	}
	// 当前这个位置，可以摆放哪个数字
	for i := n; i >= 1; i-- {
		// 当前这个位置，摆放i，是否是一个合法的摆放
		// 先检查是否已经有两个i了
		cnt := 0
		for j := 0; j < len(pre); j++ {
			if pre[j] == byte(i+'0') {
				cnt++
			}
		}
		if cnt == 2 {
			continue
		}
		// 检查当前这个位置是否可以摆放i
		next := pre + string(i+'0')
		if check(next) {
			f(n, next)
		}
	}
}

func check(s string) bool {
	visit := make([]bool, 10)
	for i := 0; i < len(s); i++ {
		k := int(s[i] - '0')
		if visit[k] {
			j := i - (k + 1)
			if j >= 0 {
				if s[j] != s[i] {
					return false
				}
			}
			continue
		}
		visit[k] = true
		// 目标位置
		j := i + (k + 1)
		if j < len(s) {
			if s[j] != s[i] {
				return false
			}
		}
	}
	return true
}
