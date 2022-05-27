import java.util.Scanner;

public class Menu {
    public static String main (){
        System.out.println("\n*******************************");
        System.out.println("1. Input User");
        System.out.println("2. View User Details");
        System.out.println("3. Transfer Funds");
        System.out.println("4. Request chequebook (Business account only)");
        System.out.println("5. Deposit");
        System.out.println("6. Withdraw");
        System.out.println("7. Exit");
        System.out.println("*******************************");

//      Get user choice
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
