import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Business extends Account{

    public Business(String name, String address,String previousAddress1, String previousAddress2, String email, int age, double balance) {
        super(name, address,previousAddress1, previousAddress2, email, age, balance);
        this.sortCode = "24-65-27";
    }


    public static String getNotBlank(){
        String accNum="";
        while (accNum.equals("")){
            Scanner userIn=new Scanner(System.in);
            System.out.println("Enter the account number for the business account requesting a chequebook");
            accNum = userIn.nextLine();
        }
        return accNum;
    }

    public static void chequeBook(){
        String accNum = getNotBlank();

        String line;
        String address = "";
        boolean accFound=false;

//      Search for account
        File f = new File("Business.txt");
        try {
            Scanner readFile = new Scanner(f);
            while(readFile.hasNext()){
                line = readFile.nextLine();
                if(line.equals(accNum)){
                    accFound=true;

//                  Get address for output message
                    for(int i=0; i<3;i++){
                        line = readFile.nextLine();
                        if(i==2){
                            address = line;
                        }
                    }
                    System.out.println("Chequebook sent to " + address);
                }
            }

            if(!accFound){
                System.out.println("Account not found");
                chequeBook();
            }

        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
