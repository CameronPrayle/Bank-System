import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Staff {
    String[] accountTags ={"Account Number: ", "Sort Code: ", "Name: ", "Address: ", "Email: ", "Age: ", "Balance: ", "Interest: ", "Previous Address 1: ", "Previous Address 2: ", "Company Name: ", "ISA Cap Remaining: "};

    public Staff() {
    }

    public void displayAccountType(){
        String sortcode = Account.sortcodeCheck("display");
        switch (sortcode) {
            case "24-65-32" -> sortcode="Current Account";
            case "24-65-69" -> sortcode="ISA Account";
            case "24-65-27" -> sortcode="Business Account";
        }
        System.out.println("Account type: "+sortcode);
    }
    public void viewWhichDetails(){
        System.out.println("""
                Do you want to:
                1. View all details
                2. Just Balance
                3. Just Account type""");
        Scanner scan = new Scanner(System.in);
        String userChoice = scan.nextLine();
        switch (userChoice) {
            case "1" -> viewDetails(false);
            case "2" -> viewDetails(true);
            case "3" -> displayAccountType();
            default -> {
                System.out.println("Enter either 1, 2 or 3");
                viewWhichDetails();
            }
        }
    }
    public void viewDetails(boolean justBalance){

//      Get account info
        System.out.println("\nEnter account number: ");
        Scanner scan = new Scanner(System.in);
        String userChoice = scan.nextLine();
        System.out.println("\nEnter sort code: ");
        String sortcode = scan.nextLine();
        switch (sortcode) {
//          Current
            case "24-65-32" -> sortcode="Accounts.txt";
//          ISA
            case "24-65-69" -> sortcode="ISA.txt";
//          Business
            case "24-65-27" -> sortcode="Business.txt";
        }

        boolean accFound=false;

//      Displaying
        try {
            File f = new File(sortcode);
            Scanner readFile = new Scanner(f);
            String line;

            while (readFile.hasNextLine()) {
                line = readFile.nextLine();
                if(line.equals(userChoice)){
                    accFound=true;
                    System.out.println("\n");
                    System.out.println(accountTags[0]+line);

                    if(!justBalance){
//                      Print all lines from account number (loops until last line for that account)
                        int i=1;
                        while (!Objects.equals(line, "----------")){
                            line = readFile.nextLine();
                            try {
                                if(!line.equals("----------")){
                                    if(sortcode.equals("ISA.txt") && accountTags[i].equals("Company Name: ")){

                                        System.out.println(accountTags[i+1] + line);
                                    }else {
                                        System.out.println(accountTags[i] + line);
                                    }

                                }
                            } catch(ArrayIndexOutOfBoundsException e){
                                continue;
                            }
                            i++;

                        }

//                  Displaying just the balance
                    }else{
                        for (int i=0; i<6; i++){
                            line = readFile.nextLine();

//                          When i is equal to the line number of the balance for that account
                            if(i==5){
                                System.out.println(accountTags[i+1] + line);
                            }
                        }
                    }
                }
            }

//          Account not found messages
            if(!accFound){
                System.out.println("Account not found");
                viewDetails(justBalance);
            }
        } catch (IOException e) {
            System.out.println("Account not found");
            viewDetails(justBalance);
        }
    }

    public void checkDate(){
        String date = new Date().toString();

//      Split up date string to separate month
        String[] dateSplit = date.split(" ");

        try {
            File f = new File("Date.txt");
            Scanner readFile = new Scanner(f);

//          if the current month doesn't equal the month in the txt file update the date and add the monthly interest
            if(!dateSplit[1].equals(readFile.nextLine())){
                addInterest(false);

//              if the current month is April then add business charge using addInterest()
                if(dateSplit[1].equals("Apr")){
                    addInterest(true);
                }

                if(!dateSplit[5].equals(readFile.nextLine())){
                    resetISACap();
                }

                FileWriter fw = new FileWriter("Date.txt");
                fw.write(dateSplit[1]);
                fw.write("\n");
                fw.write(dateSplit[5]);
                fw.close();
            }



        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void resetISACap(){
        List<String> fileContents = new ArrayList<>();
        try {
            File f = new File("ISA.txt");
            Scanner readFile = new Scanner(f);
            String line;

            while (readFile.hasNextLine()){
                line = readFile.nextLine();
                fileContents.add(line);
                if(line.equals("24-65-69")){
                    for (int i=0; i<9; i++){
                        line = readFile.nextLine();

//                      When i is equal to the line number of the ISA cap remaining for that account
                        if(i==8){
                            line = "20000";
                            fileContents.add(line);
                        }else{
                            fileContents.add(line);
                        }
                    }
                }
            }

//          Write contents of list to txt file
            FileWriter fw = new FileWriter("ISA.txt");
            for (String fileContent : fileContents) {
                fw.write(fileContent + "\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Account not found");
        }
    }
    public void addInterest(boolean businessCharge){
        List<String> fileContents = new ArrayList<>();
        String fileType;
        String sortcode;
        String balance;
        String interest = "";
        String line1;

        if(businessCharge){
            fileType = "Business.txt";
            sortcode= "24-65-27";
        }else{
            fileType = "ISA.txt";
            sortcode= "24-65-69";
        }

        try {
            File f = new File(fileType);
            Scanner readFile = new Scanner(f);

            while (readFile.hasNextLine()) {
                line1 = readFile.nextLine();

                if(line1.equals(sortcode)){
                    for(int i=0; i<7; i++){
//                      Backtracking since balance comes first in the txt file
                        balance = interest;
                        interest = line1;
                        if(i==5){
                            continue;
                        }
                        else if(i==6){
//                          If business charge run, take the charge away from bal. else, it's an interest run.
                            if(businessCharge){
                                balance=String.valueOf(Float.parseFloat(balance)-50f);
                                fileContents.add(balance);
                                fileContents.add(interest);

                            }else{
//                              Add interest to bal line
                                balance = String.valueOf((Float.parseFloat(balance) * Float.parseFloat(interest)) + Float.parseFloat(balance));
                                fileContents.add(balance);

//                              Scale interest
                                interest = String.valueOf((Float.parseFloat(interest) * Float.parseFloat(interest)) + Float.parseFloat(interest));
                                fileContents.add(interest);
                            }
                        }
                        else{
                            fileContents.add(line1);
                        }
                        line1 = readFile.nextLine();
                    }
                    fileContents.add(line1);
                }else{
                    fileContents.add(line1);
                }
            }

//          Write contents of list to txt file
            FileWriter fw = new FileWriter(fileType);
            for (String fileContent : fileContents) {
                fw.write(fileContent + "\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Error");
        }
    }

}

