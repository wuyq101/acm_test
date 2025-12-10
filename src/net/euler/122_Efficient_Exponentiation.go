package main

import "fmt"

func main() {
	/*
		k := 15
		best = k + 1
		dfs(0, k, []int{1})
		fmt.Printf("%d: %d\n", k, best)
		return
	*/

	sum := 0
	for k := 1; k <= 200; k++ {
		best = k - 1
		t, ok := bestSet[k]
		if ok && t < best {
			best = t
		}
		dfs(0, k, []int{1})
		sum += best
		fmt.Printf("%d: %d\n", k, best)
	}
	fmt.Printf("sum=%d\n", sum)
}

var best int

var bestSet = map[int]int{}

func dfs(cnt, k int, list []int) {
	if contains(list, k) && cnt < best {
		best = cnt
		fmt.Printf("cnt=%d,k=%d,list=%v\n", cnt, k, list)
		return
	}
	if cnt >= best {
		//		fmt.Printf("break,cnt=%d, best=%d, list=%v\n", cnt, best, list)
		return
	}
	size := len(list)
	for i := size - 1; i >= 0; i-- {
		for j := i; j >= 0; j-- {
			v := list[i] + list[j] // 做一次乘法
			// 记录一下中间结果
			a, ok := bestSet[v]
			if !ok {
				bestSet[v] = cnt + 1
			} else if cnt+1 < a {
				bestSet[v] = cnt + 1
			}
			if v > k {
				continue
			}
			if contains(list, v) {
				continue
			}
			// 严格递增
			if list[len(list)-1] > v {
				continue
			}

			// 有效的乘法
			list = append(list, v)
			dfs(cnt+1, k, list)
			list = list[:len(list)-1]
		}
	}
}

func contains(list []int, k int) bool {
	for _, v := range list {
		if v == k {
			return true
		}
	}
	return false
}
