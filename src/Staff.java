import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Staff {
    String[] usernames = {"admin"};
    String[] password = {"Password123!"};

    public Staff() {
    }

    public void viewDetails(){
        System.out.println("\nEnter account number: ");
        Scanner scan = new Scanner(System.in);
        String userChoice = scan.nextLine();

        try {
            File f = new File("Accounts.txt");
            Scanner readFile = new Scanner(f);
            String line;

            while (readFile.hasNextLine()) {
                line = readFile.nextLine();
                if(line.equals(userChoice)){
                    System.out.println("\n");
                    for (int i=0; i<7; i++){
                        System.out.println(line);
                        line = readFile.nextLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
