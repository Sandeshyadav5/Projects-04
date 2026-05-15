package in.co.rays.proj4.bean;

public class JobBean extends BaseBean {

    private String jobCode;
    private String jobName;
    private String cronExpression;
    private String status;

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 🔥 IMPORTANT for dropdown
    public String getKey() {
        return jobCode;
    }

    public String getValue() {
        return jobCode;
    }
}