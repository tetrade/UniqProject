package main;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String comLine = in.nextLine();
        System.out.println(Arrays.toString(comLine.split("\\s-[ioucs]")));
    }
}
