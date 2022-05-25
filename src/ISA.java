import java.io.FileWriter;
import java.io.IOException;

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
