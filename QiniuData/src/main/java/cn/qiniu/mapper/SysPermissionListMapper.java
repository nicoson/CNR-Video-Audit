package cn.qiniu.mapper;

import java.util.List;

import cn.qiniu.entity.SysResource;
import cn.qiniu.entity.SysResourceSearch;

public interface SysPermissionListMapper {
   public List<SysResource> getUserResList(SysResourceSearch search);
}