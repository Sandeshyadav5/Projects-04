package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimeTableBean class represents the Time Table entity.
 * It contains details about semester, course, subject, and exam schedule.
 * 
 * @author 
 */
public class TimetableBean extends BaseBean {

    /**
     * Semester of the exam
     */
    private String semester;

    /**
     * Description of the timetable
     */
    private String description;

    /**
     * Date of the exam
     */
    private Date examDate;

    /**
     * Time of the exam
     */
    private String examTime;

    /**
     * Course ID associated with the timetable
     */
    private long courseId;

    /**
     * Course name
     */
    private String courseName;

    /**
     * Subject ID associated with the timetable
     */
    private long subjectId;

    /**
     * Subject name
     */
    private String subjectName;

    /**
     * Gets the semester
     * 
     * @return semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets the semester
     * 
     * @param semester
     */
    public void setSemester(String semester) {
        this.semester = semester;
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
     * Gets the exam date
     * 
     * @return examDate
     */
    public Date getExamDate() {
        return examDate;
    }

    /**
     * Sets the exam date
     * 
     * @param examDate
     */
    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    /**
     * Gets the exam time
     * 
     * @return examTime
     */
    public String getExamTime() {
        return examTime;
    }

    /**
     * Sets the exam time
     * 
     * @param examTime
     */
    public void setExamTime(String examTime) {
        this.examTime = examTime;
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
     * Gets the subject ID
     * 
     * @return subjectId
     */
    public long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the subject ID
     * 
     * @param subjectId
     */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Gets the subject name
     * 
     * @return subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the subject name
     * 
     * @param subjectName
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
     * Returns the display value (semester)
     * 
     * @return value
     */
    @Override
    public String getValue() {
        return semester;
    }
}
