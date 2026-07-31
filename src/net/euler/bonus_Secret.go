package main

/*
(a+b+c+d)^7 = (a+b+c+d)%7  (mod 7)
中间的那些项，系数都是7的倍数，mod 7之后都是0

因此一个点的数值，经过7次之后，会和和它距离是7的那些点数值有关，中间的点数值都抵消掉了。
*/

import (
	"fmt"
	"image"
	"image/color"
	"image/png"
	"os"
)

func main() {
	grid := readPNG()
	N := int(1e12)
	s := 1

	for N > 0 {
		r := N % 7
		N /= 7
		for range r {
			// 步长为s，迭代r次
			grid = T(grid, s)
		}
		s *= 7
	}
	savePNG(grid)
}

func T(grid [][]int, s int) [][]int {
	h, w := len(grid), len(grid[0])
	n := makeGrid(h, w)
	for x := 0; x < h; x++ {
		for y := 0; y < w; y++ {
			v := 0
			// x+s,y
			v += grid[(x+s)%h][y]
			// x-s,y
			v += grid[((x-s)%h+h)%h][y]
			// x,y+s
			v += grid[x][(y+s)%w]
			// x,y-s
			v += grid[x][((y-s)%w+w)%w]
			n[x][y] = v % 7
		}
	}
	return n
}

func savePNG(grid [][]int) {
	f, err := os.Create("./bonus_secret_decode.png")
	if err != nil {
		panic(err)
	}
	defer f.Close()
	h, w := len(grid), len(grid[0])
	upLeft := image.Point{0, 0}
	lowRight := image.Point{h, w}
	img := image.NewRGBA(image.Rectangle{upLeft, lowRight})
	colorWhite := color.RGBA{255, 255, 255, 255} // 背景色
	colorBlack := color.RGBA{0, 0, 0, 255}       // 文字色 (非0的点)
	for x := 0; x < h; x++ {
		for y := 0; y < w; y++ {
			if grid[x][y] <= 3 {
				img.Set(x, w-y, colorBlack)
				continue
			}
			img.Set(x, w-y, colorWhite)

			//v := uint8(grid[x][y])
			//img.Set(x, w-y, color.RGBA{R: v, G: v, B: v, A: 255})
		}
	}
	err = png.Encode(f, img)
	if err != nil {
		panic(err)
	}
}

func readPNG() [][]int {
	f, err := os.Open("./bonus_secret_statement.png")
	if err != nil {
		panic(err)
	}
	defer f.Close()
	img, err := png.Decode(f)
	if err != nil {
		panic(err)
	}
	bounds := img.Bounds()
	width := bounds.Max.X - bounds.Min.X
	height := bounds.Max.Y - bounds.Min.Y
	fmt.Printf("图片尺寸: 宽 %d x 高 %d\n", width, height)
	grid := makeGrid(height, width)
	for y := bounds.Min.Y; y < bounds.Max.Y; y++ {
		for x := bounds.Min.X; x < bounds.Max.X; x++ {
			r, g, b, a := img.At(x, y).RGBA()
			r >>= 8
			g >>= 8
			b >>= 8
			a >>= 8
			grid[y-bounds.Min.Y][x-bounds.Min.X] = int(g)
			//	fmt.Printf("x=%d, y=%d, r=%d, g=%d, b=%d, a=%d\n", x, y, r, g, b, a)
		}
	}
	return grid
}

func makeGrid(h, w int) [][]int {
	grid := make([][]int, h)
	for i := range grid {
		grid[i] = make([]int, w)
	}
	return grid
}
