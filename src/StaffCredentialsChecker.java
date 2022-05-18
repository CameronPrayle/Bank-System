import java.util.ArrayList;
import java.util.Scanner;

public class StaffCredentialsChecker {

    //setting up a few example credentials and adding them to an arrayList in the constructor:
    StaffCredentials user1 = new StaffCredentials("user1@example.co.uk", "Password1");
    StaffCredentials user2 = new StaffCredentials("user2@example.co.uk", "Password2");

    ArrayList<StaffCredentials> staffCredentials = new ArrayList<>(2);

    StaffCredentialsChecker(){
        staffCredentials.add(user1);
        staffCredentials.add(user2);
    }

    //init variables for credentials checking:
    String enteredName;
    String enteredPassword;
    Scanner in = new Scanner(System.in);
    boolean areCredentialsCorrect = false;

    //Collecting Credentials:
    public void CollectCredentials() {
        System.out.println("Please enter your username:");
        in = new Scanner(System.in);
        enteredName = in.nextLine();
        System.out.println("Please enter your password:");
        in = new Scanner(System.in);
        enteredPassword = in.nextLine();
    }

    //Checking Credentials against know credentials:
    public void CheckCredentials() {
        for (StaffCredentials staffCredential : staffCredentials) {

            if (enteredName.equalsIgnoreCase(staffCredential.getUsername()) && enteredPassword.equals(staffCredential.getPassword())) {
                areCredentialsCorrect = true;
                System.out.println("Welcome " + staffCredential.getUsername() + "!");
            }
        }
        if(!areCredentialsCorrect){
            System.out.println("Username or password not recognised");
        }
    }

    //Creating run login function to wrap both Collect and Check Credentials functions:
    public void RunLoginSystem(){
        areCredentialsCorrect = false;
        while(!areCredentialsCorrect){
            CollectCredentials();
            CheckCredentials();
        }
    }
}
