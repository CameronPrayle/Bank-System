import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Staff {
    String[] usernames = {"admin"};
    String[] password = {"Password123!"};
    String[] accountTags ={"Account Number: ", "Sort Code: ", "Name: ", "Address: ", "Email: ", "Age: ", "Balance: ", "Interest: "};

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
                    System.out.println(accountTags[0]+line);
//                  Print all lines from account number (loops until last line for that account)
                    int i=1;
                    while (!Objects.equals(line, "----------")){
                        line = readFile.nextLine();
                        try {
                            System.out.println(accountTags[i] + line);
                        } catch(ArrayIndexOutOfBoundsException e){
                            continue;
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
