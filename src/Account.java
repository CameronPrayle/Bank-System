import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
public class Account {
    String accNum;
    String sortCode;
    String name;
    String address;
    String email;
    int age;
    float balance;
    float interest = 0;


    public Account(String name, String address, String email, int age, float balance) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
        this.balance = balance;
        Random rand = new Random();
        String rNums = "";
        for (int i=0; i<8;i++){
            String rNum = String.valueOf(rand.nextInt(9));
            if(i==0 && rNum.equals("0")){
                while(rNum.equals("0")){
                    rNum = String.valueOf(rand.nextInt(9));
                }
            }
            rNums = rNums + rNum;
        }
        this.accNum = rNums;
    }

    public void writeDetails() {
        try {
            FileWriter fw = new FileWriter("Accounts.txt", true);

            fw.write(this.accNum +
                    "\n" + this.sortCode +
                    "\n" + this.name +
                    "\n" + this.address +
                    "\n" + this.email +
                    "\n" + this.age +
                    "\n" + this.balance +
                    "\n" + this.interest + "\n");

            fw.close();
            System.out.println("Information Saved");

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}

