package in.co.rays.proj4.bean;

/**
 * CourseBean represents a Course entity in the application.
 * 
 * It extends BaseBean to inherit common properties like id,
 * createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * 
 * This bean contains course-specific details such as name,
 * duration, and description.
 * 
 * It also provides key-value methods for dropdown display.
 * 
 * @author Sandesh
 */
public class CourseBean extends BaseBean {

    /** Name of the course */
    private String name;

    /** Duration of the course (e.g., 3 Years, 6 Months) */
    private String duration;

    /** Description of the course */
    private String description;

    /**
     * Gets the course name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name.
     * 
     * @param name the course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course duration.
     * 
     * @return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the course duration.
     * 
     * @param duration the duration of the course
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets the course description.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the course description.
     * 
     * @param description the course description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the key value for dropdown lists.
     * 
     * @return id as String
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value for dropdown lists.
     * 
     * @return course name
     */
    @Override
    public String getValue() {
        return name;
    }
}