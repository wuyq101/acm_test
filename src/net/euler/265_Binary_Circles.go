package main

import "fmt"

func main() {
	Sum = 0
	used[0] = 1
	dfs(0, 0, N)
	fmt.Printf("sum=%d\n", Sum)

}

var N = 5
var Sum = int64(0)
var used = make([]int, 1<<N)
var MASK = 1<<N - 1

func dfs(n, pre, depth int) {
	//	fmt.Printf("n=%08b, pre=%03b, depth=%d\n", n, pre, depth)
	if depth == 1<<N {
		for k := 1; k <= N-1; k++ {
			// 取n的最后k位，补充N-k个0
			v := n & ((1 << k) - 1) << (N - k)
			if used[v] == 1 {
				return
			}
		}
		Sum += int64(n)
		return
	}
	// 下一个是用0
	next := (pre << 1) & MASK
	if used[next] == 0 {
		used[next] = 1
		dfs(n<<1, next, depth+1)
		used[next] = 0
	}
	// 下一个是用1
	next = ((pre << 1) | 1) & MASK
	if used[next] == 0 {
		used[next] = 1
		dfs(n<<1|1, next, depth+1)
		used[next] = 0
	}
}
