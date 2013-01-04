package com.poj;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 2351 Time zones
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2351 {
    private static Map<String, Double> zones = new HashMap<String, Double>();

    public static void main(String[] args) {
        init_zones();
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int N = cin.nextInt();
        while (N-- > 0) {
            String time = cin.next();
            String am = null;
            if (!"noon".equals(time) && !"midnight".equals(time))
                am = cin.next();
            String z1 = cin.next();
            String z2 = cin.next();
            change(time, am, z1, z2);
        }
    }

    private static void change(String time, String am, String z1, String z2) {
        // 11:29 a.m. EST GMT
        // 11:29 a.m -5 0
        double dlt = zones.get(z2) - zones.get(z1); // 5
        // 16:29 a.m --> 4:29 pm
        int h = 0, m = 0;
        if ("noon".equals(time)) {
            h = 12;
            m = 0;
        } else if ("midnight".equals(time)) {
            h = 0;
            m = 0;
        } else {
            int idx = time.indexOf(":");
            h = Integer.parseInt(time.substring(0, idx));
            String minite = time.substring(idx + 1);
            if (minite.charAt(0) == '0')
                m = Integer.parseInt(minite.substring(1));
            else
                m = Integer.parseInt(minite);
        }
        // 60进制
        int t = h * 60 + m;
        if ("p.m.".equals(am) && h != 12)
            t += 720;
        if ("a.m.".equals(am) && h == 12)
            t -= 720;
        t += dlt * 60;

        if (t < 0)
            t += 1440;
        else if (t >= 1440)
            t -= 1440;
        if (t == 0) {
            System.out.println("midnight");
            return;
        }
        if (t == 720) {
            System.out.println("noon");
            return;
        }
        boolean pm = t >= 720;
        h = t / 60;
        m = t % 60;
        if (!pm) {
            if (h == 0)
                h = 12;
        } else {
            h -= 12;
            if (h == 0)
                h = 12;
        }
        String s = h + ":";
        if (m < 10)
            s += "0";
        s += m;
        s += " ";
        s += pm ? "p.m." : "a.m.";
        System.out.println(s);
    }

    private static void init_zones() {
        zones.put("UTC", 0.0);
        zones.put("GMT", 0.0);
        zones.put("BST", 1.0);
        zones.put("IST", 1.0);
        zones.put("WET", 0.0);
        zones.put("WEST", 1.0);
        zones.put("CET", 1.0);
        zones.put("CEST", 2.0);
        zones.put("EET", 2.0);
        zones.put("EEST", 3.0);
        zones.put("MSK", 3.0);
        zones.put("MSD", 4.0);
        zones.put("AST", -4.0);
        zones.put("ADT", -3.0);
        zones.put("NST", -3.5);
        zones.put("NDT", -2.5);
        zones.put("EST", -5.0);
        zones.put("EDT", -4.0);
        zones.put("CST", -6.0);
        zones.put("CDT", -5.0);
        zones.put("MST", -7.0);
        zones.put("MDT", -6.0);
        zones.put("PST", -8.0);
        zones.put("PDT", -7.0);
        zones.put("HST", -10.0);
        zones.put("AKST", -9.0);
        zones.put("AKDT", -8.0);
        zones.put("AEST", 10.0);
        zones.put("AEDT", 11.0);
        zones.put("ACST", 9.5);
        zones.put("ACDT", 10.5);
        zones.put("AWST", 8.0);
    }
}
