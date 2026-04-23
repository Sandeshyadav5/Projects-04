package in.co.rays.proj4.bean;

/**
 * SubjectBean class represents the Subject entity.
 * It contains details about subject name, associated course, and description.
 * 
 * @author 
 */
public class SubjectBean extends BaseBean {

    /**
     * Name of the subject
     */
    private String name;

    /**
     * Course ID to which the subject belongs
     */
    private long courseId;

    /**
     * Name of the course
     */
    private String courseName;

    /**
     * Description of the subject
     */
    private String description;

    /**
     * Gets the subject name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the subject name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course ID
     * 
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID
     * 
     * @param courseId
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the course name
     * 
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the course name
     * 
     * @param courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the description
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Returns the display value (subject name)
     * 
     * @return value
     */
    @Override
    public String getValue() {
        return name;
    }
}