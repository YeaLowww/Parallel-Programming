package org.example.task1;

public class TransferThread extends Thread {
    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    private final int variant;

    private static final int REPS = 1000;

    public TransferThread(Bank bank, int fromAccount, int maxAmount, int variant) {
        this.bank = bank;
        this.fromAccount = fromAccount;
        this.maxAmount = maxAmount;
        this.variant = variant;
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random() / REPS);
            switch (variant) {
                case 1:
                    bank.transferV1(fromAccount, toAccount, amount);
                    break;
                case 2:
                    bank.transferV2(fromAccount, toAccount, amount);
                    break;
                case 3:
                    bank.transferV3(fromAccount, toAccount, amount);
                    break;
                default:
                    System.out.println("Wrong variant provided!");
                    return;
            }
        }
    }
}
