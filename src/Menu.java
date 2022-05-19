import java.util.Scanner;

public class Menu {
    public static int main (){
        System.out.println("\n*******************************");
        System.out.println("1. Input User");
        System.out.println("2. View User Details");
        System.out.println("3. Transfer Funds");
        System.out.println("4. Exit");
        System.out.println("*******************************");

//          Get user choice
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }
}
