package business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexityChecker {
    public static boolean check(String rawPassword) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,50}$");
        Matcher matcher = pattern.matcher(rawPassword);

        if (matcher.matches()) {
            System.out.println("Password passed complexity check");
        }
        else {
            System.out.println("Password is not complex enough");
        }
        return matcher.matches();
    }
}
