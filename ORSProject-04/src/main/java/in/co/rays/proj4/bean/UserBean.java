package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean represents a User entity in the application.
 * 
 * It extends BaseBean to inherit common properties like id,
 * createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * 
 * This bean contains user-specific details such as personal
 * information, login credentials, role, and contact details.
 * 
 * It also provides key-value methods for dropdown display.
 * 
 * @author  Durgesh Lohiya
 */
public class UserBean extends BaseBean {

    /** First name of the user */
    private String firstName;

    /** Last name of the user */
    private String lastName;

    /** Login username (usually email or unique ID) */
    private String login;

    /** Password of the user */
    private String password;

    /** Confirm password (used for validation) */
    private String confirmPassword;

    /** Date of birth of the user */
    private Date dob;

    /** Mobile number of the user */
    private String mobileNo;

    /** Role ID assigned to the user */
    private long roleId;

    /** Gender of the user */
    private String gender;

    /**
     * Gets the first name.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * 
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     * 
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the login username.
     * 
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login username.
     * 
     * @param login the login value
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the password.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the confirm password.
     * 
     * @return confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the confirm password.
     * 
     * @param confirmPassword the confirm password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Gets the date of birth.
     * 
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth.
     * 
     * @param dob the date of birth
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Gets the mobile number.
     * 
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number.
     * 
     * @param mobileNo the mobile number
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Gets the role ID.
     * 
     * @return roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * Sets the role ID.
     * 
     * @param roleId the role ID
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the gender.
     * 
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     * 
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the key value for dropdown lists.
     * 
     * @return id as String
     */
    @Override
    public String getKey() {
        return String.valueOf(id); // improved version
    }

    /**
     * Returns the display value for dropdown lists.
     * 
     * @return full name of user
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName; // fixed spacing
    }
}