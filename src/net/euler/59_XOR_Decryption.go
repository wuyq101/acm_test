package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

/**
 * https://projecteuler.net/problem=59
 * 通过枚举可以求的key = exp
 */
func main() {
	buf, _ := os.ReadFile("59_cipher.txt")
	// 去掉换行
	buf = buf[:len(buf)-1]
	s := string(buf)
	strs := strings.Split(s, ",")
	cipher := make([]byte, 0)
	for _, str := range strs {
		if str == "" {
			continue
		}
		b, err := strconv.Atoi(str)
		if err != nil {
			fmt.Printf("err = %v\n", err)
		}
		cipher = append(cipher, byte(b))
	}
	fmt.Printf("%d\n", int(cipher[len(cipher)-1]))
	fmt.Printf("len = %d\n", len(cipher))
	sum := 0
	for i := 0; i < len(cipher); i += 3 {
		cipher[i] ^= 'e'
		cipher[i+1] ^= 'x'
		cipher[i+2] ^= 'p'
		sum += int(cipher[i]) + int(cipher[i+1]) + int(cipher[i+2])
	}
	fmt.Printf("%s\n", string(cipher))
	fmt.Printf("sum=%d\n", sum)
	return

	/*
		black := []byte("$%`#")

		i, j, k := 'e', 'x', 'p'
		result := make([]byte, 0, len(cipher))
		b := true
		for h := 0; h < len(cipher); h += 3 {
			v1 := cipher[h] ^ byte(i)
			if bytes.Contains(black, []byte{v1}) {
				b = false
				break
			}

			result = append(result, v1)
			var v2 byte
			if h+1 < len(cipher) {
				v2 = cipher[h+1] ^ byte(j)
				result = append(result, v2)
			}
			if bytes.Contains(black, []byte{v2}) {
				b = false
				break
			}

			var v3 byte
			if h+2 < len(cipher) {
				v3 = cipher[h+2] ^ byte(k)
				result = append(result, v3)
			}
			if bytes.Contains(black, []byte{v3}) {
				b = false
				break
			}
		}

		if b {
			fmt.Println("-----")
			fmt.Printf("%c^%c^%c = [%s]\n", i, j, k, string(result))
			fmt.Printf("----- len=%d\n", len(result))
			sum := 0
			for _, b := range result {
				sum += int(b)
			}
			fmt.Printf("sum=%d\n", sum)
		}

	*/

}
