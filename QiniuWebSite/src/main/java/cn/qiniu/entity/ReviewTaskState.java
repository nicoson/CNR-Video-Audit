package cn.qiniu.entity;

import java.util.Date;

public class ReviewTaskState {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.job_id
     *
     * @mbggenerated
     */
    private String jobId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.time_created
     *
     * @mbggenerated
     */
    private Integer timeCreated;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.time_done
     *
     * @mbggenerated
     */
    private Integer timeDone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.created_by
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.updated_by
     *
     * @mbggenerated
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_task_state.del_flg
     *
     * @mbggenerated
     */
    private String delFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_task_state
     *
     * @mbggenerated
     */
    public ReviewTaskState(String id, String jobId, Integer timeCreated, Integer timeDone, String createdBy, Date createdAt, String updatedBy, Date updatedAt, String delFlg) {
        this.id = id;
        this.jobId = jobId;
        this.timeCreated = timeCreated;
        this.timeDone = timeDone;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.delFlg = delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_task_state
     *
     * @mbggenerated
     */
    public ReviewTaskState() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.id
     *
     * @return the value of review_task_state.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.id
     *
     * @param id the value for review_task_state.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.job_id
     *
     * @return the value of review_task_state.job_id
     *
     * @mbggenerated
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.job_id
     *
     * @param jobId the value for review_task_state.job_id
     *
     * @mbggenerated
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.time_created
     *
     * @return the value of review_task_state.time_created
     *
     * @mbggenerated
     */
    public Integer getTimeCreated() {
        return timeCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.time_created
     *
     * @param timeCreated the value for review_task_state.time_created
     *
     * @mbggenerated
     */
    public void setTimeCreated(Integer timeCreated) {
        this.timeCreated = timeCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.time_done
     *
     * @return the value of review_task_state.time_done
     *
     * @mbggenerated
     */
    public Integer getTimeDone() {
        return timeDone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.time_done
     *
     * @param timeDone the value for review_task_state.time_done
     *
     * @mbggenerated
     */
    public void setTimeDone(Integer timeDone) {
        this.timeDone = timeDone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.created_by
     *
     * @return the value of review_task_state.created_by
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.created_by
     *
     * @param createdBy the value for review_task_state.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.created_at
     *
     * @return the value of review_task_state.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.created_at
     *
     * @param createdAt the value for review_task_state.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.updated_by
     *
     * @return the value of review_task_state.updated_by
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.updated_by
     *
     * @param updatedBy the value for review_task_state.updated_by
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.updated_at
     *
     * @return the value of review_task_state.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.updated_at
     *
     * @param updatedAt the value for review_task_state.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_task_state.del_flg
     *
     * @return the value of review_task_state.del_flg
     *
     * @mbggenerated
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_task_state.del_flg
     *
     * @param delFlg the value for review_task_state.del_flg
     *
     * @mbggenerated
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }
}