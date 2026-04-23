package in.co.rays.proj4.bean;

/**
 * CollegeBean represents a College entity in the application.
 * 
 * It extends BaseBean to inherit common properties like id,
 * createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * 
 * This bean contains college-specific details such as name,
 * address, state, city, and phone number.
 * 
 * It also implements methods required for dropdown display.
 * 
 * @author Sandesh
 */
public class CollegeBean extends BaseBean {

    /** Name of the college */
    private String name;

    /** Address of the college */
    private String address;

    /** State where the college is located */
    private String state;

    /** City where the college is located */
    private String city;

    /** Contact phone number of the college */
    private String phoneNo;

    /**
     * Gets the college name.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the college name.
     * 
     * @param name the college name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the college address.
     * 
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the college address.
     * 
     * @param address the college address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the state of the college.
     * 
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the college.
     * 
     * @param state the state name
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the city of the college.
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the college.
     * 
     * @param city the city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the phone number of the college.
     * 
     * @return phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the phone number of the college.
     * 
     * @param phoneNo the contact number
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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
     * @return college name
     */
    @Override
    public String getValue() {
        return name;
    }
}