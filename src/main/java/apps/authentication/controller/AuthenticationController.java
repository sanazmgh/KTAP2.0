package apps.authentication.controller;

import apps.authentication.event.LoginFormEvent;
import apps.authentication.event.UserInfoFormEvent;
import apps.authentication.model.User;
import db.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class AuthenticationController {

    static private final Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final Context context = Context.getContext();
    private User user ;
    private static AuthenticationController authenticationController;

    private AuthenticationController() {}

    public static AuthenticationController getAuthenticationController()
    {
        if(authenticationController == null)
            authenticationController = new AuthenticationController();

        return authenticationController;
    }

    public User getUser() {
        return user;
    }

    /***
     * there is 2 conditions for login :
     * 0 : the action done successfully
     * 1 : wrong information
     */
    public boolean login(LoginFormEvent loginForm)
    {
        if(!validLogin(loginForm))
        {
            logger.warn("invalid login");
            return false;
        }

        user = context.users.getByUsername(loginForm.getUsername());
        logger.warn(user.getId() + " logged in successfully");
        user.setActive(true);
        user.setLastSeen(new Date());
        logger.warn(user.getId() + " sent for update");
        context.users.update(user);

        return true;
    }

    public boolean validLogin(LoginFormEvent loginForm)
    {
        User user = context.users.getByUsername(loginForm.getUsername());

        if(user != null)
            if(user.getPassword().equals(loginForm.getPass()))
                return true;

        return false;
    }

    /***
     * there is five conditions for signing up;
     * 0 : the action done successfully
     * 1 : username already taken
     * 2 : passwords don't match
     * 3 : email already taken
     * 4 : not all the necessary fields are filled
     */

    public int signUp (UserInfoFormEvent signUpForm)
    {
        int validation = checkValidSignUp(signUpForm);
        if(validation != 0)
        {
            logger.warn("invalid login");
            return validation;
        }

        user = new User(signUpForm);
        logger.warn(user.getId() + " signed up successfully");
        user.setLastSeen(new Date());
        logger.warn(user.getId() + " sent to add");
        context.users.add(user);
        return 0;
    }

    public int checkValidSignUp(UserInfoFormEvent signUpForm)
    {
        if(context.users.getByUsername(signUpForm.getUsername()) != null)
            return 1;

        if(!signUpForm.getPass().equals(signUpForm.getPass2()))
            return 2;

        if(context.users.getByEmail(signUpForm.getEmail()) != null)
            return 3;

        if(signUpForm.getUsername().equals("") || signUpForm.getPass().equals("") || signUpForm.getPass2().equals("") ||
                signUpForm.getName().equals("") || signUpForm.getLastName().equals("") || signUpForm.getEmail().equals(""))
            return 4;

        return 0;
    }

}
