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


        this.accNum = GenerateRandomAccountNumber();
    }

    //      Generate random account number:
    String GenerateRandomAccountNumber(){

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
        return rNums;
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
    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                if (c !=' '){
                    System.out.println("Names can only consist of letters");
                    return false;}
            }
        }
        return true;
    }
    public static void inputDetails() {
        Scanner anotherScan = new Scanner(System.in);
        String nameInput;
        do{
        System.out.println("\nEnter customer name: ");
        nameInput = anotherScan.nextLine();
        } while (!isAlpha(nameInput));
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
            Scanner ageScan = new Scanner(System.in);
            System.out.println("\nEnter customer age: ");
            try{
                ageInput = ageScan.nextInt();
                if(ageInput<16){
                    System.out.println("\nAge must be over 16");
                }
            }catch (Exception e){
                System.out.println("\nYou must enter a number");
            }
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
            boolean valid=false;
            int userChoice=0;
            while(!valid){
                Scanner scan = new Scanner(System.in);
                try{
                    userChoice = scan.nextInt();
                    valid=true;
                }catch (Exception e){
                    System.out.println("You must enter a number");
                }
            }



        String fileChoice;
        if (userChoice == 1) {
            Current c1 = new Current(name, address, previousAddress1, previousAddress2, email, age, balance);
            fileChoice = "Accounts.txt";
            c1.writeDetails(fileChoice);
            }
        else if (userChoice == 2){
            ISA i1 = new ISA(name, address,previousAddress1, previousAddress2, email, age, balance);
            fileChoice = "ISA.txt";
            i1.writeDetails(fileChoice);
            }
        else if (userChoice == 3){

//          Get company name
            String company="";
            while (company.equals("")){
                Scanner businessScan = new Scanner(System.in);
                System.out.println("Enter your company name");
                company = businessScan.nextLine();
                if (company.equals("")){
                    System.out.println("you must enter a valid company name");
                }
            }

            Business b1 = new Business(name, address,previousAddress1, previousAddress2, email, age, balance, company);
            fileChoice = "business.txt";
            b1.writeDetails(fileChoice);
            }
        else {
            System.out.println("Invalid choice");
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
        String accFrom;
        String sortFrom;
        String accTo;
        String sortTo;
        float amountToTransfer = 0;

//      Get sender details
        sortFrom = sortcodeCheck("From");
        accFrom = accNumCheck(sortFrom,"From");

//      Get receiver details
        sortTo = sortcodeCheck("To");
        accTo = accNumCheck(sortTo,"To");

//      Get amount
        boolean valid=false;
        System.out.println("Input amount to transfer:");
        while (!valid){
            try{
                Scanner amountScan = new Scanner(System.in);
                amountToTransfer = Float.parseFloat(amountScan.nextLine());
                valid=true;
            }catch (Exception e){
                System.out.println("Amount must be a number:");
            }
        }

        transfer(accFrom, accTo, sortFrom, sortTo, amountToTransfer, false);
    }

    public static String accNumCheck(String sortcode, String transType){
        String accNum="";
        boolean accFound=false;

        switch (sortcode) {
//          Current
            case "24-65-32" -> sortcode="Accounts.txt";
//          ISA
            case "24-65-69" -> sortcode="ISA.txt";
//          Business
            case "24-65-27" -> sortcode="Business.txt";
        }

        while(!accFound){
            System.out.println("Input account number to transfer "  + transType + ":");
            Scanner anotherScan = new Scanner(System.in);
            accNum = anotherScan.nextLine();

            File f = new File(sortcode);
            try {
                String currentLine;
                Scanner readFile = new Scanner(f);

                while(readFile.hasNextLine()) {
                    currentLine = readFile.nextLine();
                    if (accNum.equals(currentLine)) {
                        accFound=true;
                    }
                }
                if (!accFound) {
                    System.out.println("Invalid account number");
                }
            } catch (IOException e) {
                System.out.println("Invalid sort-code");
            }
        }
        return accNum;
    }
    public static String sortcodeCheck(String transType){
        String sortcode="";
        while(!sortcode.equals("24-65-32")&&!sortcode.equals("24-65-69")&&!sortcode.equals("24-65-27")){
            Scanner anotherScan = new Scanner(System.in);
            System.out.println("Input sort-code to transfer " + transType + ":");
            sortcode = anotherScan.nextLine();
            if(!sortcode.equals("24-65-32")&&!sortcode.equals("24-65-69")&&!sortcode.equals("24-65-27")){
                System.out.println("Sort-code invalid");
            }
        }
        return sortcode;
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

//              If the account number (either sender or receiver) is equal to current line
                if(accAction.equals(currentLine)){
                    for(int i=0; i<6; i++){
                        currentLine=readFile.nextLine();

//                      when i is equal to the line number of the balance for that account
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
            System.out.println("Invalid sort-code2");
            transferDetails();
        }
    }

    //Function to check if account number already exists:
    boolean CheckAccountNumberAlreadyExists(String filePathName){
        File f = new File(filePathName);
        boolean accountNumExists = false;
        try {
            String currentLine;
            Scanner readFile = new Scanner(f);

            while(readFile.hasNextLine()) {
                currentLine = readFile.nextLine();
                if (this.accNum.equals(currentLine)) {
                    accountNumExists = true;
                }
            }

        } catch (IOException e) {
            System.out.println("Invalid sort-code");
        }

        return accountNumExists;
    }
}


