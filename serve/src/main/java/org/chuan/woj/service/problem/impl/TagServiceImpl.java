package org.chuan.woj.service.problem.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.pojo.vo.problem.TagVO;
import org.chuan.woj.service.problem.TagService;
import org.chuan.woj.mapper.TagMapper;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author lenovo
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-08-27 12:07:13
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public BaseResponse<List<TagVO>> getProblemTagList() {
        List<Tag> list = this.list();
        List<TagVO> tagVOList = new ArrayList<>();
        for (Tag tag : list) {
            TagVO tagVO = new TagVO();
            BeanUtils.copyProperties(tag, tagVO);
            tagVOList.add(tagVO);
        }
        return ResultUtils.success(tagVOList);
    }
}




