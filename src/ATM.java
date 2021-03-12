import Accounts.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private Map<String, Account> accounts;
    public final String MENU;

    public ATM() {
        this.accounts = readClientAccountsFile();
        MENU = "====== Welcome ======\n" +
                "[1] Check balance\n" +
                "[2] Deposit\n" +
                "[3] Withdraw\n" +
                "[4] Transaction\n" +
                "[0] Quit\n" +
                "[L] Logout\n" +
                "Enter your choice: ";
    }

    public double getBalance(String id) {
        return accounts.get(id).getBalance();
    }

    public boolean transaction(String clientId1, String clientId2, double value) {
        Account account1 = accounts.get(clientId1);
        Account account2 = accounts.get(clientId2);

        if (account1 == null || account2 == null) return false;
        if (account1.getBalance() < value) return false;
        if (clientId1.equals(clientId2)) return false;

        account1.withdraw(value);
        account2.deposit(value);
        transactionLog(account1.toString(), account2.toString(), value);
        return true;
    }

    public boolean deposit(String clientId, double value) {
        if (value <= 0) return false;

        accounts.get(clientId).deposit(value);
        return true;
    }

    public boolean withdraw(String clientId, double value) {
        Account account = accounts.get(clientId);

        if (account == null) return false;
        if (account.getBalance() < value) return false;

        account.withdraw(value);
        return true;
    }

    public boolean isValidId(String id) {
        return accounts.containsKey(id);
    }

    private void transactionLog(String account1, String account2, double value) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactionLog.txt", true))) {
            writer.write(String.format("%s to %s: %.2f at %s\n", account1, account2, value, date));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String,Account> readClientAccountsFile() {
        try (Scanner scan = new Scanner(new BufferedReader(new FileReader("clientAccounts.txt")))) {
            Map<String,Account> accountMap = new HashMap<>();
            String[] data;
            String id;
            String name;
            double balance;

            while (scan.hasNextLine()) {
                data = scan.nextLine().split(",");
                id = data[0];
                name = data[1];
                balance = Double.parseDouble(data[2]);

                if (id.contains("C")) {
                    accountMap.put(id, new CheckingAccount(id, name, balance));
                } else if (id.contains("S")) {
                    accountMap.put(id, new SavingsAccount(id, name, balance));
                } else if (id.contains("B")) {
                    accountMap.put(id, new BusinessAccount(id, name, balance));
                }
            }
            scan.close();
            return accountMap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
