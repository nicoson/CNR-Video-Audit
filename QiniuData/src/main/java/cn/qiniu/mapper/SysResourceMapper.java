package cn.qiniu.mapper;

import cn.qiniu.entity.SysResource;
import cn.qiniu.entity.SysResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    int countByExample(SysResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    int deleteByExample(SysResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    @Delete({
        "delete from `sys_resource`",
        "where `resource_id` = #{resourceId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String resourceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    @Insert({
        "insert into `sys_resource` (`resource_id`, `resource_nm`, ",
        "`resource_type`, `resource_ident`, ",
        "`resource_url`, `is_show`, ",
        "`resource_pic`, `sort_no`, ",
        "`parent_resource_id`, `rmrk`, ",
        "`del_flg`, `created_by`, ",
        "`created_at`, `updated_by`, ",
        "`updated_at`)",
        "values (#{resourceId,jdbcType=VARCHAR}, #{resourceNm,jdbcType=VARCHAR}, ",
        "#{resourceType,jdbcType=VARCHAR}, #{resourceIdent,jdbcType=VARCHAR}, ",
        "#{resourceUrl,jdbcType=VARCHAR}, #{isShow,jdbcType=VARCHAR}, ",
        "#{resourcePic,jdbcType=VARCHAR}, #{sortNo,jdbcType=INTEGER}, ",
        "#{parentResourceId,jdbcType=VARCHAR}, #{rmrk,jdbcType=VARCHAR}, ",
        "#{delFlg,jdbcType=VARCHAR}, #{createdBy,jdbcType=VARCHAR}, ",
        "#{createdAt,jdbcType=TIMESTAMP}, #{updatedBy,jdbcType=VARCHAR}, ",
        "#{updatedAt,jdbcType=TIMESTAMP})"
    })
    int insert(SysResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    int insertSelective(SysResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    List<SysResource> selectByExample(SysResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "`resource_id`, `resource_nm`, `resource_type`, `resource_ident`, `resource_url`, ",
        "`is_show`, `resource_pic`, `sort_no`, `parent_resource_id`, `rmrk`, `del_flg`, ",
        "`created_by`, `created_at`, `updated_by`, `updated_at`",
        "from `sys_resource`",
        "where `resource_id` = #{resourceId,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    SysResource selectByPrimaryKey(String resourceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SysResource record, @Param("example") SysResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SysResource record, @Param("example") SysResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_resource
     *
     * @mbggenerated
     */
    @Update({
        "update `sys_resource`",
        "set `resource_nm` = #{resourceNm,jdbcType=VARCHAR},",
          "`resource_type` = #{resourceType,jdbcType=VARCHAR},",
          "`resource_ident` = #{resourceIdent,jdbcType=VARCHAR},",
          "`resource_url` = #{resourceUrl,jdbcType=VARCHAR},",
          "`is_show` = #{isShow,jdbcType=VARCHAR},",
          "`resource_pic` = #{resourcePic,jdbcType=VARCHAR},",
          "`sort_no` = #{sortNo,jdbcType=INTEGER},",
          "`parent_resource_id` = #{parentResourceId,jdbcType=VARCHAR},",
          "`rmrk` = #{rmrk,jdbcType=VARCHAR},",
          "`del_flg` = #{delFlg,jdbcType=VARCHAR},",
          "`created_by` = #{createdBy,jdbcType=VARCHAR},",
          "`created_at` = #{createdAt,jdbcType=TIMESTAMP},",
          "`updated_by` = #{updatedBy,jdbcType=VARCHAR},",
          "`updated_at` = #{updatedAt,jdbcType=TIMESTAMP}",
        "where `resource_id` = #{resourceId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(SysResource record);
}