
public class Main {
    public static void main(String[] args) {

        Staff s1 = new Staff();
        s1.checkDate();
        /*
        //Creating StaffCredentialsChecker class and running login:
        StaffCredentialsChecker staffCredentialsChecker = new StaffCredentialsChecker();
        staffCredentialsChecker.RunLoginSystem();
        */
//      Options Interface
        boolean exit=false;
        while (!exit){
            int userChoice  = Menu.main();
//          Choice checks
            switch (userChoice) {
                case 1 -> Account.inputDetails();
                case 2 -> s1.viewDetails();
                case 4 -> exit = true;
            }
        }
    }
}

