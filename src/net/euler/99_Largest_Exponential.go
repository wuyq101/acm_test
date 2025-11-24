package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

/*
a^b 与 c^d 比较大小
两边取对数

ln(a^b) = bln(a)
ln(c^d) = dln(c)

*/

func main() {
	buf, err := os.ReadFile("base_exp.txt")
	if err != nil {
		panic(err)
	}
	lines := strings.Split(string(buf), "\n")

	a, b := 0, 0
	lineNum := 0
	for i, line := range lines {
		if len(line) == 0 {
			continue
		}
		fmt.Printf("%s\n", line)
		if a == 0 && b == 0 {
			a, b = parseLine(line)
			lineNum = 0
		}
		c, d := parseLine(line)
		v := cmp(a, b, c, d)
		if v < 0 {
			a, b = c, d
			lineNum = i
		}

	}
	fmt.Printf("line num = %d, %d^%d\n", lineNum, a, b)
}

func cmp(a, b, c, d int) int {
	f1 := float64(b) * math.Log(float64(a))
	f2 := float64(d) * math.Log(float64(c))
	if f1 > f2 {
		return 1
	}
	if f1 < f2 {
		return -1
	}
	return 0
}

func parseLine(line string) (int, int) {
	strs := strings.Split(line, ",")
	a, _ := strconv.Atoi(strs[0])
	b, _ := strconv.Atoi(strs[1])
	return a, b
}
