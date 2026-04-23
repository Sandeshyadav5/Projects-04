package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * StudentBean class represents the Student entity.
 * It contains personal details and college information of a student.
 * 
 * @author 
 */
public class StudentBean extends BaseBean {

    /**
     * First name of the student
     */
    private String firstName;

    /**
     * Last name of the student
     */
    private String lastName;

    /**
     * Date of birth of the student
     */
    private Date dob;

    /**
     * Gender of the student
     */
    private String gender;

    /**
     * Mobile number of the student
     */
    private String mobileNo;

    /**
     * Email address of the student
     */
    private String email;

    /**
     * College ID associated with the student
     */
    private long collegeId;

    /**
     * Name of the college
     */
    private String collegeName;

    /**
     * Gets the first name
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the date of birth
     * 
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth
     * 
     * @param dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Gets the gender
     * 
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender
     * 
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the mobile number
     * 
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number
     * 
     * @param mobileNo
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Gets the email
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the college ID
     * 
     * @return collegeId
     */
    public long getCollegeId() {
        return collegeId;
    }

    /**
     * Sets the college ID
     * 
     * @param collegeId
     */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * Gets the college name
     * 
     * @return collegeName
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Sets the college name
     * 
     * @param collegeName
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Returns the primary key as a String
     * 
     * @return key
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value (full name of student)
     * 
     * @return value
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
}