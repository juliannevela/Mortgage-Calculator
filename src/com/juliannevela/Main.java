package com.juliannevela;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    final static byte MONTHS_IN_YEAR = 12;
    final static byte PERCENT = 100;

    public static String convertCurrency(double balance) {
        return NumberFormat.getCurrencyInstance().format(balance);
    }
    public static void main(String[] args) {
        int principal = (int)readPrompt("Principal ($1K - $1M)", 1000, 1_000_000);
        float annualInterest = (float)readPrompt("APR", 1, 30);
        byte years = (byte)readPrompt("Term length", 10, 30);

        printMortgage(principal, annualInterest, years);

        printPaymentSchedule(principal, annualInterest, years);

    }

    private static void printMortgage(int principal, float annualInterest, byte years) {
        double mortgage = calculateMortgage(principal, annualInterest, years);
        System.out.println("Mortgage: ");
        System.out.println("=====================");
        System.out.println("Monthly Payments: " + convertCurrency(mortgage));
    }

    private static void printPaymentSchedule(int principal, float annualInterest, byte years) {
        System.out.println();
        System.out.println("Payment Schedule: ");
        for(short month = 1; month < years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterest, years, month);
            System.out.println(month + ": " + convertCurrency(balance));
        }
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
        float monthlyRate = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short totalPayments = (short)(years * MONTHS_IN_YEAR);

        return principal
                * (monthlyRate * Math.pow(1 + monthlyRate, totalPayments)
                / (Math.pow(1 + monthlyRate, totalPayments) - 1));
    }

    public static double calculateBalance(int principal, float annualInterest, byte years, short completedNumberOfPayments) {
        float monthlyRate = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short totalPayments = (short)(years * MONTHS_IN_YEAR);

        return principal
                    * (Math.pow(1+monthlyRate, totalPayments) - Math.pow(1+monthlyRate, completedNumberOfPayments))
                    / (Math.pow(1+monthlyRate, totalPayments) - 1);
    }
}