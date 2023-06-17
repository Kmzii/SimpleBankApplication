import java.util.Scanner;

public class BankApplication {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount[] bankAccounts = new BankAccount[3]; // Array to store bank accounts

        // Create bank accounts for 3 customers

        for (int i = 0; i < 3; i++) {
            System.out.println("Enter name for customer " + (i + 1) + ":");
            String name = sc.nextLine();
            //Check if exists
            if(check(bankAccounts,name)){
                System.out.println("Account with this name already exists.");
                i--;
                continue;
            }
            String customerId = generateCustomerId(); // Generate a random 6-digit account number
            String password = getPass(sc);
            bankAccounts[i] = new BankAccount(name, customerId, password);
            System.out.println("Bank account created for customer " + (i + 1) + ".");
            System.out.println("-----------------------------");
        }

        System.out.println("Enter your username (customer name):");
        String username = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();

        BankAccount currentAccount = null;
        for (BankAccount account : bankAccounts) {
            if (account.validateCredentials(username, password)) {
                currentAccount = account;
                break;
            }
        }

        if (currentAccount != null) {
            currentAccount.menu();
        } else {
            System.out.println("Invalid username or password. Access denied.");
        }
        sc.close();
    }
    //to check account name
    public static boolean check(BankAccount[] bankAccounts, String n){
        for(BankAccount account : bankAccounts){
            if(account !=null && account.getCustomerName().equalsIgnoreCase(n)){
                return true;
            }
        }
        return false;
    }
    private static String getPass(Scanner sc){
       String password;
       do{
           System.out.println("Enter a password: ");
           password = sc.nextLine();

           if(checkPass(password)){
               System.out.println("Password must contain at least one uppercase letter, one lowercase letter, one digit and one special character.");
           }
       }while(checkPass(password));
       return password;
    }
    //to check password
    public static boolean checkPass(String pass){
        boolean hasNumber = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasChar = false;
        for(char c : pass.toCharArray()){
            if(Character.isDigit(c))
                hasNumber = true;
            else if(Character.isUpperCase(c))
                hasUpper = true;
            else if(Character.isLowerCase(c))
                hasLower = true;
            else
                hasChar = true;
        }
        return !hasNumber || !hasUpper || !hasLower || !hasChar;
    }
    // Method to generate a random 6-digit account number
    public static String generateCustomerId() {
        int min = 100000;
        int max = 999999;
        int randomNum = min + (int) (Math.random() * (max - min + 1));
        return String.valueOf(randomNum);
    }
}

class BankAccount {
    private double bal;
    private double prevTrans;
    final String customerName;
    final String customerId;
    final String password;

    BankAccount(String customerName, String customerId, String password) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.password = password;
    }

    String getCustomerName(){
        return customerName;
    }
    void deposit(double amount) {
        if (amount != 0) {
            bal += amount;
            prevTrans = amount;
        }
    }

    void withdraw(double amt) {
        if (amt != 0) {
            if (bal >= amt && bal>=5000) {
                bal -= amt;
                prevTrans = -amt;
            }
            else {
                System.out.println("Insufficient balance. Withdrawal canceled.");
            }
        }
    }

    void getPreviousTrans() {
        if (prevTrans > 0) {
            System.out.println("Deposited: " + prevTrans);
        } else if (prevTrans < 0) {
            System.out.println("Withdrawn: " + Math.abs(prevTrans));
        } else {
            System.out.println("No transaction occurred.");
        }
    }

    boolean validateCredentials(String username, String password) {
        return customerName.equals(username) && this.password.equals(password);
    }

    void menu() {
        char option;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome " + customerName);
        System.out.println("Your ID: " + customerId);
        System.out.println();
        System.out.println("a) Check Balance");
        System.out.println("b) Deposit Amount");
        System.out.println("c) Withdraw Amount");
        System.out.println("d) Previous Transaction");
        System.out.println("e) Exit");

        do {
            System.out.println("********************************************");
            System.out.println("Choose an option");
            option = sc.next().charAt(0);
            System.out.println();

            switch (option) {
                case 'a' -> {
                    System.out.println("......................");
                    System.out.println("Balance =" + bal);
                    System.out.println("......................");
                    System.out.println();
                }
                case 'b' -> {
                    System.out.println("......................");
                    System.out.println("Enter an amount to deposit:");
                    System.out.println("......................");
                    double amt = sc.nextDouble();
                    deposit(amt);
                    System.out.println();
                }
                case 'c' -> {
                    System.out.println("......................");
                    System.out.println("Enter an amount to withdraw:");
                    System.out.println("......................");
                    double amtW = sc.nextDouble();
                    withdraw(amtW);
                    System.out.println();
                }
                case 'd' -> {
                    System.out.println("......................");
                    System.out.println("Previous Transaction:");
                    getPreviousTrans();
                    System.out.println("......................");
                    System.out.println();
                }
                case 'e' -> System.out.println("......................");
                default -> System.out.println("Choose a correct option to proceed");
            }

        } while (option != 'e');

        System.out.println("Thank you for using our banking services");
        sc.close();
    }
}
