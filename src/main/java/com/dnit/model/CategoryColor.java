package com.dnit.model;

import java.util.HashMap;
import java.util.Map;

public class CategoryColor {

    private static Map<Integer, String> colors = new HashMap<>();

    static {
        colors.put(1, "45caff");  colors.put(2, "A272B4");  colors.put(3, "45CAFF");  colors.put(4, "ff930f");
        colors.put(5, "fff95b");  colors.put(6, "f89b29");  colors.put(7, "ff5858");  colors.put(8, "103783");
        colors.put(9, "ebf4f5");  colors.put(10, "432371"); colors.put(11, "aefb2a"); colors.put(12, "5cb270");
        colors.put(13, "f5e6ad"); colors.put(14, "d4acfb"); colors.put(15, "95f9c3"); colors.put(16, "83f5e5");
        colors.put(17, "5fc52e"); colors.put(18, "471069"); colors.put(19, "eed991"); colors.put(20, "028cf3");
        colors.put(21, "0d41e1"); colors.put(22, "f44369"); colors.put(23, "87a3a3"); colors.put(24, "f74c06"); 
        colors.put(25, "08203e"); colors.put(26, "bc1b68"); colors.put(27, "5e435d"); colors.put(28, "295270"); 
        colors.put(29, "beb15b"); colors.put(30, "62f4f9"); colors.put(31, "8578ea"); colors.put(0, "f3a46c"); 
    }



    public static String color(int i) {
        if (i > 31)
            throw new RuntimeException("Only 31 category avaliable. A invalid number was inserted");
        return "ff" + colors.get(i);
    }

}
