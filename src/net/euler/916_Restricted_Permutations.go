package main

/*
*

  - @see https://projecteuler.net/problem=916
    1. There is no ascending subsequence with more than n+1 elements, and

    2. There is no descending subsequence with more than two elements.

    P(2) = 13
    1. 不能有长度为3以上的递增子序列
    2. 不能有长度为2以上的递减子序列

    {1,2,3,4}	X    包含{1,2,3,4}
    {1,2,4,3}	Y
    {1,3,2,4}	Y
    {1,3,4,2}	Y
    {1,4,2,3}	Y
    {1,4,3,2}   X    包含{4,3,2}
    {2,1,3,4}   Y
    {2,1,4,3}   Y
    {2,3,1,4}   Y
    {2,3,4,1}   Y
    {2,4,1,3}   Y
    {2,4,3,1}   X	 包含{4,3,1}
    {3,1,2,4}   Y
    {3,1,4,2}   Y
    {3,2,1,4}   X	 包含{3,2,1}
    {3,2,4,1}   X	 包含{3,2,1}
    {3,4,1,2}   Y
    {3,4,2,1}   X	 包含{3,2,1}
    {4,1,2,3}   Y
    {4,1,3,2}   X	 包含{4,3,2}
    {4,2,1,3}	X	 包含{4,2,1}
    {4,2,3,1}   X	 包含{4,2,1}
    {4,3,1,2}	X	 包含{4,3,2}
    {4,3,2,1}   X	 包含{4,3,2}

    P(1) = 2
    {1,2}	Y
    {2,1}	Y
*/
func main() {
	println("916")
}
