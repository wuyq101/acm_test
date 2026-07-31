package main

import "fmt"

/*
方法一： dfs  回溯枚举，提前剪枝

方法二：设x0,x1,...,x15 一共16个参数，10个方程（4行，4列，2对角线）
通过分析，可以得知，自由度为7，枚举其中7个，其他都可以直接推出

x0  x1  x2  x3
x4  x5  x6  x7
x8  x9  x10 x11
x12 x13 x14 x15

第一行，选择x0,x1,x2,  那么x3=S-x0-x1-x2
第二行，选择x4,x5,x6,  那么x7=S-x4-x5-x6
第三行, 选择x8
x12 = S-x0-x4-x8
x9 = S-x3-x6-x12
x13=S-x1-x5-x9

剩余x10,x11,x14,x15可以通过解方程
x10+x11 = S-x8-x9 = A
x14+x15 = S-x12-x13 = B
x10+x14 = S-x2-x6 = C
x11+x15 = S-x3-x7 = D
x10+x15 = S-x0-x5 = E

B+C+E = 2*(x10+x14+x15) = 2(x10+B)
--> x10 = (B+C+E)/2 - B
x11 = A-x10
x14 = C-x10
x15 = E-x10





*/

func main() {
	total := 0
	for i := 0; i <= 18; i++ {
		S := i
		for x0 := 0; x0 <= 9; x0++ {
			for x1 := 0; x1 <= 9; x1++ {
				for x2 := 0; x2 <= 9; x2++ {
					x3 := S - x0 - x1 - x2
					if x3 < 0 {
						break
					}
					if x3 > 9 {
						continue
					}
					for x4 := 0; x4 <= 9; x4++ {
						for x5 := 0; x5 <= 9; x5++ {
							//x10+x15 = S-x0-x5 = E
							E := S - x0 - x5
							if E < 0 {
								break
							}

							for x6 := 0; x6 <= 9; x6++ {
								//x10+x14 = S-x2-x6 = C
								C := S - x2 - x6
								if C < 0 {
									break
								}

								x7 := S - x4 - x5 - x6
								if x7 < 0 {
									break
								}
								if x7 > 9 {
									continue
								}
								for x8 := 0; x8 <= 9; x8++ {
									x12 := S - x0 - x4 - x8
									if x12 < 0 {
										break
									}
									if x12 > 9 {
										continue
									}
									x9 := S - x3 - x6 - x12
									if x9 < 0 || x9 > 9 {
										continue
									}
									x13 := S - x1 - x5 - x9
									if x13 < 0 || x13 > 9 {
										continue
									}
									//x14+x15 = S-x12-x13 = B
									B := S - x12 - x13
									if B < 0 {
										continue
									}
									if (B+C+E)%2 != 0 {
										continue
									}
									x10 := (B+C+E)/2 - B
									if x10 < 0 || x10 > 9 {
										continue
									}
									x14 := C - x10
									if x14 < 0 || x14 > 9 {
										continue
									}
									x15 := E - x10
									if x15 < 0 || x15 > 9 {
										continue
									}
									x11 := S - x8 - x9 - x10
									if x11 < 0 || x11 > 9 {
										continue
									}
									if S-x3-x7-x11 != x15 {
										continue
									}

									cnt++
								}
							}
						}
					}
				}
			}
		}
		fmt.Printf("i=%d, cnt=%d\n", i, cnt)
		if i != 18 {
			total += 2 * cnt
		} else {
			total += cnt
		}
		cnt = 0
	}
	fmt.Printf("total = %d\n", total)
	/*
		total := 0
		for i := 0; i <= 18; i++ {
			S = i
			cols := [4]int{}
			rows := [4]int{}
			diags := [2]int{}
			cnt = 0
			dfs(0, cols, rows, diags)
			if i != 18 {
				total += 2 * cnt
			} else {
				total += cnt
			}
			fmt.Printf("i=%d, cnt=%d\n", i, cnt)
		}
		fmt.Printf("total = %d\n", total)
	*/
}

var cnt int
var S int

func dfs(idx int, cols, rows [4]int, diags [2]int) {
	if idx == 16 {
		for i := 0; i < 4; i++ {
			if cols[i] != S {
				return
			}
			if rows[i] != S {
				return
			}
		}
		for i := 0; i < 2; i++ {
			if diags[i] != S {
				return
			}
		}
		//		fmt.Printf("%d\n%v\n%v %v %v\n", idx, grid, cols, rows, diags)
		cnt++
		return
	}
	// 在r,c这个位置上填入合法数字
	r, c := idx/4, idx%4

	// 之前已经结束的行，和必须是12
	for i := 0; i <= r-1; i++ {
		if rows[i] != S {
			return
		}
	}
	// 已经结束的列
	if r == 3 {
		for i := 0; i <= c-1; i++ {
			if cols[i] != S {
				return
			}
		}
		if c > 0 {
			if diags[1] != S {
				return
			}
		}
	}
	// 对这一行来说，后面全部填9，所以前面的和必须超过S-(4-c)*9
	if rows[r] < S-(4-c)*9 {
		return
	}
	if cols[c] < S-(4-r)*9 {
		return
	}

	// 对于已经填充的列和如果超过12
	for i := 0; i < 4; i++ {
		if cols[i] > S {
			return
		}
	}
	for i := 0; i < 2; i++ {
		if diags[i] > S {
			return
		}
	}

	for k := 0; k <= 9; k++ {
		if rows[r]+k > S || cols[c]+k > S {
			break
		}
		if r == c && diags[0]+k > S {
			break
		}
		if r+c == 3 && diags[1]+k > S {
			break
		}
		rows[r] += k
		cols[c] += k
		if r == c {
			diags[0] += k
		}
		if r+c == 3 {
			diags[1] += k
		}
		dfs(idx+1, cols, rows, diags)
		rows[r] -= k
		cols[c] -= k
		if r == c {
			diags[0] -= k
		}
		if r+c == 3 {
			diags[1] -= k
		}
	}

}

func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}
