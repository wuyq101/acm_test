package main

import (
	"fmt"
	"os"
	"strings"
)

/*
I = 1
V = 5
X = 10
L = 50
C = 100
D = 500
M = 1000
1. Numerals must be arranged in descending order of size.
2. M, C, and X cannot be equalled or exceeded by smaller denominations.
3. D, L, and V can each only appear once.

i.   Only one I, X, and C can be used as the leading numeral in part of a subtractive pair.
ii.  I can only be placed before V and X.
iii. X can only be placed before L and C.
iv.  C can only be placed before D and M.
*/

func main() {
	s := "MDCCCXXXVII"
	v := romanToInt(s)
	fmt.Printf("%s = %d\n", s, v)
	s = intToRoman(v)
	fmt.Printf("%s = %d\n", s, v)
	buf, err := os.ReadFile("roman.txt")
	if err != nil {
		panic(err)
	}
	sum := 0
	lines := strings.Split(string(buf), "\n")
	for _, line := range lines {
		if len(line) == 0 {
			continue
		}
		v := romanToInt(line)
		//fmt.Printf("%s = %d\n", line, v)
		s := intToRoman(v)
		//fmt.Printf("%s = %d\n", s, v)
		if len(s) != len(line) {
			fmt.Printf("invalid %s, %d\n", line, len(line)-len(s))
			sum += len(line) - len(s)
		}
	}
	fmt.Printf("total = %d\n", sum)
}

func intToRoman(n int) string {
	if n >= 1000 {
		return "M" + intToRoman(n-1000)
	}
	if n >= 900 {
		return "CM" + intToRoman(n-900)
	}
	if n >= 500 {
		return "D" + intToRoman(n-500)
	}
	if n >= 400 {
		return "CD" + intToRoman(n-400)
	}
	if n >= 100 {
		return "C" + intToRoman(n-100)
	}
	if n >= 90 {
		return "XC" + intToRoman(n-90)
	}
	if n >= 50 {
		return "L" + intToRoman(n-50)
	}
	if n >= 40 {
		return "XL" + intToRoman(n-40)
	}
	if n >= 10 {
		return "X" + intToRoman(n-10)
	}
	if n >= 9 {
		return "IX" + intToRoman(n-9)
	}
	if n >= 5 {
		return "V" + intToRoman(n-5)
	}
	if n >= 4 {
		return "IV" + intToRoman(n-4)
	}
	if n >= 1 {
		return "I" + intToRoman(n-1)
	}
	return ""
}

// MMMMDCCCLXXIII
func romanToInt(s string) int {
	if len(s) == 0 {
		return 0
	}
	if len(s) == 1 {
		switch s {
		case "I":
			return 1
		case "V":
			return 5
		case "X":
			return 10
		case "L":
			return 50
		case "C":
			return 100
		case "D":
			return 500
		case "M":
			return 1000
		default:
			panic("invalid")
		}
	}
	if len(s) == 2 {
		switch s {
		case "IV":
			return 4
		case "IX":
			return 9
		case "XL":
			return 40
		case "XC":
			return 90
		case "CD":
			return 400
		case "CM":
			return 900
		}
	}
	if hasSubtractivePair(s) {
		sub := s[0:2]
		left := s[2:]
		return romanToInt(sub) + romanToInt(left)
	}
	first := s[0:1]
	left := s[1:]
	return romanToInt(first) + romanToInt(left)
}

var subs = []string{"IV", "IX", "XL", "XC", "CD", "CM"}

func hasSubtractivePair(s string) bool {
	for _, sub := range subs {
		if strings.HasPrefix(s, sub) {
			return true
		}
	}
	return false
}
