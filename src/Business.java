public class Business extends Account{

    public Business(String name, String address,String previousAddress1, String previousAddress2, String email, int age, double balance) {
        super(name, address,previousAddress1, previousAddress2, email, age, balance);
        this.sortCode = "24-65-27";
    }


}
