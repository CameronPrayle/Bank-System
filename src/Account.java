import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Account {

    //Variables:
    String accNum;
    String sortCode;
    String name;
    String address;
    String previousAddress1;
    String previousAddress2;
    String email;
    int age;
    double balance;
    float interest = 0;

    //Constructor:
    public Account(String name, String address, String previousAddress1, String previousAddress2, String email, int age, double balance) {
        this.name = name;
        this.address = address;
        this.previousAddress1 = previousAddress1;
        this.previousAddress2 = previousAddress2;
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

    private static String askForPreviousCustomerAddress(){
        //setting up scanner:
        Scanner addressScanner = new Scanner(System.in);
        System.out.println("\nHas customer lived at any other addresses in past 3 years? (y/n) ");
        String addressQAnswer = addressScanner.nextLine();
        String previousAddressInput = "";
        if(addressQAnswer.equalsIgnoreCase("y")){
            System.out.println("\nEnter previous customer address: ");
            previousAddressInput = addressScanner.nextLine();
        }
        return previousAddressInput;
    }

    public static void inputDetails() {
        Scanner anotherScan = new Scanner(System.in);
        System.out.println("\nEnter customer name: ");
        String nameInput = anotherScan.nextLine();
        System.out.println("\nEnter customer address: ");
        String addressInput = anotherScan.nextLine();

        //Checking for previous addresses in past 3 years:
        String previousAddress1 = askForPreviousCustomerAddress();
        String previousAddress2 = "";
        if(!Objects.equals(previousAddress1, ""))
        {
            previousAddress2 = askForPreviousCustomerAddress();
        }

        String emailInput;
        do {
            assert false;
            System.out.println("\nEnter customer email address: ");
            emailInput = anotherScan.nextLine();

            // Establish input rules

        } while (!emailInput.contains("@"));
        int ageInput = 0;
        while (ageInput < 16) {
            System.out.println("\nEnter customer age: ");
            ageInput = anotherScan.nextInt();
        }
        double balance = 0.00;
        Account account = new Account(nameInput, addressInput,previousAddress1,previousAddress2, emailInput, ageInput, balance);
        account.writeDetailsChoice();
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


        String fileChoice;
        if (userChoice == 1) {
            Current c1 = new Current(name, address, previousAddress1, previousAddress2, email, age, balance);
            fileChoice = "Accounts.txt";
            c1.writeDetails(fileChoice);
            System.out.println("current");}
        else if (userChoice == 2){
            ISA i1 = new ISA(name, address,previousAddress1, previousAddress2, email, age, balance);
            fileChoice = "ISA.txt";
            i1.writeDetails(fileChoice);
            System.out.println("ISA");}
        else if (userChoice == 3){
            Business b1 = new Business(name, address,previousAddress1, previousAddress2, email, age, balance);
            fileChoice = "business.txt";
            b1.writeDetails(fileChoice);
            System.out.println("Business");}
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
                    "\n" + this.previousAddress1 +
                    "\n" + this.previousAddress2 +
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
            for (String fileContent : fileContents) {
                fw.write(fileContent + "\n");
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


