package cn.qiniu.entity;

import java.util.Date;

public class ReviewLevelSetting {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.ops_op
     *
     * @mbggenerated
     */
    private String opsOp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.ops_op_label
     *
     * @mbggenerated
     */
    private String opsOpLabel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.ops_op_level
     *
     * @mbggenerated
     */
    private String opsOpLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.created_by
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.updated_by
     *
     * @mbggenerated
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_level_setting.del_flg
     *
     * @mbggenerated
     */
    private String delFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_level_setting
     *
     * @mbggenerated
     */
    public ReviewLevelSetting(String id, String opsOp, String opsOpLabel, String opsOpLevel, String createdBy, Date createdAt, String updatedBy, Date updatedAt, String delFlg) {
        this.id = id;
        this.opsOp = opsOp;
        this.opsOpLabel = opsOpLabel;
        this.opsOpLevel = opsOpLevel;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.delFlg = delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_level_setting
     *
     * @mbggenerated
     */
    public ReviewLevelSetting() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.id
     *
     * @return the value of review_level_setting.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.id
     *
     * @param id the value for review_level_setting.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.ops_op
     *
     * @return the value of review_level_setting.ops_op
     *
     * @mbggenerated
     */
    public String getOpsOp() {
        return opsOp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.ops_op
     *
     * @param opsOp the value for review_level_setting.ops_op
     *
     * @mbggenerated
     */
    public void setOpsOp(String opsOp) {
        this.opsOp = opsOp == null ? null : opsOp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.ops_op_label
     *
     * @return the value of review_level_setting.ops_op_label
     *
     * @mbggenerated
     */
    public String getOpsOpLabel() {
        return opsOpLabel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.ops_op_label
     *
     * @param opsOpLabel the value for review_level_setting.ops_op_label
     *
     * @mbggenerated
     */
    public void setOpsOpLabel(String opsOpLabel) {
        this.opsOpLabel = opsOpLabel == null ? null : opsOpLabel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.ops_op_level
     *
     * @return the value of review_level_setting.ops_op_level
     *
     * @mbggenerated
     */
    public String getOpsOpLevel() {
        return opsOpLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.ops_op_level
     *
     * @param opsOpLevel the value for review_level_setting.ops_op_level
     *
     * @mbggenerated
     */
    public void setOpsOpLevel(String opsOpLevel) {
        this.opsOpLevel = opsOpLevel == null ? null : opsOpLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.created_by
     *
     * @return the value of review_level_setting.created_by
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.created_by
     *
     * @param createdBy the value for review_level_setting.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.created_at
     *
     * @return the value of review_level_setting.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.created_at
     *
     * @param createdAt the value for review_level_setting.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.updated_by
     *
     * @return the value of review_level_setting.updated_by
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.updated_by
     *
     * @param updatedBy the value for review_level_setting.updated_by
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.updated_at
     *
     * @return the value of review_level_setting.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.updated_at
     *
     * @param updatedAt the value for review_level_setting.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_level_setting.del_flg
     *
     * @return the value of review_level_setting.del_flg
     *
     * @mbggenerated
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_level_setting.del_flg
     *
     * @param delFlg the value for review_level_setting.del_flg
     *
     * @mbggenerated
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }
}