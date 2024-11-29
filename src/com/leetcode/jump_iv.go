package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	arr := []int{1, 222, 2, 2, 2, 2, 2, 23, 3}
	arr = readFile()
	fmt.Printf("len = %v\n", len(arr))
	d := minJumps(arr)
	fmt.Printf("result=%d\n", d)
}

func readFile() []int {
	buf, _ := os.ReadFile("./jump_iv.txt")
	strs := strings.Split(string(buf), ",")
	a := make([]int, 0)
	for _, s := range strs {
		v, _ := strconv.Atoi(s)
		a = append(a, v)
	}
	return a
}

func minJumps(arr []int) int {
	return 0
}

// 构造成图，使用图的点和边，广度优先求最短路径
