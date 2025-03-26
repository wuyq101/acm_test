package main

import (
	"fmt"
	"math"
	"math/rand"
)

var N int

func main() {

	fmt.Println("绘制热力图")
	// 生成数据
	// N
	N = 1 + rand.Intn(10)
	//	N = 6
	pct := make([]float64, N)
	// pct
	for i := 0; i < N; i++ {
		pct[i] = rand.Float64()
	}

	//	N = 10
	//	pct := []float64{0.01, 0.02, 0.04, 0.05, 0.10, 0.13, 0.14, 0.16, 0.16, 0.18}
	//	N = 8
	//	pct := []float64{0.05, 0.15, 0.21, 0.08, 0.09, 0.09, 0.12, 0.21} //0.05 0.15 0.21 0.08 0.09 0.09 0.12 0.21

	psum := sum(pct)
	for i := 0; i < N; i++ {
		pct[i] = pct[i] / psum
	}
	//	sort.Float64s(pct)

	fmt.Printf("N=%d\n", N)
	for _, v := range pct {
		fmt.Printf("%.2f ", v)
	}
	fmt.Printf("\n")

	// 原始地图
	R := 4
	C := 8
	fmt.Printf("R=%d,C=%d\n", R, C)
	m := make([][]int, R)
	for i := range m {
		m[i] = make([]int, C)
	}
	//	printMap(m)

	// 按照比例分配数据

	counts := make([]int, N)
	for i := 0; i < N; i++ {
		counts[i] = int(pct[i]*float64(R*C) + 0.5)
	}
	fmt.Printf("总格子数=%d\n", R*C)
	fmt.Printf("调整前的counts=%v\n", counts)
	a := sum(counts)
	fmt.Printf("分配的总格子数=%d\n", a)
	if a != R*C {
		delta := R*C - a
		// 将差值添加到最大值上
		max := findMaxIdx(counts)
		counts[max] += delta
		fmt.Printf("调整后的counts=%v\n", counts)
		fmt.Printf("调整后的总格子数=%d\n", sum(counts))
	}
	// 按照比例分配数据
	labels := make([]int, N)
	for i := 0; i < N; i++ {
		labels[i] = i
	}
	idx := 0
	cnt := 0
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if cnt < counts[idx] {
				m[i][j] = labels[idx]
				cnt++
			} else {
				idx++
				cnt = 0
				m[i][j] = labels[idx]
				cnt++
			}
		}
	}
	fmt.Printf("调整后的地图\n")
	printMap(m)

	// 对每一个数字计算中心坐标
	for i := 0; i < N; i++ {
		x, y := findCenter(m, i)
		fmt.Printf("数字%d的中心坐标(x,y)=(%d,%d)\n", i, x, y)
	}

	/*
		for i := N - 1; i >= 0; i-- {
			v := 0
			for v < 10 {
				x, y := findCenter(m, labels[i])
				adjust(m, labels[i], x, y)
				v++
			}
			fmt.Printf("调整1个后的地图\n")
			printMap(m)
		}
	*/
	total := 0
	for {

		// 定义损失函数，每个点到中心点的距离之和
		centers := make(map[int][]int)
		for i := 0; i < N; i++ {
			x, y := findCenter(m, i)
			centers[i] = []int{x, y}
			//	fmt.Printf("数字%d的中心坐标(x,y)=(%d,%d)\n", i, x, y)
		}
		//		cost(m, centers)
		// 找到使得cost最小的swap坐标
		a, b, c, d := findMinSwap(m, centers)
		if a == 0 && b == 0 && c == 0 && d == 0 {
			cost(m, centers)
			printMap(m)
			fmt.Printf("优化完成\n")
			break
		}
		//	fmt.Printf("找到使得cost最小的swap坐标(%d,%d)和(%d,%d)\n", a, b, c, d)
		// swap
		//	fmt.Printf("交换%d(%d,%d)和%d(%d,%d)\n", m[a][b], a, b, m[c][d], c, d)
		label1 := m[a][b]
		label2 := m[c][d]
		m[a][b] = label2
		m[c][d] = label1
		total++
		if total%100 == 0 {
			cost(m, centers)
			printMap(m)
			println()
			println()
			println()
		}
	}

	// 矩形化处理
	//	rectangle(m)

}

// 矩形化处理
func rectangle(m [][]int) {
	cnt := 0
	for {
		cost := rectangleCost(m)
		//	fmt.Printf("rectangle cost=%v\n", cost)
		// 下降最多的
		a, b, c, d := findRecMinCost(m, cost)
		if a == 0 && b == 0 && c == 0 && d == 0 {
			fmt.Printf("优化完成\n")
			break
		}
		// swap
		//	fmt.Printf("swap %d(%d,%d) 和 %d(%d,%d)\n", m[a][b], a, b, m[c][d], c, d)
		swap(m, a, b, c, d)
		cnt++
		cost = rectangleCost(m)
		//	fmt.Printf("after swap rectangle cost=%v\n", cost)
		//	break
		// 继续
	}
	printMap(m)
}

func findRecMinCost(m [][]int, preCost float64) (int, int, int, int) {
	R := len(m)
	C := len(m[0])
	delta := float64(0)
	a, b, c, d := 0, 0, 0, 0
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			for k := 0; k < R; k++ {
				for l := 0; l < C; l++ {
					if m[i][j] == m[k][l] {
						continue
					}
					// 如果调换这两个，开始计算cost，计算下降的幅度
					swap(m, i, j, k, l)
					cost := rectangleCost(m)
					if cost-preCost < delta {
						delta = cost - preCost
						a, b, c, d = i, j, k, l
					}
					// 重置回去
					swap(m, k, l, i, j)
				}
			}
		}
	}
	return a, b, c, d
}

func swap(m [][]int, a, b, c, d int) {
	label1 := m[a][b]
	label2 := m[c][d]
	m[a][b] = label2
	m[c][d] = label1
}

func rectangleCost(m [][]int) float64 {
	// 每一个标签的cnt * 长/宽, 越接近正方形越好
	R := len(m)
	C := len(m[0])
	labels := make(map[int]int)
	total := float64(0)
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			label := m[i][j]
			if labels[label] == 1 {
				continue
			}
			total += calcRectangleCost(m, label)
			labels[label] = 1
		}
	}
	return total
}

func calcRectangleCost(m [][]int, label int) float64 {
	cnt := 0
	R := len(m)
	C := len(m[0])
	minx, miny := R+1, C+1
	maxx, maxy := -1, -1
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if label != m[i][j] {
				continue
			}
			if i < minx {
				minx = i
			}
			if i > maxx {
				maxx = i
			}
			if j < miny {
				miny = j
			}
			if j > maxy {
				maxy = j
			}
			cnt++
		}
	}
	r := math.Abs(float64(maxx-minx) + 1)
	c := math.Abs(float64(maxy-miny) + 1)
	//fmt.Printf("label=%v cnt=%v r=%v c=%v\n", label, cnt, r, c)
	ratio := float64(1.0)
	if r > c {
		ratio = r / c
	} else {
		ratio = c / r
	}
	return float64(cnt) * c * r * ratio
}

func findMinSwap(m [][]int, centers map[int][]int) (int, int, int, int) {
	R := len(m)
	C := len(m[0])
	min := int64(0)
	a, b, c, d := 0, 0, 0, 0
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			for k := 0; k < R; k++ {
				for l := 0; l < C; l++ {
					if m[i][j] == m[k][l] {
						continue
					}
					// pre label a
					// i,j --> center a
					// k,l --> center b
					xa, ya := centers[m[i][j]][0], centers[m[i][j]][1]
					xb, yb := centers[m[k][l]][0], centers[m[k][l]][1]
					pre := int64((i-xa)*(i-xa)+(j-ya)*(j-ya)) + int64((k-xb)*(k-xb)+(l-yb)*(l-yb))
					// post
					// i,j --> center b
					// k,l --> center a
					post := int64((i-xb)*(i-xb)+(j-yb)*(j-yb)) + int64((k-xa)*(k-xa)+(l-ya)*(l-ya))
					delta := post - pre
					if delta < min {
						min = delta
						a, b, c, d = i, j, k, l
						/*
							bar := 50 + rand.Intn(50)
							if delta < -int64(bar) {
								return a, b, c, d
							}
						*/
						//	fmt.Printf("找到使得cost最小的swap坐标(%d,%d)和(%d,%d),delta=%v\n", a, b, c, d, delta)
					}
				}
			}
		}
	}
	return a, b, c, d
}

func cost(m [][]int, centers map[int][]int) int64 {
	sum := int64(0)
	R := len(m)
	C := len(m[0])
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			label := m[i][j]
			x, y := centers[label][0], centers[label][1]
			sum += int64((i-x)*(i-x) + (j-y)*(j-y))
		}
	}
	fmt.Printf("cost=%v\n", sum)
	return sum
}

func adjust(m [][]int, label int, x, y int) {
	fmt.Printf("调整数字%d\n", label)
	r1, c1 := findFarthest(m, x, y, label)
	// 找到最远的属于label的点
	fmt.Printf("找到离中心点最远的点(%d,%d)\n", r1, c1)
	// 找到最近的不属于label的点
	r2, c2 := findNearest(m, x, y, label)
	fmt.Printf("找到离中心点最近的点(%d,%d)\n", r2, c2)
	// swap
	fmt.Printf("交换(%d,%d)和(%d,%d)\n", r1, c1, r2, c2)
	label1 := m[r1][c1]
	label2 := m[r2][c2]
	m[r1][c1] = label2
	m[r2][c2] = label1
}

func findNearest(m [][]int, x, y int, label int) (int, int) {
	// 找到离中心点最近的点
	R := len(m)
	C := len(m[0])
	d := R*R + C*C
	r, c := 0, 0
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if label == m[i][j] {
				continue
			}
			dist := (i-x)*(i-x) + (j-y)*(j-y)
			if dist < d {
				d = dist
				r = i
				c = j
			}
		}
	}
	return r, c
}

func findFarthest(m [][]int, x, y int, label int) (int, int) {
	// 找到离中心点最远的点
	R := len(m)
	C := len(m[0])
	d := 0
	r, c := 0, 0
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if label != m[i][j] {
				continue
			}
			dist := (i-x)*(i-x) + (j-y)*(j-y)
			if dist > d {
				d = dist
				r = i
				c = j
			}
		}
	}
	return r, c
}

func sum[T ~int | float64](n []T) T {
	var a T
	for _, c := range n {
		a += c
	}
	return a
}

func findMaxIdx(n []int) int {
	idx := 0
	max := n[idx]
	for i, v := range n {
		if v > max {
			idx = i
			max = v
		}
	}
	return idx
}

var colors = []string{
	`\033[1;31;40m`, `\033[1;32;40m`, `\033[1;33;40m`, `\033[1;34;40m`, `\033[1;35;40m`,
	`\033[1;36;40m`, `\033[1;91;40m`, `\033[1;92;40m`, `\033[1;93;40m`, `\033[1;94;40m`,
}
var reset = `\033[0m`

func printMap(m [][]int) {
	R := len(m)
	C := len(m[0])
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if m[i][j] == 0 {
				//fmt.Printf("\033[1;31;40m%s\033[0m\n", "Red.")
				//fmt.Printf("\033[1;37;41m%s\033[0m\n", "Red.")
				//fmt.Printf("%s%d%s", colors[0], m[i][j], reset)
				fmt.Printf("\033[1;31;40m0\033[0m")
				continue
			}
			if m[i][j] == 1 {
				fmt.Printf("\033[1;32;40m1\033[0m")
				continue
			}
			if m[i][j] == 2 {
				fmt.Printf("\033[1;33;40m2\033[0m")
				continue
			}
			if m[i][j] == 3 {
				fmt.Printf("\033[1;34;40m3\033[0m")
				continue
			}
			if m[i][j] == 4 {
				fmt.Printf("\033[1;35;40m4\033[0m")
				continue
			}
			if m[i][j] == 5 {
				fmt.Printf("\033[1;36;40m5\033[0m")
				continue
			}
			if m[i][j] == 6 {
				fmt.Printf("\033[1;91;40m6\033[0m")
				continue
			}
			if m[i][j] == 7 {
				fmt.Printf("\033[1;92;40m7\033[0m")
				continue
			}
			if m[i][j] == 8 {
				fmt.Printf("\033[1;93;40m8\033[0m")
				continue
			}
			if m[i][j] == 9 {
				fmt.Printf("\033[1;94;40m9\033[0m")
				continue
			}
			fmt.Printf("%d", m[i][j])
		}
		fmt.Printf("\n")
	}
}

func findCenter(m [][]int, v int) (int, int) {
	// 求数字v的中心坐标
	// 将所有的横坐标和纵坐标分别求平均
	x, y := 0, 0
	cnt := 0
	R := len(m)
	C := len(m[0])
	for i := 0; i < R; i++ {
		for j := 0; j < C; j++ {
			if m[i][j] == v {
				x += i
				y += j
				cnt++
			}
		}
	}
	if cnt == 0 {
		return 0, 0
	}
	x = x / cnt
	y = y / cnt
	if m[x][y] != v {
		// 如果找的中心点都不是这个v,则找到离中心点最近的点
		d := R*R + C*C
		tx, ty := 0, 0
		for i := 0; i < R; i++ {
			for j := 0; j < C; j++ {
				if m[i][j] == v {
					dist := (i-x)*(i-x) + (j-y)*(j-y)
					if dist < d {
						d = dist
						tx, ty = i, j
					}
				}
			}
		}
		return tx, ty
	}
	return x, y
}
