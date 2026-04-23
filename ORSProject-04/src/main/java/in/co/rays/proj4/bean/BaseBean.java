package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * BaseBean is an abstract class that provides common properties
 * for all beans in the application.
 * 
 * It includes audit fields such as:
 * id, createdBy, modifiedBy, createdDatetime, and modifiedDatetime.
 * 
 * All other beans should extend this class to inherit these properties.
 * 
 * @author Sandesh
 */
public abstract class BaseBean implements DropdownListBean {

    /** Unique identifier for the record */
    protected long id;

    /** Username of the creator */
    protected String createdBy;

    /** Username of the last modifier */
    protected String modifiedBy;

    /** Date and time when the record was created */
    protected Timestamp createdDatetime;

    /** Date and time when the record was last modified */
    protected Timestamp modifiedDatetime;

    /**
     * Gets the unique ID of the record.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the record.
     * 
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the creator's username.
     * 
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the creator's username.
     * 
     * @param createdBy the creator's username
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the last modifier's username.
     * 
     * @return modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the last modifier's username.
     * 
     * @param modifiedBy the modifier's username
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Gets the creation date and time.
     * 
     * @return createdDatetime
     */
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * Sets the creation date and time.
     * 
     * @param createdDatetime the creation timestamp
     */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * Gets the last modified date and time.
     * 
     * @return modifiedDatetime
     */
    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    /**
     * Sets the last modified date and time.
     * 
     * @param modifiedDatetime the modified timestamp
     */
    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }
}