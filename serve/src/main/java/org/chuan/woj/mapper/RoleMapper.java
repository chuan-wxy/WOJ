package org.chuan.woj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chuan.woj.pojo.entity.Role;

import java.util.List;

/**
* @author lenovo
* @description 针对表【role】的数据库操作Mapper
* @createDate 2024-08-20 12:26:42
* @Entity org.chuan.woj.pojo.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> SelectRoleByUserAccount(String userAccount);

}
