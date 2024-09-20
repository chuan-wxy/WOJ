package org.chuan.woj.mapper;

import org.chuan.woj.pojo.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lenovo
* @description 针对表【tag】的数据库操作Mapper
* @createDate 2024-08-27 12:07:13
* @Entity org.chuan.woj.pojo.entity.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    public long seleceIdByName(String name);

}




