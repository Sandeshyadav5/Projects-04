package in.co.rays.proj4.bean;

/**
 * RoleBean represents different user roles in the application.
 * 
 * It extends BaseBean to inherit common properties like id,
 * createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * 
 * This bean defines role constants and contains role-specific
 * details such as name and description.
 * 
 * It also provides key-value methods for dropdown display.
 * 
 * @author  Durgesh Lohiya
 */
public class RoleBean extends BaseBean {

    /** Role constant for Admin */
    public static final int ADMIN = 1;

    /** Role constant for Student */
    public static final int STUDENT = 2;

    /** Role constant for College */
    public static final int COLLEGE = 3;

    /** Role constant for Kiosk */
    public static final int KIOSK = 4;

    /** Role constant for Faculty */
    public static final int FACULTY = 5;

    /** Name of the role */
    private String name;

    /** Description of the role */
    private String description;

    /**
     * Gets the role name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the role name.
     * 
     * @param name the role name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the role description.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the role description.
     * 
     * @param description the role description
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
        return String.valueOf(id); // improved version
    }

    /**
     * Returns the display value for dropdown lists.
     * 
     * @return role name
     */
    @Override
    public String getValue() {
        return name;
    }
}