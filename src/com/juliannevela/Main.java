package com.juliannevela;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int principal = (int)readPrompt("Principal ($1K - $1M)", 1000, 1_000_000);
        float annualInterest = (float)readPrompt("APR", 1, 30);
        byte years = (byte)readPrompt("Term length", 10, 30);

        double mortgage = calculateMortgage(principal, annualInterest, years);
        System.out.println("Mortgage: ");
        System.out.println("=====================");
        System.out.println("Monthly Payments: " + NumberFormat.getCurrencyInstance().format(mortgage));
        System.out.println("Payment Schedule: ");
        System.out.println("=====================");
        // display list of remaining balance after each payment. 
    }

    public static double readPrompt(String question, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;

        while(true) {
            System.out.print(question + ": ");
            value = scanner.nextDouble();
            if(value >= min && value <= max)
                break;
            System.out.println("Enter a valid number between " + min + " and " + max);
        }
        return value;
    }

    public static double calculateMortgage(int principal, float annualInterest, byte years) {
        final byte MONTHS_IN_YEAR = 12;
        final byte PERCENT = 100;

        float monthlyRate = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short totalPayments = (short)(years * MONTHS_IN_YEAR);
        return principal
                * (monthlyRate * Math.pow(1 + monthlyRate, totalPayments)
                / (Math.pow(1 + monthlyRate, totalPayments) - 1));
    }
}