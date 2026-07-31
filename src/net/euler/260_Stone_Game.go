package main

import "fmt"

func main() {
	N := 1000
	win := make([][][]bool, N+1)
	for x := 0; x <= N; x++ {
		win[x] = make([][]bool, N+1)
		for y := 0; y <= N; y++ {
			win[x][y] = make([]bool, N+1)
		}
	}
	mark := func(a, b, c int) {
		if a > b {
			a, b = b, a
		}
		if b > c {
			b, c = c, b
		}
		if a > b {
			a, b = b, a
		}
		win[a][b][c] = true
	}
	sum := 0
	for x := 0; x <= N; x++ {
		for y := x; y <= N; y++ {
			for z := y; z <= N; z++ {
				// 如果 x,y,z是必败态，那么通过取子到达x,y,z状态的那些是必胜态
				if !win[x][y][z] {
					sum += x + y + z
					// 0,0,N
					for i := 1; x+i <= N; i++ {
						mark(x+i, y, z)
					}
					for i := 1; y+i <= N; i++ {
						mark(x, y+i, z)
					}
					for i := 1; z+i <= N; i++ {
						mark(x, y, z+i)
					}
					// 0,N,N
					for i := 1; x+i <= N && y+i <= N; i++ {
						mark(x+i, y+i, z)
					}
					for i := 1; x+i <= N && z+i <= N; i++ {
						mark(x+i, y, z+i)
					}
					for i := 1; y+i <= N && z+i <= N; i++ {
						mark(x, y+i, z+i)
					}
					// N,N,N
					for i := 1; x+i <= N && y+i <= N && z+i <= N; i++ {
						mark(x+i, y+i, z+i)
					}
					// 不可能有更大的z,使得相同的x,y,和新的z一起组成一个必败态
					break
				}
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}
