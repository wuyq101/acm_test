package main

import "fmt"

/*
http://projecteuler.net/problem=151

求每次取的时候信封中只有一张纸的期望

按照状态转移的方式来计算
状态: <A2,A3,A4,A5>

第一次之后，
A2 = A3 = A4 = A5 = 1

第二步，每个25%的概率，变成其他状态

如果新得到的状态各个纸张的和是1，累加到结果中


*/

func main() {
	p := float64(0)
	S1 := State{Step: 1, P: 1, A: [4]int{1, 1, 1, 1}}
	list := make([]State, 0)
	list = append(list, S1)
	for len(list) > 0 {
		s := list[0]
		A := s.A
		sum := A[0] + A[1] + A[2] + A[3]
		if sum == 1 && s.Step < 15 {
			p += s.P
		}
		if sum == 1 {
			fmt.Printf("s = %v\n", s)
		}
		list = list[1:]
		next := s.Next()
		if len(next) > 0 {
			for _, v := range next {
				list = Append(list, v)
			}
		}
	}
	fmt.Printf("p = %f\n", p)
	p = dfs(1, 1, 1, 1)
	fmt.Printf("exp= %f\n", p)
}

func Append(list []State, s State) []State {
	// 查找到如果属于同一层，并且分布是一样的，直接概率相加
	idx := -1
	for i := 0; i < len(list); i++ {
		if list[i].Step == s.Step && list[i].A == s.A {
			idx = i
			break
		}
	}
	if idx >= 0 {
		list[idx].P += s.P
	} else {
		list = append(list, s)
	}
	return list
}

type State struct {
	Step int
	P    float64
	A    [4]int
}

func (s State) Next() []State {
	list := []State{}
	step := s.Step + 1
	A := s.A
	sum := A[0] + A[1] + A[2] + A[3]
	// A2的张数, 如果抽到A2， 分割之后用掉一张A5， A2的张数减1，A3，A4，A5的张数加1
	if A[0] > 0 {
		p := float64(A[0]) / float64(sum)
		list = append(list, State{Step: step, P: p * s.P, A: [4]int{A[0] - 1, A[1] + 1, A[2] + 1, A[3] + 1}})
	}
	// A3的张数
	if A[1] > 0 {
		p := float64(A[1]) / float64(sum)
		list = append(list, State{Step: step, P: p * s.P, A: [4]int{A[0], A[1] - 1, A[2] + 1, A[3] + 1}})
	}
	// A4的张数
	if A[2] > 0 {
		p := float64(A[2]) / float64(sum)
		list = append(list, State{Step: step, P: p * s.P, A: [4]int{A[0], A[1], A[2] - 1, A[3] + 1}})
	}
	// A5的张数
	if A[3] > 0 {
		p := float64(A[3]) / float64(sum)
		list = append(list, State{Step: step, P: p * s.P, A: [4]int{A[0], A[1], A[2], A[3] - 1}})
	}
	return list
}

// 备忘录：用于保存已计算过状态的期望值，避免重复计算
var memo = make(map[[4]int]float64)

func dfs(a2, a3, a4, a5 int) float64 {
	// 这个是最后一步，排除掉
	if a2 == 0 && a3 == 0 && a4 == 0 && a5 == 1 {
		return 0
	}

	state := [4]int{a2, a3, a4, a5}
	if v, ok := memo[state]; ok {
		return v
	}

	exp := 0.0
	sum := a2 + a3 + a4 + a5
	if sum == 1 {
		exp = 1.0
	}
	if a2 > 0 {
		exp += float64(a2) / float64(sum) * dfs(a2-1, a3+1, a4+1, a5+1)
	}
	if a3 > 0 {
		exp += float64(a3) / float64(sum) * dfs(a2, a3-1, a4+1, a5+1)
	}
	if a4 > 0 {
		exp += float64(a4) / float64(sum) * dfs(a2, a3, a4-1, a5+1)
	}
	if a5 > 0 {
		exp += float64(a5) / float64(sum) * dfs(a2, a3, a4, a5-1)
	}
	memo[state] = exp
	return exp
}
