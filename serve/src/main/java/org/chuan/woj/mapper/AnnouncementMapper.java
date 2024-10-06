package org.chuan.woj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.chuan.woj.pojo.entity.Announcement;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementContentVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementTitleVO;

import java.util.List;

/**
* @author lenovo
* @description 针对表【announcement】的数据库操作Mapper
* @createDate 2024-10-06 19:45:34
* @Entity generator.domain.Announcement
*/
public interface AnnouncementMapper extends BaseMapper<Announcement> {

    List<AnnouncementTitleVO> selectAnnouncementTitleList();

    AnnouncementContentVO selectAnnouncementContentVO(Integer id);
}




