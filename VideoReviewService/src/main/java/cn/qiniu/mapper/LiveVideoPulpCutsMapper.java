package cn.qiniu.mapper;

import cn.qiniu.entity.LiveVideoPulpCuts;
import cn.qiniu.entity.LiveVideoPulpCutsExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface LiveVideoPulpCutsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    int countByExample(LiveVideoPulpCutsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    int deleteByExample(LiveVideoPulpCutsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    @Delete({
        "delete from `live_video_pulp_cuts`",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    @Insert({
        "insert into `live_video_pulp_cuts` (`id`, `job_id`, ",
        "`url`, `timestamp`, ",
        "`reqid`, `label`, `created_by`, ",
        "`created_at`, `updated_by`, ",
        "`updated_at`, `del_flg`, ",
        "`review_type`, `score`)",
        "values (#{id,jdbcType=VARCHAR}, #{jobId,jdbcType=VARCHAR}, ",
        "#{url,jdbcType=VARCHAR}, #{timestamp,jdbcType=VARCHAR}, ",
        "#{reqid,jdbcType=VARCHAR}, #{label,jdbcType=VARCHAR}, #{createdBy,jdbcType=VARCHAR}, ",
        "#{createdAt,jdbcType=TIMESTAMP}, #{updatedBy,jdbcType=VARCHAR}, ",
        "#{updatedAt,jdbcType=TIMESTAMP}, #{delFlg,jdbcType=VARCHAR}, ",
        "#{reviewType,jdbcType=VARCHAR}, #{score,jdbcType=DECIMAL})"
    })
    int insert(LiveVideoPulpCuts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    int insertSelective(LiveVideoPulpCuts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    List<LiveVideoPulpCuts> selectByExample(LiveVideoPulpCutsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "`id`, `job_id`, `url`, `timestamp`, `reqid`, `label`, `created_by`, `created_at`, ",
        "`updated_by`, `updated_at`, `del_flg`, `review_type`, `score`",
        "from `live_video_pulp_cuts`",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    LiveVideoPulpCuts selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") LiveVideoPulpCuts record, @Param("example") LiveVideoPulpCutsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") LiveVideoPulpCuts record, @Param("example") LiveVideoPulpCutsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(LiveVideoPulpCuts record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table live_video_pulp_cuts
     *
     * @mbggenerated
     */
    @Update({
        "update `live_video_pulp_cuts`",
        "set `job_id` = #{jobId,jdbcType=VARCHAR},",
          "`url` = #{url,jdbcType=VARCHAR},",
          "`timestamp` = #{timestamp,jdbcType=VARCHAR},",
          "`reqid` = #{reqid,jdbcType=VARCHAR},",
          "`label` = #{label,jdbcType=VARCHAR},",
          "`created_by` = #{createdBy,jdbcType=VARCHAR},",
          "`created_at` = #{createdAt,jdbcType=TIMESTAMP},",
          "`updated_by` = #{updatedBy,jdbcType=VARCHAR},",
          "`updated_at` = #{updatedAt,jdbcType=TIMESTAMP},",
          "`del_flg` = #{delFlg,jdbcType=VARCHAR},",
          "`review_type` = #{reviewType,jdbcType=VARCHAR},",
          "`score` = #{score,jdbcType=DECIMAL}",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(LiveVideoPulpCuts record);
}