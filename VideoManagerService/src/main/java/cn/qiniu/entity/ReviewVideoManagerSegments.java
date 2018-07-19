package cn.qiniu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ReviewVideoManagerSegments {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.video_id
     *
     * @mbggenerated
     */
    private String videoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.op
     *
     * @mbggenerated
     */
    private String op;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.offset_begin
     *
     * @mbggenerated
     */
    private Integer offsetBegin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.offset_end
     *
     * @mbggenerated
     */
    private Integer offsetEnd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.uri
     *
     * @mbggenerated
     */
    private String uri;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.level
     *
     * @mbggenerated
     */
    private String level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.manual_level
     *
     * @mbggenerated
     */
    private String manualLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.robotic_level
     *
     * @mbggenerated
     */
    private String roboticLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.robotic_score
     *
     * @mbggenerated
     */
    private BigDecimal roboticScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.created_by
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.updated_by
     *
     * @mbggenerated
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_segments.del_flg
     *
     * @mbggenerated
     */
    private String delFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager_segments
     *
     * @mbggenerated
     */
    public ReviewVideoManagerSegments(String id, String videoId, String op, Integer offsetBegin, Integer offsetEnd, String uri, String level, String manualLevel, String roboticLevel, BigDecimal roboticScore, String createdBy, Date createdAt, String updatedBy, Date updatedAt, String delFlg) {
        this.id = id;
        this.videoId = videoId;
        this.op = op;
        this.offsetBegin = offsetBegin;
        this.offsetEnd = offsetEnd;
        this.uri = uri;
        this.level = level;
        this.manualLevel = manualLevel;
        this.roboticLevel = roboticLevel;
        this.roboticScore = roboticScore;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.delFlg = delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager_segments
     *
     * @mbggenerated
     */
    public ReviewVideoManagerSegments() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.id
     *
     * @return the value of review_video_manager_segments.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.id
     *
     * @param id the value for review_video_manager_segments.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.video_id
     *
     * @return the value of review_video_manager_segments.video_id
     *
     * @mbggenerated
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.video_id
     *
     * @param videoId the value for review_video_manager_segments.video_id
     *
     * @mbggenerated
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.op
     *
     * @return the value of review_video_manager_segments.op
     *
     * @mbggenerated
     */
    public String getOp() {
        return op;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.op
     *
     * @param op the value for review_video_manager_segments.op
     *
     * @mbggenerated
     */
    public void setOp(String op) {
        this.op = op == null ? null : op.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.offset_begin
     *
     * @return the value of review_video_manager_segments.offset_begin
     *
     * @mbggenerated
     */
    public Integer getOffsetBegin() {
        return offsetBegin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.offset_begin
     *
     * @param offsetBegin the value for review_video_manager_segments.offset_begin
     *
     * @mbggenerated
     */
    public void setOffsetBegin(Integer offsetBegin) {
        this.offsetBegin = offsetBegin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.offset_end
     *
     * @return the value of review_video_manager_segments.offset_end
     *
     * @mbggenerated
     */
    public Integer getOffsetEnd() {
        return offsetEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.offset_end
     *
     * @param offsetEnd the value for review_video_manager_segments.offset_end
     *
     * @mbggenerated
     */
    public void setOffsetEnd(Integer offsetEnd) {
        this.offsetEnd = offsetEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.uri
     *
     * @return the value of review_video_manager_segments.uri
     *
     * @mbggenerated
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.uri
     *
     * @param uri the value for review_video_manager_segments.uri
     *
     * @mbggenerated
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.level
     *
     * @return the value of review_video_manager_segments.level
     *
     * @mbggenerated
     */
    public String getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.level
     *
     * @param level the value for review_video_manager_segments.level
     *
     * @mbggenerated
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.manual_level
     *
     * @return the value of review_video_manager_segments.manual_level
     *
     * @mbggenerated
     */
    public String getManualLevel() {
        return manualLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.manual_level
     *
     * @param manualLevel the value for review_video_manager_segments.manual_level
     *
     * @mbggenerated
     */
    public void setManualLevel(String manualLevel) {
        this.manualLevel = manualLevel == null ? null : manualLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.robotic_level
     *
     * @return the value of review_video_manager_segments.robotic_level
     *
     * @mbggenerated
     */
    public String getRoboticLevel() {
        return roboticLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.robotic_level
     *
     * @param roboticLevel the value for review_video_manager_segments.robotic_level
     *
     * @mbggenerated
     */
    public void setRoboticLevel(String roboticLevel) {
        this.roboticLevel = roboticLevel == null ? null : roboticLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.robotic_score
     *
     * @return the value of review_video_manager_segments.robotic_score
     *
     * @mbggenerated
     */
    public BigDecimal getRoboticScore() {
        return roboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.robotic_score
     *
     * @param roboticScore the value for review_video_manager_segments.robotic_score
     *
     * @mbggenerated
     */
    public void setRoboticScore(BigDecimal roboticScore) {
        this.roboticScore = roboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.created_by
     *
     * @return the value of review_video_manager_segments.created_by
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.created_by
     *
     * @param createdBy the value for review_video_manager_segments.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.created_at
     *
     * @return the value of review_video_manager_segments.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.created_at
     *
     * @param createdAt the value for review_video_manager_segments.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.updated_by
     *
     * @return the value of review_video_manager_segments.updated_by
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.updated_by
     *
     * @param updatedBy the value for review_video_manager_segments.updated_by
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.updated_at
     *
     * @return the value of review_video_manager_segments.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.updated_at
     *
     * @param updatedAt the value for review_video_manager_segments.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_segments.del_flg
     *
     * @return the value of review_video_manager_segments.del_flg
     *
     * @mbggenerated
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_segments.del_flg
     *
     * @param delFlg the value for review_video_manager_segments.del_flg
     *
     * @mbggenerated
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }
}