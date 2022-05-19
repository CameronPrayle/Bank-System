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

        try {
            File f = new File("Date.txt");
            Scanner readFile = new Scanner(f);

//          if the month doesn't equal the month in the txt file update the date and add the monthly interest
            if(!dateSplit[1].equals(readFile.nextLine())){
                FileWriter fw = new FileWriter("Date.txt");
                fw.write(dateSplit[1]);
                fw.close();
                addInterest();
            }

        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void addInterest(){
        List<String> fileContents = new ArrayList<>();

        try {
            File f = new File("ISA.txt");
            Scanner readFile = new Scanner(f);

//          3 variables to backtrack from end of user up to Bal
            String line1="";
            String line2="";
            String line3="";

            while (readFile.hasNextLine()) {
//              Backtracking
                line3 = line2;
                line2 = line1;
                line1 = readFile.nextLine();

//              Add line to list if not at end of user and backtracks aren't the same as line1 was, to avoid duplicate additions
                if(!line3.equals("")&&!line1.equals("----------")&&!line2.equals("----------")&&!line3.equals("----------")){
                    fileContents.add(line3);
                }

                if(line1.equals("----------")){

//                  Add interest to bal line
                    line3 = String.valueOf((Float.parseFloat(line3) * Float.parseFloat(line2)) + Float.parseFloat(line3));

//                  Scale interest
                    line2 = String.valueOf((Float.parseFloat(line2) * Float.parseFloat(line2)) + Float.parseFloat(line2));

//                  Add remaining lines to list including new bal
                    fileContents.add(line3);
                    fileContents.add(line2);
                    fileContents.add(line1);
                }
            }

//          Write contents of list to txt file
            FileWriter fw = new FileWriter("ISA.txt");
            for(int i=0; i<fileContents.size(); i++){
                fw.write(fileContents.get(i)+"\n");
            }
            fw.close();

        } catch (IOException e) {
            System.out.println("Error");
        }
    }



}

