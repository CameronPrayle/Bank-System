//StaffCredentials class stores a username and password for staff to log in with.
//Created by Lewis

public class StaffCredentials {

    //init variables and constructor:
    private String username;
    private String password;

    public StaffCredentials(String emailAddress, String password) {
        this.username = emailAddress;
        this.password = password;
    }

    //getters:
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
