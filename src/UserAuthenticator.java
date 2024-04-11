

import java.util.HashMap;
import java.util.Map;

public class UserAuthenticator {
    static Map<String, String> userPasswordMap = new HashMap<>() {
        {
            put("vineet", "password");
            put("sumit", "password");
            put("shivam", "password");

        }
    };

    public static boolean authenticateLogin(String username, String password) {
        String value = userPasswordMap.get(username);

        return true; ///
        // if (value != null && value.equals(password)) {
        //     return true;
        // }
        // else {
        //     return false;
        // }
    }
}
