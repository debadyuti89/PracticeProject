package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter how many rows you want to print:");
        int rows = sc.nextInt();
        for (int i = 1; i <= rows; i++) {
            //Print leading spaces for alignment
            for (int j = 1; j <= (rows - i); j++) {
                System.out.print("  ");
            }
            //Print descending numbers from i to 1
            for (int j = i; j >= 1; j--) {
                System.out.print(j + " ");
            }
            //Print ascending numbers 2 up to i
            for (int j = 2; j <= i; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}