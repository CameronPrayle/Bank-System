import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Staff s1 = new Staff();
        s1.checkDate();
        //Creating StaffCredentialsChecker class and running login:
        StaffCredentialsChecker staffCredentialsChecker = new StaffCredentialsChecker();
        staffCredentialsChecker.RunLoginSystem();

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
            String userChoice = scan.nextLine();

//          Choice checks
            switch (userChoice) {
                case "1" -> {
                    Account A1 = new Account("Ellie Jones", "18 Brighton Road", "ellie.jones@gmail.com", 27, 2571.82f);
                    A1.writeDetails();
                }
                case "2" -> s1.viewDetails();
                case "4" -> exit = true;
            }
        }
    }
}

