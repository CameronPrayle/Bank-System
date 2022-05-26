
public class Current extends Account{

    public Current(String name, String address,String previousAddress1, String previousAddress2, String email, int age, double balance) {
        super(name, address,previousAddress1, previousAddress2, email, age, balance);
        this.sortCode = "24-65-32";

        //Checks that the account number doesn't already exist and generates new account number if it does:
        while(CheckAccountNumberAlreadyExists("Accounts.txt")) {
            this.accNum = GenerateRandomAccountNumber();
        }
    }


}
