package cn.qiniu.mapper;

import cn.qiniu.entity.SysDict;
import cn.qiniu.entity.SysDictExample;
import cn.qiniu.form.SysDictSearch;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysDictMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    int countByExample(SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    int deleteByExample(SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    @Delete({
        "delete from `sys_dict`",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    @Insert({
        "insert into `sys_dict` (`id`, `type`, ",
        "`description`, `sort`, ",
        "`value`, `label`, `remarks`, ",
        "`created_by`, `created_at`, ",
        "`updated_by`, `updated_at`, ",
        "`del_flg`)",
        "values (#{id,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{sort,jdbcType=VARCHAR}, ",
        "#{value,jdbcType=VARCHAR}, #{label,jdbcType=VARCHAR}, #{remarks,jdbcType=VARCHAR}, ",
        "#{createdBy,jdbcType=VARCHAR}, #{createdAt,jdbcType=TIMESTAMP}, ",
        "#{updatedBy,jdbcType=VARCHAR}, #{updatedAt,jdbcType=TIMESTAMP}, ",
        "#{delFlg,jdbcType=VARCHAR})"
    })
    int insert(SysDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    int insertSelective(SysDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    List<SysDict> selectByExample(SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "`id`, `type`, `description`, `sort`, `value`, `label`, `remarks`, `created_by`, ",
        "`created_at`, `updated_by`, `updated_at`, `del_flg`",
        "from `sys_dict`",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    SysDict selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SysDict record, @Param("example") SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SysDict record, @Param("example") SysDictExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysDict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_dict
     *
     * @mbggenerated
     */
    @Update({
        "update `sys_dict`",
        "set `type` = #{type,jdbcType=VARCHAR},",
          "`description` = #{description,jdbcType=VARCHAR},",
          "`sort` = #{sort,jdbcType=VARCHAR},",
          "`value` = #{value,jdbcType=VARCHAR},",
          "`label` = #{label,jdbcType=VARCHAR},",
          "`remarks` = #{remarks,jdbcType=VARCHAR},",
          "`created_by` = #{createdBy,jdbcType=VARCHAR},",
          "`created_at` = #{createdAt,jdbcType=TIMESTAMP},",
          "`updated_by` = #{updatedBy,jdbcType=VARCHAR},",
          "`updated_at` = #{updatedAt,jdbcType=TIMESTAMP},",
          "`del_flg` = #{delFlg,jdbcType=VARCHAR}",
        "where `id` = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(SysDict record);
    
    /**
     * 查询字典表type
     *
     */
    List<String> selectDictTypeList();
    
    /**
     * 根据type查询字典表
     *
     */
    List<String> selectByDicCode(SysDictSearch search);
}