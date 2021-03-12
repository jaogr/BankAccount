package Accounts;

public abstract class Account {
    private String clientId;
    private String clientName;
    private double balance;

    public Account(String clientId, String clientName, double balance) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.balance = balance;
    }

    public void deposit(double value) {
        balance += value;
    }

    public void withdraw(double value) {
        balance -= value;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", clientName, clientId);
    }
}
