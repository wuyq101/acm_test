package main

import "fmt"

/*
4*N的格子，可以走4个方向，
从(0,0)到(3,0), 经过每一个格子刚好一次，一共有多少种不同的走法？

在最左侧增加一个虚拟节点V，让它和(0,0),(3,0)连接，所以必定是一个回路。




*/

func main() {
	configs := makeValidConfigs()
	for _, config := range configs {
		fmt.Printf("config L %b, R %b, pairs: %v\n", config.L_mask, config.R_mask, config.pairs)
	}
	fmt.Printf("valid configs: %d\n", len(configs))

	M := makeMatrix(configs)
	// 第三步：优雅地打印矩阵
	fmt.Printf("      ")
	for i := 0; i < 8; i++ {
		fmt.Printf("S%d ", i)
	}
	fmt.Println("\n   ---------------------------")
	for i := 0; i < 8; i++ {
		fmt.Printf("S%d | ", i)
		for j := 0; j < 8; j++ {
			fmt.Printf("%2d ", M[i][j])
		}
		fmt.Printf("   <- %s\n", StateNames[i])
	}

	n := int(1e12)
	MP := pow(M, n-1)
	// 初识V0 = [0,0,1,0,0,0,0,0], 只有S2这个状态
	Vf := MP[2]
	fmt.Printf("Vf = %v\n", Vf)
	// Vf中，只有S2,S7的状态可以作为最右侧
	cnt := (Vf[2] + Vf[7]) % MOD
	fmt.Printf("cnt=%d\n", cnt)

}

// 1. 定义 8 种合法的规范化状态（最小表示法）
// 数字代表连通块的 ID，0 表示无插头
// 例如: {1, 1, 0, 0} 表示 0行 和 1行 在左侧连通，对应 `( ) . .`
var CanonicalStates = [8][4]int{
	{1, 1, 0, 0}, // State 0: ( ) . .
	{1, 0, 1, 0}, // State 1: ( . ) .
	{1, 0, 0, 1}, // State 2: ( . . )
	{0, 1, 1, 0}, // State 3: . ( ) .
	{0, 1, 0, 1}, // State 4: . ( . )
	{0, 0, 1, 1}, // State 5: . . ( )
	{1, 1, 2, 2}, // State 6: ( ) ( )  并排
	{1, 2, 2, 1}, // State 7: ( ( ) )  嵌套
}

var StateNames = []string{
	"( ) . .", "( . ) .", "( . . )", ". ( ) .",
	". ( . )", ". . ( )", "( ) ( )", "( ( ) )",
}

type DSU struct {
	parent []int
}

func NewDSU(n int) *DSU {
	p := make([]int, n)
	for i := 0; i < n; i++ {
		p[i] = i
	}
	return &DSU{p}
}

func (d *DSU) Find(i int) int {
	if d.parent[i] == i {
		return i
	}
	d.parent[i] = d.Find(d.parent[i])
	return d.parent[i]
}

func (d *DSU) Union(i, j int) {
	ri, rj := d.Find(i), d.Find(j)
	if ri != rj {
		d.parent[ri] = rj
	}
}

// ---------------- 辅助函数 ----------------
func bit(mask, pos int) int {
	return (mask >> pos) & 1
}

// 将任意的连通性 ID 数组，转化为从 1 开始递增的规范化格式
func normalize(state [4]int) [4]int {
	res := [4]int{}
	idx := 1
	m := make(map[int]int)
	for i, v := range state {
		if v == 0 {
			res[i] = 0
			continue
		}
		id, ok := m[v]
		if ok {
			res[i] = id
			continue
		}
		m[v] = idx
		res[i] = idx
		idx++
	}
	return res
}

func getCanonicalIndex(state [4]int) int {
	for i, s := range CanonicalStates {
		if s == state {
			return i
		}
	}
	return -1
}

// Config 表示一个 4x1 网格内合法的物理布线方案
/*
(端口0) -> [格子0] -> (端口4)
(端口1) -> [格子1] -> (端口5)
(端口2) -> [格子2] -> (端口6)
(端口3) -> [格子3] -> (端口7)

L_mask = 15 (二进制 1111)：左边 4 个口全开（第 0, 1, 2, 3 位全是 1）。

R_mask = 15 (二进制 1111)：右边 4 个口全开（第 0, 1, 2, 3 位全是 1）。

pairs = [[0,4], [1,5], [2,6], [3,7]]：
积木内部形成了 4 根独立的管道。0 号进 4 号出，1 进 5 出，以此类推。



(端口0) -> [格子0]
             | (水管往下走)
(端口1) <- [格子1]

(端口2) -> [格子2]
             | (水管往下走)
(端口3) <- [格子3]

L_mask = 15 (二进制 1111)：左边 4 个口（0, 1, 2, 3）都有水管进出，全开。

R_mask = 0 (二进制 0000)：右边没有任何插头。

pairs = [[0,1], [2,3]]：
积木内部形成了 2 根 U 型管。它把左侧的 0 号端口和 1 号端口连在了一起，把 2 号端口和 3 号端口连在了一起。



*/
type Config struct {
	L_mask int      // 左侧 4 个接口的启用掩码
	R_mask int      // 右侧 4 个接口的启用掩码
	pairs  [][2]int // 内部打通的端口对 (0~3表示左端口，4~7表示右端口)
}

func makeValidConfigs() []Config {
	configs := make([]Config, 0)
	for L := 0; L < 16; L++ {
		for R := 0; R < 16; R++ {
			for D := 0; D < 8; D++ { // D代表四个个字中3个间隔是否打通，一共3个位置，每个位置2中状态
				//第 0 位 bit(D, 0)：代表格子 0 和格子 1 是否连通。
				// 第 1 位 bit(D, 1)：代表格子 1 和格子 2 是否连通。
				// 第 2 位 bit(D, 2)：代表格子 2 和格子 3 是否连通
				// 每个格子必须有且仅有 2 根管子连着它 一进一出
				d0 := bit(L, 0) + bit(R, 0) + bit(D, 0)
				d1 := bit(L, 1) + bit(R, 1) + bit(D, 0) + bit(D, 1)
				d2 := bit(L, 2) + bit(R, 2) + bit(D, 1) + bit(D, 2)
				d3 := bit(L, 3) + bit(R, 3) + bit(D, 2)
				if d0 != 2 || d1 != 2 || d2 != 2 || d3 != 2 {
					continue
				}

				// 2. 使用并查集检查内部是否存在闭合死环
				//	左插座(0~3)      网格房间(8~11)      右插座(4~7)
				//	   [ 0 ]   --->   ( 房间 8 )   --->   [ 4 ]
				//	                      | (D0连通)
				//	   [ 1 ]   --->   ( 房间 9 )   --->   [ 5 ]
				//	                      | (D1连通)
				//	   [ 2 ]   --->   ( 房间 10)   --->   [ 6 ]
				//	                      | (D2连通)
				//	   [ 3 ]   --->   ( 房间 11)   --->   [ 7 ]
				dsu := NewDSU(12)
				hasCycle := false
				addEdge := func(u, v int) {
					if dsu.Find(u) == dsu.Find(v) {
						hasCycle = true
					}
					dsu.Union(u, v)
				}
				// 检查D
				for i := 0; i < 3; i++ {
					if bit(D, i) == 1 {
						addEdge(8+i, 9+i)
					}
				}
				// 检查左右两边
				for i := 0; i < 4; i++ {
					if bit(L, i) == 1 {
						addEdge(i, 8+i)
					}
					if bit(R, i) == 1 {
						addEdge(4+i, 8+i)
					}
				}
				if hasCycle {
					continue
				}

				// 3. 提取被连通的端口对
				ports := make([]int, 0)
				for i := 0; i < 8; i++ {
					if i < 4 && bit(L, i) == 1 {
						ports = append(ports, i)
					}
					if i >= 4 && bit(R, i-4) == 1 {
						ports = append(ports, i)
					}
				}
				groups := make(map[int][]int)
				for _, p := range ports {
					r := dsu.Find(p)
					groups[r] = append(groups[r], p)
				}
				pairs := make([][2]int, 0)
				for _, g := range groups {
					pairs = append(pairs, [2]int{g[0], g[1]})
				}
				configs = append(configs, Config{L_mask: L, R_mask: R, pairs: pairs})
			}
		}
	}
	return configs
}

func makeMatrix(configs []Config) [][]int {
	M := make([][]int, 8)
	for i := range M {
		M[i] = make([]int, 8)
	}
	for inIdx, state := range CanonicalStates {
		// 计算输入的掩码
		// 1 1 0 0,  ()..
		in_L := 0
		for i := 0; i < 4; i++ {
			if state[i] > 0 {
				in_L |= (1 << i)
			}
		}
		for _, cfg := range configs {
			if cfg.L_mask != in_L {
				continue
			}
			// 将输入状态与列内部的连线结合，检测是否形成早产闭环
			dsu := NewDSU(8) // 端口0--7
			hasCycle := false
			addEdge := func(u, v int) {
				if dsu.Find(u) == dsu.Find(v) {
					hasCycle = true
				}
				dsu.Union(u, v)
			}
			// 遍历连通块 ID（因为我们最多只有两对括号，所以 ID 只有 1 和 2）
			for id := 1; id <= 2; id++ {
				ports := make([]int, 0)
				// 端口0--3
				for i := 0; i < 4; i++ {
					if state[i] == id {
						ports = append(ports, i)
					}
				}
				if len(ports) == 2 {
					addEdge(ports[0], ports[1])
				}
			}
			// 连上列内部的水管布线
			for _, pair := range cfg.pairs {
				addEdge(pair[0], pair[1])
			}
			if hasCycle {
				continue
			}
			// 提取右侧的输出状态
			out := [4]int{0, 0, 0, 0}
			rightPorts := make([]int, 0)
			for i := 0; i < 4; i++ {
				if bit(cfg.R_mask, i) == 1 {
					rightPorts = append(rightPorts, i+4)
				}
			}
			groups := make(map[int][]int)
			for _, p := range rightPorts {
				r := dsu.Find(p)
				groups[r] = append(groups[r], p-4)
			}
			id := 1
			for _, g := range groups {
				out[g[0]] = id
				out[g[1]] = id
				id++
			}
			out = normalize(out)
			outIdx := getCanonicalIndex(out)
			if outIdx != -1 {
				M[inIdx][outIdx]++
			}
		}
	}
	return M
}

var MOD = int(1e8)

// 矩阵乘法
func mul(A, B [][]int) [][]int {
	res := make([][]int, 8)
	for i := 0; i < 8; i++ {
		res[i] = make([]int, 8)
		for j := 0; j < 8; j++ {
			for k := 0; k < 8; k++ {
				res[i][j] = (res[i][j] + A[i][k]*B[k][j]) % MOD
			}
		}
	}
	return res
}

func pow(A [][]int, n int) [][]int {
	if n == 0 {
		I := make([][]int, 8)
		// 初识化 单位矩阵
		for i := 0; i < 8; i++ {
			I[i] = make([]int, 8)
			I[i][i] = 1
		}
		return I
	}
	if n&1 == 1 {
		return mul(A, pow(A, n-1))
	}
	B := pow(A, n/2)
	return mul(B, B)
}
