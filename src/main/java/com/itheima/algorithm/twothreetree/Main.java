package com.itheima.algorithm.twothreetree;


public class Main {
    public static void main(String[] args) {
        TwoThreeTree ttt = new TwoThreeTree();
        ttt.insert(10);
        ttt.insert(20);
        ttt.insert(5);
        ttt.insert(6);
        ttt.insert(12);
        ttt.insert(30);
        ttt.insert(7);
        ttt.insert(17);
        ttt.traverse(); // output: 5 6 7 10 12 17 20 30
    }
}
