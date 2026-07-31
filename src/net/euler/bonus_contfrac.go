package main

import (
	"fmt"
)

/*

x = ai - 1/x

x = (ai*x - 1) / (1*x + 0)

正对每个参数ai，都可以得到一个举证

Mi = (
	ai, -1
	1 , 0
)

M = M0 * M1 * ... * Mn
= (
	A, B
	C, D
)

x = A*x + B / C*x + D
C*x^2 + (D-A)*x - B = 0
∆ = (D-A)^2 + 4*B*C < 0

Mi的行列式 = ai*0 - (-1)*1 = 1
M的行列式 = 多个1相乘 = 1 = AD - BC
得到BC = AD - 1, 带入∆得到
∆ = (D-A)^2 + 4*(AD-4) = (A+D)^2 - 4 < 0
(A+D)^2 < 4
|A+D| < 2
由于A,D都是整数，所以A+D = -1,0,1 只能是这些值

ai是非负整数，Mi的连乘如果让最后得到的A+D足够小，Mi 中的ai只能取0或者1.

使用搜索的方法来寻找参数

假设现在已经进行到第i个参数，那么可以得到
假设i=7, 后面还有5个参数
M = M0 * M1 * ... * Mi =
(
	A B
	C D
)

如果后面5个参数都是0 or 1构成，那么
R = M6*M7*...*M11*M12, 一共有2^5 = 32 种可能

假设R = (
	x y
	z w
)

M*R = (
	A*x + B*z, A*y + B*w
	C*x + D*z, C*y + D*w
)

这个矩阵的Trace = A*x + B*z + C*y + D*w, 这个值必须在-1和1之间
如果这32种可能，都已经不满足来，那么说明当前的搜索已经进入死胡同，M是无效的，放弃。a7后面更大的数值，就不用再考虑了


dfs搜索，假设结果是a0,a1,...an, 这个序列经过旋转之后，也是满足条件的。
所示搜索的时候只求最大值位与最后一位的M





*/

func main() {
	/*
		// 使用dp的方式来求解
		dp := make([]map[Matrix]int, N+1)
		for i := 0; i <= N; i++ {
			dp[i] = make(map[Matrix]int)
		}

		dp[0][Matrix{1, 0, 0, 1}] = 1 // 单位矩阵
		// 从第i层开始，往下计算所有第i+1层的可能性
		for i := 0; i < N; i++ {
			for m, cnt := range dp[i] {
				// 对这个当前矩阵 * ai
				for ai := 0; ai <= 13; ai++ {
					next := Mul(m, Matrix{ai, -1, 1, 0})
					if next.MaxAbs() >= 100000 {
						break
					}
					dp[i+1][next] += cnt
				}
			}
			fmt.Printf("dp[%d]: len %d\n", i+1, len(dp[i+1]))
			// save dp[i+1] into file
			f, _ := os.Create(fmt.Sprintf("dp_%d.txt", i+1))
			for m, cnt := range dp[i+1] {
				f.WriteString(fmt.Sprintf("%v %d\n", m, cnt))
			}
			f.Close()
		}
		return
	*/

	/*
		ncfs := make([][][]int, 14)
		for i := 0; i < 14; i++ {
			ncfs[i] = make([][]int, 0)
		}
		ncfs[1] = [][]int{{0}, {1}}
		ncfs[2] = [][]int{{1, 1}, {1, 2}, {2, 1}, {1, 3}, {3, 1}}

		for i := 1; i <= 6; i++ {
			ncfs[i] = dup(ncfs[i])
			fmt.Printf("len(ncfs[%d])=%d\n", i, len(ncfs[i]))
			for _, seq := range ncfs[i] {
				fmt.Printf("seq=%v\n", seq)
				results := G(seq)
				for _, result := range results {
					fmt.Printf("%d --> %v\n", i, result)
					idx := len(result)
					if idx <= 12 {
						ncfs[idx] = append(ncfs[idx], result)
					}
				}
			}
		}
		for _, v := range ncfs[3] {
			fmt.Printf("ncfs[3]=%v\n", v)
		}
		//	return
	*/

	args := make([]int, 13)
	answers = make([]map[string]bool, 13)
	for i := 0; i <= N; i++ {
		args[i] = -1
		answers[i] = make(map[string]bool)
	}

	cnt = 0
	/*
		dfs(11, 0, Matrix{1, 0, 0, 1}, args)
		fmt.Printf("cnt=%d\n", cnt)
		return
	*/

	for n := 1; n <= 12; n++ {
		for j := 0; j < N; j++ {
			args[j] = -1
		}
		fmt.Printf("dfs(%d)\n", n)
		dfs(n, 0, Matrix{1, 0, 0, 1}, args)
	}
	fmt.Printf("cnt=%d\n", cnt)
}

var N = 12
var cnt int

var answers = []map[string]bool{}

// 一共n个参数, 当前已经枚举到第i个，i的下一个取值是ai
// 当前已经得到的矩阵是M
func dfs(n, i int, m Matrix, args []int) bool {
	//	fmt.Printf("dfs(%d, %s, %v)\n", i, m, args)
	if i >= n {
		// 已经枚举到最后一个参数，检查m是否符合要求
		if m.Trace() >= -1 && m.Trace() <= 1 {
			fmt.Printf("find one answer: %d %s, %v\n", n, m, args)
			cnt++
			return true
		}
		return false
	}
	left := 0
	right := n + 1
	if i == n-1 {
		for j := 0; j < n; j++ {
			if args[j] > left {
				left = args[j]
			}
		}
		// 只剩余最后一个参数，假设当前矩阵是 A B C D, 下一个参数ai，求ai的范围
		// M*Matrix{ai, -1, 1, 0})  在[-1, 1]
		// tr = A*ai+B-C
		if m.A != 0 {
			a := (-1 - m.B + m.C) / m.A
			b := (1 - m.B + m.C) / m.A
			if a > b {
				a, b = b, a
			}
			// ai的范围是[a, b]
			if a > left {
				left = a
			}
			if b < right {
				right = b
			}
		} else {
			// A = 0, tr = B - C, 和下一个ai无关了，只要看B-C的范围
			tr := m.B - m.C
			if !(tr >= -1 && tr <= 1) {
				return false
			}
		}
	}

	for ai := left; ai <= right; ai++ {
		// 只计算这绝对有前途的一步
		C := Mul(m, Matrix{ai, -1, 1, 0})
		args[i] = ai
		dfs(n, i+1, C, args)
		args[i] = -1
	}
	// 如果flag是false，说明m, 前i-1个参数，已经是失败的结果，可以标记一下
	// 说明m在后面的剩余参数中已经找不到合理解
	return false
}

func Mul(a, b Matrix) Matrix {
	A, B, C, D := a.A, a.B, a.C, a.D
	x, y, z, w := b.A, b.B, b.C, b.D
	return Matrix{
		A*x + B*z, A*y + B*w,
		C*x + D*z, C*y + D*w,
	}
}

type Matrix struct {
	A, B, C, D int
}

func (m Matrix) Trace() int {
	return m.A + m.D
}

func (m Matrix) MaxAbs() int {
	return max(max(abs(m.A), abs(m.B)), max(abs(m.C), abs(m.D)))
}

func abs(a int) int {
	if a < 0 {
		return -a
	}
	return a
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func (m Matrix) String() string {
	return fmt.Sprintf("{%d %d %d %d}", m.A, m.B, m.C, m.D)
}

func G(list []int) [][]int {
	result := make([][]int, 0)
	seeds := p(list)
	for _, v := range seeds {
		result = append(result, g(v)...)
		result = append(result, h(v)...)
	}
	return dup(result)
}

func dup(list [][]int) [][]int {
	m := make(map[string]bool)
	result := make([][]int, 0)
	for _, v := range list {
		key := fmt.Sprintf("%v", v)
		if !m[key] {
			m[key] = true
			result = append(result, v)
		}
	}
	return result
}

// 旋转操作
// [a,b,c] -> [b,c,a] ---> [c,a,b]
func p(list []int) [][]int {
	result := make([][]int, 0)
	fn := func(i int) []int {
		s := make([]int, len(list))
		idx := 0
		for k := i; k < len(list); k++ {
			s[idx] = list[k]
			idx++
		}
		for k := 0; k < i; k++ {
			s[idx] = list[k]
			idx++
		}
		return s
	}
	for i := 0; i < len(list); i++ {
		s := fn(i)
		result = append(result, s)
	}
	return dup(result)
}

// (a,b) -> (a+1, 1, b+1)
func g(list []int) [][]int {
	if len(list) < 2 {
		return nil
	}
	// 第i个和第i+1个元素中间增加1
	fn := func(i int) []int {
		s := make([]int, len(list)+1)
		for k := 0; k < i; k++ {
			s[k] = list[k]
		}
		s[i] = list[i] + 1
		s[i+1] = 1
		s[i+2] = list[i+1] + 1
		for k := i + 2; k < len(list); k++ {
			s[k+1] = list[k]
		}
		return s
	}
	result := make([][]int, 0)
	for i := 0; i < len(list)-1; i++ {
		s := fn(i)
		result = append(result, s)
		result = append(result, p(s)...)
	}
	return result
}

// (c) --> (a,0,b), a+b=c
func h(list []int) [][]int {
	// 将第i个元素拆拆成 k, 0, ai-k
	fn := func(i, k int) []int {
		s := make([]int, len(list)+2)
		for j := 0; j < i; j++ {
			s[j] = list[j]
		}
		s[i] = k
		s[i+1] = 0
		s[i+2] = list[i] - k
		for j := i + 1; j < len(list); j++ {
			s[j+2] = list[j]
		}
		return s
	}
	result := make([][]int, 0)
	for i := 0; i < len(list); i++ {
		for k := 0; k <= list[i]; k++ {
			s := fn(i, k)
			result = append(result, s)
			result = append(result, p(s)...)
		}
	}
	return result
}
