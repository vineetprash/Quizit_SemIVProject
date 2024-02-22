package userAuth;

import java.util.HashMap;
import java.util.Map;

public class UserAuthenticator {

    Map<String, String> userPasswordMap = new HashMap<>() {
        {
            put("vineet", "password");
            put("sumit", "password");
            put("shivam", "password");

        }
    };

    public static boolean authenticateLogin(String username, String password) {
        // Your authentication logic here
        // For simplicity, let's say it always returns true
        return true;
    }
}
