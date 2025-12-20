package main

/*
a*b*c的长方体, 第n层
C(a,b,c,n) = 2(ab+bc+ac)+4(n−1)(a+b+c)+4(n−1)(n−2)
2(ab+bc+ac)最内层（第一层）的六个“面壳”
4(n-1)(a + b + c) —— 随着层数增加，在“棱”上堆积的立方体
4(n-1)(n-2) —— 随着层数增加，在“顶角”上堆积的立方体
*/
import "fmt"

func main() {
	i := 2
	for {
		c := Count(i)
		//		fmt.Printf("C(%d) = %d\n", i, c)
		//	if c%10 == 0 {
		fmt.Printf("C(%d) = %d\n", i, c)
		//	}
		if c == 1000 {
			break
		}
		i += 2
	}
	return

}

// C(a,b,c,n) 求所有的可能a,b,c, a<=b<=c
func Count(C int) int {
	if C%2 == 1 {
		return 0
	}

	cnt := 0
	n := 1
	for {
		// C(a,b,c,n) = 2(ab+bc+ac)+4(n−1)(a+b+c)+4(n−1)(n−2)
		p := 4 * (n - 1) * (n - 2)
		if p > C {
			break
		}
		// C = 2(ab+bc+ac)+4(n−1)(a+b+c)+4(n−1)(n−2)
		// n固定的时候，求合适的a,b,c
		for a := 1; 6*a*a+12*(n-1)*a+p <= C; a++ {
			// 在a确定的情况下，就b的上限
			// C = 2(ab+bc+ac)+4(n−1)(a+b+c)+4(n−1)(n−2)
			// 2(ab+b^2+a*b)+4(n-1)(a+b+b)+4(n-1)(n-2) <= C
			for b := a; 2*(2*a*b+b*b)+4*(n-1)*(a+2*b)+p <= C; b++ {
				// n,c,b固定的情况下，求c
				// C = 2(ab+bc+ac)+4(n−1)(a+b+c)+p
				// 2ab + 2(a+b)*c + 4(n-1)(a+b) + 4(n-1)c + p = C
				// 2(a+b)*c + 4(n-1)*c = C - 2ab - 4(n-1)(a+b) - p
				// c = (C - 2ab - 4(n-1)(a+b) - p)/(2(a+b) + 4(n-1))
				c := (C - 2*a*b - 4*(n-1)*(a+b) - p) / (2*(a+b) + 4*(n-1))
				// 验算
				k := 2*(a*b+b*c+c*a) + 4*(n-1)*(a+b+c) + p
				if k == C {
					//		fmt.Printf("a=%d,b=%d,c=%d,n=%d C=%d\n", a, b, c, n, C)
					cnt++
				}
				if k > C {
					break
				}
			}
		}
		n++
	}
	return cnt
}

func f(x, y, z int) {
	// x = 3 y =2 z = 1
	cubes := make([]Cube, 0)
	// 每个方块用左下角的坐标表示
	for i := 0; i < x; i++ {
		for j := 0; j < y; j++ {
			for k := 0; k < z; k++ {
				cubes = append(cubes, Cube{i, j, k})
			}
		}
	}
	cuboid := NewCuboid(cubes)
	fmt.Printf("%v\n", cuboid)
	MAX := 10000
	for {
		out := cuboid.Layer()
		fmt.Printf("%v\n", out)
		cuboid = out
		C[len(cuboid.cubes)]++
		if len(cuboid.cubes) > MAX {
			break
		}
	}
}

var C = map[int]int{}

type Cube struct {
	x, y, z int
}

func (c Cube) String() string {
	return fmt.Sprintf("[%d,%d,%d]", c.x, c.y, c.z)
}

type Cuboid struct {
	cubes []Cube
	// up,down,left,right,front,back
	// 从上往下看，xy坐标一样的时候，z最大
	up map[string]int
	// 从下往上看，xy坐标一样的时候，z最小
	down map[string]int
	// 从左往右看，yz坐标一样的时候，x最小
	left map[string]int
	// 从右往左看，yz坐标一样的时候，x最大
	right map[string]int
	// 从前往后看，xz坐标一样的时候，y最小
	front map[string]int
	// 从后往前看，xz坐标一样的时候，y最大
	back map[string]int
}

func (c Cuboid) String() string {
	buf := fmt.Sprintf("%d cubes\n", len(c.cubes))
	//buf += fmt.Sprintf("up=%v\ndown=%v\nleft=%v\nright=%v\nfront=%v\nback=%v\n", c.up, c.down, c.left, c.right, c.front, c.back)
	return buf
}

func (c Cuboid) Layer() Cuboid {
	// 正对每个方块，往外面扩展一层
	m := make(map[string]Cube)
	for _, cube := range c.cubes {
		// 针对每个方块，往外面扩展一层
		// up
		key := fmt.Sprintf("%d,%d", cube.x, cube.y)
		z, ok := c.up[key]
		if ok && z == cube.z {
			// 说明这个方块上面没有其他方块，需要往外面扩展一层
			tmp := Cube{cube.x, cube.y, cube.z + 1}
			tk := fmt.Sprintf("%d,%d,%d", tmp.x, tmp.y, tmp.z)
			m[tk] = tmp
		}
		// down
		z, ok = c.down[key]
		if ok && z == cube.z {
			// 说明这个方块下面没有其他方块，需要往外面扩展一层
			tmp := Cube{cube.x, cube.y, cube.z - 1}
			tk := fmt.Sprintf("%d,%d,%d", tmp.x, tmp.y, tmp.z)
			m[tk] = tmp
		}
		// left
		key = fmt.Sprintf("%d,%d", cube.y, cube.z)
		x, ok := c.left[key]
		if ok && x == cube.x {
			// 说明这个方块左面没有其他方块，需要往外面扩展一层
			tmp := Cube{cube.x - 1, cube.y, cube.z}
			tk := fmt.Sprintf("%d,%d,%d", tmp.x, tmp.y, tmp.z)
			m[tk] = tmp
		}
		// right
		x, ok = c.right[key]
		if ok && x == cube.x {
			// 说明这个方块右面没有其他方块，需要往外面扩展一层
			tmp := Cube{cube.x + 1, cube.y, cube.z}
			tk := fmt.Sprintf("%d,%d,%d", tmp.x, tmp.y, tmp.z)
			m[tk] = tmp
		}
		// front
		key = fmt.Sprintf("%d,%d", cube.x, cube.z)
		y, ok := c.front[key]
		if ok && y == cube.y {
			// 说明这个方块前面没有其他方块，需要往外面扩展一层
			tmp := Cube{cube.x, cube.y - 1, cube.z}
			tk := fmt.Sprintf("%d,%d,%d", tmp.x, tmp.y, tmp.z)
			m[tk] = tmp
		}
		// back
		y, ok = c.back[key]
		if ok && y == cube.y {
			// 说明这个方块后面没有其他方块，需要往外面扩展一层
			tmp := Cube{cube.x, cube.y + 1, cube.z}
			tk := fmt.Sprintf("%d,%d,%d", tmp.x, tmp.y, tmp.z)
			m[tk] = tmp
		}
	}
	list := make([]Cube, 0, len(m))
	for _, cube := range m {
		list = append(list, cube)
	}
	return NewCuboid(list)
}

func NewCuboid(cubes []Cube) Cuboid {
	c := Cuboid{
		cubes: cubes,
		up:    make(map[string]int),
		down:  make(map[string]int),
		left:  make(map[string]int),
		right: make(map[string]int),
		front: make(map[string]int),
		back:  make(map[string]int),
	}
	for _, cube := range cubes {
		// up, down, 看xy坐标, 保持z坐标
		key := fmt.Sprintf("%d,%d", cube.x, cube.y)
		save(key, cube.z, c.up, true)
		save(key, cube.z, c.down, false)

		// left, right, 看yz坐标, 保持x坐标
		key = fmt.Sprintf("%d,%d", cube.y, cube.z)
		save(key, cube.x, c.left, false)
		save(key, cube.x, c.right, true)

		// front, back, 看xz坐标, 保持y坐标
		key = fmt.Sprintf("%d,%d", cube.x, cube.z)
		save(key, cube.y, c.front, false)
		save(key, cube.y, c.back, true)
	}
	return c
}

func save(key string, n int, m map[string]int, big bool) {
	v, ok := m[key]
	if !ok {
		m[key] = n
		return
	}
	if big {
		if n > v {
			m[key] = n
		}
	} else {
		if n < v {
			m[key] = n
		}
	}
}
