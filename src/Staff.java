import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Staff {
    String[] accountTags ={"Account Number: ", "Sort Code: ", "Name: ", "Address: ", "Email: ", "Age: ", "Balance: ", "Interest: ", "Previous Address 1: ", "Previous Address 2: ", "Company Name: "};

    public Staff() {
    }

    public void viewWhichDetails(){
        System.out.println("Do you want to:" +
                "\n1. View all details" +
                "\n2. Just Balance");
        Scanner scan = new Scanner(System.in);
        String userChoice = scan.nextLine();
        if(userChoice.equals("1")){
            viewDetails(false);
        }else if (userChoice.equals("2")) {
            viewDetails(true);
        }else{
            System.out.println("Enter either 1 or 2");
            viewWhichDetails();
        }
    }
    public void viewDetails(boolean justBalance){
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
                                    System.out.println(accountTags[i] + line);
                                }
                            } catch(ArrayIndexOutOfBoundsException e){
                                continue;
                            }
                            i++;

                        }


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
                FileWriter fw = new FileWriter("Date.txt");
                fw.write(dateSplit[1]);
                fw.close();
                addInterest(false);

//              if the current month is April then add business charge using addInterest()
                if(dateSplit[1].equals("Apr")){
                    addInterest(true);
                }
            }



        } catch (IOException e) {
            System.out.println("Error");
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
                                fileContents.add(String.valueOf(interest));
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

