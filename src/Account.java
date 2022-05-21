import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Account {
    String accNum;
    String sortCode;
    String name;
    String address;
    String email;
    int age;
    double balance;
    float interest = 0;


    public static void inputDetails(){
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
        Account account = new Account(nameInput, addressInput, emailInput, ageInput, balance);
        account.writeDetailsChoice();
    }

    public Account(String name, String address, String email, int age, double balance) {
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

    public void writeDetailsChoice() {

            System.out.println("\n*******************************");
            System.out.println("Enter account type: ");
            System.out.println("1. Personal");
            System.out.println("2. ISA");
            System.out.println("3. Business");
            System.out.println("*******************************");
            Scanner scan = new Scanner(System.in);
            int userChoice = scan.nextInt();


        String fileChoice = "";
        if (userChoice == 1) {
            Current c1 = new Current(name, address, email, age, balance);
            fileChoice = "Accounts.txt";
            c1.writeDetails(fileChoice);
            System.out.println("current");}
        else if (userChoice == 2){
            ISA i1 = new ISA(name, address, email, age, balance);
            fileChoice = "ISA.txt";
            i1.writeDetails(fileChoice);
            System.out.println("ISA");}
        else if (userChoice == 3){
            Business b1 = new Business(name, address, email, age, balance);
            fileChoice = "business.txt";
            b1.writeDetails(fileChoice);
            System.out.println("business");}
        else {
            writeDetailsChoice();
        }
    }

    public void writeDetails(String fileChoice) {
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



    public static void transferDetails() {
        Scanner anotherScan = new Scanner(System.in);

//      Get sender details
        System.out.println("Input account number to transfer from:");
        String accFrom = anotherScan.nextLine();
        System.out.println("Input sort-code to transfer from:");
        String sortFrom = anotherScan.nextLine();


//      Get receiver details
        System.out.println("Input account number to transfer to:");
        String accTo = anotherScan.nextLine();
        System.out.println("Input sort-code to transfer to:");
        String sortTo = anotherScan.nextLine();

//      Get amount
        System.out.println("Input amount to transfer:");
        float amountToTransfer = Float.parseFloat(anotherScan.nextLine());

        transfer(accFrom, accTo, sortFrom, sortTo, amountToTransfer, false);
    }

    public static void transfer(String accFrom, String accTo, String sortFrom, String sortTo, float amountToTransfer, boolean send){
        String accTypeFile;
        String accAction;

        if(!send){
            accTypeFile=sortFrom;
            accAction=accFrom;
        }else{
            accTypeFile=sortTo;
            accAction=accTo;
        }

        switch (accTypeFile) {
//          Current
            case "24-65-32" -> accTypeFile="Accounts.txt";
//          ISA
            case "24-65-69" -> accTypeFile="ISA.txt";
//          Business
            case "24-65-27" -> accTypeFile="Business.txt";
        }


        File f = new File(accTypeFile);
        try {
            List<String> fileContents = new ArrayList<>();
            String currentLine;
            Scanner readFile = new Scanner(f);

            while(readFile.hasNextLine()){
                currentLine = readFile.nextLine();
                fileContents.add(currentLine);
                if(accAction.equals(currentLine)){
                    for(int i=0; i<6; i++){
                        currentLine=readFile.nextLine();
                        if(i==5){
                            if(!send){
                                currentLine= String.valueOf(Float.parseFloat(currentLine)-amountToTransfer);
                                fileContents.add(currentLine);
                            }else{
                                currentLine= String.valueOf(Float.parseFloat(currentLine)+amountToTransfer);
                                fileContents.add(currentLine);
                            }
                        }else{
                            fileContents.add(currentLine);
                        }
                    }
                }
            }


//          Write contents of list to txt file
            FileWriter fw = new FileWriter(accTypeFile);
            for(int i=0; i<fileContents.size(); i++){
                fw.write(fileContents.get(i)+"\n");
            }
            fw.close();

            if (!send) {
                transfer(accFrom, accTo, sortFrom, sortTo, amountToTransfer, true);
            }

        } catch (IOException e) {
            System.out.println("Invalid sort-code");
            transferDetails();
        }
    }
}


