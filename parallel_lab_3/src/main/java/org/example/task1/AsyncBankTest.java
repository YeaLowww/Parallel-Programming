package org.example.task1;

public class AsyncBankTest {
    //    VARIANT can range from 1 to 3
    public static final int VARIANT = 3;
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        System.out.println("\nVariant #" + VARIANT +
                "\n--------------------------------------"+
                "\nNACCOUNTS = " + NACCOUNTS +
                "\nINITIAL_BALANCE = " + INITIAL_BALANCE +
                "\nInitial total bank: " + NACCOUNTS * INITIAL_BALANCE +
                "\n--------------------------------------");
        Bank bank = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            TransferThread thread = new TransferThread(bank, i, INITIAL_BALANCE, VARIANT);
            thread.setPriority(Thread.NORM_PRIORITY + i % 2);
            thread.start();
        }
    }
}