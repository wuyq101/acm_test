package main

import (
	"fmt"
	"sort"
)

/*

https://projecteuler.net/problem=194

dp(a,b,c) =



A:
*   *
  *
  *
  *
*   *

左边两个点记为L1,L2
中间三个点记为M1,M2,M3
右边两个点记为R1,R2

分两种情况讨论 a(c), c种颜色下，A的着色方案数

1. M1==M3 s(c)
2. M1==M3 d(s)

a(c) = s(c)*(c-1)+d(s)*(c-2), 同色的情况下，中间点M2 c-1; 异色的情况下，M2: c-2

1. M1==M3=X, 这两个点都选择了同一种颜色X
   X的可选情况, X!=L1 &&  X!=L2, c-2种

   1.1 R1==L2 1种
       R2!=L2,R2!=X, c-2
   1.2 R1!=L2, (R1!=L1,R1!=X,R1!=L2), c-3种
       R2!=L2,R2!=X, R2!=R1, c-3种

   c-2 + (c-3)(c-3) = c^2-5c+7

   s(c) = (c-2)(c^2-5c+7)

2. M1!=M2, M1=X, M2=Y

   2.1 X==L2 1
       Y!=L2, 因为X==L2, Y有c-1种
       2.1.1 R1==Y or R1==L2  2
             R2: c-2
       2.1.2 R1!=Y && R1!=L2

   2.2 X!=L2

   分类讨论还是太复杂了。

   使用着色多项式的方法 Chromatic Polynomial
   P(G,c):对于给定的图G，用不超过c种颜色的着色方案数

   核心定理：删除-收缩定理（Deletion-Contraction）
   P(G,c) = P(G-e,c) - P(G/e,c)
   G-e: 删除一条边e, 但是保留它的两个端点u,v。此时它们可以用相同颜色或者不同颜色
   G/e: 把u和v两个点合并成一个点t，所有连接到u,v的点，都连接到这个新的点t上,这意味着强制将u,v保持同色
   递归结束条件：空图P(G,c) = c^k, k=G的定点个数， 空图：G只有k个点，但是没有边

   G:(V,E)
   V: 顶点集合
   E: 边集合




*/

func main() {
	//A
	N := 7
	matrix := make([][]int, N)
	for i := 0; i < N; i++ {
		matrix[i] = make([]int, N)
	}
	g := &Graph{
		N: 7,
		G: matrix,
	}
	g.G[0][1] = 1
	g.G[1][0] = 1
	g.G[0][2] = 1
	g.G[2][0] = 1
	g.G[0][5] = 1
	g.G[5][0] = 1

	g.G[1][4] = 1
	g.G[4][1] = 1
	g.G[1][6] = 1
	g.G[6][1] = 1

	g.G[2][3] = 1
	g.G[3][2] = 1
	g.G[2][5] = 1
	g.G[5][2] = 1

	g.G[3][4] = 1
	g.G[4][3] = 1

	g.G[4][6] = 1
	g.G[6][4] = 1

	g.G[5][6] = 1
	g.G[6][5] = 1

	p := CP(g)
	printP(p)

	//B
	N = 7
	matrix = make([][]int, N)
	for i := 0; i < N; i++ {
		matrix[i] = make([]int, N)
	}
	g = &Graph{
		N: 7,
		G: matrix,
	}
	g.G[0][1] = 1
	g.G[1][0] = 1
	g.G[0][2] = 1
	g.G[2][0] = 1
	g.G[0][5] = 1
	g.G[5][0] = 1

	g.G[1][4] = 1
	g.G[4][1] = 1

	g.G[2][3] = 1
	g.G[3][2] = 1
	g.G[2][5] = 1
	g.G[5][2] = 1

	g.G[3][4] = 1
	g.G[4][3] = 1

	g.G[4][6] = 1
	g.G[6][4] = 1

	g.G[5][6] = 1
	g.G[6][5] = 1

	p = CP(g)
	printP(p)

	return

	/*
		a, b, c := 1, 0, 3
		v := N(a, b, c)
		fmt.Printf("v=%d\n", v)

		a, b, c = 0, 2, 4
		v = N(a, b, c)
		fmt.Printf("v=%d\n", v)

		a, b, c = 2, 2, 3
		v = N(a, b, c)
		fmt.Printf("v=%d\n", v)

		a, b, c = 25, 75, 1984
		v = N(a, b, c)
		fmt.Printf("v=%d\n", v)
	*/

}

func P(a, b int) int64 {
	dp := make([][]int64, a+1)
	for i := 0; i <= a; i++ {
		dp[i] = make([]int64, b+1)
	}
	for i := 1; i <= a; i++ {
		dp[i][0] = 1
	}
	for j := 1; j <= b; j++ {
		dp[0][j] = 1
	}
	for i := 1; i <= a; i++ {
		for j := 1; j <= b; j++ {
			dp[i][j] = (dp[i][j-1] + dp[i-1][j]) % M
		}
	}
	return dp[a][b]
}

var M = int64(100000000)

func N(a, b, c int) int64 {
	// 排列方案种数
	p := P(a, b)
	// 颜色方案总数
	ac := A(c) % M
	bc := B(c) % M
	// ac^a * bc^b * c *(c-1)
	color := int64(c * (c - 1))
	for i := 0; i < a; i++ {
		color = (color * ac) % M
	}
	for i := 0; i < b; i++ {
		color = (color * bc) % M
	}
	return p * color % M
}

func B(c int) int64 {
	cnt := int64(0)
	L1, L2 := 1, 2
	// case 1: M1==M3
	for M1 := 1; M1 <= c; M1++ {
		if M1 == L1 || M1 == L2 {
			continue
		}
		for R1 := 1; R1 <= c; R1++ {
			if R1 == L1 || R1 == M1 {
				continue
			}
			s := int64(c - 2)
			cnt += s
		}
	}
	scnt := (cnt % M) * int64(c-1)
	// case 2: M1!=M3
	cnt = 0
	for M1 := 1; M1 <= c; M1++ {
		if M1 == L1 {
			continue
		}
		for M3 := 1; M3 <= c; M3++ {
			if M3 == M1 || M3 == L2 {
				continue
			}

			for R1 := 1; R1 <= c; R1++ {
				if R1 == L1 || R1 == M1 {
					continue
				}
				// R2 != M3, R1
				s := int64(c - 2)
				if R1 == M3 {
					s = int64(c - 1)
				}
				cnt += s
			}
		}
	}
	dcnt := (cnt % M) * int64(c-2)

	return (scnt + dcnt) % M
}

func AA(c int) int64 {
	cnt := int64(0)
	L1, L2 := 1, 2
	// case 1: M1==M3
	for M1 := 1; M1 <= c; M1++ {
		if M1 == L1 || M1 == L2 {
			continue
		}
		for R1 := 1; R1 <= c; R1++ {
			if R1 == L1 || R1 == M1 {
				continue
			}
			s := int64(c)
			if R1 != L2 {
				s -= 3
			} else {
				s -= 2
			}
			cnt += s
		}
	}
	scnt := (cnt % M) * int64(c-1)
	// case 2: M1!=M3
	cnt = 0
	for R1 := 1; R1 <= c; R1++ {
		if R1 == L1 {
			continue
		}
		for R2 := 1; R2 <= c; R2++ {
			if R2 == R1 || R2 == L2 {
				continue
			}
			// M1 & M3
			// M1==2, M3:c-2
			// M1==R2, M3:c-2
			// M1!=2 && M1!=R1, M1: c
			cnt += 2 * int64(c-2)

		}
	}
	dcnt := (cnt % M) * int64(c-2)

	return (scnt + dcnt) % M
}
func A(c int) int64 {
	cnt := int64(0)
	L1, L2 := 1, 2
	// case 1: M1==M3
	for M1 := 1; M1 <= c; M1++ {
		if M1 == L1 || M1 == L2 {
			continue
		}
		for R1 := 1; R1 <= c; R1++ {
			if R1 == L1 || R1 == M1 {
				continue
			}
			s := int64(c)
			if R1 != L2 {
				s -= 3
			} else {
				s -= 2
			}
			cnt += s
		}
	}
	scnt := (cnt % M) * int64(c-1)
	// case 2: M1!=M3
	cnt = 0
	for M1 := 1; M1 <= c; M1++ {
		if M1 == L1 {
			continue
		}
		for M3 := 1; M3 <= c; M3++ {
			if M3 == M1 || M3 == L2 {
				continue
			}

			for R1 := 1; R1 <= c; R1++ {
				if R1 == L1 || R1 == M1 {
					continue
				}
				// R2 != L2, M2, R1
				s := int64(c)
				if R1 == M3 || R1 == L2 {
					s -= 2
				} else {
					s -= 3
				}
				cnt += s
			}
		}
	}
	dcnt := (cnt % M) * int64(c-2)

	return (scnt + dcnt) % M
}

type Graph struct {
	N int     // N个定点
	G [][]int // 邻接矩阵
}

func (g *Graph) IsEmpty() bool {
	for i := 0; i < g.N; i++ {
		for j := 0; j < g.N; j++ {
			if g.G[i][j] != 0 {
				return false
			}
		}
	}
	return true
}

func (g *Graph) FindEdge() (int, int) {
	N := g.N
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			if g.G[i][j] != 0 {
				return i, j
			}
		}
	}
	return -1, -1
}

func (g *Graph) RemoveEdge(u, v int) *Graph {
	N := g.N
	G := make([][]int, N)
	for i := 0; i < N; i++ {
		G[i] = make([]int, N)
	}
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			G[i][j] = g.G[i][j]
		}
	}
	G[u][v] = 0
	G[v][u] = 0
	return &Graph{N, G}
}

func (g *Graph) Contract(u, v int) *Graph {
	edges := make([][]int, 0)
	for i := 0; i < g.N; i++ {
		for j := 0; j < g.N; j++ {
			if g.G[i][j] == 1 {
				edges = append(edges, []int{i, j})
			}
		}
	}
	N := g.N
	mapping := func(i int) int {
		if i < u && i < v {
			return i
		}
		if i > u && i > v {
			return i - 2
		}
		return i - 1
	}
	for _, edge := range edges {
		i, j := edge[0], edge[1]
		if i == u || i == v {
			edge[0] = N
		}
		if j == u || j == v {
			edge[1] = N
		}
	}

	G := make([][]int, g.N-1)
	for i := 0; i < g.N-1; i++ {
		G[i] = make([]int, g.N-1)
	}
	for _, edge := range edges {
		i, j := mapping(edge[0]), mapping(edge[1])
		if i == j {
			continue
		}
		G[i][j] = 1
	}

	return &Graph{g.N - 1, G}
}

type Polynomial struct {
	C int // 系数
	E int // 指数
}

func Sub(p1, p2 []Polynomial) []Polynomial {
	result := make([]Polynomial, 0)
	for _, p := range p1 {
		result = append(result, p)
	}
	for _, p := range p2 {
		flag := false
		for i, r := range result {
			if r.E == p.E {
				result[i].C -= p.C
				flag = true
				break
			}
		}
		if !flag {
			result = append(result, Polynomial{-p.C, p.E})
		}
	}
	// 去掉系数为0的项
	list := make([]Polynomial, 0)
	for _, p := range result {
		if p.C != 0 {
			list = append(list, p)
		}
	}
	// sort
	sort.Slice(list, func(i, j int) bool {
		return list[i].E > list[j].E
	})
	return list
}

func printP(p []Polynomial) {
	for _, v := range p {
		if v.C > 0 {
			fmt.Printf("+%d*c^%d ", v.C, v.E)
		} else {
			fmt.Printf("%d*c^%d ", v.C, v.E)
		}
	}
	fmt.Printf("\n")
}

// Chromatic Polynomial
func CP(g *Graph) []Polynomial {
	// 检查是否为空图
	if g.IsEmpty() {
		// c^N
		return []Polynomial{{1, g.N}}
	}
	//	if g.N == 2 {
	// 只有两个顶点，且不是空图 c*(c-1) = c^2-c
	//		return []Polynomial{{1, 2}, {-1, 1}}
	//	}
	// 找到第一条边
	u, v := g.FindEdge()
	//	fmt.Printf("u=%d, v=%d\n", u, v)
	// G-e
	g1 := g.RemoveEdge(u, v)
	p1 := CP(g1)
	//	fmt.Printf("G-e:\n")
	//	printP(p1)
	// G/e
	g2 := g.Contract(u, v)
	p2 := CP(g2)
	//	fmt.Printf("G/e:\n")
	//	printP(p2)
	p := Sub(p1, p2)
	//	fmt.Printf("G:\n")
	//	printP(p)
	return p
}
