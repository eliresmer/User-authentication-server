import DAO.UserDAO;
import business.AuthService;
import entities.User;

public class Main {
    public static void main(String[] args) {

        // registering a user
        AuthService.registerUser("jim123", "wordPass123!");
        System.out.println();
        // registering a user with a weak password
        AuthService.registerUser("sally564", "password");
        System.out.println();
        // querying user
        UserDAO userDao = new UserDAO();
        User user = userDao.getUserByUsername("jim123");
        System.out.println();
        System.out.println("Result for jim123:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password hash: " + user.getPassword_hash());
        System.out.println();
        //attempting to authenticate user with correct password
        AuthService.authenticate("jim123", "wordPass123!");
        System.out.println();
        //attempting to authenticate user with incorrect password
        AuthService.authenticate("jim123", "wordPass123$");
        System.out.println();
        //attempting to authenticate non-existent user
        AuthService.authenticate("jeremy985", "wordPass123!");
        System.out.println();
    }
}
