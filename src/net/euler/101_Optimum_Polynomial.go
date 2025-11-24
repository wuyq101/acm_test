package main

import (
	"fmt"
	"math"
	"math/big"
)

/*
f(1)=1
f(2)=683
f(3)=44287
f(4)=838861
f(5)=8138021
f(6)=51828151
f(7)=247165843
f(8)=954437177
f(9)=3138105961
f(10)=9090909091

OP(1,n) = 1

OP(2,n) = a*n + b
a*1 + b = 1
a*2 + b = 683
---> a = 682, b = 681
OP(2,n) = 682*n + 681

OP(3,n) = a*n^2 + b*n + c
a*1 + b*1 + c = 1
a*4 + b*2 + c = 683
a*9 + b*3 + c = 44287

1 1 1     a     f(1)
4 2 1  X  b  =  f(2)
9 3 1     c     f(3)
解线性方程组
1 1 1 | 1 0 0
4 2 1 | 0 1 0
9 3 1 | 0 0 1

1. r2 = r2-4*r1
1  1  1  |  1 0 0
0 -2 -3  | -4 1 0
9  3  1  |  0 0 1

2. r3 = r3-9*r1
1  1  1  |  1 0 0
0 -2 -3  | -4 1 0
0 -6 -8  | -9 0 1

3. r2 = r2*(-1/2)
1  1  1   |  1   0  0
0  1 3/2  |  2 -1/2 0
0 -6 -8   | -9   0  1

4. r1 = r1-r2
1  0 -1/2   |  1   0  0
0  1 3/2  |  2 -1/2 0
0 -6 -8   | -9   0  1

--->
*/
func main() {
	for i := 1; i <= 10; i++ {
		fmt.Printf("f(%d)=%d\n", i, f(i))
	}
	sum := int64(0)
	for i := 1; i <= 11; i++ {
		fit := solve(i)
		fmt.Printf("fit=%d\n", fit)
		sum += fit
	}
	fmt.Printf("total=%d\n", sum)
}

func f(n int) int {
	return 1 - n + n*n - n*n*n + n*n*n*n - n*n*n*n*n + n*n*n*n*n*n - n*n*n*n*n*n*n + n*n*n*n*n*n*n*n - n*n*n*n*n*n*n*n*n + n*n*n*n*n*n*n*n*n*n
	// return n * n * n
}

func solve(k int) int64 {
	A := makeMatrix(k)
	println("----A")
	printMatrix(A)

	B := inv(A)
	println("----B")
	printMatrix(B)

	C := mkFn(k)
	// 求系数
	X := make([]Fraction, k)
	for i := 0; i < k; i++ {
		f := Fraction{big.NewInt(0), big.NewInt(1)}
		for j := 0; j < k; j++ {
			f = FAdd(FMul(B[i][j], C[j]), f)
		}
		fmt.Printf("i=%d,f=%v\n", i, f)
		X[i] = f
	}
	// 预测第n+1项
	for i := 1; i <= k; i++ {
		fmt.Printf("f(%d)=%d fv=%v\n", i, fv(X, i), f(i))
	}
	v := fv(X, k+1)
	fmt.Printf("f(%d)=%d fv=%v\n", k+1, fv(X, k+1), f(k+1))
	b := big.NewInt(0)
	b.Div(v.Numerator, v.Denominator)
	return b.Int64()
}

func fv(X []Fraction, n int) Fraction {
	// x1*n^k + x2*n^(k-1) + ... + xk
	sum := Fraction{Numerator: big.NewInt(0), Denominator: big.NewInt(1)}
	k := len(X)
	p := int64(math.Pow(float64(n), float64(k-1)))
	for i := 0; i < k; i++ {
		f := Fraction{Numerator: big.NewInt(p), Denominator: big.NewInt(1)}
		p /= int64(n)
		sum = FAdd(sum, FMul(X[i], f))
	}
	return sum
}

func mkFn(k int) []Fraction {
	list := make([]Fraction, 0)
	for i := 1; i <= k; i++ {
		n := big.NewInt(int64(f(i)))
		list = append(list, Fraction{n, big.NewInt(1)})
	}
	return list
}

func makeMatrix(k int) [][]Fraction {
	A := make([][]Fraction, k)
	for i := 0; i < k; i++ {
		A[i] = make([]Fraction, k)
		A[i][k-1] = Fraction{big.NewInt(1), big.NewInt(1)}
		for j := k - 2; j >= 0; j-- {
			a := big.NewInt(0)
			a.Mul(big.NewInt(int64(i+1)), A[i][j+1].Numerator)
			A[i][j] = Fraction{a, big.NewInt(1)}
		}
	}
	return A
}

// 求矩阵的逆
func inv(A [][]Fraction) [][]Fraction {
	N := len(A)
	B := make([][]Fraction, N)
	for i := 0; i < N; i++ {
		B[i] = make([]Fraction, N)
		for j := 0; j < N; j++ {
			B[i][j] = Fraction{big.NewInt(0), big.NewInt(1)}
			if i == j {
				B[i][j] = Fraction{big.NewInt(1), big.NewInt(1)}
			}
		}
	}
	println("----B")
	printMatrix(B)
	for i := 0; i < N; i++ {
		// 针对第i行，将A[i][i]换成1
		f := A[i][i]
		finv := Fraction{f.Denominator, f.Numerator}
		// 第i行 * finv
		rowMul(A, i, finv)
		rowMul(B, i, finv)
		//	fmt.Printf("第%d行 * %v\n", i, finv)
		//		printWithArguments(A, B)
		for j := 0; j < N; j++ {
			if j == i {
				continue
			}
			// 将A[j][i]换成0
			f = A[j][i]
			b := big.NewInt(0)
			b.Neg(f.Numerator)
			f = Fraction{b, f.Denominator}
			// A[j] = A[i] * f + A[j]
			// 将第i行*f加到第j行
			//	fmt.Printf("A[%d] = A[%d] * %v + A[%d]\n", j, i, f, j)
			rowAdd(A, i, j, f)
			rowAdd(B, i, j, f)
			//			printWithArguments(A, B)
		}

	}
	return B
}

func printWithArguments(A, B [][]Fraction) {
	fmt.Printf("------\n")
	N := len(A)
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			fmt.Printf("%v\t", A[i][j])
		}
		fmt.Printf("\t|\t")
		for j := 0; j < N; j++ {
			fmt.Printf("%v\t", B[i][j])
		}
		fmt.Printf("\n")
	}
}

func rowAdd(A [][]Fraction, i, j int, f Fraction) {
	// 将A的第i行*f加到A的第j行
	N := len(A)
	for k := 0; k < N; k++ {
		A[j][k] = FAdd(FMul(A[i][k], f), A[j][k])
	}
}

func FAdd(a, b Fraction) Fraction {
	n := big.NewInt(0)
	n1 := big.NewInt(0)
	n1.Mul(a.Numerator, b.Denominator)
	n2 := big.NewInt(0)
	n2.Mul(b.Numerator, a.Denominator)
	n.Add(n1, n2)

	d := big.NewInt(0)
	d.Mul(a.Denominator, b.Denominator)
	g := big.NewInt(0)
	g.GCD(nil, nil, n, d)
	n.Div(n, g)
	d.Div(d, g)
	return Fraction{n, d}
}

func rowMul(A [][]Fraction, i int, f Fraction) {
	//  将A的第i行乘以f
	N := len(A)
	for j := 0; j < N; j++ {
		A[i][j] = FMul(A[i][j], f)
	}
}

func FMul(a, b Fraction) Fraction {
	//	fmt.Printf("%v * %v\n", a, b)
	n := big.NewInt(0)
	n.Mul(a.Numerator, b.Numerator)
	d := big.NewInt(0)
	d.Mul(a.Denominator, b.Denominator)
	g := big.NewInt(0)
	g.GCD(nil, nil, n, d)
	n.Div(n, g)
	d.Div(d, g)
	return Fraction{n, d}
}

func printMatrix(A [][]Fraction) {
	for i := 0; i < len(A); i++ {
		for j := 0; j < len(A[i]); j++ {
			fmt.Printf("%v\t", A[i][j])
		}
		fmt.Println()
	}
}

type Fraction struct {
	Numerator   *big.Int
	Denominator *big.Int
}

func (f Fraction) String() string {
	zero := big.NewInt(0)
	if f.Denominator.Cmp(zero) < 0 {
		f.Denominator.Neg(f.Denominator)
		f.Numerator.Neg(f.Numerator)
	}
	one := big.NewInt(1)
	if one.Cmp(f.Denominator) == 0 {
		return f.Numerator.String()
	}
	return fmt.Sprintf("%s/%s", f.Numerator.String(), f.Denominator.String())
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
