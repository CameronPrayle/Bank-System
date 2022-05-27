import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ISA extends Account{

    double capAmountLeft;
    public ISA(String name, String address,String previousAddress1,String previousAddress2, String email, int age, double balance) {
        super(name, address,previousAddress1,previousAddress2, email, age, balance);
        this.sortCode = "24-65-69";
        this.interest=0.02f;
        this.capAmountLeft=20000f - balance;

        //Checks that the account number doesn't already exist and generates new account number if it does:
        while(CheckAccountNumberAlreadyExists("ISA.txt")) {
            this.accNum = GenerateRandomAccountNumber();
        }

    }

//  Check the ISA cap
    public static boolean isAmountTooBigForCap(String accTo, float amountToTransfer) {
        String capLine;
        boolean tooBig = false;
        try {
            File f = new File("ISA.txt");
            Scanner readFile = new Scanner(f);
            String line;

            while (readFile.hasNextLine()) {
                line = readFile.nextLine();

                if (line.equals(accTo)){
                    while (!line.equals("----------")){
                        capLine=line;
                        line = readFile.nextLine();
                        if(line.equals("----------")){
                            if(Float.parseFloat(capLine)< amountToTransfer){
                                tooBig = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        return tooBig;
    }

    public static String[] getNewCap(String accAction, float amountToTransfer) {
        String capLine = null;
        String[] capArray = new String[2];
        try {
            File f = new File("ISA.txt");
            Scanner readFile = new Scanner(f);
            String line;

            while (readFile.hasNextLine()) {
                line = readFile.nextLine();

//              if line = account number
                if (line.equals(accAction)){
                    while (!line.equals("----------")){

//                      backtrack to get the ISA cap
                        capLine=line;
                        line = readFile.nextLine();
                        if(line.equals("----------")){
                            capArray[0]=capLine;
                            capLine= String.valueOf(Float.parseFloat(capLine)-amountToTransfer);
                            capArray[1]=capLine;
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("File not found");
        }
        return capArray;
    }

    @Override
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
                    "\n" + this.capAmountLeft +
                    "\n" + "----------" + "\n");


            fw.close();
            System.out.println("Information Saved");

        } catch (IOException e) {
            System.out.println("Error");
        }
    }

}
