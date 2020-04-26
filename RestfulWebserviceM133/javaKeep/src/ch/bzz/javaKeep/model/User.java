package ch.bzz.javaKeep.model;

/**
 * model for users
 * <p>
 * M133: Restful Webservice
 *
 * @author Aladin Boudouda
 */

public class User {
    public String username;
    public String userRole;

    /**
     * default constructor
     */
    public User() {
        setUserRole("guest");
    }

    /**
     * Gets the username
     *
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**n
u
     * Sets the username
     *
     * @param username the value to set
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the userRole
     *
     * @return value of userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * Sets the userRole
     *
     * @param userRole the value to set
     */

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
