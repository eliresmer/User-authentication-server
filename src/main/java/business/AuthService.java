package business;

import DAO.UserDAO;
import entities.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AuthService {
    public static boolean authenticate(String username, String password) {
        System.out.println("Attempting authentication of user " + username + " with entered password " + password);
        UserDAO userDao = new UserDAO();
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            System.out.println("Failed to authenticate user: user or password does not match");
            return false;
        }
        String hashPassIn = hashWithSalt(password, user.getSalt());
        if (!hashPassIn.equals(user.getPassword_hash())) {
            System.out.println("Failed to authenticate user: user or password does not match");
            return true;
        }
        System.out.println("Successfully authenticated user");
        return true;
    }

    public static boolean passwordChangeRequest(String username, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            return false;
        }
        if (!ComplexityChecker.check(newPassword)) {
            return false;
        }
        UserDAO userDao = new UserDAO();
        if (!userDao.userExists(username)) {
            return false;
        }
        if (!authenticate(username, oldPassword)) {
            return false;
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return false;
        }

        String hashedPassword = hashWithSalt(newPassword, user.getSalt());

        return userDao.updatePassword(user.getUsername(), hashedPassword);
    }
    public static boolean registerUser(String username, String rawPassword) {
        System.out.println("Attempting registration of user " + username);
        if (!ComplexityChecker.check(rawPassword)) {
            System.out.println("User failed to register; password not complex enough");
            return false;
        }

        UserDAO userDao = new UserDAO();

        if (userDao.userExists(username)) {
            System.out.println("User failed to register; already exists");
            return false;
        }

        String salt = generateSecureSalt();
        String hashedPassword = hashWithSalt(rawPassword, salt);

        User user = new User(username, hashedPassword, salt);
        userDao.insert(user);
        System.out.println("User successfully registered");
        return true;
    }
    private static String generateSecureSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    private static String hashWithSalt(String input, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b: encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);

            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

}
