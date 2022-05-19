public class ISA extends Account{

    public ISA(String name, String address, String email, int age, float balance) {
        super(name, address, email, age, balance);
        this.sortCode = "24-65-69";
        this.interest=0.02f;
    }

}
