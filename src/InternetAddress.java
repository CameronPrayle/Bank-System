
public class InternetAddress {

    public InternetAddress(Object email) {
    }

    public static <AddressException extends Throwable> boolean InternetAddress() {
        String email = "";
        String email1 = email;
        boolean result;
        {

            result = true;

            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();

            result = false;
        }
        return result;
    }


    public void validate() {
    }
}
