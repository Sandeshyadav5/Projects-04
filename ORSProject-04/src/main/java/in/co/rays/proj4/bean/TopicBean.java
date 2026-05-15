package in.co.rays.proj4.bean;

import java.util.Date;

public class TopicBean extends BaseBean {

    private String topicCode;
    private String topicName;
    private Integer partitions;
    private String status;

    // Getter & Setter

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Integer getPartitions() {
        return partitions;
    }

    public void setPartitions(Integer partitions) {
        this.partitions = partitions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // For Dropdown / Utility use (same as PasswordBean)

    @Override
    public String getKey() {
        return topicCode;
    }

    @Override
    public String getValue() {
        return topicCode;
    }

}