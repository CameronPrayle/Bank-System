import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Staff s1 = new Staff();
        s1.checkDate();
        /*
        //Creating StaffCredentialsChecker class and running login:
        StaffCredentialsChecker staffCredentialsChecker = new StaffCredentialsChecker();
        staffCredentialsChecker.RunLoginSystem();
        */
//      Options Interface
        boolean exit=false;
        while (!exit){
            System.out.println("\n*******************************");
            System.out.println("1. Input User");
            System.out.println("2. View User Details");
            System.out.println("3. Transfer Funds");
            System.out.println("4. Exit");
            System.out.println("*******************************");

//          Get user choice
            Scanner scan = new Scanner(System.in);
            int userChoice = scan.nextInt();

//          Choice checks
            switch (userChoice) {
                case 1 -> {
                    Scanner anotherScan = new Scanner(System.in);
                    System.out.println("\nEnter customer name: ");
                    String nameInput = anotherScan.nextLine();
                    System.out.println("\nEnter customer address: ");
                    String addressInput = anotherScan.nextLine();
                    System.out.println("\nEnter customer email address: ");
                    // Establish input rules
                    String emailInput = anotherScan.nextLine();
                    int ageInput = 0;
                    while (ageInput<16){
                        System.out.println("\nEnter customer age: ");
                        ageInput = anotherScan.nextInt();
                    }
                    double balance = 0.00;
                    Account A1 = new Account(nameInput, addressInput, emailInput, ageInput, balance);
                    A1.writeDetails();
                }
                case 2 -> s1.viewDetails();
                case 4 -> exit = true;
            }
        }
    }
}

