package org.chuan.woj.service.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.service.problem.TagService;
import org.chuan.woj.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-08-27 12:07:13
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




