import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Staff s1 = new Staff();

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
            if(userChoice.equals("1")){
                Current c1 = new Current("Ellie Jones", "18 Brighton Road", "ellie.jones@gmail.com", 27, 2571.82f);
                c1.writeDetails();
            }

            else if(userChoice.equals("2")){
                s1.viewDetails();
            }

            else if(userChoice.equals("4")){
                exit=true;
            }
        }
    }
}

