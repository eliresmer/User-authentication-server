import DAO.UserDAO;
import entities.User;

public class Main {
    public static void main(String[] args) throws Exception {

        // registering a user
        AuthService.registerUser("eli", "wordPass123!");

        // registering a user with a weak password
        AuthService.registerUser("eli2", "password");

        // querying user
        UserDAO userDao = new UserDAO();
        User user = userDao.getUserByUsername("eli");

        System.out.println("Result for eli:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password hash: " + user.getPassword_hash());

        //attempting to authenticate user with correct password
        AuthService.authenticate("eli", "wordPass123!");
        //attempting to authenticate user with incorrect password
        AuthService.authenticate("eli", "wordPass123$");




    }
}
