package cn.qiniu.mapper;

import cn.qiniu.entity.ReviewVideoManager;
import cn.qiniu.entity.ReviewVideoManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ReviewVideoManagerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    int countByExample(ReviewVideoManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    int deleteByExample(ReviewVideoManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    @Delete({
        "delete from `review_video_manager`",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    @Insert({
        "insert into `review_video_manager` (`id`, `job_id`, ",
        "`source`, `video_uri`, ",
        "`video_cover`, `review_stage`, ",
        "`review_level`, `review_manual_level`, ",
        "`review_robotic_level`, `review_robotic_score`, ",
        "`review_ops_pulp_manual_level`, `review_ops_pulp_robotic_level`, ",
        "`review_ops_pulp_robotic_score`, `review_ops_terror_manual_level`, ",
        "`review_ops_terror_robotic_level`, `review_ops_terror_robotic_score`, ",
        "`review_ops_politician_manual_level`, `review_ops_politician_robotic_level`, ",
        "`review_ops_politician_robotic_score`, `video_name`, ",
        "`video_time`, `reviewer_id`, ",
        "`created_by`, `created_at`, ",
        "`updated_by`, `updated_at`, ",
        "`del_flg`, `video_type`, ",
        "`live_end`)",
        "values (#{id,jdbcType=VARCHAR}, #{jobId,jdbcType=VARCHAR}, ",
        "#{source,jdbcType=VARCHAR}, #{videoUri,jdbcType=VARCHAR}, ",
        "#{videoCover,jdbcType=VARCHAR}, #{reviewStage,jdbcType=VARCHAR}, ",
        "#{reviewLevel,jdbcType=VARCHAR}, #{reviewManualLevel,jdbcType=VARCHAR}, ",
        "#{reviewRoboticLevel,jdbcType=VARCHAR}, #{reviewRoboticScore,jdbcType=DECIMAL}, ",
        "#{reviewOpsPulpManualLevel,jdbcType=VARCHAR}, #{reviewOpsPulpRoboticLevel,jdbcType=VARCHAR}, ",
        "#{reviewOpsPulpRoboticScore,jdbcType=DECIMAL}, #{reviewOpsTerrorManualLevel,jdbcType=VARCHAR}, ",
        "#{reviewOpsTerrorRoboticLevel,jdbcType=VARCHAR}, #{reviewOpsTerrorRoboticScore,jdbcType=DECIMAL}, ",
        "#{reviewOpsPoliticianManualLevel,jdbcType=VARCHAR}, #{reviewOpsPoliticianRoboticLevel,jdbcType=VARCHAR}, ",
        "#{reviewOpsPoliticianRoboticScore,jdbcType=DECIMAL}, #{videoName,jdbcType=VARCHAR}, ",
        "#{videoTime,jdbcType=VARCHAR}, #{reviewerId,jdbcType=VARCHAR}, ",
        "#{createdBy,jdbcType=VARCHAR}, #{createdAt,jdbcType=TIMESTAMP}, ",
        "#{updatedBy,jdbcType=VARCHAR}, #{updatedAt,jdbcType=TIMESTAMP}, ",
        "#{delFlg,jdbcType=VARCHAR}, #{videoType,jdbcType=VARCHAR}, ",
        "#{liveEnd,jdbcType=VARCHAR})"
    })
    int insert(ReviewVideoManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    int insertSelective(ReviewVideoManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    List<ReviewVideoManager> selectByExample(ReviewVideoManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "`id`, `job_id`, `source`, `video_uri`, `video_cover`, `review_stage`, `review_level`, ",
        "`review_manual_level`, `review_robotic_level`, `review_robotic_score`, `review_ops_pulp_manual_level`, ",
        "`review_ops_pulp_robotic_level`, `review_ops_pulp_robotic_score`, `review_ops_terror_manual_level`, ",
        "`review_ops_terror_robotic_level`, `review_ops_terror_robotic_score`, `review_ops_politician_manual_level`, ",
        "`review_ops_politician_robotic_level`, `review_ops_politician_robotic_score`, ",
        "`video_name`, `video_time`, `reviewer_id`, `created_by`, `created_at`, `updated_by`, ",
        "`updated_at`, `del_flg`, `video_type`, `live_end`",
        "from `review_video_manager`",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    ReviewVideoManager selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") ReviewVideoManager record, @Param("example") ReviewVideoManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") ReviewVideoManager record, @Param("example") ReviewVideoManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ReviewVideoManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table review_video_manager
     *
     * @mbggenerated
     */
    @Update({
        "update `review_video_manager`",
        "set `job_id` = #{jobId,jdbcType=VARCHAR},",
          "`source` = #{source,jdbcType=VARCHAR},",
          "`video_uri` = #{videoUri,jdbcType=VARCHAR},",
          "`video_cover` = #{videoCover,jdbcType=VARCHAR},",
          "`review_stage` = #{reviewStage,jdbcType=VARCHAR},",
          "`review_level` = #{reviewLevel,jdbcType=VARCHAR},",
          "`review_manual_level` = #{reviewManualLevel,jdbcType=VARCHAR},",
          "`review_robotic_level` = #{reviewRoboticLevel,jdbcType=VARCHAR},",
          "`review_robotic_score` = #{reviewRoboticScore,jdbcType=DECIMAL},",
          "`review_ops_pulp_manual_level` = #{reviewOpsPulpManualLevel,jdbcType=VARCHAR},",
          "`review_ops_pulp_robotic_level` = #{reviewOpsPulpRoboticLevel,jdbcType=VARCHAR},",
          "`review_ops_pulp_robotic_score` = #{reviewOpsPulpRoboticScore,jdbcType=DECIMAL},",
          "`review_ops_terror_manual_level` = #{reviewOpsTerrorManualLevel,jdbcType=VARCHAR},",
          "`review_ops_terror_robotic_level` = #{reviewOpsTerrorRoboticLevel,jdbcType=VARCHAR},",
          "`review_ops_terror_robotic_score` = #{reviewOpsTerrorRoboticScore,jdbcType=DECIMAL},",
          "`review_ops_politician_manual_level` = #{reviewOpsPoliticianManualLevel,jdbcType=VARCHAR},",
          "`review_ops_politician_robotic_level` = #{reviewOpsPoliticianRoboticLevel,jdbcType=VARCHAR},",
          "`review_ops_politician_robotic_score` = #{reviewOpsPoliticianRoboticScore,jdbcType=DECIMAL},",
          "`video_name` = #{videoName,jdbcType=VARCHAR},",
          "`video_time` = #{videoTime,jdbcType=VARCHAR},",
          "`reviewer_id` = #{reviewerId,jdbcType=VARCHAR},",
          "`created_by` = #{createdBy,jdbcType=VARCHAR},",
          "`created_at` = #{createdAt,jdbcType=TIMESTAMP},",
          "`updated_by` = #{updatedBy,jdbcType=VARCHAR},",
          "`updated_at` = #{updatedAt,jdbcType=TIMESTAMP},",
          "`del_flg` = #{delFlg,jdbcType=VARCHAR},",
          "`video_type` = #{videoType,jdbcType=VARCHAR},",
          "`live_end` = #{liveEnd,jdbcType=VARCHAR}",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(ReviewVideoManager record);
}