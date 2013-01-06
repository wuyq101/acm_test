package com.wuyq101.template;

/**
 * 计算几何相关的模板类
 * @author wuyq101
 * @version 1.0
 */
public class Geometry {
    /**
     * 一个坐标点的数据结构
     *
     * @author wuyq101
     * @version 1.0
     */
    static class P {
        int x;
        int y;

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
    
    /**
     * <pre>
     * 两个向量（x2-x1,y2-y1）,(x3-x1,y3-y1)的叉积cp。
     * 1. cp的一半是一个三角型的有向面积
     * 2. cp的符号代表向量旋转的方向，如果向量A乘以向量B，如果为正，则A逆时针转向B，否则为顺时针
     *    如果为0，则共线
     * </pre>
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static long cross_product(P p1, P p2, P p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }
    
    /**
     * 计算两点之间的距离
     * 
     * @param p1
     * @param p2
     * @return
     */
    public static double distance(P p1, P p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }
}
