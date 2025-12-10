package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

/**
判断原点是否在三角形内

*/

func main() {
	buf, err := os.ReadFile("triangles.txt")
	if err != nil {
		panic(err)
	}
	cnt := 0
	lines := strings.Split(string(buf), "\n")
	for _, line := range lines {
		fmt.Println(line)
		nums := strings.Split(line, ",")
		if len(nums) == 6 {
			x, _ := strconv.Atoi(nums[0])
			y, _ := strconv.Atoi(nums[1])
			A := &Point{x, y}
			fmt.Printf("A = %v\n", A)
			x, _ = strconv.Atoi(nums[2])
			y, _ = strconv.Atoi(nums[3])
			B := &Point{x, y}
			fmt.Printf("B = %v\n", B)
			x, _ = strconv.Atoi(nums[4])
			y, _ = strconv.Atoi(nums[5])
			C := &Point{x, y}
			fmt.Printf("C = %v\n", C)
			if IsValid(A, B, C) {
				cnt++
			}

		}
	}
	fmt.Printf("cnt = %d\n", cnt)
}

func IsValid(A, B, C *Point) bool {
	O := &Point{0, 0}
	// O,A在边BC的同侧
	// O,B在AC
	// O,C在AB同侧
	return isSameSide(O, A, B, C) && isSameSide(O, B, A, C) && isSameSide(O, C, A, B)
}

// 判断O，P是否在边AB的同侧
func isSameSide(O, P, A, B *Point) bool {
	AB := NewVec(A, B)
	AO := NewVec(A, O)
	AP := NewVec(A, P)
	a := crossprod(AB, AO)
	b := crossprod(AB, AP)
	return a*b > 0
}

func crossprod(a, b *Vec) int {
	return a.X*b.Y - a.Y*b.X
}

type Point struct {
	X int
	Y int
}

func (p Point) String() string {
	return fmt.Sprintf("(%d,%d)", p.X, p.Y)
}

type Vec struct {
	X int
	Y int
}

// A指向B的向量
func NewVec(A, B *Point) *Vec {
	return &Vec{
		X: B.X - A.X,
		Y: B.Y - A.Y,
	}
}
