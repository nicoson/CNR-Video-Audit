package cn.qiniu.entity.manager;

import java.util.Date;

public class ReviewVideoManagerCuts {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.video_id
     *
     * @mbggenerated
     */
    private String videoId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.segment_id
     *
     * @mbggenerated
     */
    private String segmentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.offset
     *
     * @mbggenerated
     */
    private Integer offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.uri
     *
     * @mbggenerated
     */
    private String uri;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.created_by
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.updated_by
     *
     * @mbggenerated
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager_cuts.del_flg
     *
     * @mbggenerated
     */
    private String delFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager_cuts
     *
     * @mbggenerated
     */
    public ReviewVideoManagerCuts(String id, String videoId, String segmentId, Integer offset, String uri, String createdBy, Date createdAt, String updatedBy, Date updatedAt, String delFlg) {
        this.id = id;
        this.videoId = videoId;
        this.segmentId = segmentId;
        this.offset = offset;
        this.uri = uri;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.delFlg = delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager_cuts
     *
     * @mbggenerated
     */
    public ReviewVideoManagerCuts() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.id
     *
     * @return the value of review_video_manager_cuts.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.id
     *
     * @param id the value for review_video_manager_cuts.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.video_id
     *
     * @return the value of review_video_manager_cuts.video_id
     *
     * @mbggenerated
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.video_id
     *
     * @param videoId the value for review_video_manager_cuts.video_id
     *
     * @mbggenerated
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.segment_id
     *
     * @return the value of review_video_manager_cuts.segment_id
     *
     * @mbggenerated
     */
    public String getSegmentId() {
        return segmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.segment_id
     *
     * @param segmentId the value for review_video_manager_cuts.segment_id
     *
     * @mbggenerated
     */
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId == null ? null : segmentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.offset
     *
     * @return the value of review_video_manager_cuts.offset
     *
     * @mbggenerated
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.offset
     *
     * @param offset the value for review_video_manager_cuts.offset
     *
     * @mbggenerated
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.uri
     *
     * @return the value of review_video_manager_cuts.uri
     *
     * @mbggenerated
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.uri
     *
     * @param uri the value for review_video_manager_cuts.uri
     *
     * @mbggenerated
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.created_by
     *
     * @return the value of review_video_manager_cuts.created_by
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.created_by
     *
     * @param createdBy the value for review_video_manager_cuts.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.created_at
     *
     * @return the value of review_video_manager_cuts.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.created_at
     *
     * @param createdAt the value for review_video_manager_cuts.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.updated_by
     *
     * @return the value of review_video_manager_cuts.updated_by
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.updated_by
     *
     * @param updatedBy the value for review_video_manager_cuts.updated_by
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.updated_at
     *
     * @return the value of review_video_manager_cuts.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.updated_at
     *
     * @param updatedAt the value for review_video_manager_cuts.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager_cuts.del_flg
     *
     * @return the value of review_video_manager_cuts.del_flg
     *
     * @mbggenerated
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager_cuts.del_flg
     *
     * @param delFlg the value for review_video_manager_cuts.del_flg
     *
     * @mbggenerated
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }
}