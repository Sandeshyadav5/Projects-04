package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean represents a Faculty entity in the application.
 * 
 * It extends BaseBean to inherit common properties like id,
 * createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * 
 * This bean contains faculty-specific details such as personal
 * information, contact details, and associated college, course,
 * and subject information.
 * 
 * It also provides key-value methods for dropdown display.
 * 
 * @author  Durgesh Lohiya
 */
public class FacultyBean extends BaseBean {

    /** First name of the faculty */
    private String firstname;

    /** Last name of the faculty */
    private String lastName;

    /** Date of birth of the faculty */
    private Date dob;

    /** Gender of the faculty */
    private String gender;

    /** Mobile number of the faculty */
    private String mobileNo;

    /** Email address of the faculty */
    private String email;

    /** College ID associated with the faculty */
    private long collegeId;

    /** College name associated with the faculty */
    private String collegeName;

    /** Course ID associated with the faculty */
    private long courseId;

    /** Course name associated with the faculty */
    private String coursename;

    /** Subject ID associated with the faculty */
    private long subjectId;

    /** Subject name associated with the faculty */
    private String subjectName;

    /**
     * Gets the first name of the faculty.
     * 
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the faculty.
     * 
     * @param firstname the first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the last name of the faculty.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the faculty.
     * 
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Gets the email address.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     * 
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the college ID.
     * 
     * @return collegeId
     */
    public long getCollegeId() {
        return collegeId;
    }

    /**
     * Sets the college ID.
     * 
     * @param collegeId the college ID
     */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * Gets the college name.
     * 
     * @return collegeName
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Sets the college name.
     * 
     * @param collegeName the college name
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Gets the course ID.
     * 
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID.
     * 
     * @param courseId the course ID
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the course name.
     * 
     * @return coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * Sets the course name.
     * 
     * @param coursename the course name
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    /**
     * Gets the subject ID.
     * 
     * @return subjectId
     */
    public long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the subject ID.
     * 
     * @param subjectId the subject ID
     */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Gets the subject name.
     * 
     * @return subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the subject name.
     * 
     * @param subjectName the subject name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Returns the key value for dropdown lists.
     * 
     * @return id as String
     */
    @Override
    public String getKey() {
        return String.valueOf(id); // better than id + ""
    }

    /**
     * Returns the display value for dropdown lists.
     * 
     * @return full name of faculty
     */
    @Override
    public String getValue() {
        return firstname + " " + lastName;
    }
}