public class ISA extends Account{

    public ISA(String name, String address,String previousAddress1,String previousAddress2, String email, int age, double balance) {
        super(name, address,previousAddress1,previousAddress2, email, age, balance);
        this.sortCode = "24-65-69";
        this.interest=0.02f;

        //Checks that the account number doesn't already exist and generates new account number if it does:
        while(CheckAccountNumberAlreadyExists("ISA.txt")) {
            this.accNum = GenerateRandomAccountNumber();
        }

    }

}
