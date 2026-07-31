package main

import "fmt"

/*

 */

func main() {

	lfg := make([]int, 55)
	for i := 1; i <= 55; i++ {
		lfg[i-1] = (100003 - 200003*i + 300007*i*i*i) % 1000000
	}
	idx := 0
	next := func() int {
		p := lfg[idx]
		lfg[idx] = (lfg[idx] + lfg[(idx+31)%55]) % 1000000
		idx = (idx + 1) % 55
		return p
	}
	cubes := make([]Cube, 0)
	M := 10000
	N := 50000
	for i := 0; i < N; i++ {
		cubes = append(cubes, Cube{next() % M, next() % M, next() % M, 1 + next()%399, 1 + next()%399, 1 + next()%399})
	}
	v := V(cubes[:100], 0, 10398, 0, 10398, 0, 10398)
	fmt.Printf("volume = %v\n", v)
	v = V(cubes, 0, 10398, 0, 10398, 0, 10398)
	fmt.Printf("volume = %v\n", v)
}

func V(cubes []Cube, xmin, xmax, ymin, ymax, zmin, zmax int) int {
	if xmin >= xmax || ymin >= ymax || zmin >= zmax {
		return 0
	}
	cnt := 0
	for i, cube := range cubes {
		if isActive(cube, xmin, xmax, ymin, ymax, zmin, zmax) {
			cubes[cnt], cubes[i] = cubes[i], cubes[cnt]
			cnt++
		}
	}
	actives := cubes[:cnt]
	//	fmt.Printf("len(actives) = %d\n", len(actives))
	// 这个空间中没有方块
	if len(actives) == 0 {
		//		fmt.Printf("empty space\n")
		return 0
	}
	if len(actives) == 1 {
		cube := actives[0]
		//	fmt.Printf("cube=%v\n", cube)
		// 在这个空间中，只有一个方块
		if isFull(cube, xmin, xmax, ymin, ymax, zmin, zmax) {
			return (xmax - xmin) * (ymax - ymin) * (zmax - zmin)
		} else {
			// 直接求这个方块在空间内部占的体积
			x := min(xmax, cube.x+cube.dx) - max(xmin, cube.x)
			y := min(ymax, cube.y+cube.dy) - max(ymin, cube.y)
			z := min(zmax, cube.z+cube.dz) - max(zmin, cube.z)
			return x * y * z
		}
	}
	// 如果这个空间中有一个方块充满整个空间，则直接返回整体的体积
	xa, xb := actives[0].x, actives[0].x+actives[0].dx
	ya, yb := actives[0].y, actives[0].y+actives[0].dy
	za, zb := actives[0].z, actives[0].z+actives[0].dz
	for _, cube := range actives {
		if isFull(cube, xmin, xmax, ymin, ymax, zmin, zmax) {
			return (xmax - xmin) * (ymax - ymin) * (zmax - zmin)
		}
		xa = min(xa, cube.x)
		xb = max(xb, cube.x+cube.dx)
		ya = min(ya, cube.y)
		yb = max(yb, cube.y+cube.dy)
		za = min(za, cube.z)
		zb = max(zb, cube.z+cube.dz)
	}
	xmin = max(xmin, xa)
	xmax = min(xmax, xb)
	ymin = max(ymin, ya)
	ymax = min(ymax, yb)
	zmin = max(zmin, za)
	zmax = min(zmax, zb)

	// x方向最长, 沿着x方向对半分
	dx, dy, dz := xmax-xmin, ymax-ymin, zmax-zmin
	if dx >= dy && dx >= dz {
		// x轴最长
		mid := (xmax + xmin) / 2
		return V(actives, xmin, mid, ymin, ymax, zmin, zmax) + V(actives, mid, xmax, ymin, ymax, zmin, zmax)
	}
	if dy >= dx && dy >= dz {
		mid := (ymax + ymin) / 2
		return V(actives, xmin, xmax, ymin, mid, zmin, zmax) + V(actives, xmin, xmax, mid, ymax, zmin, zmax)
	}
	mid := (zmax + zmin) / 2
	return V(actives, xmin, xmax, ymin, ymax, zmin, mid) + V(actives, xmin, xmax, ymin, ymax, mid, zmax)
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}

func isFull(cube Cube, xmin, xmax, ymin, ymax, zmin, zmax int) bool {
	return cube.x <= xmin && cube.x+cube.dx >= xmax &&
		cube.y <= ymin && cube.y+cube.dy >= ymax &&
		cube.z <= zmin && cube.z+cube.dz >= zmax
}

func isActive(cube Cube, xmin, xmax, ymin, ymax, zmin, zmax int) bool {
	if cube.x+cube.dx <= xmin || cube.x >= xmax ||
		cube.y+cube.dy <= ymin || cube.y >= ymax ||
		cube.z+cube.dz <= zmin || cube.z >= zmax {
		return false
	}
	return true
}

type Cube struct {
	x, y, z    int
	dx, dy, dz int
}
