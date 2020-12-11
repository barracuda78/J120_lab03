package com.barracuda.tasks.politeh.labs.j120.lab03.src;




public class Test {
    public static void main(String[] args){
        int x = 222;
        int y = 1;

        x = x ^ y;
        y = x ^ y;
        x = x ^ y;


        System.out.println(x);
        System.out.println(y);


        double inf = Double.POSITIVE_INFINITY;
        System.out.println(inf);
        System.out.println(inf * -1);
        System.out.println(inf - inf);
    }
}
