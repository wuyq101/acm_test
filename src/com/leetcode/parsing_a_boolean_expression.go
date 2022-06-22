package main

import "fmt"

/**
Return the result of evaluating a given boolean expression, represented as a string.

An expression can either be:

"t", evaluating to True;
"f", evaluating to False;
"!(expr)", evaluating to the logical NOT of the inner expression expr;
"&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
"|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...
*/
func main() {
	list := []string{
		"!(f)",
		"|(f,t)",
		"&(t,f)",
	}
	for _, s := range list {
		fmt.Printf("%s ---> %v\n", s, parseBoolExpr(s))
	}

}

// parseBoolExpr
// 将 !( &( |( 当作操作符入栈，当遇到）时候，开始将之前的结果出栈，开始计算，知道遇到某个操作符位置
func parseBoolExpr(expression string) bool {
	stack := make([]byte, 0)
	expr := []byte(expression)
	for len(expr) > 0 {
		first := expr[0]
		expr = expr[1:]
		switch first {
		case 't', 'f':
			stack = append(stack, first)
			continue
		case '!', '&', '|':
			stack = append(stack, first)
		case '(', ',':
			continue
		case ')':
			//开始出栈，直到遇到一个操作符号
			list := make([]byte, 0)
			last := stack[len(stack)-1]
			for last == 't' || last == 'f' {
				list = append(list, last)
				stack = stack[:len(stack)-1]
				last = stack[len(stack)-1]
			}
			op := last
			stack = stack[:len(stack)-1]
			value := eval(op, list)
			stack = append(stack, value)
		}
	}
	return stack[0] == 't'
}

func eval(op byte, args []byte) byte {
	if op == '!' {
		if args[0] == 'f' {
			return 't'
		}
		return 'f'
	}
	if op == '&' {
		for i := 0; i < len(args); i++ {
			if args[i] == 'f' {
				return 'f'
			}
		}
		return 't'
	}

	if op == '|' {
		for i := 0; i < len(args); i++ {
			if args[i] == 't' {
				return 't'
			}
		}
		return 'f'
	}

	return 't'
}
