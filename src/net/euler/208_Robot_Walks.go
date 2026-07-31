package main

import "fmt"

/*
https://projecteuler.net/problem=208
本质上是在5个方向上进行移动，最后回到原点的要求是每个方向都移动了相同的步数

N = 5*k, 每个方向上都走了k步
方向d0,d1,d2,d3,d4
当前方向current, 如果顺时针，方向会变成 current+1, 作用在边 d[current+1]上
如果是逆时针，方向会变成 current-1, 作用在边 d[current]上
状态包含：当前方向，每个方向上走的步数，步数不超过14（4位二进制够用)
方向 0,1,2,3,4, 3为二进制，和方向一样处理，也用4位，这样一来一个state需要4*6=24位，int32够用了。
工具函数 get(state,i) 返回4i位，4i+1位，4i+2位，4i+3位的值
*/
func main() {
	N := 70
	k := N / 5
	dp := make(map[int]int64)
	dp[0] = 1
	for step := 0; step < N; step++ {
		next := make(map[int]int64)
		for state, cnt := range dp {
			d := get(state, 0)
			// 顺时针
			i := (d + 1) % 5
			a := set(state, 0, i)
			c := get(state, i+1) + 1
			a = set(a, i+1, c)
			if c <= k {
				next[a] += cnt
			}

			// 逆时针
			i = (d + 4) % 5
			b := set(state, 0, i)
			c = get(state, d+1) + 1
			b = set(b, d+1, c)
			if c <= k {
				next[b] += cnt
			}
		}
		dp = next
	}

	fmt.Printf("i=%d dp=%v\n", N, len(dp))
	sum := int64(0)
	for i := 0; i < 5; i++ {
		state := set(0, 0, i)
		for j := 1; j <= 5; j++ {
			state = set(state, j, k)
		}
		sum += dp[state]
	}
	fmt.Printf("total = %d\n", sum)
}

func get(state, i int) int {
	return (state >> (i * 4)) & 15
}

func set(state, i, v int) int {
	return (state & ^(15 << (i * 4))) | (v << (i * 4))
}
