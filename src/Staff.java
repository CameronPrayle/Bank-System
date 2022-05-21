import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Staff {
    String[] accountTags ={"Account Number: ", "Sort Code: ", "Name: ", "Address: ", "Email: ", "Age: ", "Balance: ", "Interest: "};

    public Staff() {
    }

    public void viewDetails(){
        System.out.println("\nEnter account number: ");
        Scanner scan = new Scanner(System.in);
        String userChoice = scan.nextLine();
        System.out.println("\nEnter sort code: ");
        String sort_code = scan.nextLine();

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

    public void checkDate(){
        String date = new Date().toString();

//      Split up date string to separate month
        String[] dateSplit = date.split(" ");
        String testing = "lol";

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
        if(businessCharge){
            fileType = "Business.txt";
        }else{fileType = "ISA.txt";}

        try {
            File f = new File(fileType);
            Scanner readFile = new Scanner(f);

//          3 variables to backtrack from end of user up to Bal
            String line1="";
            String line2="";


            while (readFile.hasNextLine()) {
//              Backtracking
                String line3 = line2;
                line2 = line1;
                line1 = readFile.nextLine();

//              Add line3 to list if not at end of user and backtracks aren't the same as line1 was at the end of the user, to avoid duplicate additions
                if(!line3.equals("")&&!line1.equals("----------")&&!line2.equals("----------")&&!line3.equals("----------")){
                    fileContents.add(line3);
                }

                if(line1.equals("----------")){

//                  If business charge run, take the charge away from bal. else, it's an interest run.
                    if (businessCharge){
                        line3 = String.valueOf(Float.parseFloat(line3) - 50f);
                        System.out.println("Business run: " + line3 + "\n");

                    }else{

//                      Add interest to bal line
                        line3 = String.valueOf((Float.parseFloat(line3) * Float.parseFloat(line2)) + Float.parseFloat(line3));

//                      Scale interest
                        line2 = String.valueOf((Float.parseFloat(line2) * Float.parseFloat(line2)) + Float.parseFloat(line2));
                        System.out.println("Interest run: " + line3 + "\n");
                    }


//                  Add remaining lines to list including new bal
                    fileContents.add(line3);
                    fileContents.add(line2);
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

