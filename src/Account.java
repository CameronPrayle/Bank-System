import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Account {
    String accNum;
    String sortCode;
    String name;
    String address;
    String email;
    int age;
    float balance;
    float interest = 0;


    public Account(String name, String address, String email, int age, float balance) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
        this.balance = balance;

//      Generate random account number
        Random rand = new Random();
        String rNums = "";
        for (int i=0; i<8;i++){
            String rNum = String.valueOf(rand.nextInt(9));

//          Get new number if first random number is 0
            if(i==0 && rNum.equals("0")){
                while(rNum.equals("0")){
                    rNum = String.valueOf(rand.nextInt(9));
                }
            }
            rNums = rNums + rNum;
        }
        this.accNum = rNums;
    }

    public void writeDetails() {

        System.out.println("\n*******************************");
        System.out.println("Enter account type: ");
        System.out.println("1. Personal");
        System.out.println("2. ISA");
        System.out.println("3. Business");
        System.out.println("*******************************");
        Scanner scan = new Scanner(System.in);
        String userChoice = scan.nextLine();
        String fileChoice = "";

        boolean exit = false;
        switch (userChoice) {
            case "1" :
                Current c1 = new Current(name, address, email, age, balance);
                fileChoice = "Accounts.txt";
            case "2" : Business b1 = new Business(name, address, email, age, balance);
                fileChoice = "Business.txt";
            case "3" : ISA i1 = new ISA(name, address, email, age, balance);
                fileChoice = "ISA.txt";
            default : exit = true;
        }

        try {
            FileWriter fw = new FileWriter(fileChoice, true);

            fw.write(this.accNum +
                    "\n" + this.sortCode +
                    "\n" + this.name +
                    "\n" + this.address +
                    "\n" + this.email +
                    "\n" + this.age +
                    "\n" + this.balance +
                    "\n" + this.interest +
                    "\n" + "----------" + "\n");

            fw.close();
            System.out.println("Information Saved");

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}

