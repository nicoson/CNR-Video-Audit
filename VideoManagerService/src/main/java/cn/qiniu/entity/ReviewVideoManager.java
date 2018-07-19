package cn.qiniu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ReviewVideoManager {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.job_id
     *
     * @mbggenerated
     */
    private String jobId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.source
     *
     * @mbggenerated
     */
    private String source;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.video_uri
     *
     * @mbggenerated
     */
    private String videoUri;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.video_cover
     *
     * @mbggenerated
     */
    private String videoCover;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_stage
     *
     * @mbggenerated
     */
    private String reviewStage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_level
     *
     * @mbggenerated
     */
    private String reviewLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_manual_level
     *
     * @mbggenerated
     */
    private String reviewManualLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_robotic_level
     *
     * @mbggenerated
     */
    private String reviewRoboticLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_robotic_score
     *
     * @mbggenerated
     */
    private BigDecimal reviewRoboticScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_pulp_manual_level
     *
     * @mbggenerated
     */
    private String reviewOpsPulpManualLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_pulp_robotic_level
     *
     * @mbggenerated
     */
    private String reviewOpsPulpRoboticLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_pulp_robotic_score
     *
     * @mbggenerated
     */
    private BigDecimal reviewOpsPulpRoboticScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_terror_manual_level
     *
     * @mbggenerated
     */
    private String reviewOpsTerrorManualLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_terror_robotic_level
     *
     * @mbggenerated
     */
    private String reviewOpsTerrorRoboticLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_terror_robotic_score
     *
     * @mbggenerated
     */
    private BigDecimal reviewOpsTerrorRoboticScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_politician_manual_level
     *
     * @mbggenerated
     */
    private String reviewOpsPoliticianManualLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_politician_robotic_level
     *
     * @mbggenerated
     */
    private String reviewOpsPoliticianRoboticLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.review_ops_politician_robotic_score
     *
     * @mbggenerated
     */
    private BigDecimal reviewOpsPoliticianRoboticScore;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.video_name
     *
     * @mbggenerated
     */
    private String videoName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.video_time
     *
     * @mbggenerated
     */
    private String videoTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.reviewer_id
     *
     * @mbggenerated
     */
    private String reviewerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.created_by
     *
     * @mbggenerated
     */
    private String createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.created_at
     *
     * @mbggenerated
     */
    private Date createdAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.updated_by
     *
     * @mbggenerated
     */
    private String updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.updated_at
     *
     * @mbggenerated
     */
    private Date updatedAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.del_flg
     *
     * @mbggenerated
     */
    private String delFlg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.video_type
     *
     * @mbggenerated
     */
    private String videoType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column review_video_manager.live_end
     *
     * @mbggenerated
     */
    private String liveEnd;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    public ReviewVideoManager(String id, String jobId, String source, String videoUri, String videoCover, String reviewStage, String reviewLevel, String reviewManualLevel, String reviewRoboticLevel, BigDecimal reviewRoboticScore, String reviewOpsPulpManualLevel, String reviewOpsPulpRoboticLevel, BigDecimal reviewOpsPulpRoboticScore, String reviewOpsTerrorManualLevel, String reviewOpsTerrorRoboticLevel, BigDecimal reviewOpsTerrorRoboticScore, String reviewOpsPoliticianManualLevel, String reviewOpsPoliticianRoboticLevel, BigDecimal reviewOpsPoliticianRoboticScore, String videoName, String videoTime, String reviewerId, String createdBy, Date createdAt, String updatedBy, Date updatedAt, String delFlg, String videoType, String liveEnd) {
        this.id = id;
        this.jobId = jobId;
        this.source = source;
        this.videoUri = videoUri;
        this.videoCover = videoCover;
        this.reviewStage = reviewStage;
        this.reviewLevel = reviewLevel;
        this.reviewManualLevel = reviewManualLevel;
        this.reviewRoboticLevel = reviewRoboticLevel;
        this.reviewRoboticScore = reviewRoboticScore;
        this.reviewOpsPulpManualLevel = reviewOpsPulpManualLevel;
        this.reviewOpsPulpRoboticLevel = reviewOpsPulpRoboticLevel;
        this.reviewOpsPulpRoboticScore = reviewOpsPulpRoboticScore;
        this.reviewOpsTerrorManualLevel = reviewOpsTerrorManualLevel;
        this.reviewOpsTerrorRoboticLevel = reviewOpsTerrorRoboticLevel;
        this.reviewOpsTerrorRoboticScore = reviewOpsTerrorRoboticScore;
        this.reviewOpsPoliticianManualLevel = reviewOpsPoliticianManualLevel;
        this.reviewOpsPoliticianRoboticLevel = reviewOpsPoliticianRoboticLevel;
        this.reviewOpsPoliticianRoboticScore = reviewOpsPoliticianRoboticScore;
        this.videoName = videoName;
        this.videoTime = videoTime;
        this.reviewerId = reviewerId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.delFlg = delFlg;
        this.videoType = videoType;
        this.liveEnd = liveEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    public ReviewVideoManager() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.id
     *
     * @return the value of review_video_manager.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.id
     *
     * @param id the value for review_video_manager.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.job_id
     *
     * @return the value of review_video_manager.job_id
     *
     * @mbggenerated
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.job_id
     *
     * @param jobId the value for review_video_manager.job_id
     *
     * @mbggenerated
     */
    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.source
     *
     * @return the value of review_video_manager.source
     *
     * @mbggenerated
     */
    public String getSource() {
        return source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.source
     *
     * @param source the value for review_video_manager.source
     *
     * @mbggenerated
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.video_uri
     *
     * @return the value of review_video_manager.video_uri
     *
     * @mbggenerated
     */
    public String getVideoUri() {
        return videoUri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.video_uri
     *
     * @param videoUri the value for review_video_manager.video_uri
     *
     * @mbggenerated
     */
    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri == null ? null : videoUri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.video_cover
     *
     * @return the value of review_video_manager.video_cover
     *
     * @mbggenerated
     */
    public String getVideoCover() {
        return videoCover;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.video_cover
     *
     * @param videoCover the value for review_video_manager.video_cover
     *
     * @mbggenerated
     */
    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover == null ? null : videoCover.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_stage
     *
     * @return the value of review_video_manager.review_stage
     *
     * @mbggenerated
     */
    public String getReviewStage() {
        return reviewStage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_stage
     *
     * @param reviewStage the value for review_video_manager.review_stage
     *
     * @mbggenerated
     */
    public void setReviewStage(String reviewStage) {
        this.reviewStage = reviewStage == null ? null : reviewStage.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_level
     *
     * @return the value of review_video_manager.review_level
     *
     * @mbggenerated
     */
    public String getReviewLevel() {
        return reviewLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_level
     *
     * @param reviewLevel the value for review_video_manager.review_level
     *
     * @mbggenerated
     */
    public void setReviewLevel(String reviewLevel) {
        this.reviewLevel = reviewLevel == null ? null : reviewLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_manual_level
     *
     * @return the value of review_video_manager.review_manual_level
     *
     * @mbggenerated
     */
    public String getReviewManualLevel() {
        return reviewManualLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_manual_level
     *
     * @param reviewManualLevel the value for review_video_manager.review_manual_level
     *
     * @mbggenerated
     */
    public void setReviewManualLevel(String reviewManualLevel) {
        this.reviewManualLevel = reviewManualLevel == null ? null : reviewManualLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_robotic_level
     *
     * @return the value of review_video_manager.review_robotic_level
     *
     * @mbggenerated
     */
    public String getReviewRoboticLevel() {
        return reviewRoboticLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_robotic_level
     *
     * @param reviewRoboticLevel the value for review_video_manager.review_robotic_level
     *
     * @mbggenerated
     */
    public void setReviewRoboticLevel(String reviewRoboticLevel) {
        this.reviewRoboticLevel = reviewRoboticLevel == null ? null : reviewRoboticLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_robotic_score
     *
     * @return the value of review_video_manager.review_robotic_score
     *
     * @mbggenerated
     */
    public BigDecimal getReviewRoboticScore() {
        return reviewRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_robotic_score
     *
     * @param reviewRoboticScore the value for review_video_manager.review_robotic_score
     *
     * @mbggenerated
     */
    public void setReviewRoboticScore(BigDecimal reviewRoboticScore) {
        this.reviewRoboticScore = reviewRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_pulp_manual_level
     *
     * @return the value of review_video_manager.review_ops_pulp_manual_level
     *
     * @mbggenerated
     */
    public String getReviewOpsPulpManualLevel() {
        return reviewOpsPulpManualLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_pulp_manual_level
     *
     * @param reviewOpsPulpManualLevel the value for review_video_manager.review_ops_pulp_manual_level
     *
     * @mbggenerated
     */
    public void setReviewOpsPulpManualLevel(String reviewOpsPulpManualLevel) {
        this.reviewOpsPulpManualLevel = reviewOpsPulpManualLevel == null ? null : reviewOpsPulpManualLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_pulp_robotic_level
     *
     * @return the value of review_video_manager.review_ops_pulp_robotic_level
     *
     * @mbggenerated
     */
    public String getReviewOpsPulpRoboticLevel() {
        return reviewOpsPulpRoboticLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_pulp_robotic_level
     *
     * @param reviewOpsPulpRoboticLevel the value for review_video_manager.review_ops_pulp_robotic_level
     *
     * @mbggenerated
     */
    public void setReviewOpsPulpRoboticLevel(String reviewOpsPulpRoboticLevel) {
        this.reviewOpsPulpRoboticLevel = reviewOpsPulpRoboticLevel == null ? null : reviewOpsPulpRoboticLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_pulp_robotic_score
     *
     * @return the value of review_video_manager.review_ops_pulp_robotic_score
     *
     * @mbggenerated
     */
    public BigDecimal getReviewOpsPulpRoboticScore() {
        return reviewOpsPulpRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_pulp_robotic_score
     *
     * @param reviewOpsPulpRoboticScore the value for review_video_manager.review_ops_pulp_robotic_score
     *
     * @mbggenerated
     */
    public void setReviewOpsPulpRoboticScore(BigDecimal reviewOpsPulpRoboticScore) {
        this.reviewOpsPulpRoboticScore = reviewOpsPulpRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_terror_manual_level
     *
     * @return the value of review_video_manager.review_ops_terror_manual_level
     *
     * @mbggenerated
     */
    public String getReviewOpsTerrorManualLevel() {
        return reviewOpsTerrorManualLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_terror_manual_level
     *
     * @param reviewOpsTerrorManualLevel the value for review_video_manager.review_ops_terror_manual_level
     *
     * @mbggenerated
     */
    public void setReviewOpsTerrorManualLevel(String reviewOpsTerrorManualLevel) {
        this.reviewOpsTerrorManualLevel = reviewOpsTerrorManualLevel == null ? null : reviewOpsTerrorManualLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_terror_robotic_level
     *
     * @return the value of review_video_manager.review_ops_terror_robotic_level
     *
     * @mbggenerated
     */
    public String getReviewOpsTerrorRoboticLevel() {
        return reviewOpsTerrorRoboticLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_terror_robotic_level
     *
     * @param reviewOpsTerrorRoboticLevel the value for review_video_manager.review_ops_terror_robotic_level
     *
     * @mbggenerated
     */
    public void setReviewOpsTerrorRoboticLevel(String reviewOpsTerrorRoboticLevel) {
        this.reviewOpsTerrorRoboticLevel = reviewOpsTerrorRoboticLevel == null ? null : reviewOpsTerrorRoboticLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_terror_robotic_score
     *
     * @return the value of review_video_manager.review_ops_terror_robotic_score
     *
     * @mbggenerated
     */
    public BigDecimal getReviewOpsTerrorRoboticScore() {
        return reviewOpsTerrorRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_terror_robotic_score
     *
     * @param reviewOpsTerrorRoboticScore the value for review_video_manager.review_ops_terror_robotic_score
     *
     * @mbggenerated
     */
    public void setReviewOpsTerrorRoboticScore(BigDecimal reviewOpsTerrorRoboticScore) {
        this.reviewOpsTerrorRoboticScore = reviewOpsTerrorRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_politician_manual_level
     *
     * @return the value of review_video_manager.review_ops_politician_manual_level
     *
     * @mbggenerated
     */
    public String getReviewOpsPoliticianManualLevel() {
        return reviewOpsPoliticianManualLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_politician_manual_level
     *
     * @param reviewOpsPoliticianManualLevel the value for review_video_manager.review_ops_politician_manual_level
     *
     * @mbggenerated
     */
    public void setReviewOpsPoliticianManualLevel(String reviewOpsPoliticianManualLevel) {
        this.reviewOpsPoliticianManualLevel = reviewOpsPoliticianManualLevel == null ? null : reviewOpsPoliticianManualLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_politician_robotic_level
     *
     * @return the value of review_video_manager.review_ops_politician_robotic_level
     *
     * @mbggenerated
     */
    public String getReviewOpsPoliticianRoboticLevel() {
        return reviewOpsPoliticianRoboticLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_politician_robotic_level
     *
     * @param reviewOpsPoliticianRoboticLevel the value for review_video_manager.review_ops_politician_robotic_level
     *
     * @mbggenerated
     */
    public void setReviewOpsPoliticianRoboticLevel(String reviewOpsPoliticianRoboticLevel) {
        this.reviewOpsPoliticianRoboticLevel = reviewOpsPoliticianRoboticLevel == null ? null : reviewOpsPoliticianRoboticLevel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.review_ops_politician_robotic_score
     *
     * @return the value of review_video_manager.review_ops_politician_robotic_score
     *
     * @mbggenerated
     */
    public BigDecimal getReviewOpsPoliticianRoboticScore() {
        return reviewOpsPoliticianRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.review_ops_politician_robotic_score
     *
     * @param reviewOpsPoliticianRoboticScore the value for review_video_manager.review_ops_politician_robotic_score
     *
     * @mbggenerated
     */
    public void setReviewOpsPoliticianRoboticScore(BigDecimal reviewOpsPoliticianRoboticScore) {
        this.reviewOpsPoliticianRoboticScore = reviewOpsPoliticianRoboticScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.video_name
     *
     * @return the value of review_video_manager.video_name
     *
     * @mbggenerated
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.video_name
     *
     * @param videoName the value for review_video_manager.video_name
     *
     * @mbggenerated
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.video_time
     *
     * @return the value of review_video_manager.video_time
     *
     * @mbggenerated
     */
    public String getVideoTime() {
        return videoTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.video_time
     *
     * @param videoTime the value for review_video_manager.video_time
     *
     * @mbggenerated
     */
    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime == null ? null : videoTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.reviewer_id
     *
     * @return the value of review_video_manager.reviewer_id
     *
     * @mbggenerated
     */
    public String getReviewerId() {
        return reviewerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.reviewer_id
     *
     * @param reviewerId the value for review_video_manager.reviewer_id
     *
     * @mbggenerated
     */
    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId == null ? null : reviewerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.created_by
     *
     * @return the value of review_video_manager.created_by
     *
     * @mbggenerated
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.created_by
     *
     * @param createdBy the value for review_video_manager.created_by
     *
     * @mbggenerated
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.created_at
     *
     * @return the value of review_video_manager.created_at
     *
     * @mbggenerated
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.created_at
     *
     * @param createdAt the value for review_video_manager.created_at
     *
     * @mbggenerated
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.updated_by
     *
     * @return the value of review_video_manager.updated_by
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.updated_by
     *
     * @param updatedBy the value for review_video_manager.updated_by
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.updated_at
     *
     * @return the value of review_video_manager.updated_at
     *
     * @mbggenerated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.updated_at
     *
     * @param updatedAt the value for review_video_manager.updated_at
     *
     * @mbggenerated
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.del_flg
     *
     * @return the value of review_video_manager.del_flg
     *
     * @mbggenerated
     */
    public String getDelFlg() {
        return delFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.del_flg
     *
     * @param delFlg the value for review_video_manager.del_flg
     *
     * @mbggenerated
     */
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.video_type
     *
     * @return the value of review_video_manager.video_type
     *
     * @mbggenerated
     */
    public String getVideoType() {
        return videoType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.video_type
     *
     * @param videoType the value for review_video_manager.video_type
     *
     * @mbggenerated
     */
    public void setVideoType(String videoType) {
        this.videoType = videoType == null ? null : videoType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column review_video_manager.live_end
     *
     * @return the value of review_video_manager.live_end
     *
     * @mbggenerated
     */
    public String getLiveEnd() {
        return liveEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column review_video_manager.live_end
     *
     * @param liveEnd the value for review_video_manager.live_end
     *
     * @mbggenerated
     */
    public void setLiveEnd(String liveEnd) {
        this.liveEnd = liveEnd == null ? null : liveEnd.trim();
    }
}