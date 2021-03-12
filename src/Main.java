import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String beneficiaryId;
        String choice;
        String message;
        double amount;
        boolean wasSuccessful;
        boolean quitFlag = false;
        Scanner scan = new Scanner(System.in);
        ATM atm = new ATM();


        String id = "";
        while (!quitFlag) {
            while (!atm.isValidId(id)) {
                System.out.println("==== ATM service ====");
                System.out.print("Enter account id: ");
                id = scan.nextLine().toUpperCase();

                if (!atm.isValidId(id))
                    System.out.println("Invalid ID");
                System.out.println("=====================\n");
            }

            System.out.print(atm.MENU);
            choice = scan.nextLine().toUpperCase();
            switch (choice) {
                case "1":
                    System.out.printf("\nYour balance is %.2f%n\n", atm.getBalance(id));
                    break;

                case "2":
                    System.out.print("\nAmount to deposit: ");
                    amount = Double.parseDouble(scan.nextLine());

                    wasSuccessful = atm.deposit(id, amount);

                    message = wasSuccessful ? "$" + amount + " Deposited successfully." : "Deposit failed";
                    System.out.println(message + "\n");
                    break;

                case "3":
                    System.out.print("\nAmount to withdraw: ");
                    amount = Double.parseDouble(scan.nextLine());

                    wasSuccessful = atm.withdraw(id, amount);

                    message = wasSuccessful ? "$" + amount + " Withdrew successfully." : "Withdraw failed";
                    System.out.println(message + "\n");
                    break;
                case "4":
                    System.out.print("\nBeneficiary ID: ");
                    beneficiaryId = scan.nextLine().toUpperCase();

                    System.out.print("\nEnter amount: ");
                    amount = Double.parseDouble(scan.nextLine());

                    wasSuccessful = atm.transaction(id, beneficiaryId, amount);

                    message = wasSuccessful ? "$" + amount + " Transaction made successfully." : "Transaction failed";
                    System.out.println(message + "\n");
                    break;
                case "L":
                    id = "";
                    System.out.println("Logging out...");
                    break;
                case "0":
                    quitFlag = true;
                    System.out.println("Quiting...");
                    break;
            }
        }
    }
}
