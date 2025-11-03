package main

import "fmt"

/**
 * @see https://projecteuler.net/problem=68
 * 1+2+...+9+10 = 55
 * 16 digit,so 10 is outside.
 */
//                                       a
//                                                b
//	 	                             f
//					   j   g
//					    i h    c
//                                     e
//                                            d
// afg,bgh,chi,dij,ejf
func main() {
	a := 10
	for f := 1; f <= 9; f++ {
		for g := 1; g <= 9; g++ {
			if g == f {
				continue
			}
			sum := a + f + g
			fmt.Printf("sum=%d\n", sum)

			for b := 1; b <= 9; b++ {
				if b == f || b == g {
					continue
				}
				h := sum - g - b
				if h <= 0 {
					break
				}
				if h == f || h == g || h == b || h >= 10 {
					continue
				}
				//	fmt.Printf("afg=%d%d%d,bgh=%d%d%d\n", a, f, g, b, g, h)

				for c := 1; c <= 9; c++ {
					if c == f || c == g || c == b || c == h {
						continue
					}
					i := sum - h - c
					if i <= 0 {
						break
					}
					if i == f || i == g || i == b || i == h || i == c || i >= 10 {
						continue
					}
					//		fmt.Printf("afg=%d%d%d,bgh=%d%d%d,chi=%d%d%d\n", a, f, g, b, g, h, c, h, i)

					for d := 1; d <= 9; d++ {
						if d == f || d == g || d == b || d == h || d == c || d == i {
							continue
						}
						j := sum - i - d
						if j <= 0 {
							break
						}
						if j == f || j == g || j == b || j == h || j == c || j == i || j == d || j >= 10 {
							continue
						}
						//	fmt.Printf("afg=%d%d%d,bgh=%d%d%d,chi=%d%d%d,dij=%d%d%d\n", a, f, g, b, g, h, c, h, i, d, i, j)
						e := sum - j - f
						if e <= 0 || e >= 10 {
							continue
						}
						if e == f || e == g || e == h || e == i || e == j || e == b || e == c || e == d {
							continue
						}
						//						fmt.Printf("afg=%d%d%d,bgh=%d%d%d,chi=%d%d%d,dij=%d%d%d,ejf=%d%d%d\n", a, f, g, b, g, h, c, h, i, d, i, j, e, j, f)
						ans := norm(a, b, c, d, e, f, g, h, i, j)
						fmt.Printf("the answer is %s\n", ans)
					}
				}
			}
		}
	}
}

func norm(a, b, c, d, e, f, g, h, i, j int) string {
	if a < b && a < c && a < d && a < e {
		// afg,bgh,chi,dij,ejf
		return fmt.Sprintf("%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d", a, f, g, b, g, h, c, h, i, d, i, j, e, j, f)
	}
	if b < a && b < c && b < d && b < e {
		// bgh,chi,dij,ejf,afg
		return fmt.Sprintf("%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d", b, g, h, c, h, i, d, i, j, e, j, f, a, f, g)
	}
	if c < a && c < b && c < d && c < e {
		// chi,dij,ejf,afg,bgh
		return fmt.Sprintf("%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d", c, h, i, d, i, j, e, j, f, a, f, g, b, g, h)
	}
	if d < a && d < b && d < c && d < e {
		// dij,ejf,afg,bgh,chi
		return fmt.Sprintf("%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d", d, i, j, e, j, f, a, f, g, b, g, h, c, h, i)
	}
	// ejf,afg,bgh,chi,dij
	return fmt.Sprintf("%d%d%d%d%d%d%d%d%d%d%d%d%d%d%d", e, j, f, a, f, g, b, g, h, c, h, i, d, i, j)
}
