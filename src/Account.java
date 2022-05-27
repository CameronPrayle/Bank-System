import java.io.File;
import java.io.FileNotFoundException;
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
    String type;
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
    public String GenerateRandomAccountNumber(){

        Random rand = new Random();
        StringBuilder rNums = new StringBuilder();
        for (int i=0; i<8;i++){
            String rNum = String.valueOf(rand.nextInt(9));

//          Get new number if first random number is 0
            if(i==0 && rNum.equals("0")){
                while(rNum.equals("0")){
                    rNum = String.valueOf(rand.nextInt(9));
                }
            }
            rNums.append(rNum);
        }
        return rNums.toString();
    }

    private static String askForPreviousCustomerAddress(){
        //This code needs to loop until the input is either y or n
        //setting up scanner:
        String addressQAnswer="";
        String previousAddressInput="";
        while(!addressQAnswer.equals("y") && !addressQAnswer.equals("n")){
            Scanner addressScanner = new Scanner(System.in);
            System.out.println("\nHas customer lived at any other addresses in past 3 years? (y/n) ");
            addressQAnswer = addressScanner.nextLine();
            previousAddressInput = "";
            if(addressQAnswer.equalsIgnoreCase("y")){
                previousAddressInput = inputAddress();
            } else if (!addressQAnswer.equals("n")) {
                System.out.println("You must enter either y or n");
            }
        }
        return previousAddressInput;
    }
    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                if (c !=' '){
                    //System.out.println("Names can only consist of letters");
                    return false;}
            }
        }
        return true;
    }

    public static String inputAddress(){
        Scanner anotherScan = new Scanner(System.in);

        // Currently, the user can input alphanumeric house number that must contain at least one number.
        // This can be changed in future to strictly only allow numbers.
        String houseNumberInput;
        do {
            System.out.println("\nEnter customer house number: ");
            houseNumberInput = anotherScan.nextLine();
        } while (isAlpha(houseNumberInput));

        String streetNameInput;
        do {
            System.out.println("\nEnter customer street name: ");
            streetNameInput = anotherScan.nextLine();
        } while (!isAlpha(streetNameInput));

        String townInput;
        do {
            System.out.println("\nEnter customer town: ");
            townInput = anotherScan.nextLine();
        } while (!isAlpha(townInput));

        // In future add check to verify the validity of the post code.
        String postCodeInput;
        do {
            System.out.println("\nEnter customer post code: ");
            postCodeInput = anotherScan.nextLine();
        } while (postCodeInput.length() < 5 || postCodeInput.length()>8);

        return houseNumberInput +", "+ streetNameInput+ ", " + townInput + ", " + postCodeInput;
    }

    public static void existingCustomerCheck(String emailInput, String fileName) throws FileNotFoundException {
        String currentLine;
        File f = new File(fileName);
        Scanner readBusiness = new Scanner(f);
        while(readBusiness.hasNextLine()) {
            currentLine = readBusiness.nextLine();
            if (emailInput.equals(currentLine)) {
                System.out.println("This email address is already associated with an account!");
                Menu.main();
            }
        }
    }

    public static void inputDetails() throws FileNotFoundException {
        Scanner anotherScan = new Scanner(System.in);
        String nameInput;
        do{
            System.out.println("\nEnter customer name: ");
            nameInput = anotherScan.nextLine();
        } while (!isAlpha(nameInput));

        String addressInput = inputAddress();

        //Checking for previous addresses in past 3 years:
        String previousAddress1 = askForPreviousCustomerAddress();
        String previousAddress2 = "";
        if(!Objects.equals(previousAddress1, ""))
        {
            previousAddress2 = askForPreviousCustomerAddress();
        }

        // I feel like this dowhile loop should force users to input both @ and . before continuing.
        // For some reason the loop breaks with either @ or . being entered.
        String emailInput;
        do {
            assert false;
            System.out.println("\nEnter customer email address: ");
            System.out.println("(Must be in the example@example.com format)");
            emailInput = anotherScan.nextLine();
        } while (!emailInput.contains("@") && !emailInput.contains("."));

        // Checks each document to see if the email address matches up with an existing account.
        existingCustomerCheck(emailInput,"Accounts.txt");
        existingCustomerCheck(emailInput,"Business.txt");
        existingCustomerCheck(emailInput,"ISA.txt");

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
                System.out.println("\nAge must be a number");
            }
        }
        double balance = getNewBalance();
        Account account = new Account(nameInput, addressInput,previousAddress1,previousAddress2, emailInput, ageInput, balance);
        account.writeDetailsChoice();
    }

    public static Double getNewBalance(){
        double newBalance = 0.00;
        boolean valid2=false;
        while (!valid2) {
            Scanner balScan = new Scanner(System.in);
            System.out.println("\nEnter a balance: ");
            try{
                newBalance = balScan.nextDouble();
                valid2=true;
            }catch (Exception e){
                System.out.println("\nYou must enter a number");
            }
        }
        return newBalance;
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


        //Creating new accounts of type Current, ISA, and Business depending on user input.
        //Writing details of new account in relative .txt file and displaying basic info on screen:
        String fileChoice;
        if (userChoice == 1) {
            Current c1 = new Current(name, address, previousAddress1, previousAddress2, email, age, balance);
            fileChoice = "Accounts.txt";
            c1.writeDetails(fileChoice);
            System.out.println("New Current Account Created:");
            PrintBasicDetails(c1);
        }
        else if (userChoice == 2){
            double newBalance;

//          Checking for cash cap on ISA's
            if(balance>20000) {
                newBalance=20001;
                while(newBalance>20000){
                    System.out.println("ISA accounts have a cap of £20,000");
                    newBalance=getNewBalance();
                }
            }else{
                newBalance=balance;
            }

            ISA i1 = new ISA(name, address,previousAddress1, previousAddress2, email, age, newBalance);
            fileChoice = "ISA.txt";
            i1.writeDetails(fileChoice);
            System.out.println("New ISA Account Created:");
            PrintBasicDetails(i1);
        }
        else if (userChoice == 3){
            String company="";
            while (company.equals("")){
                Scanner businessScan = new Scanner(System.in);
                System.out.println("Enter your company name");
                company = businessScan.nextLine();
                if (company.equals("")){
                    System.out.println("you must enter a valid company name");
                }
            }
            Business b1 = new Business(name, address, previousAddress1, previousAddress2, email, age, balance, company);
            fileChoice = "business.txt";
            b1.writeDetails(fileChoice);
            System.out.println("New Business Account Created:");
            PrintBasicDetails(b1);
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
                    "\n" + this.type +
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

//      Check remaining ISA cap
        if(sortTo.equals("24-65-69")){
            boolean capCheck = ISA.isAmountTooBigForCap(accTo, amountToTransfer);
            if(capCheck){
                System.out.println("The amount you want to transfer exceeds the ISA limit for that account");
            }else{
                transfer(accFrom, accTo, sortFrom, sortTo, amountToTransfer, false);
            }
        }else{
            transfer(accFrom, accTo, sortFrom, sortTo, amountToTransfer, false);
        }


    }

//   Returns valid account number
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
                    if (accNum.equals(currentLine) && !currentLine.contains("-")) {
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


//  Returns valid sort code
    public static String sortcodeCheck(String transType){
        String sortcode="";

//      Customise message for different uses
        String message;
        if(transType.equals("display")){
            message = "get account type";
        } else if (transType.equals("deposit")){
            message = "deposit";
        }
        else{
            message = "transfer";
        }

//      Get valid sort code
        while(!sortcode.equals("24-65-32")&&!sortcode.equals("24-65-69")&&!sortcode.equals("24-65-27")){
            Scanner anotherScan = new Scanner(System.in);
            System.out.println("Input sort-code to "+ message + " " + transType + ":");
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
        boolean insufficientFunds=false;
        String[] newCap = {"old", "new"}; //Checking old ISA cap with new ISA cap post transfer

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
                    for(int i=0; i<10; i++) {
                        currentLine = readFile.nextLine();

//                      when i is equal to the line number of the balance for that account
                        if (i == 5) {
                            if (!send) {
                                float balance = Float.parseFloat(currentLine);
                                if (balance >= amountToTransfer) {
                                    currentLine = String.valueOf(balance - amountToTransfer);
                                    fileContents.add(currentLine);
                                } else {
                                    System.out.println("\nInsufficient funds!");
                                    insufficientFunds = true;
                                    fileContents.add(currentLine);
                                }
                            } else {
                                currentLine = String.valueOf(Float.parseFloat(currentLine) + amountToTransfer);
                                System.out.println("Transfer successful!");
                                fileContents.add(currentLine);
                                if(sortTo.equals("24-65-69")){
                                    newCap = ISA.getNewCap(accAction, amountToTransfer);
                                }
                            }
                        } else {
                            if(newCap[0].equals(currentLine)){
                                currentLine=newCap[1];
                            }
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

            if(!insufficientFunds){
                if (!send) {
                    transfer(accFrom, accTo, sortFrom, sortTo, amountToTransfer, true);
                }
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

    //Function that displays basic account details in console:
    //(takes in an Account)
    void PrintBasicDetails(Account account){
        System.out.println(account.name);
        System.out.println("Account No.: "+ account.accNum);
        System.out.println("Sort Code: "+ account.sortCode);
    }
    void PrintBasicDetails(Business account){
        System.out.println(account.name);
        System.out.println(account.companyName);
        System.out.println("Account No.: "+ account.accNum);
        System.out.println("Sort Code: "+ account.sortCode);
    }


    public static void deposit(){
        String accTo;
        String sortTo;
        float amountToTransfer = 0;

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

//      Check remaining ISA cap
        if(sortTo.equals("24-65-69")){
            boolean capCheck = ISA.isAmountTooBigForCap(accTo, amountToTransfer);
            if(capCheck==true){
                System.out.println("The amount you want to transfer exceeds the ISA limit for that account");
            }else{
                transfer(null, accTo, null, sortTo, amountToTransfer, true);
            }
        }else{
            transfer(null, accTo, null, sortTo, amountToTransfer, true);
        }
    }

    

}


