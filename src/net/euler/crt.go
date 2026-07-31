package main

/*
中国剩余定理 CRT
假设有一组两两互质的正整数 m1,m2,...,mk.

有一组正整数 a1,a2,...,ak.
那么存在唯一的正整数 x 满足:

x ≡ a1 (mod m1)
x ≡ a2 (mod m2)
x ≡ a3 (mod m3)
...
x ≡ ak (mod mk)

在模M=m1*m2*...*mk下，有且仅有一个解。 在[0,M-1] 内，有且仅有一个解。
在其他范围内，x 都可以写成 x+cM,(c为整数)的形式，这些解是等价的。

求解过程：
1. 计算M和Mi，正交隔离
M = m1*m2*...*mk
Mi = M/mi, 除mi之外另外数的乘积
构造Mi的意义是，对于任意的j!=i,Mi一定能被mj整除。
Mi = 0 (mod mj)
但是对mi来说，由于mi和其他的数互质，所以 gcd(Mi,mi) = 1, 两者互质
所以Mi就像一个开关，只对mi有用，对所有其他的mj，它产生的余数都是0

2. 求逆元ti, Mi * ti = 1 (mod mi)
因为 x=ai (mod mi), 我们需要余数是ai.
但是Mi对mi的余数不确定，我们需要找到Mi在(mod mi)下的逆元ti。
这样 当x = Mi*ti*ai (mod mi)的时候，余数就是ai了。

3. 求和，线性叠加
x = ∑Mi*ti*ai
分析其中的一项 Xi = Mi*ti*ai, Xi mod mi = ai， Xi mod mj = 0 (mj!=mi)

所以这个x就是上面方程组的解
*/
