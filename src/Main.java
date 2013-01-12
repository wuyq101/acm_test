import java.util.Scanner;

/**
 * <pre>
 * 原题：http://poj.org/problem?id=1031
 * 题意：平面上有多边形栅栏，然后原点处是灯，求栅栏被灯所照到的总亮度。
 * 分析：看了讨论里边Discuss的某条信息(http://poj.org/showmessage?message_id=172128)之后，可以当做题解报告了，摘抄如下：
 * 设dl的一个端点为点A,一个端点为点B，点O(台灯)作垂直于该栅栏的线，交栅栏于点D，如下图：
 *          ————D——A—-—B—————  OA请自行想象...
 *          |         /
 *          |        /
 *          |       / 
 *          |      /
 *          |     /
 *          |    /
 *          |   /
 *          |  /
 *          | /
 *          |/
 *          O
 * 
 * 有OB=r, 那么OD=r*cosa
 * 设OA与OB的夹角为da,则，
 * dl
 * =AB
 * =BD-AD
 * =OD*(tana-tan(a-da))
 * =r*(sina-cosa*(tana-tanda)/(1+tana*tanda))      //tan(a-b)公式
 * =r*(sina*tana*tanda+cosa*tanda)/(1+tana*tanda)  //通分
 * =r*tanda/(cosa+sina*tanda)                      //上下同乘cosa
 * =r*sinda/(cosa*cosda+sina*sinda)                //上下同乘cosda
 * =r*sinda/cos(a-da)
 * =r*da/cos(a-da)                                 //sinda=da                                
 * =r*da/cosa                                      //这步不知道是不是有点问题
 * 
 * 所以
 * dI
 * =I0*|cosa|*dl*h
 * =k/r*|cosa|*r*da/cosa*h
 * =k*h*da
 * 经过上面的分析，可以发现栅栏的总亮度只和多边形被台灯照到的角度有关。如果台灯在多边形内部，照到的总角度和为2*pi；如果台灯在多边
 * 形外部，则需要计算多边形被光照到的最大角度。计算的时候，只有按照逆时针顺序来计算多边形一条边的两个顶点和原点构成的两个向量的夹角，
 * 角度a = cos-1(a.b/|a|*|b|); 如果角度和是2*pi，则台灯在内部，如果角度和是0.则台灯在外部，把刚才计算的角度中所有符号为正的相
 * 加，即是该多边形被照到的最大角度。
 * </pre>
 */
public class Main {
    private static double k;
    private static double h;
    private static int N;
    private static double[] x;
    private static double[] y;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        input(cin);
    }

    private static void input(Scanner cin) {
        k = cin.nextDouble();
        h = cin.nextDouble();
        N = cin.nextInt();
        x = new double[N];
        y = new double[N];
        for (int i = 0; i < N; i++) {
            x[i] = cin.nextDouble();
            y[i] = cin.nextDouble();
        }
    }
}
