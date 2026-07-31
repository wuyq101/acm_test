package main

import "fmt"

/*
https://projecteuler.net/problem=280
5*5的地图，蚂蚁从(2,2)出发，四个方向随机游走，

在最下方一排 y=0,x=0..4, 每个格子中有一颗种子，蚂蚁走到这些格子的时候，会将种子带着，
到达y=4,x=0..4的时候，最上面一行的时候，蚂蚁会将种子放下。

求蚂蚁搬完所有的种子，需要的步数期望值

整体的状态只和最下一行和最上一行有关

E(S) = 1 + 1/k * ∑E(S')

S状态下的期望，由前一个状态走1步之后得到。

E(i,carry,B,T), i当前位置，carry是否携带种子，B，bottom的状态(0---31), T,top的状态(0---31)

i，从0----24. 将二维的格子一唯化。
(2,2) -->2*5+2 = 12

初识的时候（12,0,0,31) E的值是0，初识的时候，假设种子都已经在Top了，这个时候需要的期望步数是0步。


状态：S的定义
i,位置，0---24. 一共需要5个bit
carry：0，1, 需要1个bit
B：0--31，需要5个bit
T: 0--31, 需要5个bit，
那么一共需要5*3+1 = 16个bit，2^16次。






*/

func main() {
	dx := []int{-1, 0, 1, 0}
	dy := []int{0, 1, 0, -1}
	S := 1 << 16
	E := make([]float64, S)
	// 修正：统一B在0-4位，T在5-9位。
	// 初始状态：(2,2)位置为12，空手carry=0，顶部T=0，底部B=31
	start := 12<<11 | 0<<10 | 0<<5 | 31
	//	pre := E[start]
	validStates := make([]int, 0)
	for s := 0; s < S; s++ {
		i := s >> 11
		if i > 24 {
			continue
		}
		carry := (s >> 10) & 1
		t := (s >> 5) & 31
		b := s & 31
		if cntBits(b)+cntBits(t)+carry != 5 {
			continue
		}
		validStates = append(validStates, s)
	}
	fmt.Printf("len=%d\n", len(validStates))
	dp := make([][]float64, 2)
	dp[0] = E
	dp[1] = make([]float64, S)
	idx := 1
	for {
		// 循环迭代，更新E
		next := dp[idx]
		idx = 1 - idx
		maxDiff := 0.0 // 控制全局收敛，最大的变化量
		for _, s := range validStates {
			//	对于状态s来说，一共16位，最高的5位代表位置
			i := s >> 11
			r, c := i/5, i%5
			// 第10位，carry
			carry := (s >> 10) & 1
			// T
			t := (s >> 5) & 31
			if t == 31 {
				next[s] = 0.0
				continue
			}
			// B
			b := s & 31
			k := 0.0
			sum := 0.0
			for j := 0; j < 4; j++ {
				x := c + dx[j]
				y := r + dy[j]
				if !(x >= 0 && x < 5 && y >= 0 && y < 5) {
					continue
				}
				k++
				nc := carry
				nb := b
				nt := t
				if nc == 1 {
					// 当前携带种子，
					// x，y的位置
					if y == 4 && nt&(1<<x) == 0 {
						// 到达最上一行，检查x位置是否已经被种子覆盖
						// x位置没有种子
						nc = 0
						nt |= 1 << x
					}
				} else {
					// 当前没有携带种子
					if y == 0 && nb&(1<<x) != 0 {
						// x位置是否有种子，有的话，就需要捡起来
						nc = 1
						nb ^= 1 << x
					}
				}
				nextState := state(y, x, nc, nb, nt)
				sum += E[nextState]
			}
			next[s] = 1 + sum/k
			df := abs(next[s] - E[s])
			if df > maxDiff {
				maxDiff = df
			}
		}
		E = next
		// 停止迭代，认为已经收敛
		if maxDiff <= 1e-12 {
			fmt.Printf("finished\n")
			break
		}
		//	fmt.Printf("pre = %.12f, cur=%.12f, max diff=%.12f\n", pre, E[start], maxDiff)
		//pre = E[start]
	}
	fmt.Printf("E[%d] = %f\n", start, E[start])
}

func state(r, c, carry, B, T int) int {
	i := r*5 + c
	return i<<11 | carry<<10 | T<<5 | B
}

func abs(x float64) float64 {
	if x < 0 {
		return -x
	}
	return x
}

func cntBits(n int) int {
	cnt := 0
	for n > 0 {
		n = n & (n - 1)
		cnt++
	}
	return cnt
}
