package main

/**
You are given two strings s and t. In one step, you can append any character to either s or t.

Return the minimum number of steps to make s and t anagrams of each other.

An anagram of a string is a string that contains the same characters with a different (or the same) ordering.

Input: s = "leetcode", t = "coats"
Output: 7
Explanation:
- In 2 steps, we can append the letters in "as" onto s = "leetcode", forming s = "leetcodeas".
- In 5 steps, we can append the letters in "leede" onto t = "coats", forming t = "coatsleede".
"leetcodeas" and "coatsleede" are now anagrams of each other.
We used a total of 2 + 5 = 7 steps.
It can be shown that there is no way to make them anagrams of each other with less than 7 steps.
*/
func main() {
	a := "night"
	b := "thing"
	println(minSteps(a, b))
}

func minSteps(s string, t string) int {
	cnt := make([]int, 26)
	buf := []byte(s)
	for _, ch := range buf {
		cnt[ch-'a']++
	}
	buf = []byte(t)
	for _, ch := range buf {
		cnt[ch-'a']--
	}
	sum := 0
	for _, v := range cnt {
		if v > 0 {
			sum += v
		} else {
			sum -= v
		}
	}
	return sum
}
